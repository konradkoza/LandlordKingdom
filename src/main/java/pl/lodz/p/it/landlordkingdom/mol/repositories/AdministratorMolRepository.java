package pl.lodz.p.it.landlordkingdom.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.Administrator;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface AdministratorMolRepository extends JpaRepository<Administrator, UUID> {
    Optional<Administrator> findByUserIdAndActiveIsTrue(UUID id);
}
