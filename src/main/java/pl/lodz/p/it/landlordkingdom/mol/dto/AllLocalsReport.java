package pl.lodz.p.it.landlordkingdom.mol.dto;

import pl.lodz.p.it.landlordkingdom.model.FixedFee;
import pl.lodz.p.it.landlordkingdom.model.Payment;
import pl.lodz.p.it.landlordkingdom.model.Rent;
import pl.lodz.p.it.landlordkingdom.model.VariableFee;

import java.util.List;

public record AllLocalsReport(List<Rent> rents,
                              List<VariableFee> variableFees,
                              List<FixedFee> fixedFees,
                              List<Payment> payments) {
}
