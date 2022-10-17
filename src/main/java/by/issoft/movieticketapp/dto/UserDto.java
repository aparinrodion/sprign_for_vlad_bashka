package by.issoft.movieticketapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "First name cannot be empty")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;
    @Pattern(regexp = "^[+](375|80)(\\((29|44|33)\\))\\d{3}-\\d{2}-\\d{2}$",
            message = "Phone number is not valid")
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
    @NotBlank(message = "Username cannot be empty")
    private String username;
}