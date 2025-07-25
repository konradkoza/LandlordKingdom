package pl.lodz.p.it.landlordkingdom.mol.mappers;

import pl.lodz.p.it.landlordkingdom.model.Address;
import pl.lodz.p.it.landlordkingdom.mol.dto.AddressResponse;
import pl.lodz.p.it.landlordkingdom.mol.dto.EditLocalAddressRequest;

public class AddressMapper {
    public static AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                address.getNumber(),
                address.getZip()
        );
    }

    public static Address editAddressRequestToAddress(EditLocalAddressRequest editLocalAddressRequest) {
        return new Address(
                editLocalAddressRequest.country(),
                editLocalAddressRequest.city(),
                editLocalAddressRequest.street(),
                editLocalAddressRequest.number(),
                editLocalAddressRequest.zipCode()
        );
    }
}
