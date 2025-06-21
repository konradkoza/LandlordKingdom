package pl.lodz.p.it.landlordkingdom.mok.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.landlordkingdom.exceptions.AccessLevelAlreadyAssignedException;
import pl.lodz.p.it.landlordkingdom.exceptions.AccessLevelAlreadyTakenException;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.model.Owner;

import java.util.UUID;

public interface OwnerService {

    Page<Owner> getAllFiltered(Specification<Owner> specification, Pageable pageable);

    Owner addOwnerAccessLevel(UUID id) throws NotFoundException, AccessLevelAlreadyAssignedException;

    Owner removeOwnerAccessLevel(UUID id) throws NotFoundException, AccessLevelAlreadyTakenException;
}
