package pl.lodz.p.it.landlordkingdom.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.RoleRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RoleRequestRepository extends JpaRepository<RoleRequest, UUID> {

    @NonNull
    List<RoleRequest> findAll();

    Optional<RoleRequest> findByTenantId(UUID tenantId);

    @NonNull
    void delete(@NonNull RoleRequest roleRequest);

    @NonNull
    RoleRequest saveAndFlush(@NonNull RoleRequest roleRequest);
}
