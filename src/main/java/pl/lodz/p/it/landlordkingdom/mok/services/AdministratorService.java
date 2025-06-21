package pl.lodz.p.it.landlordkingdom.mok.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.landlordkingdom.exceptions.AccessLevelAlreadyAssignedException;
import pl.lodz.p.it.landlordkingdom.exceptions.AccessLevelAlreadyTakenException;
import pl.lodz.p.it.landlordkingdom.exceptions.AdministratorOwnRoleRemovalException;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.model.Administrator;

import java.util.UUID;

public interface AdministratorService {

    Page<Administrator> getAllFiltered(Specification<Administrator> specification, Pageable pageable);

    Administrator addAdministratorAccessLevel(UUID id) throws NotFoundException, AccessLevelAlreadyAssignedException;

    Administrator removeAdministratorAccessLevel(UUID id, UUID administratorId) throws NotFoundException, AdministratorOwnRoleRemovalException, AccessLevelAlreadyTakenException;
}
