package dev.razafindratelo.trackmyclass.exceptionHandler;


import lombok.Getter;

@Getter
public final class IllegalRequestException extends ExceptionHandler {
    public IllegalRequestException(String message) {
        super("Illegal request : " + message);
    }
}
