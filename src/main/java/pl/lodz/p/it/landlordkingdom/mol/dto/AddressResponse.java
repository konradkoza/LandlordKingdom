package pl.lodz.p.it.landlordkingdom.mol.dto;

public record AddressResponse(
        String country,
        String city,
        String street,
        String number,
        String zipCode
) {
}
