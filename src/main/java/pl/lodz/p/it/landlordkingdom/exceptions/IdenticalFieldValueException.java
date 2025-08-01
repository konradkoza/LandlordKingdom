package pl.lodz.p.it.landlordkingdom.exceptions;

import lombok.Getter;

@Getter
public class IdenticalFieldValueException extends ApplicationBaseException {

    public IdenticalFieldValueException(String message, String code) {
        super(message, code);
    }

    public IdenticalFieldValueException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
