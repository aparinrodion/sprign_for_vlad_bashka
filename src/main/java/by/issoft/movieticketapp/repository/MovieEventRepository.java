package by.issoft.movieticketapp.repository;

import by.issoft.movieticketapp.model.MovieEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieEventRepository extends CrudRepository<MovieEvent, Long> {

}
