package pl.lodz.p.it.landlordkingdom.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.Owner;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface OwnerMolRepository extends JpaRepository<Owner, UUID> {
    Optional<Owner> findByUserIdAndActiveIsTrue(UUID id);

    @NonNull
    Owner saveAndFlush(@NonNull Owner owner);

    Optional<Owner> findByUserId(UUID id);
}
