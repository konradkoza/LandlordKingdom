package pl.lodz.p.it.landlordkingdom.mok.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token cannot be blank.")
        String refreshToken
) {
}
