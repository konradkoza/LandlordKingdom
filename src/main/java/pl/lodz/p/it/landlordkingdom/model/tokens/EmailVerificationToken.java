package pl.lodz.p.it.landlordkingdom.model.tokens;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.landlordkingdom.model.User;

import java.time.Instant;

@DiscriminatorValue("EMAIL")
@Entity
@NoArgsConstructor
public class EmailVerificationToken extends VerificationToken {

    public static int EXPIRATION_TIME = 15;

    public EmailVerificationToken(String token, Instant expirationDate, User user) {
        super(token, expirationDate, user);
    }
}
