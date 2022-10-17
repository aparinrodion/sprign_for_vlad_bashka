package by.issoft.movieticketapp.mapper;

import by.issoft.movieticketapp.dto.MovieRoomDto;
import by.issoft.movieticketapp.model.Cinema;
import by.issoft.movieticketapp.model.MovieRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MovieRoomMapper {
    @Mapping(target = "cinema", ignore = true)
    MovieRoom mapToMovieRoom(MovieRoomDto movieRoomDto);

    @Mapping(target = "cinemaId", source = "cinema", qualifiedByName = "getCinemaId")
    MovieRoomDto mapToDto(MovieRoom movieRoom);

    @Named("getCinemaId")
    static Long getCinemaId(Cinema cinema) {
        return cinema.getId();
    }
}
