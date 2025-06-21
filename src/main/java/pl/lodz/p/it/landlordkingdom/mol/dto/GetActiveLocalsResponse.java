package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.UUID;

public record GetActiveLocalsResponse(
        UUID id,
        String name,
        String description,
        int size,
        String city
) {
}
