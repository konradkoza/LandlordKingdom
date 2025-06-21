package pl.lodz.p.it.landlordkingdom.mol.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record RentForOwnerResponse(
        UUID id,
        GetOwnLocalsResponse local,
        TenantForOwnerResponse tenant,
        String startDate,
        String endDate,
        BigDecimal balance
) {
}
