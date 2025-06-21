package pl.lodz.p.it.landlordkingdom.exceptions;

public class InvalidPasswordException extends ApplicationBaseException {

    public InvalidPasswordException(String message, String code) {
        super(message, code);
    }

    public InvalidPasswordException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
