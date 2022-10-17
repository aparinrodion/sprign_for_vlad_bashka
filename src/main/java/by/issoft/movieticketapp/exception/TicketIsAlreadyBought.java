package by.issoft.movieticketapp.exception;

public class TicketIsAlreadyBought extends RuntimeException {
    public TicketIsAlreadyBought(String message) {
        super(message);
    }
}
