package pl.lodz.p.it.landlordkingdom.mok.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.exceptions.*;
import pl.lodz.p.it.landlordkingdom.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.landlordkingdom.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.landlordkingdom.messages.UserExceptionMessages;
import pl.lodz.p.it.landlordkingdom.model.User;
import pl.lodz.p.it.landlordkingdom.model.tokens.VerificationToken;
import pl.lodz.p.it.landlordkingdom.mok.authRepositories.AuthAdministratorRepository;
import pl.lodz.p.it.landlordkingdom.mok.authRepositories.AuthOwnerRepository;
import pl.lodz.p.it.landlordkingdom.mok.authRepositories.AuthTenantRepository;
import pl.lodz.p.it.landlordkingdom.mok.authRepositories.AuthUserRepository;
import pl.lodz.p.it.landlordkingdom.mok.dto.PasswordHolder;
import pl.lodz.p.it.landlordkingdom.mok.dto.oauth.GoogleOAuth2TokenPayload;
import pl.lodz.p.it.landlordkingdom.mok.services.AuthenticationService;
import pl.lodz.p.it.landlordkingdom.mok.services.UserService;
import pl.lodz.p.it.landlordkingdom.mok.services.VerificationTokenService;
import pl.lodz.p.it.landlordkingdom.mok.services.EmailService;
import pl.lodz.p.it.landlordkingdom.util.DateUtils;

