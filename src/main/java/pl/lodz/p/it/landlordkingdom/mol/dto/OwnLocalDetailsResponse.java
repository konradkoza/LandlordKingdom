package pl.lodz.p.it.landlordkingdom.mol.dto;

import pl.lodz.p.it.landlordkingdom.model.LocalState;

import java.math.BigDecimal;

public record OwnLocalDetailsResponse(String name,
                                      int size,
                                      String description,
                                      LocalState state,
                                      AddressResponse address,
                                      BigDecimal marginFee,
                                      BigDecimal rentalFee,
                                      BigDecimal nextMarginFee,
                                      BigDecimal nextRentalFee
) {
}