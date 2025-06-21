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
import pl.lodz.p.it.landlordkingdom.model.Administrator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface AdministratorRepository extends JpaRepository<Administrator, UUID>, JpaSpecificationExecutor<Administrator> {

    @NonNull
    List<Administrator> findAll();

    @NonNull
    Page<Administrator> findAll(@NonNull Specification specification, @NonNull Pageable pageable);

    Optional<Administrator> findByUserId(UUID id);

    Optional<Administrator> findByUserIdAndActive(UUID id, boolean active );

}
