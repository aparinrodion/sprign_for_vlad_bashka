package by.issoft.movieticketapp.service;

import by.issoft.movieticketapp.model.MovieEvent;

import java.util.List;

public interface MovieEventService {
    List<MovieEvent> getAll();

    MovieEvent save(MovieEvent movieEvent, Long movieRoomId);
}
