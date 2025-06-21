package pl.lodz.p.it.landlordkingdom.exceptions;

public class WrongEndDateException extends ApplicationBaseException {
    public WrongEndDateException(String message, String code) {
        super(message, code);
    }

    public WrongEndDateException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
