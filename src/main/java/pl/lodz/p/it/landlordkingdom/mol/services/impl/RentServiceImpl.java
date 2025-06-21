package pl.lodz.p.it.landlordkingdom.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.exceptions.RentAlreadyEndedException;
import pl.lodz.p.it.landlordkingdom.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.landlordkingdom.messages.RentExceptionMessages;
import pl.lodz.p.it.landlordkingdom.messages.RentMessages;
import pl.lodz.p.it.landlordkingdom.model.Rent;
import pl.lodz.p.it.landlordkingdom.exceptions.WrongEndDateException;
import pl.lodz.p.it.landlordkingdom.model.Tenant;
import pl.lodz.p.it.landlordkingdom.mol.repositories.RentRepository;
import pl.lodz.p.it.landlordkingdom.mol.repositories.TenantMolRepository;
import pl.lodz.p.it.landlordkingdom.mol.services.RentService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RentServiceImpl implements RentService {
    private final RentRepository rentRepository;
    private final TenantMolRepository tenantRepository;

    @Override
    public Rent getTenantRent(UUID rentId, UUID userId) throws NotFoundException {
        Tenant tenant = tenantRepository.findByUserId(userId).get();
        return rentRepository.findByIdAndTenantId(rentId, tenant.getId())
                .orElseThrow(() -> new NotFoundException(RentMessages.RENT_NOT_FOUND, ErrorCodes.RENT_NOT_FOUND));
    }

    @Override
    public Page<Rent> getCurrentOwnerRents(UUID userId, Pageable pageable) {
        return rentRepository.findCurrentRentsByOwnerId(userId, pageable);
    }

    @Override
    public Rent editEndDate(UUID rentId, UUID userId, LocalDate newEndDate) throws WrongEndDateException, NotFoundException, RentAlreadyEndedException {
        Rent rent = rentRepository.findByOwner_User_IdAndId(userId, rentId).orElseThrow(() -> new NotFoundException(RentExceptionMessages.RENT_NOT_FOUND, ErrorCodes.RENT_NOT_FOUND));

        if (newEndDate.isBefore(LocalDate.now())
                || !newEndDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || newEndDate.equals(rent.getEndDate())) {
            throw new WrongEndDateException(RentExceptionMessages.WRONG_END_DATE, ErrorCodes.WRONG_END_DATE);
        }
        if (rent.getEndDate().isBefore(LocalDate.now())) {
            throw new RentAlreadyEndedException(RentExceptionMessages.RENT_ENDED, ErrorCodes.RENT_ENDED);
        }
        rent.setEndDate(newEndDate);
        return rentRepository.saveAndFlush(rent);
    }

    @Override
    public List<Rent> getCurrentTenantRents(UUID userId) {
        return rentRepository.findAllCurrentRentsByTenantUserId(userId);
    }

    @Override
    public List<Rent> getArchivalRentsForTenant(UUID userId) {
        return rentRepository.findAllPastRentsByTenantUserId(userId);
    }

    @Override
    public Rent getOwnerRent(UUID userId, UUID rentId) throws NotFoundException {
        return rentRepository.findByOwner_User_IdAndId(userId, rentId).orElseThrow(() -> new NotFoundException(RentExceptionMessages.RENT_NOT_FOUND, ErrorCodes.RENT_NOT_FOUND));
    }

    @Override
    public Page<Rent> getArchivalOwnerRents(UUID userId, Pageable pageable) {
        return rentRepository.findArchivalRentsByOwnerUserId(userId, pageable);
    }

}
