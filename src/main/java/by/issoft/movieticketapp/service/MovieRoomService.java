package by.issoft.movieticketapp.service;

import by.issoft.movieticketapp.model.MovieRoom;

import java.util.List;
//TODO find movie events by cinema
public interface MovieRoomService {
    List<MovieRoom> getAll();

    MovieRoom save(MovieRoom movieRoom, Long cinemaId);

    List<MovieRoom> getAllByCinemaId(Long cinemaId);

    MovieRoom getById(Long id);
}
