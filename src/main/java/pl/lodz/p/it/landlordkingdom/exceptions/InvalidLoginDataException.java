package pl.lodz.p.it.landlordkingdom.exceptions;

public class InvalidLoginDataException extends ApplicationBaseException {

    public InvalidLoginDataException(String message, String code) {
        super(message, code);
    }

    public InvalidLoginDataException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
