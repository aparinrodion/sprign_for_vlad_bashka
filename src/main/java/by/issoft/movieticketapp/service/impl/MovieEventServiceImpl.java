package by.issoft.movieticketapp.service.impl;

import by.issoft.movieticketapp.model.MovieEvent;
import by.issoft.movieticketapp.model.MovieRoom;
import by.issoft.movieticketapp.repository.MovieEventRepository;
import by.issoft.movieticketapp.service.MovieEventService;
import by.issoft.movieticketapp.service.MovieRoomService;
import by.issoft.movieticketapp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieEventServiceImpl implements MovieEventService {
    private final MovieEventRepository movieEventRepository;
    private final TicketService ticketService;
    private final MovieRoomService movieRoomService;

    @Override
    public List<MovieEvent> getAll() {
        List<MovieEvent> movieEvents = new ArrayList<>();
        movieEventRepository.findAll().forEach(movieEvents::add);
        return movieEvents;
    }

    @Transactional
    @Override
    public MovieEvent save(MovieEvent movieEvent, Long movieRoomId) {
        MovieRoom movieRoom = movieRoomService.getById(movieRoomId);
        movieEvent.setMovieRoom(movieRoom);
        MovieEvent savedMovieEvent = movieEventRepository.save(movieEvent);
        ticketService.fillMovieRoom(movieEvent);
        return savedMovieEvent;
    }
}