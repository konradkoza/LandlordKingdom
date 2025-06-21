package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AddLocalResponse(
        UUID id,
        String name,
        String description,
        String state,
        int size,
        BigDecimal marginFee,
        BigDecimal rentalFee,
        AddressResponse address
) {
}
