package pl.lodz.p.it.landlordkingdom.mol.dto;

import jakarta.validation.constraints.NotBlank;
import pl.lodz.p.it.landlordkingdom.util.ValidDate;

public record SetEndDateRequest(

        @ValidDate(message = "Field must be a valid date.")
        @NotBlank(message = "End date cannot be blank.")
        String newEndDate
) {
}
