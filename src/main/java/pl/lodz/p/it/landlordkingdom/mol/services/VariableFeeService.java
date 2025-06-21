package pl.lodz.p.it.landlordkingdom.mol.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.exceptions.VariableFeeAlreadyExistsException;
import pl.lodz.p.it.landlordkingdom.model.VariableFee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
public interface VariableFeeService {

    VariableFee create(UUID userId, UUID rentId, BigDecimal amount) throws NotFoundException, VariableFeeAlreadyExistsException;

    Page<VariableFee> getRentVariableFees(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
