package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;

public record RentVariableFeesResponse(
        List<VariableFeeResponse> rentVariableFees,
        long totalPages
) {
}
