package pl.lodz.p.it.landlordkingdom.exceptions;

public class VerificationTokenExpiredException extends ApplicationBaseException{
    public VerificationTokenExpiredException(String message, String code) {
        super(message, code);
    }

    public VerificationTokenExpiredException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
