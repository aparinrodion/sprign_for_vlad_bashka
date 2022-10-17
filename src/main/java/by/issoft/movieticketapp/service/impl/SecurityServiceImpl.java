package by.issoft.movieticketapp.service.impl;

import by.issoft.movieticketapp.model.Cinema;
import by.issoft.movieticketapp.model.User;
import by.issoft.movieticketapp.service.CinemaService;
import by.issoft.movieticketapp.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {
    private final CinemaService cinemaService;

    @Override
    public boolean isUserAdminOfCinema(Long cinemaId, UserDetails principal) {
        Cinema cinema = cinemaService.findById(cinemaId);
        User admin = cinema.getAdmin();
        String username = principal.getUsername();
        log.info(admin.getUsername());
        log.info(username);
        return admin.getUsername().equals(username);
    }
}
