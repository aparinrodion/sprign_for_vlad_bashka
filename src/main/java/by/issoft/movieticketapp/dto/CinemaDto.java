package by.issoft.movieticketapp.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CinemaDto {
    private Long id;
    @NotBlank(message = "cinema name cannot be empty")
    private String name;
    @NotBlank(message = "cinema address cannot be empty")
    private String address;
    @NotNull
    private Long adminId;
}
