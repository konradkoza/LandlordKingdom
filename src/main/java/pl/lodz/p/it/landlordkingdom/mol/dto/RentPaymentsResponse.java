package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;

public record RentPaymentsResponse(
        List<PaymentResponse> rentPayments,
        long totalPages
) {

}
