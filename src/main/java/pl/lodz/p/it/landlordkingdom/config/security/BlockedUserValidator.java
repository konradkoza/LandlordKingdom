package pl.lodz.p.it.landlordkingdom.config.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.mok.services.UserService;

import java.util.UUID;

public class BlockedUserValidator implements OAuth2TokenValidator<Jwt> {


    UserService userService;


    public BlockedUserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        try {
            if (!userService.getUserById(UUID.fromString(token.getSubject())).isBlocked()){
                return OAuth2TokenValidatorResult.success();
            } else {
                return OAuth2TokenValidatorResult.failure(new OAuth2Error("user_blocked", "User with id is blocked", null));
            }
        } catch (NotFoundException e) {
            return OAuth2TokenValidatorResult.failure(new OAuth2Error("user_not_exists", e.getMessage(), null));
        }
    }
}
