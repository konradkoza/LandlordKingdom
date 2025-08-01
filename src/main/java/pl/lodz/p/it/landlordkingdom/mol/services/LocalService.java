package pl.lodz.p.it.landlordkingdom.mol.services;

import pl.lodz.p.it.landlordkingdom.exceptions.ApplicationOptimisticLockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.it.landlordkingdom.exceptions.*;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.model.Address;
import pl.lodz.p.it.landlordkingdom.model.Local;
import pl.lodz.p.it.landlordkingdom.mol.dto.EditLocalRequest;
import pl.lodz.p.it.landlordkingdom.mol.dto.EditLocalRequestAdmin;
import pl.lodz.p.it.landlordkingdom.exceptions.GivenAddressAssignedToOtherLocalException;
import pl.lodz.p.it.landlordkingdom.exceptions.InvalidLocalState;

import java.math.BigDecimal;
import java.util.UUID;

public interface LocalService {

    Local addLocal(Local local, UUID ownerId) throws GivenAddressAssignedToOtherLocalException, NotFoundException, CreationException;

    Page<Local> getActiveLocals(Pageable pageable);

    Page<Local> getActiveLocalsFilter(String city, Double minSize, Double maxSize, Pageable pageable);

    Page<Local> getUnapprovedLocals(Pageable pageable);

    Page<Local> getOwnLocals(UUID id, Pageable pageable, String state);

    Local editLocal(UUID userId, UUID localId, EditLocalRequest editLocalRequest, String tagValue) throws NotFoundException, ApplicationOptimisticLockException;

    Local leaveLocal(UUID userId, UUID localId) throws InvalidLocalState, NotFoundException;

    Local setFixedFee(UUID localId, UUID ownerId, BigDecimal marginFee, BigDecimal rentalFee, String tagValue) throws NotFoundException, ApplicationOptimisticLockException;

    Page<Local> getAllLocals(Pageable pageable, String state, String ownerLogin);

    Local changeLocalAddress(UUID id, Address address, String tagValue) throws GivenAddressAssignedToOtherLocalException, NotFoundException, ApplicationOptimisticLockException, InvalidLocalState;

    Local editLocalByAdmin(UUID localId, EditLocalRequestAdmin editLocalRequest, String tagValue) throws NotFoundException, ApplicationOptimisticLockException;

    Local approveLocal(UUID id) throws NotFoundException, InvalidLocalState;

    Local rejectLocal(UUID id) throws NotFoundException, InvalidLocalState;

    Local archiveLocal(UUID id) throws NotFoundException, InvalidLocalState;

    Local getLocal(UUID id) throws NotFoundException;

    Local getOwnLocal(UUID id, UUID ownerId) throws NotFoundException;

    Local getActiveLocal(UUID id) throws NotFoundException;
}
