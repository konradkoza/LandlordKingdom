package pl.lodz.p.it.landlordkingdom.mol.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.Administrator;
import pl.lodz.p.it.landlordkingdom.model.FixedFee;
import pl.lodz.p.it.landlordkingdom.model.Local;
import pl.lodz.p.it.landlordkingdom.model.Rent;
import pl.lodz.p.it.landlordkingdom.mol.repositories.AdministratorMolRepository;
import pl.lodz.p.it.landlordkingdom.mol.repositories.FixedFeeRepository;
import pl.lodz.p.it.landlordkingdom.mol.repositories.LocalRepository;
import pl.lodz.p.it.landlordkingdom.mol.repositories.RentRepository;
import pl.lodz.p.it.landlordkingdom.mol.services.FixedFeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FixedFeeServiceImpl implements FixedFeeService {
    private final FixedFeeRepository fixedFeeRepository;
    private final RentRepository rentRepository;
    private final LocalRepository localRepository;
    private final AdministratorMolRepository administratorRepository;

    @Override
    @Transactional(propagation = Propagation.NESTED)
    @Retryable(retryFor = {RuntimeException.class},
            maxAttempts = 5)
    public FixedFee createFixedFeeForEndOfBillingPeriod(UUID rentId) {
        Rent rent = rentRepository.findById(rentId).orElseThrow();

        Local local = rent.getLocal();
        LocalDate today = LocalDate.now();

        BigDecimal rentalFee = rent.getLocal().getRentalFee();
        BigDecimal marginFee = rent.getLocal().getMarginFee();

        FixedFee fixedFee = new FixedFee(rentalFee, marginFee, today, rent);
        rent.setBalance(rent.getBalance().subtract(fixedFee.getRentalFee()).subtract(fixedFee.getMarginFee()));

        fixedFeeRepository.saveAndFlush(fixedFee);
        rentRepository.saveAndFlush(rent);

        BigDecimal nextMarginFee = local.getNextMarginFee();
        BigDecimal nextRentalFee = local.getNextRentalFee();

        if (nextMarginFee != null) {
            local.setMarginFee(nextMarginFee);
            local.setNextMarginFee(null);
        }

        if (nextRentalFee != null) {
            local.setRentalFee(nextRentalFee);
            local.setNextRentalFee(null);
        }

        localRepository.saveAndFlush(local);
        return fixedFee;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Page<FixedFee> getRentFixedFees(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Optional< Administrator> administrator = administratorRepository.findByUserIdAndActiveIsTrue(userId);

        if (administrator.isPresent()) {
            return fixedFeeRepository.findRentVariableFeesBetween(rentId, startDate, endDate, pageable);
        }

        return fixedFeeRepository.findRentVariableFeesBetween(rentId, userId, startDate, endDate, pageable);
    }
}
