package pl.lodz.p.it.landlordkingdom.exceptions;

public class AccessLevelAlreadyTakenException extends ApplicationBaseException {
    public AccessLevelAlreadyTakenException(String message, String code) {
        super(message, code);
    }

    public AccessLevelAlreadyTakenException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
