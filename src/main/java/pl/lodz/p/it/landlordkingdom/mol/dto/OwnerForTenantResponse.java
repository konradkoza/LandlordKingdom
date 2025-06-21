package pl.lodz.p.it.landlordkingdom.mol.dto;

public record OwnerForTenantResponse(
        String firstName,
        String lastName,
        String login,
        String email
) {

}
