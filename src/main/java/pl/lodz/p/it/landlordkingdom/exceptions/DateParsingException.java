package pl.lodz.p.it.landlordkingdom.exceptions;

public class DateParsingException extends ApplicationBaseException{
    public DateParsingException(String message, String code) {
        super(message, code);
    }

    public DateParsingException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
