package by.issoft.movieticketapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto {
    private Long id;
    @NotNull
    private Long movieRoomEventId;
    @NotNull
    private Integer seatRow;
    @NotNull
    private Integer seatNumber;
}
