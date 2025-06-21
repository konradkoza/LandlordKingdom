package pl.lodz.p.it.landlordkingdom.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.SemanticException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.exceptions.AccessLevelAlreadyAssignedException;
import pl.lodz.p.it.landlordkingdom.exceptions.AccessLevelAlreadyTakenException;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.landlordkingdom.messages.UserExceptionMessages;
import pl.lodz.p.it.landlordkingdom.model.Owner;
import pl.lodz.p.it.landlordkingdom.model.RoleRequest;
import pl.lodz.p.it.landlordkingdom.model.User;
import pl.lodz.p.it.landlordkingdom.mok.repositories.OwnerRepository;
import pl.lodz.p.it.landlordkingdom.mok.repositories.RoleRequestMOKRepository;
import pl.lodz.p.it.landlordkingdom.mok.repositories.UserRepository;
import pl.lodz.p.it.landlordkingdom.mok.services.OwnerService;
import pl.lodz.p.it.landlordkingdom.mok.services.EmailService;
import pl.lodz.p.it.landlordkingdom.mok.services.UserService;

import java.util.Optional;
import java.util.UUID;

@Transactional(rollbackFor = NotFoundException.class, propagation = Propagation.REQUIRES_NEW)
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final EmailService emailService;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final RoleRequestMOKRepository roleRequestRepository;
    private final UserService userService;

    @Override
    @Transactional(rollbackFor = {SemanticException.class, PathElementException.class})
    public Page<Owner> getAllFiltered(Specification<Owner> specification, Pageable pageable) {
        return ownerRepository.findAll(specification, pageable);
    }

    @Override
    public Owner removeOwnerAccessLevel(UUID id) throws NotFoundException, AccessLevelAlreadyTakenException {
        Owner owner = ownerRepository.findByUserId(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));

        if(userService.getUserRoles(owner.getUser().getId()).size() <= 1){
            throw new AccessLevelAlreadyTakenException(UserExceptionMessages.ACCESS_LEVEL_TAKEN, ErrorCodes.ACCESS_LEVEL_TAKEN);
        }

        if (!owner.isActive()){
            throw new AccessLevelAlreadyTakenException(UserExceptionMessages.ACCESS_LEVEL_TAKEN, ErrorCodes.ACCESS_LEVEL_TAKEN);
        }

        owner.setActive(false);
        User user = owner.getUser();

        emailService.sendOwnerPermissionLostEmail(user.getEmail(), user.getFirstName(), user.getLanguage());

        return ownerRepository.saveAndFlush(owner);
    }

    @Override
    public Owner addOwnerAccessLevel(UUID id) throws NotFoundException, AccessLevelAlreadyAssignedException {
        Optional<Owner> ownerOptional = ownerRepository.findByUserId(id);

        Owner owner;
        if (ownerOptional.isPresent()) {
            owner = ownerOptional.get();
        } else {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
            owner = new Owner();
            owner.setUser(user);
        }

        if (owner.isActive()) {
            throw new AccessLevelAlreadyAssignedException(UserExceptionMessages.ACCESS_LEVEL_ASSIGNED, ErrorCodes.ACCESS_LEVEL_ASSIGNED);
        }

        owner.setActive(true);
        User user = owner.getUser();

        emailService.sendOwnerPermissionGainedEmail(user.getEmail(), user.getFirstName(), user.getLanguage());
        Owner savedOwner = ownerRepository.saveAndFlush(owner);

        Optional<RoleRequest> roleRequest = roleRequestRepository.findByUserId(savedOwner.getUser().getId());

        roleRequest.ifPresent(roleRequestRepository::delete);

        return savedOwner;
    }
}
