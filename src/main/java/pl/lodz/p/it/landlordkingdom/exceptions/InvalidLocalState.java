package pl.lodz.p.it.landlordkingdom.exceptions;

import pl.lodz.p.it.landlordkingdom.model.LocalState;

public class InvalidLocalState extends ApplicationBaseException {

    public InvalidLocalState(String message, String code, LocalState requiredState, LocalState actualState) {
        super(message, code);
    }

    public InvalidLocalState(String message, Throwable cause, String code, LocalState requiredState, LocalState actualState) {
        super(message, cause, code);
    }

    public InvalidLocalState(String message, String code) {
        super(message, code);
    }
}
