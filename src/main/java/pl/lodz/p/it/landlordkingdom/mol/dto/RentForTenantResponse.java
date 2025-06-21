package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record RentForTenantResponse(
        UUID id,
        LocalForTenantResponse local,
        OwnerForTenantResponse owner,
        String startDate,
        String endDate,
        BigDecimal balance
) {
}
