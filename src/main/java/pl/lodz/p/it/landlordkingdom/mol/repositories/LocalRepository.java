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
import pl.lodz.p.it.landlordkingdom.model.Address;
import pl.lodz.p.it.landlordkingdom.model.Local;
import pl.lodz.p.it.landlordkingdom.model.LocalState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface LocalRepository extends JpaRepository<Local, UUID> {

    @NonNull
    Optional<Local> findById(@NonNull UUID id);

    List<Local> findByAddress(Address address);

    @Query("SELECT l FROM Local l WHERE l.address = :address AND l.state != :state")
    Optional<Local> findByAddressAndStateNotContaining(@Param("address") Address address, @Param("state") LocalState state);

    @NonNull
    Local saveAndFlush(@NonNull Local local);

    @Query("SELECT l FROM Local l WHERE l.owner.user.id = :id")
    Page<Local> findAllByOwnerId(UUID id, Pageable pageable);

    @Query("SELECT l FROM Local l WHERE l.owner.user.id = :id AND l.state = :state")
    Page<Local> findAllByOwnerIdAndState(UUID id, Pageable pageable, LocalState state);

    List<Local> findAllByState(LocalState localState);

    Page<Local> findAllByState(Pageable pageable, LocalState state);

    @Query("SELECT l FROM Local l " +
            "WHERE l.state = :localState " +
            "AND (LOWER(l.address.city) LIKE LOWER(CONCAT('%', :city, '%')) OR :city IS NULL) " +
            "AND (:minSize IS NULL OR l.size >= :minSize) " +
            "AND (:maxSize IS NULL OR l.size <= :maxSize)")
    Page<Local> findAllByStateCityAndSize(
            Pageable pageable,
            @Param("localState") LocalState localState,
            @Param("city") String city,
            @Param("minSize") Double minSize,
            @Param("maxSize") Double maxSize);



    Optional<Local> findByOwner_User_IdAndId(UUID userId, UUID id);

    @Query("SELECT l FROM Local l WHERE LOWER(l.owner.user.login) LIKE LOWER(CONCAT('%', :ownerLogin, '%'))")
    @NonNull
    Page<Local> findAll(@NonNull Pageable pageable, String ownerLogin);

    @Query("SELECT l FROM Local l WHERE l.state = :state AND LOWER(l.owner.user.login) LIKE LOWER(CONCAT('%', :ownerLogin, '%'))")
    Page<Local> findAllByStateAndOwnerLogin(Pageable pageable, LocalState state, String ownerLogin);

    @NonNull
    Page<Local> findAll(@NonNull Pageable pageable);

    Optional<Local> findByIdAndState(UUID id, LocalState state);
}
