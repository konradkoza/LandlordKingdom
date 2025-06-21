package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.util.List;
import java.util.UUID;

public record LocalImagesResponse(
    List<UUID> imageIds
) {
}
