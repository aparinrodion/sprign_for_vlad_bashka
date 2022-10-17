package by.issoft.movieticketapp.controller;

import by.issoft.movieticketapp.dto.MovieEventDto;
import by.issoft.movieticketapp.dto.TicketDto;
import by.issoft.movieticketapp.mapper.MovieEventMapper;
import by.issoft.movieticketapp.mapper.TicketMapper;
import by.issoft.movieticketapp.model.MovieEvent;
import by.issoft.movieticketapp.service.MovieEventService;
import by.issoft.movieticketapp.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movie-room-event")
@RequiredArgsConstructor
public class MovieEventController {
    private final TicketMapper ticketMapper;
    private final MovieEventMapper movieRoomMapper;
    private final MovieEventService movieEventService;
    private final TicketService ticketService;

    @Operation(description = "Get all movie room events")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MovieEventDto.class)))
    })
    @GetMapping
    public List<MovieEventDto> getAll() {
        return movieEventService.getAll().stream()
                .map(movieRoomMapper::mapToDto)
                .toList();
    }

    @Operation(description = "Get available tickets to the movie room event")
    @GetMapping("/free/{id}")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TicketDto.class)))
    })
    public List<TicketDto> getFreeTicketsForMovieRoomEvent(@Parameter(description = "Id of movie room event")
                                                           @PathVariable Long id) {
        return ticketService.getFreeTicketsForMovieRoom(id).stream()
                .map(ticketMapper::mapToTicketDto)
                .toList();
    }

    @Operation(summary = "Save movie room event")
    @ApiResponse(responseCode = "200", description = "Movie room event saved",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MovieEventDto.class))})
    @Secured("ROLE_ADMIN")
    @PostMapping
    public MovieEventDto save(@RequestBody @Valid MovieEventDto movieEventDto) {
        MovieEvent movieEventToSave = movieRoomMapper.mapToMovieRoom(movieEventDto);
        Long movieRoomId = movieEventDto.getMovieRoomId();
        MovieEvent savedMovieEvent = movieEventService.save(movieEventToSave, movieRoomId);
        return movieRoomMapper.mapToDto(savedMovieEvent);
    }
}
