package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.UUID;

public record OwnerForAdminResponse(
        UUID userId,
        String firstName,
        String lastName,
        String login,
        String email
) {
}
