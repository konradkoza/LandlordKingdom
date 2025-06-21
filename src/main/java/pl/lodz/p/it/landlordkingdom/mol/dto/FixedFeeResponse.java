package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.math.BigDecimal;

public record FixedFeeResponse(String date, BigDecimal marginFee, BigDecimal rentalFee) {
}
