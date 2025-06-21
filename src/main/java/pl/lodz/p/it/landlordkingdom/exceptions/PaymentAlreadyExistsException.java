package pl.lodz.p.it.landlordkingdom.exceptions;

public class PaymentAlreadyExistsException extends ApplicationBaseException{
    public PaymentAlreadyExistsException(String message, String code) {
        super(message, code);
    }

    public PaymentAlreadyExistsException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
