package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;

public record RentFixedFeesResponse(
        List<FixedFeeResponse> rentFixedFees,
        long totalPages
) {
}