import java.security.InvalidKeyException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthUserRepository userRepository;
    private final AuthTenantRepository tenantRepository;
    private final AuthOwnerRepository ownerRepository;
    private final AuthAdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final UserService userService;

    @Value("${login.maxAttempts:3}")
    private int maxLoginAttempts;

    @Value("${login.timeOut:86400}")
    private int loginTimeOut;

    @Transactional(propagation = Propagation.MANDATORY)
    @PreAuthorize("permitAll()")
    public List<String> getUserRoles(User user) {
        List<String> roles = new ArrayList<>();

        ownerRepository.findByUserIdAndActive(user.getId(), true).ifPresent(owner -> roles.add("OWNER"));
        tenantRepository.findByUserIdAndActive(user.getId(), true).ifPresent(tenant -> roles.add("TENANT"));
        administratorRepository.findByUserIdAndActive(user.getId(), true).ifPresent(admin -> roles.add("ADMINISTRATOR"));

        return roles;
    }

    @Override
    @PreAuthorize("permitAll()")
    public Map<String, String> singInOAuth(String token, String ip, GoogleOAuth2TokenPayload payload) throws UserNotVerifiedException, TokenGenerationException, CreationException, IdenticalFieldValueException {
        try {
            User user = userService.getUserByGoogleId(payload.getSub());

            if (!user.isVerified()) {
                throw new UserNotVerifiedException(UserExceptionMessages.NOT_VERIFIED, ErrorCodes.USER_NOT_VERIFIED);
            }

            List<String> roles = userService.getUserRoles(user.getId());
            String userToken = jwtService.generateToken(user.getId(), user.getLogin(), roles);
            String refreshToken = jwtService.generateRefreshToken(user.getId());
            String theme = user.getTheme() != null ?
                    user.getTheme().getType().toLowerCase() : "light";


            return Map.of(
                    "token", userToken,
                    "refreshToken", refreshToken,
                    "theme", theme);

        } catch (NotFoundException e) {
            User newUser = new User(
                    payload.getGivenName(),
                    payload.getFamilyName(),
                    payload.getEmail(),
                    payload.getEmail(),
                    "en"
            );
            newUser.setGoogleId(payload.getSub());
            userService.createUser(newUser);
            return Map.of(
                    "created", "true"
            );
        }
    }

    @Override
    @PreAuthorize("permitAll()")
    public Map<String, String> authenticate(String login, PasswordHolder passwordHolder, String language, String ip) throws InvalidLoginDataException, UserInactiveException, TokenGenerationException, SignInBlockedException, UserBlockedException, UserNotVerifiedException, NotFoundException {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));

        if (!user.isVerified()) {
            throw new UserNotVerifiedException(UserExceptionMessages.NOT_VERIFIED, ErrorCodes.USER_NOT_VERIFIED);
        }

        if (user.isBlocked()) {
            throw new UserBlockedException(UserExceptionMessages.BLOCKED, ErrorCodes.USER_BLOCKED);
        }

        if (user.getLoginAttempts() >= maxLoginAttempts && Duration.between(user.getLastFailedLogin(), LocalDateTime.now()).toSeconds() <= loginTimeOut) {
            throw new SignInBlockedException(UserExceptionMessages.SIGN_IN_BLOCKED, ErrorCodes.SIGN_IN_BLOCKED);
        } else if (user.getLoginAttempts() >= maxLoginAttempts) {
            user.setLoginAttempts(0);
        }

        if (passwordEncoder.matches(passwordHolder.password(), user.getPassword())) {
            if (!user.isActive()) {
                String token = verificationTokenService.generateAccountActivateToken(user);
                emailService.sendAccountActivateAfterBlock(user.getEmail(), user.getFirstName(), token, language);
                throw new UserInactiveException(UserExceptionMessages.INACTIVE, ErrorCodes.USER_INACTIVE);
            }

            user.setLastSuccessfulLogin(LocalDateTime.now());
            user.setLoginAttempts(0);
            user.setLastSuccessfulLoginIp(ip);

            List<String> roles = getUserRoles(user);

            if (roles.contains("ADMINISTRATOR")) {
                emailService.sendAdminLoginEmail(user.getEmail(), user.getFirstName(), ip, user.getLanguage());
            }

            String jwt = jwtService.generateToken(user.getId(), user.getLogin(), roles);
            String refreshToken = jwtService.generateRefreshToken(user.getId());
            String theme = user.getTheme() != null ?
                    user.getTheme().getType().toLowerCase() : "light";

            log.info("Session started for user: {} - {}, from address IP: {}", login, user.getId(), ip);
            return Map.of(
                    "token", jwt,
                    "refreshToken", refreshToken,
                    "theme", theme);
        } else {
            handleFailedLogin(user, ip);
            throw new InvalidLoginDataException(UserExceptionMessages.INVALID_LOGIN_DATA, ErrorCodes.INVALID_LOGIN_DATA);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @PreAuthorize("permitAll()")
    protected void handleFailedLogin(User user, String ip) {
        user.setLastFailedLogin(LocalDateTime.now());
        user.setLastFailedLoginIp(ip);
        user.setLoginAttempts(user.getLoginAttempts() + 1);
        userRepository.saveAndFlush(user);

        if (user.getLoginAttempts() >= maxLoginAttempts) {
            String timezone = "UTC";
            if (user.getTimezone() != null) {
                timezone = user.getTimezone().getName();
            }
            String unblockDate = DateUtils.convertUTCToAnotherTimezoneSimple(LocalDateTime.now().plusSeconds(loginTimeOut), timezone, user.getLanguage());
            String lastFailedLogin = DateUtils.convertUTCToAnotherTimezoneSimple(user.getLastFailedLogin(), timezone, user.getLanguage());
            emailService.sendLoginBlockEmail(user.getEmail(), user.getLoginAttempts(), lastFailedLogin, unblockDate, user.getLastFailedLoginIp(), user.getLanguage());
        }
    }

    @Override
    @PreAuthorize("permitAll()")
    public Map<String, String> refresh(String refreshToken) throws NotFoundException, RefreshTokenExpiredException {
        Jwt token = jwtService.decodeRefreshToken(refreshToken);

        UUID userId = UUID.fromString(token.getSubject());
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));

        if (jwtService.validateRefreshExpiration(token)) {
            return Map.of(
                    "token", jwtService.generateToken(userId, user.getLogin(), getUserRoles(user)),
                    "refreshToken", refreshToken);
        }

        throw new RefreshTokenExpiredException(UserExceptionMessages.REFRESH_TOKEN_EXPIRED, ErrorCodes.INVALID_REFRESH_TOKEN);
    }
}
