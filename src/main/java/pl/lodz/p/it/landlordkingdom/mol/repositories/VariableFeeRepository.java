package pl.lodz.p.it.landlordkingdom.mol.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.VariableFee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface VariableFeeRepository extends JpaRepository<VariableFee, UUID> {

    @NonNull
    VariableFee saveAndFlush(@NonNull VariableFee variableFee);

    @Query("SELECT fee FROM VariableFee fee WHERE fee.rent.id = :rentId AND fee.date BETWEEN :startDate AND :endDate")
    Page<VariableFee> findRentVariableFeesBetween(UUID rentId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("SELECT fee FROM VariableFee fee WHERE fee.rent.id = :rentId AND (fee.rent.owner.user.id = :userId OR fee.rent.tenant.user.id = :userId) AND fee.date BETWEEN :startDate AND :endDate")
    Page<VariableFee> findRentVariableFeesBetween(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("SELECT fee FROM VariableFee fee WHERE fee.rent.id = :rentId AND fee.rent.tenant.user.id = :userId AND fee.date BETWEEN :startDate AND :endDate")
    Optional<VariableFee> findByRentIdBetween(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT fee FROM VariableFee fee WHERE fee.rent.local.id = :localId AND fee.rent.owner.user.id = :userId")
    List<VariableFee> findByLocalIdAndUserId(UUID localId, UUID userId);

    @Query("SELECT fee FROM VariableFee fee WHERE fee.rent.owner.user.id = :ownerId")
    List<VariableFee> findAllByOwnerId(UUID ownerId);
}
