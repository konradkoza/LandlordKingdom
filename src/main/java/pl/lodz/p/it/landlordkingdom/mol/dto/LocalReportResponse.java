package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;
import java.util.UUID;

public record LocalReportResponse(UUID id,
                                  String name,
                                  List<PaymentResponse> payments,
                                  List<VariableFeeResponse> variableFees,
                                  List<FixedFeeResponse> fixedFees,
                                  int rentCount,
                                  long longestRentDays,
                                  long shortestRentDays) {
}
