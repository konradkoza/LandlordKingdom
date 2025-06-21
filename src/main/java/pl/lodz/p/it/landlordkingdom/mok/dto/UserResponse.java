package pl.lodz.p.it.landlordkingdom.mok.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String login,
        String language,
        boolean blocked,
        String timezone
) {
}
