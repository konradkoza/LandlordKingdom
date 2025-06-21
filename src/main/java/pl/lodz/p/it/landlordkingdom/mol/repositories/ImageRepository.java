package pl.lodz.p.it.landlordkingdom.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.Image;
import pl.lodz.p.it.landlordkingdom.model.Local;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ImageRepository extends JpaRepository<Image, UUID> {
    @NonNull
    Image saveAndFlush(@NonNull Local local);

    @Query("SELECT image.id FROM Image image WHERE image.local.id = :localId")
    List<UUID> findImageIdsByLocalId(UUID localId);

    @NonNull
    Optional<Image> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);
}
