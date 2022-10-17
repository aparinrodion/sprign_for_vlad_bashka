package by.issoft.movieticketapp.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Builder
@Data
public class MostActiveUserStatisticsDto {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "movie_room_number")
    private Integer movieRoomNumber;
    @Column(name = "number_of_visits")
    private Long numberOfVisits;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
}
