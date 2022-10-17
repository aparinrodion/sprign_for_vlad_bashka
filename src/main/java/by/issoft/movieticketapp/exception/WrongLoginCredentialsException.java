package by.issoft.movieticketapp.exception;

public class WrongLoginCredentialsException extends RuntimeException {
    public WrongLoginCredentialsException(String message) {
        super(message);
    }
}
