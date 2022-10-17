package by.issoft.movieticketapp.service.impl;

import by.issoft.movieticketapp.exception.EntityNotFoundException;
import by.issoft.movieticketapp.model.Cinema;
import by.issoft.movieticketapp.model.MovieRoom;
import by.issoft.movieticketapp.repository.MovieRoomRepository;
import by.issoft.movieticketapp.service.CinemaService;
import by.issoft.movieticketapp.service.MovieRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieRoomServiceImpl implements MovieRoomService {
    private final MovieRoomRepository movieRoomRepository;
    private final CinemaService cinemaService;

    @Override
    public List<MovieRoom> getAll() {
        List<MovieRoom> movieRooms = new ArrayList<>();
        movieRoomRepository.findAll().forEach(movieRooms::add);
        return movieRooms;
    }

    @Override
    public MovieRoom save(MovieRoom movieRoom, Long cinemaId) {
        Cinema cinema = cinemaService.findById(cinemaId);
        movieRoom.setCinema(cinema);
        return movieRoomRepository.save(movieRoom);
    }

    @Override
    public List<MovieRoom> getAllByCinemaId(Long cinemaId) {
        return movieRoomRepository.getAllByCinemaId(cinemaId);
    }

    @Override
    public MovieRoom getById(Long id) {
        return movieRoomRepository.getMovieRoomById(id).orElseThrow(() -> new EntityNotFoundException("MovieRoom with id " + id + " not found"));
    }
}
