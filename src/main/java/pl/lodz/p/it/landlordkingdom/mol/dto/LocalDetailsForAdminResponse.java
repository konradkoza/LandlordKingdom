package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.math.BigDecimal;

public record LocalDetailsForAdminResponse(
        String name,
        int size,
        String description,
        String state,
        OwnerForAdminResponse owner,
        AddressResponse address,
        BigDecimal marginFee,
        BigDecimal rentalFee,
        BigDecimal nextMarginFee,
        BigDecimal nextRentFee
) {

}
