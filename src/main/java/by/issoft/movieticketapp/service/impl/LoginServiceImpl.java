package by.issoft.movieticketapp.service.impl;

import by.issoft.movieticketapp.dto.LoginDto;
import by.issoft.movieticketapp.exception.WrongLoginCredentialsException;
import by.issoft.movieticketapp.model.User;
import by.issoft.movieticketapp.security.JwtProvider;
import by.issoft.movieticketapp.service.LoginService;
import by.issoft.movieticketapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Override
    public String loginAndGetJwt(LoginDto loginDto) {
        User user = userService.getUserByUsername(loginDto.getUsername());
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return jwtProvider.generateToken(user.getUsername());
        }
        throw new WrongLoginCredentialsException("Wrong login credentials " + loginDto.getUsername() + " " + loginDto.getUsername());
    }
}
