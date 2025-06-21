package pl.lodz.p.it.landlordkingdom.exceptions;

public class UserAlreadyUnblockedException extends ApplicationBaseException {

    public UserAlreadyUnblockedException(String message, String code) {
        super(message, code);
    }

    public UserAlreadyUnblockedException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
