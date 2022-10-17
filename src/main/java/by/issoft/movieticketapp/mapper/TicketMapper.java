package by.issoft.movieticketapp.mapper;

import by.issoft.movieticketapp.dto.TicketDto;
import by.issoft.movieticketapp.model.MovieEvent;
import by.issoft.movieticketapp.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "movieRoomEventId", source = "movieEvent", qualifiedByName = "getMovieRoomEventId")
    TicketDto mapToTicketDto(Ticket ticket);

    @Named("getMovieRoomEventId")
    static Long getMovieRoomEventId(MovieEvent movieEvent) {
        return movieEvent.getId();
    }
}