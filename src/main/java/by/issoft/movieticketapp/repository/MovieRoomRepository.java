package by.issoft.movieticketapp.repository;

import by.issoft.movieticketapp.model.MovieRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRoomRepository extends CrudRepository<MovieRoom, Long> {
    @Query(value = "select m from movie_rooms m join fetch m.cinema where m.cinema.id=:cinemaId")
    List<MovieRoom> getAllByCinemaId(@Param("cinemaId") Long cinemaId);

    @Query(value = "select m from movie_rooms m join fetch m.cinema where m.id=:id")
    Optional<MovieRoom> getMovieRoomById(@Param("id") Long id);
}
