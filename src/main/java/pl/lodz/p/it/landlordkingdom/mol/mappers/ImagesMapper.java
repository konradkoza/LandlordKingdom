package pl.lodz.p.it.landlordkingdom.mol.mappers;

import pl.lodz.p.it.landlordkingdom.mol.dto.LocalImagesResponse;

import java.util.List;
import java.util.UUID;

public class ImagesMapper {
    public static LocalImagesResponse toLocalImagesResponse(List<UUID> imageIds) {
        return new LocalImagesResponse(imageIds);
    }
}
