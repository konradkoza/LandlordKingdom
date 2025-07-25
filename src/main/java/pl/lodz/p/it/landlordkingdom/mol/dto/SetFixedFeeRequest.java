package pl.lodz.p.it.landlordkingdom.mol.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record SetFixedFeeRequest(
        @NotNull(message = "fixed fee cannot be null.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Fixed fee must be greater than 0.")
        @DecimalMax(value = "10000", inclusive = true, message = "Fixed fee must be less than or equal to 10 000.00")
        @Digits(integer = 10, fraction = 2, message = "Fixed fee must be a valid monetary amount with up to 10 integer digits and 2 fractional digits.")
        BigDecimal rentalFee,

        @NotNull(message = "fixed fee cannot be null.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Fixed fee must be greater than 0.")
        @DecimalMax(value = "10000", inclusive = true, message = "Fixed fee must be less than or equal to 10 000.00")
        @Digits(integer = 10, fraction = 2, message = "Fixed fee must be a valid monetary amount with up to 10 integer digits and 2 fractional digits.")
        BigDecimal marginFee) {
}
