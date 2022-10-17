package by.issoft.movieticketapp.service.impl;

import by.issoft.movieticketapp.exception.EntityNotFoundException;
import by.issoft.movieticketapp.exception.UserCannotBeAdminOfCinemaException;
import by.issoft.movieticketapp.model.Cinema;
import by.issoft.movieticketapp.model.Role;
import by.issoft.movieticketapp.model.User;
import by.issoft.movieticketapp.repository.CinemaRepository;
import by.issoft.movieticketapp.service.CinemaService;
import by.issoft.movieticketapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;
    private final UserService userService;

    @Override
    public Cinema findById(Long cinemaId) {
        return cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema with id " + cinemaId + " not found"));
    }

    @Override
    public List<Cinema> getAll() {
        List<Cinema> cinemas = new ArrayList<>();
        cinemaRepository.findAll().forEach(cinemas::add);
        return cinemas;
    }

    @Override
    public Cinema save(Cinema cinema, Long adminId) {
        User admin = userService.getById(adminId);
        if (!admin.getRoles().stream()
                .map(Role::getAuthority)
                .toList()
                .contains("ROLE_ADMIN")) {
            throw new UserCannotBeAdminOfCinemaException("User " + adminId + " doesn't have authorities to be admin of cinema " + cinema.getId());
        }
        return cinemaRepository.save(cinema);
    }
}
