package by.issoft.movieticketapp.controller;

import by.issoft.movieticketapp.dto.LoginDto;
import by.issoft.movieticketapp.dto.MostActiveUserStatisticsDto;
import by.issoft.movieticketapp.dto.OrderDto;
import by.issoft.movieticketapp.dto.UserDto;
import by.issoft.movieticketapp.mapper.UserMapper;
import by.issoft.movieticketapp.model.User;
import by.issoft.movieticketapp.service.LoginService;
import by.issoft.movieticketapp.service.MostActiveUserStatisticsService;
import by.issoft.movieticketapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final MostActiveUserStatisticsService mostActiveUserStatisticsService;
    private final LoginService loginService;

    @Operation(description = "Get all users")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))))
    @RolesAllowed("ADMIN")
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAll()
                .stream()
                .map(userMapper::mapToDto)
                .toList();
    }

    @Operation(description = "Get user by id")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)))
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public UserDto getUserById(@Parameter(description = "Id of user to get")
                               @PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.mapToDto(user);
    }

    @Operation(description = "Update user by id")
    @ApiResponse(responseCode = "200",
            description = "User updated",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)))
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping("/{id}")
    public UserDto updateUserById(@Parameter(description = "Id of user to update")
                                  @PathVariable Long id,
                                  @RequestBody @Valid UserDto userDto) {
        User userForUpdate = userMapper.mapToUser(userDto);
        User user = userService.update(userForUpdate, id);
        return userMapper.mapToDto(user);
    }

    @Operation(summary = "Save user")
    @ApiResponse(responseCode = "200",
            description = "User saved",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrderDto.class))})
    @PostMapping
    public UserDto saveUser(@RequestBody @Valid UserDto userDto) {
        User userToSave = userMapper.mapToUser(userDto);
        User user = userService.save(userToSave);
        return userMapper.mapToDto(user);
    }

    @Operation(summary = "Delete user")
    @ApiResponse(responseCode = "200",
            description = "User deleted")
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteUser(@Parameter(description = "Id of user to delete") @PathVariable Long id) {
        userService.delete(id);
    }

    @Operation(summary = "Get most active user statistics")
    @ApiResponse(responseCode = "200",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MostActiveUserStatisticsDto.class))})
    @GetMapping("/stats/{movieRoomNumber}")
    public MostActiveUserStatisticsDto getStats(@PathVariable Integer movieRoomNumber) {
        return mostActiveUserStatisticsService.mostActiveUsersInLastMonth(movieRoomNumber);
    }

    @GetMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        return loginService.loginAndGetJwt(loginDto);
    }
}