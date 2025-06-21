package pl.lodz.p.it.landlordkingdom.model.tokens;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.landlordkingdom.model.User;

import java.time.Instant;

@DiscriminatorValue("ACCOUNT")
@Entity
@NoArgsConstructor
public class AccountVerificationToken extends VerificationToken {

    public static int EXPIRATION_TIME = 24 * 60;

    public AccountVerificationToken(String token, Instant expirationDate, User user) {
        super(token, expirationDate, user);
    }
}
