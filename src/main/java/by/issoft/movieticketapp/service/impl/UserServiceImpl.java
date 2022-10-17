package by.issoft.movieticketapp.service.impl;

import by.issoft.movieticketapp.exception.EntityNotFoundException;
import by.issoft.movieticketapp.model.User;
import by.issoft.movieticketapp.repository.UserRepository;
import by.issoft.movieticketapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " doesnt exist"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user, long id) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("User with id " + id + " doesnt exist");
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found"));
    }
}