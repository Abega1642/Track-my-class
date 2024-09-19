package dev.razafindratelo.trackmyclass.exceptionHandler;

public final class ResourceDuplicated extends ExceptionHandler {
    public ResourceDuplicated(String message) {
        super("ResourceDuplicated : " + message);
    }
}
