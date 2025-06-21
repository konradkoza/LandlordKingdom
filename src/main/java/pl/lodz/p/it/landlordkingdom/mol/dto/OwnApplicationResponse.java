package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.UUID;

public record OwnApplicationResponse(
        UUID id,
        String createdAt,
        UUID localId,
        String localName,
        String country,
        String city,
        String street
        ) {
}
