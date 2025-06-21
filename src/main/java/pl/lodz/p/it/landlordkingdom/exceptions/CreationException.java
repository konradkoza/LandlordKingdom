package pl.lodz.p.it.landlordkingdom.exceptions;

public class CreationException extends ApplicationBaseException {
    public CreationException(String message, String code) {
        super(message, code);
    }

    public CreationException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
