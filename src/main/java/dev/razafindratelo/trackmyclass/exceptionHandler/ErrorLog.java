package dev.razafindratelo.trackmyclass.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class ErrorLog {
    private int status;
    private String message;
    private String stackTrace;
    private ExceptionHandlerType type;

}
