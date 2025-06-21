package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;

public record GetOwnLocalsPage(
        List<GetOwnLocalsResponse> locals,
        int totalPages
) {
}
