package dev.razafindratelo.trackmyclass.exceptionHandler;

public final class InternalException extends ExceptionHandler {
    public InternalException(String message) {
        super("Internal error : " + message);
    }
}
