package by.issoft.movieticketapp.service;

import by.issoft.movieticketapp.model.Cinema;

import java.util.List;

public interface CinemaService {
    Cinema findById(Long cinemaId);

    List<Cinema> getAll();

    Cinema save(Cinema cinema, Long adminId);
}
