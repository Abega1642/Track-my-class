package dev.razafindratelo.trackmyclass.exceptionHandler;

import lombok.Getter;

@Getter
public final class NotImplementedException extends ExceptionHandler {
    public NotImplementedException(String message) {
        super(message);
    }
}
