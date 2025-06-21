package pl.lodz.p.it.landlordkingdom.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.Timezone;
import pl.lodz.p.it.landlordkingdom.mok.repositories.TimezoneRepository;
import pl.lodz.p.it.landlordkingdom.mok.services.TimezoneService;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
@RequiredArgsConstructor
public class TimezoneServiceImpl implements TimezoneService {
    private final TimezoneRepository timezoneRepository;

    @Override
    public Timezone findByTimezoneName(String timezoneName) {
        return timezoneRepository.findByName(timezoneName).orElse(null);
    }
}
