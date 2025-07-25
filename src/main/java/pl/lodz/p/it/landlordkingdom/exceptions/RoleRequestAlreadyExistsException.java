package pl.lodz.p.it.landlordkingdom.exceptions;

public class RoleRequestAlreadyExistsException extends ApplicationBaseException {
    public RoleRequestAlreadyExistsException(String message, String code) {
        super(message, code);
    }

    public RoleRequestAlreadyExistsException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
