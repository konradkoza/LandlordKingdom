package pl.lodz.p.it.landlordkingdom.mok.authRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.Tenant;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
@PreAuthorize("hasRole('ADMINISTRATOR')")
public interface AuthTenantRepository extends JpaRepository<Tenant, UUID> {
    @PreAuthorize("permitAll()")
    Optional<Tenant> findByUserIdAndActive(UUID id, boolean active);
}
