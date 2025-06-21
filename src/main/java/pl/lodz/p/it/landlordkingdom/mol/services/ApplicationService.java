package pl.lodz.p.it.landlordkingdom.mol.services;

import pl.lodz.p.it.landlordkingdom.exceptions.*;
import pl.lodz.p.it.landlordkingdom.model.Application;
import pl.lodz.p.it.landlordkingdom.model.Rent;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    List<Application> getLocalApplications(UUID localId, UUID ownerId);

    List<Application> getUserApplications(UUID id) throws NotFoundException;

    Application getUserApplication(UUID userId, UUID localId) throws NotFoundException;

    Rent acceptApplication(UUID applicationId, UUID ownerUserId, LocalDate endDate) throws NotFoundException, InvalidLocalState, WrongEndDateException;

    void rejectApplication(UUID applicationId, UUID ownerUserId) throws NotFoundException;

    Application createApplication(UUID localId, UUID userId) throws NotFoundException, InvalidLocalState, CreationException;

    void removeApplication(UUID localId, UUID userId) throws NotFoundException;
}
