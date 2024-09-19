package dev.razafindratelo.trackmyclass.exceptionHandler;


public sealed class ExceptionHandler extends RuntimeException
        permits
        BadRequestException,
        InternalException,
        NotImplementedException,
        ResourceDuplicatedException,
        ResourceNotFoundException
    {
    public ExceptionHandler(String message) {
        super(message);
    }
}
