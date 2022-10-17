package by.issoft.movieticketapp.mapper;

import by.issoft.movieticketapp.dto.UserDto;
import by.issoft.movieticketapp.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUser(UserDto userDto);

    UserDto mapToDto(User user);
}
