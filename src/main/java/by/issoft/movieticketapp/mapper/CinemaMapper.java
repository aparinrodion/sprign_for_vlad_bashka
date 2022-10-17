package by.issoft.movieticketapp.mapper;

import by.issoft.movieticketapp.dto.CinemaDto;
import by.issoft.movieticketapp.model.Cinema;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    Cinema mapToCinema(CinemaDto cinemaDto);

    CinemaDto mapToDto(Cinema cinema);
}
