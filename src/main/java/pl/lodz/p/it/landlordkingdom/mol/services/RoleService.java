package pl.lodz.p.it.landlordkingdom.mol.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.model.RoleRequest;
import pl.lodz.p.it.landlordkingdom.exceptions.RoleRequestAlreadyExistsException;
import pl.lodz.p.it.landlordkingdom.exceptions.UserAlreadyHasRoleException;

import java.util.UUID;

public interface RoleService {

    Page<RoleRequest> getAll(Pageable pageable);

    RoleRequest get() throws NotFoundException;

    RoleRequest requestRole() throws RoleRequestAlreadyExistsException, UserAlreadyHasRoleException, NotFoundException;

    void accept(UUID id) throws NotFoundException;

    void reject(UUID id) throws NotFoundException;
}
