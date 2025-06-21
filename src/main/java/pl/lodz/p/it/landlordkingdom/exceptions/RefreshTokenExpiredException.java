package pl.lodz.p.it.landlordkingdom.exceptions;

public class RefreshTokenExpiredException extends ApplicationBaseException {
    public RefreshTokenExpiredException(String message, String code) {
        super(message, code);
    }

    public RefreshTokenExpiredException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
