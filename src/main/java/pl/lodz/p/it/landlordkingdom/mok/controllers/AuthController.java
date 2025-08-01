package pl.lodz.p.it.landlordkingdom.mok.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.lodz.p.it.landlordkingdom.exceptions.*;
import pl.lodz.p.it.landlordkingdom.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.landlordkingdom.messages.VerificationTokenMessages;
import pl.lodz.p.it.landlordkingdom.model.User;
import pl.lodz.p.it.landlordkingdom.mok.dto.*;
import pl.lodz.p.it.landlordkingdom.mok.dto.oauth.GoogleOAuth2TokenResponse;
import pl.lodz.p.it.landlordkingdom.mok.dto.oauth.GoogleOAuth2TokenPayload;
import pl.lodz.p.it.landlordkingdom.mok.services.AuthenticationService;
import pl.lodz.p.it.landlordkingdom.mok.services.UserService;
import pl.lodz.p.it.landlordkingdom.mok.services.impl.JwtService;

import java.security.InvalidKeyException;
import java.util.Base64;
import java.util.Map;

@Slf4j
@RestController
@Scope("prototype")
@RequestMapping("/auth")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@Transactional(propagation = Propagation.NEVER)
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final HttpServletRequest servletRequest;
    private final JwtService jwtService;

    @Value("${oauth2.auth.url}")
    private String oAuth2Url;

    @Value("${oauth2.client.id}")
    private String oAuthClientId;

    @Value("${oauth2.client.secret}")
    private String oAuthClientSecret;

    @Value("${oauth2.redirect.url}")
    private String oAuthRedirectUri;

    @Value("${oauth2.token.url}")
    private String oAuthTokenUri;

    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserCreateRequest newUserData) {
        try {
            User newUser = new User(
                    newUserData.firstName(),
                    newUserData.lastName(),
                    newUserData.email(),
                    newUserData.login(),
                    newUserData.language()
            );
            userService.createUser(newUser, new PasswordHolder(newUserData.password()));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED, e);
        } catch (IdenticalFieldValueException | CreationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request, HttpServletRequest httpServletRequest) {
        try {
            Map<String, String> authResponse = authenticationService.authenticate(request.login(), new PasswordHolder(request.password()), request.language(), httpServletRequest.getRemoteAddr());
            return ResponseEntity.ok(new AuthenticationResponse(authResponse.get("token"), authResponse.get("refreshToken"), authResponse.get("theme")));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (UserNotVerifiedException | SignInBlockedException | UserBlockedException | UserInactiveException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        } catch (InvalidLoginDataException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was an error generating token", e);
        }
    }




    @GetMapping("/oauth2/url")
    public ResponseEntity<OAuth2UrlResponse> getOAuth2Url() {
        String url = UriComponentsBuilder
                .fromUriString(oAuth2Url)
                .queryParam("client_id", oAuthClientId)
                .queryParam("redirect_uri", oAuthRedirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "openid profile email")
                .queryParam("access_type", "offline")
                .queryParam("state", "standard_oauth")
                .queryParam("prompt", "consent")
                .build().toUriString();
        return ResponseEntity.ok(new OAuth2UrlResponse(url));
    }


    @GetMapping("/oauth2/token")
    public ResponseEntity<AuthenticationResponse> getOAuth2Token(@RequestParam String code) throws JsonProcessingException {
        String url = UriComponentsBuilder
                .fromUriString(oAuthTokenUri)
                .queryParam("client_id", oAuthClientId)
                .queryParam("client_secret", oAuthClientSecret)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .queryParam("redirect_uri", oAuthRedirectUri)
                .build().toUriString();
        RestClient restClient = RestClient.create();
        GoogleOAuth2TokenResponse result = restClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(GoogleOAuth2TokenResponse.class);

        String token = result.getIdToken();
        String[] tokenChunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String tokenPayload = new String(decoder.decode(tokenChunks[1]));
        ObjectMapper mapper = new ObjectMapper();
        GoogleOAuth2TokenPayload payload = mapper.readValue(tokenPayload, GoogleOAuth2TokenPayload.class);

        try {
            Map<String, String> response = authenticationService.singInOAuth(token, servletRequest.getHeader("X-Forwarded-For"), payload);
            if(response.containsKey("created")) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.ok(new AuthenticationResponse(response.get("token"), response.get("refreshToken"), response.get("theme")));
            }
        } catch (UserNotVerifiedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED, e);
        } catch (CreationException | IdenticalFieldValueException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }

    @PostMapping("/refresh")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        try {
            Map<String, String> authResponse = authenticationService.refresh(refreshToken.refreshToken());
            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .token(authResponse.get("token"))
                    .refreshToken(authResponse.get("refreshToken"))
                    .build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RefreshTokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}