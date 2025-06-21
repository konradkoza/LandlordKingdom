package pl.lodz.p.it.landlordkingdom.exceptions;

public class PasswordRepetitionException extends ApplicationBaseException {

    public PasswordRepetitionException(String message, String code) {
        super(message, code);
    }

    public PasswordRepetitionException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
