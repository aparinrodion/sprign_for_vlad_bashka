package by.issoft.movieticketapp.service;

import by.issoft.movieticketapp.model.MovieEvent;
import by.issoft.movieticketapp.model.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> getFreeTicketsForMovieRoom(Long movieRoomEventId);

    void fillMovieRoom(MovieEvent movieEvent);

    Ticket getTicketById(Long ticketId);

    Ticket save(Ticket ticket);
}
