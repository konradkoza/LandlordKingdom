package pl.lodz.p.it.landlordkingdom.mok.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByGoogleId(String googleId);

    @NonNull
    List<User> findAll();

    @NonNull
    Page<User> findAll(@NonNull Specification specification, @NonNull Pageable pageable);

    List<User> getUsersByCreatedAtBeforeAndVerifiedIsFalse(LocalDateTime createdAt);

    @Query("SELECT u FROM User u WHERE :createdAt < u.createdAt AND u.createdAt < :createdAt2 AND u.verified = false")
    List<User> getUsersToResendEmail(LocalDateTime createdAt, LocalDateTime createdAt2);

    @Query("SELECT u FROM User u WHERE u.active = true AND u.lastSuccessfulLogin < :date")
    List<User> getUserByActiveIsTrueAndLastSuccessfulLogin(@Param("date") LocalDateTime date);
}
