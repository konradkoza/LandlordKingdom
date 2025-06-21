package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.UUID;

public record EditLocalResponse(
        UUID id,
        String name,
        String description,
        int size
) {
}
