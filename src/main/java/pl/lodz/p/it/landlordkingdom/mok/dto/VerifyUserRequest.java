package pl.lodz.p.it.landlordkingdom.mok.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyUserRequest(
        @NotBlank(message = "Token cannot be blank.")
        String token) {
}
