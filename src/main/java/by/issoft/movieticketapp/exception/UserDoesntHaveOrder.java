package by.issoft.movieticketapp.exception;

public class UserDoesntHaveOrder extends RuntimeException {
    public UserDoesntHaveOrder(String message) {
        super(message);
    }
}
