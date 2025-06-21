package pl.lodz.p.it.landlordkingdom.mol.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.LocalState;
import pl.lodz.p.it.landlordkingdom.model.Rent;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RentRepository extends JpaRepository<Rent, UUID> {

    List<Rent> findAllByEndDateGreaterThanEqual(LocalDate date);

    Optional<Rent> findByIdAndTenantId(UUID id, UUID tenantId);

    @NonNull
    List<Rent> findAll();

    List<Rent> findAllByTenantIdAndEndDateBefore(UUID tenantId, LocalDate endDate);

    @Query("SELECT r FROM Rent r WHERE r.owner.user.id = :ownerId AND r.endDate >= CURRENT_DATE")
    Page<Rent> findCurrentRentsByOwnerId(UUID ownerId, Pageable pageable);

    @Query("SELECT r FROM Rent r WHERE r.owner.user.id = :userId AND r.endDate < CURRENT_DATE")
    Page<Rent> findArchivalRentsByOwnerUserId(UUID userId, Pageable pageable);

    Optional<Rent> findByOwnerIdAndId(UUID ownerId, UUID rentId);

    Rent findAllByOwnerIdAndLocalId(UUID ownerId, UUID localId);

    List<Rent> findAllByTenantIdAndEndDateAfter(UUID tenantId, LocalDate date);

    @NonNull
    Rent saveAndFlush(@NonNull Rent rent);

    List<Rent> getAllByEndDateBefore(LocalDate date);

    List<Rent> findAllByEndDateBeforeAndLocal_State(LocalDate date, LocalState state);

    Optional<Rent> findByOwner_User_IdAndId(UUID ownerId, UUID rentId);

    @Query("SELECT rent FROM Rent rent WHERE rent.tenant.user.id = :userId AND rent.endDate >= CURRENT_DATE ")
    List<Rent> findAllCurrentRentsByTenantUserId(@Param("userId") UUID userId);

    @Query("SELECT r FROM Rent r WHERE r.tenant.user.id = :userId AND r.endDate < CURRENT_DATE")
    List<Rent> findAllPastRentsByTenantUserId(UUID userId);

    @Query("SELECT COUNT(r) FROM Rent r WHERE r.owner.user.id = :userId AND r.local.id = :localId")
    int countRentsByUserIdAndLocalId(UUID localId, UUID userId);

    @Query("SELECT r FROM Rent r WHERE r.owner.user.id = :userId AND r.local.id = :localId ORDER BY r.endDate - r.startDate DESC LIMIT 1")
    Optional<Rent> getLongestRentByLocalId(UUID localId, UUID userId);

    @Query("SELECT r FROM Rent r WHERE r.owner.user.id = :userId AND r.local.id = :localId ORDER BY r.endDate - r.startDate ASC LIMIT 1")
    Optional<Rent> getShortestRentByLocalId(UUID localId, UUID userId);

    @Query("SELECT r FROM Rent r WHERE r.owner.user.id = :userId")
    List<Rent> findAllByOwnerId(UUID userId);
}
