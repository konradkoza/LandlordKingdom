package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;

public record GetAllLocalsFiltered(
        List<GetAllLocalsResponse> locals,
        long totalPages
) {
}
