package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.math.BigDecimal;

public record PaymentResponse(String date, BigDecimal amount) {
}
