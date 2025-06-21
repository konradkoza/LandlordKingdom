package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;

public record GetUnapprovedLocalPageResponse(
        int totalPages,
        List<GetAllLocalsResponse> locals) {
}
