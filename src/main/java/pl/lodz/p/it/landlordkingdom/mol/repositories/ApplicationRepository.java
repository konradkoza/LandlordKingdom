package pl.lodz.p.it.landlordkingdom.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.Application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    @NonNull
    Optional<Application> findById(@NonNull UUID id);

    Optional<Application> findByTenantUserIdAndLocalId(UUID userId, UUID localId);

    @NonNull
    Application saveAndFlush(@NonNull Application application);

    List<Application> findByTenantId(UUID id);

    List<Application> findByLocalId(UUID id);

    @Query("SELECT a FROM Application a JOIN a.local l WHERE l.id = :localId AND l.owner.user.id = :ownerId")
    List<Application> findByLocalIdAndLocal_OwnerId(@Param("localId") UUID localId, @Param("ownerId") UUID ownerId);

    void deleteByTenantIdAndLocalIdAndLocal_OwnerId(UUID tenantId, UUID localId, UUID ownerId);

    void delete(@NonNull Application application);

    @Query("SELECT a FROM Application a WHERE a.id = :applicationId AND a.local.owner.user.id = :ownerUserId AND a.local.owner.active = TRUE")
    Optional<Application> findApplicationForOwner(UUID applicationId, UUID ownerUserId);
}
