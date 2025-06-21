package pl.lodz.p.it.landlordkingdom.mol.dto;

import pl.lodz.p.it.landlordkingdom.model.FixedFee;
import pl.lodz.p.it.landlordkingdom.model.Local;
import pl.lodz.p.it.landlordkingdom.model.Payment;
import pl.lodz.p.it.landlordkingdom.model.VariableFee;

import java.util.List;

public record LocalReport(Local local,
                          List<Payment> payments,
                          List<VariableFee> variableFees,
                          List<FixedFee> fixedFees,
                          int rentCount,
                          long longestRentDays, long shortestRentDays) {

}
