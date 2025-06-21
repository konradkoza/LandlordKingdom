package pl.lodz.p.it.landlordkingdom.exceptions;

public class InvalidDataException extends ApplicationBaseException {
    public InvalidDataException(String message, String code) {
        super(message, code);
    }

    public InvalidDataException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
