package pl.lodz.p.it.landlordkingdom.mol.dto;

public record TenantForOwnerResponse(
        String login,
        String firstName,
        String lastName,
        String email
) {
}
