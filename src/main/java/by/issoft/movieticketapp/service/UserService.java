package by.issoft.movieticketapp.service;

import by.issoft.movieticketapp.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(Long id);

    User save(User user);

    User update(User user, long id);

    void delete(long id);

    User getUserByUsername(String username);
}