package pl.lodz.p.it.landlordkingdom.mok.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token,
        String refreshToken,
        String theme
) {
}
