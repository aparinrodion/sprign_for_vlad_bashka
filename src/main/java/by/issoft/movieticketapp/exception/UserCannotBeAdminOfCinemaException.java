package by.issoft.movieticketapp.exception;

public class UserCannotBeAdminOfCinemaException extends RuntimeException {
    public UserCannotBeAdminOfCinemaException(String message) {
        super(message);
    }
}
