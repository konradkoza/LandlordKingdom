package pl.lodz.p.it.landlordkingdom.exceptions;

public class ImageFormatNotSupported extends ApplicationBaseException {
    public ImageFormatNotSupported(String message, String code) {
        super(message, code);
    }

    public ImageFormatNotSupported(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}