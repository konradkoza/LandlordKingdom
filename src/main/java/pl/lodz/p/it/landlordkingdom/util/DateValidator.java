package pl.lodz.p.it.landlordkingdom.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateValidator implements ConstraintValidator<ValidDate, String> {

    private Boolean isOptional;

    @Override
    public void initialize(ValidDate validDate) {
        this.isOptional = validDate.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        boolean validDate = isValidFormat(value);

        return isOptional ? (validDate || value == null || value.isBlank() || value.isEmpty()) : validDate;
    }

    private static boolean isValidFormat(String value) {
        LocalDate date = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (value != null){
                date = LocalDate.parse(value, dtf);
                if (!value.equals(dtf.format(date))) {
                    date = null;
                }
            }

        } catch (DateTimeParseException ex) {
            log.debug("Error while parsing date", ex);
        }
        return date != null;
    }
}
