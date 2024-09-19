package dev.razafindratelo.trackmyclass.exceptionHandler;

import lombok.Getter;

@Getter
public final class ResourceNotFoundException extends ExceptionHandler {
    public ResourceNotFoundException(String message) {
        super("Resource not found exception : " + message);
    }
}
