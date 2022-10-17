package by.issoft.movieticketapp.repository;

import by.issoft.movieticketapp.model.Cinema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Long> {
}
