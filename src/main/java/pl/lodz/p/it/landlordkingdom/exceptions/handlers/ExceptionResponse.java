package pl.lodz.p.it.landlordkingdom.exceptions.handlers;

public record ExceptionResponse(
        String message,
        String exceptionCode
) {
}
