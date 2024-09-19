package dev.razafindratelo.trackmyclass.exceptionHandler;


import lombok.Getter;

@Getter
public final class BadRequestException extends ExceptionHandler {
    public BadRequestException(String message) {
        super("Bad request : " + message);
    }
}
