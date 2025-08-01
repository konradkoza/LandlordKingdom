package pl.lodz.p.it.landlordkingdom.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.tokens.AccountVerificationToken;


import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface AccountVerificationTokenRepository extends JpaRepository<AccountVerificationToken, UUID> {

    Optional<AccountVerificationToken> findByToken(String token);

    Optional<AccountVerificationToken> findByUserId(UUID id);

    void deleteByUserId(UUID id);


}
