package pl.lodz.p.it.landlordkingdom.exceptions;

public class UserAlreadyHasRoleException extends ApplicationBaseException {

    public UserAlreadyHasRoleException(String message, String code) {
        super(message, code);
    }

    public UserAlreadyHasRoleException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
