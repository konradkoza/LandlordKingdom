package pl.lodz.p.it.landlordkingdom.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.exceptions.VariableFeeAlreadyExistsException;
import pl.lodz.p.it.landlordkingdom.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.landlordkingdom.messages.RentExceptionMessages;
import pl.lodz.p.it.landlordkingdom.model.Administrator;
import pl.lodz.p.it.landlordkingdom.model.Rent;
import pl.lodz.p.it.landlordkingdom.model.Tenant;
import pl.lodz.p.it.landlordkingdom.model.VariableFee;
import pl.lodz.p.it.landlordkingdom.mol.repositories.AdministratorMolRepository;
import pl.lodz.p.it.landlordkingdom.mol.repositories.RentRepository;
import pl.lodz.p.it.landlordkingdom.mol.repositories.TenantMolRepository;
import pl.lodz.p.it.landlordkingdom.mol.repositories.VariableFeeRepository;
import pl.lodz.p.it.landlordkingdom.mol.services.VariableFeeService;
import pl.lodz.p.it.landlordkingdom.util.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class VariableFeeServiceImpl implements VariableFeeService {
    private final VariableFeeRepository variableFeeRepository;
    private final TenantMolRepository tenantRepository;
    private final RentRepository rentRepository;
    private final AdministratorMolRepository administratorMolRepository;

    @Override
    public VariableFee create(UUID userId, UUID rentId, BigDecimal amount)
            throws NotFoundException, VariableFeeAlreadyExistsException {
        Tenant tenant = tenantRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(RentExceptionMessages.RENT_NOT_FOUND, ErrorCodes.RENT_NOT_FOUND));
        Rent rent = rentRepository.findByIdAndTenantId(rentId, tenant.getId())
                .orElseThrow(() -> new NotFoundException(RentExceptionMessages.RENT_NOT_FOUND, ErrorCodes.RENT_NOT_FOUND));

        Optional<VariableFee> existingVariableFee = variableFeeRepository
                .findByRentIdBetween(rentId, userId, DateUtils.getFirstDayOfCurrentMonth(), DateUtils.getLastDayOfCurrentMonth());

        if (existingVariableFee.isPresent()) {
            throw new VariableFeeAlreadyExistsException(
                    RentExceptionMessages.VARIABLE_FEE_ALREADY_EXISTS,
                    ErrorCodes.VARIABLE_FEE_ALREADY_EXISTS);
        }

        VariableFee variableFee = new VariableFee(amount, LocalDate.now(), rent);
        rent.setBalance(rent.getBalance().subtract(amount));
        rentRepository.saveAndFlush(rent);
        return variableFeeRepository.saveAndFlush(variableFee);
    }

    @Override
    public Page<VariableFee> getRentVariableFees(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Optional<Administrator> administrator = administratorMolRepository.findByUserIdAndActiveIsTrue(userId);

        if (administrator.isPresent()) {
            return variableFeeRepository.findRentVariableFeesBetween(rentId, startDate, endDate, pageable);
        }

        return variableFeeRepository.findRentVariableFeesBetween(rentId, userId, startDate, endDate, pageable);
    }
}
