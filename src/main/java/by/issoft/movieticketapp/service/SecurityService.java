package by.issoft.movieticketapp.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityService {
    boolean isUserAdminOfCinema(Long cinemaId, UserDetails principal);
}
