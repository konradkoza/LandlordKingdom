package pl.lodz.p.it.landlordkingdom.exceptions;

public class TokenGenerationException extends ApplicationBaseException {

    public TokenGenerationException(String message, String code) {
        super(message, code);
    }

    public TokenGenerationException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
