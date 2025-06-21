package pl.lodz.p.it.landlordkingdom.exceptions;

public class UserNotVerifiedException extends ApplicationBaseException {

    public UserNotVerifiedException(String message, String code) {
        super(message, code);
    }

    public UserNotVerifiedException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
