package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;

public record RoleRequestPageResponse(
        int totalPages,
        List<RoleRequestResponse> requests
) {
}
