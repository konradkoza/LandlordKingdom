package pl.lodz.p.it.landlordkingdom.exceptions;

public class LoginNotMatchToOTPException extends ApplicationBaseException {

        public LoginNotMatchToOTPException(String message, String code) {
            super(message, code);
        }

        public LoginNotMatchToOTPException(String message, Throwable cause, String code) {
            super(message, cause, code);
        }
}
