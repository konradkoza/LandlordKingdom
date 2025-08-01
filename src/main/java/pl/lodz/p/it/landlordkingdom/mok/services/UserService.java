package pl.lodz.p.it.landlordkingdom.mok.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.landlordkingdom.exceptions.InvalidPasswordException;
import pl.lodz.p.it.landlordkingdom.exceptions.IdenticalFieldValueException;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.exceptions.TokenGenerationException;
import pl.lodz.p.it.landlordkingdom.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.landlordkingdom.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.landlordkingdom.exceptions.*;
import pl.lodz.p.it.landlordkingdom.model.User;
import pl.lodz.p.it.landlordkingdom.mok.dto.PasswordHolder;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAll();

    Page<User> getAllFiltered(Specification<User> specification, Pageable pageable);

    List<User> getAllFiltered(Specification<User> specification);

    User getUserById(UUID id) throws NotFoundException;

    User getUserByGoogleId(String googleId) throws NotFoundException;

    User createUser(User newUser, PasswordHolder password) throws IdenticalFieldValueException, TokenGenerationException, CreationException;

    User createUser(User newUser) throws IdenticalFieldValueException, TokenGenerationException, CreationException;

    void verify(String token) throws VerificationTokenUsedException, VerificationTokenExpiredException, NotFoundException;

    User updateUserData(UUID id, User user, String tagValue) throws NotFoundException, ApplicationOptimisticLockException;

    void blockUser(UUID id, UUID administratorId) throws NotFoundException, UserAlreadyBlockedException, AdministratorOwnBlockException;

    void unblockUser(UUID id) throws NotFoundException, UserAlreadyUnblockedException;

    void sendEmailUpdateVerificationEmail(UUID id, String tempEmail) throws NotFoundException, TokenGenerationException, IdenticalFieldValueException;

    void changeUserEmail(String token, PasswordHolder password) throws NotFoundException, VerificationTokenUsedException, VerificationTokenExpiredException, InvalidPasswordException, IdenticalFieldValueException;

    void sendChangePasswordEmail(String login) throws NotFoundException, TokenGenerationException, UserBlockedException, UserNotVerifiedException;

    void changePassword(UUID id, PasswordHolder oldPassword, PasswordHolder newPassword) throws NotFoundException, InvalidPasswordException, PasswordRepetitionException;

    void changePasswordWithToken(PasswordHolder password, String token) throws VerificationTokenUsedException, VerificationTokenExpiredException, UserBlockedException, PasswordRepetitionException;

    List<String> getUserRoles(UUID id);

    String changeTheme(UUID id, String theme) throws NotFoundException;

    void reactivateUser(String token) throws  VerificationTokenUsedException, VerificationTokenExpiredException;
}
