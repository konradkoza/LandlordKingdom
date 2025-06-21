package pl.lodz.p.it.landlordkingdom.mol.services;

import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.mol.dto.AllLocalsReport;
import pl.lodz.p.it.landlordkingdom.mol.dto.LocalReport;

import java.time.LocalDate;
import java.util.UUID;

public interface ReportService {
    LocalReport getLocalReport(UUID localId, UUID userId, LocalDate startDate, LocalDate endDate)
            throws NotFoundException;

    AllLocalsReport getReport(UUID userId);
}
