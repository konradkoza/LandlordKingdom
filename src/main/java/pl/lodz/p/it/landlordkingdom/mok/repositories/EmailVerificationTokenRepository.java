package pl.lodz.p.it.landlordkingdom.mok.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.tokens.EmailVerificationToken;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, UUID> {

    Optional<EmailVerificationToken> findByToken(String token);

    Optional<EmailVerificationToken> findByUserId(UUID id);

    @NonNull
    EmailVerificationToken saveAndFlush(@NonNull EmailVerificationToken emailVerificationToken);

    void deleteEmailVerificationTokenByUserId(UUID id);
}
