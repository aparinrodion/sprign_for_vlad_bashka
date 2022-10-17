package by.issoft.movieticketapp.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class MovieRoomDto {
    private Long id;
    @NotNull
    private Integer movieRoomNumber;
    @NotNull
    private Integer capacity;
    @NotNull
    private Long cinemaId;
}
