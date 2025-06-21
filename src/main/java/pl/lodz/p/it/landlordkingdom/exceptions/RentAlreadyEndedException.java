package pl.lodz.p.it.landlordkingdom.exceptions;

public class RentAlreadyEndedException extends ApplicationBaseException{
    public RentAlreadyEndedException(String message, String code) {
        super(message, code);
    }

    public RentAlreadyEndedException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
