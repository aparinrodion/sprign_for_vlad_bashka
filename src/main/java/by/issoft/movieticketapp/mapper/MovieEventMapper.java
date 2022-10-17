package by.issoft.movieticketapp.mapper;

import by.issoft.movieticketapp.dto.MovieEventDto;
import by.issoft.movieticketapp.model.MovieEvent;
import by.issoft.movieticketapp.model.MovieRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MovieEventMapper {
    @Mapping(target = "movieRoom", ignore = true)
    MovieEvent mapToMovieRoom(MovieEventDto movieEventDto);

    @Mapping(target = "movieRoomId", source = "movieRoom", qualifiedByName = "getMovieRoomId")
    MovieEventDto mapToDto(MovieEvent movieEvent);

    @Named("getMovieRoomId")
    static Long getMovieRoomId(MovieRoom movieRoom) {
        return movieRoom.getId();
    }
}
