package by.issoft.movieticketapp.service.impl;

import by.issoft.movieticketapp.exception.EntityNotFoundException;
import by.issoft.movieticketapp.model.MovieEvent;
import by.issoft.movieticketapp.model.Ticket;
import by.issoft.movieticketapp.repository.TicketRepository;
import by.issoft.movieticketapp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> getFreeTicketsForMovieRoom(Long movieRoomEventId) {
        return ticketRepository.getFreeTickets(movieRoomEventId);
    }

    @Override
    public void fillMovieRoom(MovieEvent movieEvent) {
        int capacity = movieEvent.getMovieRoom().getCapacity();

        int seatsInRow = (int) Math.ceil(Math.sqrt(capacity));
        for (int i = 0; i < capacity; i++) {
            int seatRow = i / seatsInRow + 1;
            int seatNumber = i % seatsInRow + 1;

            Ticket ticket = Ticket.builder()
                    .seatRow(seatRow)
                    .seatNumber(seatNumber)
                    .movieEvent(movieEvent)
                    .build();
            ticketRepository.save(ticket);
        }
    }

    @Override
    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket with id " + ticketId + " not found"));
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}