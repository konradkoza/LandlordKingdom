package pl.lodz.p.it.landlordkingdom.mok.services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import pl.lodz.p.it.landlordkingdom.exceptions.*;
import pl.lodz.p.it.landlordkingdom.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.landlordkingdom.mok.dto.PasswordHolder;
import pl.lodz.p.it.landlordkingdom.mok.dto.oauth.GoogleOAuth2TokenPayload;

import java.security.InvalidKeyException;
import java.util.Map;

public interface AuthenticationService {

    Map<String, String> refresh(String refreshToken) throws NotFoundException, RefreshTokenExpiredException;

    Map<String, String> singInOAuth(String token, String ip, GoogleOAuth2TokenPayload payload) throws UserNotVerifiedException, TokenGenerationException, CreationException, IdenticalFieldValueException;

    Map<String, String> authenticate(String login, PasswordHolder passwordHolder, String language, String remoteAddr) throws InvalidLoginDataException, UserInactiveException, TokenGenerationException, SignInBlockedException, UserBlockedException, UserNotVerifiedException, NotFoundException;
}
