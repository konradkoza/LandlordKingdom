package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;

public record AllLocalsReportResponse(List<PaymentResponse> payments, List<VariableFeeResponse> variableFees,
                                      List<FixedFeeResponse> fixedFees) {
}
