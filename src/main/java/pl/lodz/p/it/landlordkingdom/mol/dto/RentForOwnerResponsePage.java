package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;

public record RentForOwnerResponsePage(
        List<RentForOwnerResponse> rents,
        int pages
) {
}
