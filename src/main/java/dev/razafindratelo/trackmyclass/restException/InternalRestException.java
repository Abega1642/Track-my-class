package dev.razafindratelo.trackmyclass.restException;

import dev.razafindratelo.trackmyclass.exceptionHandler.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class InternalRestException {
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e, WebRequest request) {
        log.error("Bad request", e);
        ErrorLog err = new ErrorLog(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(), request.getDescription(false),
                ExceptionHandlerType.CLIENT_EXCEPTION
        );
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        log.error("Resource not found : ", e);
        ErrorLog err = new ErrorLog(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                request.getDescription(false),
                ExceptionHandlerType.CLIENT_EXCEPTION
        );
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ResourceDuplicatedException.class)
    public ResponseEntity<Object> handleResourceDuplicatedException(ResourceDuplicatedException e, WebRequest request) {
        log.error("Resource duplicated : ", e);
        ErrorLog err = new ErrorLog(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getDescription(false),
                ExceptionHandlerType.CLIENT_EXCEPTION
        );
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }
}
