package pl.lodz.p.it.landlordkingdom.mok.services.impl;

import com.eatthepath.otp.HmacOneTimePasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.exceptions.TokenGenerationException;
import pl.lodz.p.it.landlordkingdom.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.landlordkingdom.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.landlordkingdom.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.landlordkingdom.messages.VerificationTokenMessages;
import pl.lodz.p.it.landlordkingdom.model.*;
import pl.lodz.p.it.landlordkingdom.model.tokens.*;
import pl.lodz.p.it.landlordkingdom.mok.authRepositories.AccountActivateTokenRepository;
import pl.lodz.p.it.landlordkingdom.mok.repositories.AccountVerificationTokenRepository;
import pl.lodz.p.it.landlordkingdom.mok.repositories.EmailVerificationTokenRepository;
import pl.lodz.p.it.landlordkingdom.mok.authRepositories.OTPTokenRepository;
import pl.lodz.p.it.landlordkingdom.mok.repositories.PasswordVerificationTokenRepository;
import pl.lodz.p.it.landlordkingdom.mok.services.VerificationTokenService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
@Transactional(propagation = Propagation.MANDATORY, rollbackFor = TokenGenerationException.class)
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final AccountVerificationTokenRepository accountTokenRepository;
    private final EmailVerificationTokenRepository emailTokenRepository;
    private final PasswordVerificationTokenRepository passwordTokenRepository;
    private final AccountActivateTokenRepository accountActivateTokenRepository;
    private final OTPTokenRepository otpTokenRepository;
    private final HmacOneTimePasswordGenerator hotp = new HmacOneTimePasswordGenerator(8);
    private long counter = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) / 30;

    @Value("${otp.secret}")
    private String otpSecret;

    @Override
    public User getUserByEmailToken(String token) throws VerificationTokenUsedException {
        return emailTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED)).getUser();
    }

    public User getUserByPasswordToken(String token) throws VerificationTokenUsedException {
        return passwordTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED)).getUser();
    }

    @Override
    public String generateAccountVerificationToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        accountTokenRepository.deleteByUserId(user.getId());
        accountTokenRepository.flush();
        AccountVerificationToken token = new AccountVerificationToken(tokenVal, Instant.now().plus(AccountVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return accountTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validateAccountVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        VerificationToken verificationToken = accountTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED, ErrorCodes.VERIFICATION_TOKEN_EXPIRED);
        }
        accountTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    public String generateEmailVerificationToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        emailTokenRepository.deleteEmailVerificationTokenByUserId(user.getId());
        emailTokenRepository.flush();
        EmailVerificationToken token = new EmailVerificationToken(tokenVal, Instant.now().plus(EmailVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return emailTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validateEmailVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        EmailVerificationToken verificationToken = emailTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED, ErrorCodes.VERIFICATION_TOKEN_EXPIRED);
        }
        emailTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    public String generatePasswordVerificationToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        passwordTokenRepository.deleteByUserId(user.getId());
        passwordTokenRepository.flush();
        PasswordVerificationToken token = new PasswordVerificationToken(tokenVal, Instant.now().plus(PasswordVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return passwordTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validatePasswordVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        PasswordVerificationToken verificationToken = passwordTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED, ErrorCodes.VERIFICATION_TOKEN_EXPIRED);
        }
        passwordTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    public String generateAccountActivateToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        accountActivateTokenRepository.deleteByUserId(user.getId());
        accountActivateTokenRepository.flush();
        AccountActivateToken token = new AccountActivateToken(tokenVal, Instant.now().plus(AccountActivateToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return accountActivateTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validateAccountActivateToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        AccountActivateToken verificationToken = accountActivateTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED, ErrorCodes.VERIFICATION_TOKEN_EXPIRED);
        }
        passwordTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    public String generateOTPToken(User user) throws InvalidKeyException {
        byte[] bytes = otpSecret.getBytes();
        SecretKey key = new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA1");
        String tokenVal = hotp.generateOneTimePasswordString(key, counter++);
        otpTokenRepository.deleteByUserId(user.getId());
        otpTokenRepository.flush();
        OTPToken token = new OTPToken(tokenVal, Instant.now().plus(OTPToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return otpTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public VerificationToken validateOTPToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        OTPToken verificationToken = otpTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED, ErrorCodes.VERIFICATION_TOKEN_EXPIRED);
        }
        otpTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    private String generateSafeToken() throws TokenGenerationException {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            return random.ints(32, 0, chars.length())
                    .mapToObj(chars::charAt)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString();
        } catch (NoSuchAlgorithmException e) {
            throw new TokenGenerationException(e.getMessage(), e, ErrorCodes.INTERNAL_SERVER_ERROR);
        }

    }
}
