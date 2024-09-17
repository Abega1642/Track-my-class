package dev.razafindratelo.trackmyclass.exceptionHandler;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
