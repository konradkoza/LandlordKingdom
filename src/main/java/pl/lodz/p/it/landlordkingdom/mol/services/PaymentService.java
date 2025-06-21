package pl.lodz.p.it.landlordkingdom.mol.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.exceptions.PaymentAlreadyExistsException;
import pl.lodz.p.it.landlordkingdom.exceptions.RentAlreadyEndedException;
import pl.lodz.p.it.landlordkingdom.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface PaymentService {

    Page<Payment> getRentPayments(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    Payment create(UUID userId, UUID rentID, BigDecimal amount) throws NotFoundException, PaymentAlreadyExistsException, RentAlreadyEndedException;
}
