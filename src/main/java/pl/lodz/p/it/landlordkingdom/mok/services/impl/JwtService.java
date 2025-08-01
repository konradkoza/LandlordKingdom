package pl.lodz.p.it.landlordkingdom.mok.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.util.KeyReader;

import java.io.IOException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final JwtEncoder refreshTokenEncoder;
    private PublicKey refreshTokenPublicKey;

    @Value("${refreshToken.publicKeyFilePath}")
    private String publicRefreshTokenKeyFilePath;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${refreshToken.expiration}")
    private long refreshTokenExpiration;

    @PostConstruct
    public void readKeys() throws IOException {
        this.refreshTokenPublicKey = KeyReader.readPublicJwtKey(publicRefreshTokenKeyFilePath);
    }

    public String generateToken(UUID id, String login, List<String> roles) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("team-2.proj-sum.it.p.lodz.pl")
                .issuedAt(now)
                .expiresAt(now.plus(jwtExpiration, ChronoUnit.HOURS))
                .subject(id.toString())
                .claim("authorities", roles)
                .claim("login", login)
                .build();

        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(SignatureAlgorithm.RS256).build(), claims);
        return jwtEncoder.encode(encoderParameters).getTokenValue();
    }

    public String generateRefreshToken(UUID id) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("team-2.proj-sum.it.p.lodz.pl")
                .issuedAt(now)
                .expiresAt(now.plus(refreshTokenExpiration, ChronoUnit.HOURS))
                .subject(id.toString())
                .build();

        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(SignatureAlgorithm.RS256).build(), claims);
        return refreshTokenEncoder.encode(encoderParameters).getTokenValue();
    }

    public boolean validateRefreshExpiration(Jwt refreshToken) {
        return Objects.requireNonNull(refreshToken.getExpiresAt()).isAfter(Instant.now());
    }

    public Jwt decodeRefreshToken(String token) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withPublicKey((RSAPublicKey) refreshTokenPublicKey)
                .build();

        return decoder.decode(token);
    }
}
