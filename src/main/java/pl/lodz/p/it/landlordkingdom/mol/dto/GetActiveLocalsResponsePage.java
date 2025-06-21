package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;

public record GetActiveLocalsResponsePage(
        List<GetActiveLocalsResponse> locals,
        int pages
) {
}
