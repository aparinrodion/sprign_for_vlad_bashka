package by.issoft.movieticketapp.controller;

import by.issoft.movieticketapp.dto.CinemaDto;
import by.issoft.movieticketapp.dto.MovieRoomDto;
import by.issoft.movieticketapp.mapper.CinemaMapper;
import by.issoft.movieticketapp.mapper.MovieRoomMapper;
import by.issoft.movieticketapp.model.Cinema;
import by.issoft.movieticketapp.service.CinemaService;
import by.issoft.movieticketapp.service.MovieRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;
    private final MovieRoomService movieRoomService;
    private final CinemaMapper cinemaMapper;
    private final MovieRoomMapper movieRoomMapper;

    @GetMapping
    public List<CinemaDto> getAll() {
        return cinemaService.getAll().stream()
                .map(cinemaMapper::mapToDto)
                .toList();
    }

    @PostMapping
    public CinemaDto save(@RequestBody @Valid CinemaDto cinemaDto) {
        Cinema cinemaToSave = cinemaMapper.mapToCinema(cinemaDto);
        Long adminId = cinemaDto.getAdminId();
        Cinema savedCinema = cinemaService.save(cinemaToSave, adminId);
        return cinemaMapper.mapToDto(savedCinema);
    }

    @GetMapping("{id}/movie-rooms")
    public List<MovieRoomDto> getMovieRoomsByCinemaId(@PathVariable Long id) {
        return movieRoomService.getAllByCinemaId(id).stream()
                .map(movieRoomMapper::mapToDto)
                .toList();
    }
}
