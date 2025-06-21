package pl.lodz.p.it.landlordkingdom.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.UserFilter;

import java.util.Optional;
import java.util.UUID;

@Transactional(propagation = Propagation.MANDATORY)
public interface UserFilterRepository extends JpaRepository<UserFilter, UUID> {
    Optional<UserFilter> findByUserId(UUID userId);

    @NonNull
    UserFilter saveAndFlush(@NonNull UserFilter userFilter);
}
