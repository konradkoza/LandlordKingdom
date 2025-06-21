package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.math.BigDecimal;

public record LocalForTenantResponse(
        String name,
        int size,
        AddressResponse address,
        BigDecimal marginFee,
        BigDecimal rentalFee
) {
}
