package pl.lodz.p.it.landlordkingdom.messages;

public class RentExceptionMessages {
    public static final String RENT_NOT_FOUND = "Rent not found";
    public static final String WRONG_END_DATE = "End date must be a future Sunday and different from the current one";
    public static final String WRONG_DATE_FORMAT = "Wrong date format";
    public static final String DATE_PARSING_ERROR = "Date parsing error";
    public static final String RENT_ENDED = "Rent you are trying to edit has already ended";
    public static final String VARIABLE_FEE_ALREADY_EXISTS = "Variable fee for this week already exists";
    public static final String PAYMENT_ALREADY_EXISTS = "Payment for this week already exists";
}
