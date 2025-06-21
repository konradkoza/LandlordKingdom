package pl.lodz.p.it.landlordkingdom.mok.services;

import pl.lodz.p.it.landlordkingdom.model.Timezone;

public interface TimezoneService {
    Timezone findByTimezoneName(String timezoneName);
}
