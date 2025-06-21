package pl.lodz.p.it.landlordkingdom.exceptions;

public class VariableFeeAlreadyExistsException extends ApplicationBaseException {
    public VariableFeeAlreadyExistsException(String message, String code) {
        super(message, code);
    }

    public VariableFeeAlreadyExistsException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
