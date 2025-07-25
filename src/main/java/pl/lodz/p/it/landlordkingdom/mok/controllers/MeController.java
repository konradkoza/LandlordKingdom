package pl.lodz.p.it.landlordkingdom.mok.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.landlordkingdom.exceptions.*;
import pl.lodz.p.it.landlordkingdom.model.Timezone;
import pl.lodz.p.it.landlordkingdom.model.User;
import pl.lodz.p.it.landlordkingdom.mok.dto.*;
import pl.lodz.p.it.landlordkingdom.mok.mappers.UserMapper;
import pl.lodz.p.it.landlordkingdom.mok.services.TimezoneService;
import pl.lodz.p.it.landlordkingdom.mok.services.UserService;
import pl.lodz.p.it.landlordkingdom.util.Signer;

import java.util.List;
import java.util.UUID;

@RestController
@Scope("prototype")
@RequestMapping("/me")
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.NEVER)
public class MeController {
    private final HttpServletRequest servletRequest;
    private final UserService userService;
    private final Signer signer;
    private final TimezoneService timezoneService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DetailedUserResponse> getUserData() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            UUID id = UUID.fromString(jwt.getSubject());
            User user = userService.getUserById(id);
            List<String> roles = userService.getUserRoles(id);

            return ResponseEntity
                    .ok()
                    .eTag(signer.generateSignature(user.getId(), user.getVersion()))
                    .body(UserMapper.toDetailedUserResponse(user, roles));

        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> updateUserData(
            @Valid @RequestBody UpdateUserDataRequest request,
            @RequestHeader(HttpHeaders.IF_MATCH) String tagValue) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            UUID id = UUID.fromString(jwt.getSubject());

            Timezone timezone = timezoneService.findByTimezoneName(request.timezone());

            User user = userService.updateUserData(id, UserMapper.toUser(request, timezone), tagValue);
            return ResponseEntity.ok(UserMapper.toUserResponse(user));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (ApplicationOptimisticLockException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
        }
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid AuthenticatedChangePasswordRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            UUID id = UUID.fromString(jwt.getSubject());

            userService.changePassword(id, new PasswordHolder(request.oldPassword()), new PasswordHolder(request.newPassword()));

            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (PasswordRepetitionException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PostMapping("/change-password-with-token")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> changePasswordWithToken(@RequestBody @Valid ChangePasswordRequest request) {
        try {
            userService.changePasswordWithToken(new PasswordHolder(request.password()), request.token());
            return ResponseEntity.ok().build();
        } catch (VerificationTokenUsedException | VerificationTokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (PasswordRepetitionException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }

    @PostMapping("/email-update-request")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> sendUpdateEmail(@RequestBody @Valid StartUpdateEmailRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID id = UUID.fromString(jwt.getSubject());
        try {
            userService.sendEmailUpdateVerificationEmail(id, request.email());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (IdenticalFieldValueException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/update-email")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> updateUserEmail(@RequestBody @Valid UserEmailUpdateRequest request) {
        try {
            userService.changeUserEmail(request.token(), new PasswordHolder(request.password()));
        } catch (NotFoundException | VerificationTokenUsedException | VerificationTokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IdenticalFieldValueException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/verify")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> verify(@RequestBody @Valid VerifyUserRequest request) throws NotFoundException {
        try {
            userService.verify(request.token());
            return ResponseEntity.ok().build();
        } catch (VerificationTokenUsedException | VerificationTokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/theme")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ChangeThemeResponse> changeTheme(@RequestBody @Valid ChangeThemeRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            UUID id = UUID.fromString(jwt.getSubject());
            return ResponseEntity.ok(new ChangeThemeResponse(userService.changeTheme(id, request.theme())));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/role-view-changed")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logRoleViewChange(@RequestBody RoleViewChangeInformation roleViewChangeInformation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID id = UUID.fromString(jwt.getSubject());
        String login = jwt.getClaim("login").toString();
        log.info("User: {} - {} changed view to role: {}, from address IP: {}",
                login, id, roleViewChangeInformation.role(), servletRequest.getHeader("X-Forwarded-For"));
        return ResponseEntity.ok().build();
    }
}
