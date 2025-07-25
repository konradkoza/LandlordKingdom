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
import pl.lodz.p.it.landlordkingdom.model.Tenant;
import pl.lodz.p.it.landlordkingdom.model.User;
import pl.lodz.p.it.landlordkingdom.mok.repositories.TenantRepository;
import pl.lodz.p.it.landlordkingdom.mok.repositories.UserRepository;
import pl.lodz.p.it.landlordkingdom.mok.services.TenantService;
import pl.lodz.p.it.landlordkingdom.mok.services.EmailService;
import pl.lodz.p.it.landlordkingdom.mok.services.UserService;

import java.util.Optional;
import java.util.UUID;

@Transactional(rollbackFor = NotFoundException.class, propagation = Propagation.REQUIRES_NEW)
@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final EmailService emailService;
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    @Transactional(rollbackFor = {SemanticException.class, PathElementException.class})
    public Page<Tenant> getAllFiltered(Specification<Tenant> specification, Pageable pageable) {
        return tenantRepository.findAll(specification, pageable);
    }

    @Override
    public Tenant removeTenantAccessLevel(UUID id) throws NotFoundException, AccessLevelAlreadyTakenException {
        Tenant tenant = tenantRepository.findByUserId(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));

        if(userService.getUserRoles(tenant.getUser().getId()).size() <= 1){
            throw new AccessLevelAlreadyTakenException(UserExceptionMessages.ACCESS_LEVEL_TAKEN, ErrorCodes.ACCESS_LEVEL_TAKEN);
        }

        if(!tenant.isActive()){
            throw new AccessLevelAlreadyTakenException(UserExceptionMessages.ACCESS_LEVEL_TAKEN, ErrorCodes.ACCESS_LEVEL_TAKEN);
        }
        tenant.setActive(false);
        User user = tenant.getUser();

        emailService.sendTenantPermissionLostEmail(user.getEmail(), user.getFirstName(), user.getLanguage());

        return tenantRepository.saveAndFlush(tenant);
    }

    @Override
    public Tenant addTenantAccessLevel(UUID id) throws NotFoundException, AccessLevelAlreadyAssignedException {
        Optional<Tenant> tenantOptional = tenantRepository.findByUserId(id);

        Tenant tenant;
        if (tenantOptional.isPresent()) {
            tenant = tenantOptional.get();
        } else {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
            tenant = new Tenant();
            tenant.setUser(user);
        }

        if (tenant.isActive()) {
            throw new AccessLevelAlreadyAssignedException(UserExceptionMessages.ACCESS_LEVEL_ASSIGNED, ErrorCodes.ACCESS_LEVEL_ASSIGNED);
        }

        tenant.setActive(true);
        User user = tenant.getUser();

        emailService.sendTenantPermissionGainedEmail(user.getEmail(), user.getFirstName(), user.getLanguage());

        return tenantRepository.saveAndFlush(tenant);
    }
}
