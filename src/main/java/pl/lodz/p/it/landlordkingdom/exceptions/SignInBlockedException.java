package pl.lodz.p.it.landlordkingdom.exceptions;

public class SignInBlockedException extends ApplicationBaseException {

    public SignInBlockedException(String message, String code) {
        super(message, code);
    }

    public SignInBlockedException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
