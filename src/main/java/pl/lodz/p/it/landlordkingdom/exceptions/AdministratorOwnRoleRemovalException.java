package pl.lodz.p.it.landlordkingdom.exceptions;

public class AdministratorOwnRoleRemovalException extends ApplicationBaseException {
    public AdministratorOwnRoleRemovalException(String message, String code) {
        super(message, code);
    }

    public AdministratorOwnRoleRemovalException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
