package dev.razafindratelo.trackmyclass.exceptionHandler;


public sealed class ExceptionHandler extends RuntimeException
        permits BadRequestException,
        NotImplementedException,
        ResourceNotFoundException
    {
    public ExceptionHandler(String message) {
        super(message);
    }
}
