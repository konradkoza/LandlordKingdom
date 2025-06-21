package pl.lodz.p.it.landlordkingdom.mol.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.it.landlordkingdom.model.FixedFee;

import java.time.LocalDate;
import java.util.UUID;

public interface FixedFeeService {

    FixedFee createFixedFeeForEndOfBillingPeriod(UUID rentId);

    Page<FixedFee> getRentFixedFees(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
