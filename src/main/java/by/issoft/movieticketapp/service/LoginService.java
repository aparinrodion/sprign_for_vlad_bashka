package by.issoft.movieticketapp.service;

import by.issoft.movieticketapp.dto.LoginDto;

public interface LoginService {
    String loginAndGetJwt(LoginDto loginDto);
}
