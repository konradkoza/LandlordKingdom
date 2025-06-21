package pl.lodz.p.it.landlordkingdom.mok.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.tokens.PasswordVerificationToken;


import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface PasswordVerificationTokenRepository extends JpaRepository<PasswordVerificationToken, UUID> {

    Optional<PasswordVerificationToken> findByToken(String token);

    Optional<PasswordVerificationToken> findByUserId(UUID id);

    void deleteByUserId(UUID id);

}
