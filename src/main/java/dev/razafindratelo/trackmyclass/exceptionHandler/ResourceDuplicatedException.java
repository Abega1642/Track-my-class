package dev.razafindratelo.trackmyclass.exceptionHandler;

public final class ResourceDuplicatedException extends ExceptionHandler {
    public ResourceDuplicatedException(String message) {
        super("ResourceDuplicated : " + message);
    }
}
