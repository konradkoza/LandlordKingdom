package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.UUID;

public record GetOwnLocalApplicationsResponse(
        UUID id,
        String createdAt,
        String applicantFirstName,
        String applicantLastName,
        String applicantEmail
) {
}
