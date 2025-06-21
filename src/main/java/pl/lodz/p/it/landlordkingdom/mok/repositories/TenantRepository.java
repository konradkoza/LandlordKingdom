package pl.lodz.p.it.landlordkingdom.mok.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.Tenant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TenantRepository extends JpaRepository<Tenant, UUID>, JpaSpecificationExecutor<Tenant> {
    @NonNull
    List<Tenant> findAll();

    @NonNull
    Page<Tenant> findAll(@NonNull Specification specification,@NonNull Pageable pageable);

    Optional<Tenant> findByUserIdAndActive(UUID user_id, boolean active);

    @NonNull
    Tenant saveAndFlush(@NonNull Tenant tenant);

    Optional<Tenant> findByUserId(UUID id);
}
