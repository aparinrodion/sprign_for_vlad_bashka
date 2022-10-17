package by.issoft.movieticketapp.controller;

import by.issoft.movieticketapp.dto.MovieRoomDto;
import by.issoft.movieticketapp.mapper.MovieRoomMapper;
import by.issoft.movieticketapp.model.MovieRoom;
import by.issoft.movieticketapp.service.MovieRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movie-rooms")
@RequiredArgsConstructor
public class MovieRoomController {
    private final MovieRoomMapper movieRoomMapper;
    private final MovieRoomService movieRoomService;

    @PreAuthorize("@securityServiceImpl.isUserAdminOfCinema(2L, authentication.principal)")
    @GetMapping
    public List<MovieRoomDto> getAll() {
        return movieRoomService.getAll().stream()
                .map(movieRoomMapper::mapToDto)
                .toList();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/test")
    public List<MovieRoomDto> getAlll() {
        return movieRoomService.getAll().stream()
                .map(movieRoomMapper::mapToDto)
                .toList();
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public MovieRoomDto save(@RequestBody @Valid MovieRoomDto movieRoomDto) {
        MovieRoom movieRoomToSave = movieRoomMapper.mapToMovieRoom(movieRoomDto);
        Long cinemaId = movieRoomDto.getCinemaId();
        MovieRoom savedMovieRoom = movieRoomService.save(movieRoomToSave, cinemaId);
        return movieRoomMapper.mapToDto(savedMovieRoom);
    }
}
