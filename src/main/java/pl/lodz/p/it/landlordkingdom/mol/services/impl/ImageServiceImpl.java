package pl.lodz.p.it.landlordkingdom.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.it.landlordkingdom.exceptions.CreationException;
import pl.lodz.p.it.landlordkingdom.exceptions.ImageFormatNotSupported;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.landlordkingdom.messages.LocalExceptionMessages;
import pl.lodz.p.it.landlordkingdom.model.Image;
import pl.lodz.p.it.landlordkingdom.model.Local;
import pl.lodz.p.it.landlordkingdom.mol.repositories.ImageRepository;
import pl.lodz.p.it.landlordkingdom.mol.repositories.LocalRepository;
import pl.lodz.p.it.landlordkingdom.mol.services.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final LocalRepository localRepository;

    @Override
    public void store(MultipartFile file, UUID localId) throws NotFoundException, ImageFormatNotSupported, CreationException {
        Local local = localRepository.findById(localId).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        if (!Objects.equals(file.getContentType(), "image/jpeg") && !Objects.equals(file.getContentType(), "image/png")) {
            throw new ImageFormatNotSupported(LocalExceptionMessages.IMAGE_FORMAT_NOT_SUPPORTED, ErrorCodes.IMAGE_FORMAT_NOT_SUPPORTED);
        }

        try {
            imageRepository.saveAndFlush(new Image(local, file.getBytes(), file.getContentType()));
        } catch (IOException e) {
            throw new CreationException(LocalExceptionMessages.IMAGE_CREATION_FAILED, ErrorCodes.IMAGE_CREATION_FAILED);
        }

    }

    @Override
    public Image getImage(UUID id) throws NotFoundException {
        return imageRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.IMAGE_NOT_FOUND, ErrorCodes.IMAGE_NOT_FOUND));
    }

    @Override
    public List<UUID> getImagesByLocalId(UUID id) throws NotFoundException {
        localRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        return imageRepository.findImageIdsByLocalId(id);
    }

    @Override
    public void deleteImage(UUID id) throws NotFoundException {
        imageRepository.findById(id).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.IMAGE_NOT_FOUND, ErrorCodes.IMAGE_NOT_FOUND));
        imageRepository.deleteById(id);
        imageRepository.flush();
    }
}
