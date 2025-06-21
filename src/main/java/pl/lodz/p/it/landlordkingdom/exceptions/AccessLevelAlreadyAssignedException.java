package pl.lodz.p.it.landlordkingdom.exceptions;

public class AccessLevelAlreadyAssignedException extends ApplicationBaseException {

    public AccessLevelAlreadyAssignedException(String message, String code) {
        super(message, code);
    }

    public AccessLevelAlreadyAssignedException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
