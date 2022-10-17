package by.issoft.movieticketapp.repository;

import by.issoft.movieticketapp.model.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    @Query("select t from tickets t " +
            "join fetch t.movieEvent " +
            "join t.movieEvent.movieRoom " +
            "where t.movieEvent.movieRoom.id =:movieRoomId " +
            "and t.id not in (select ticket.id from orders )")
    List<Ticket> getFreeTickets(@Param("movieRoomId") Long movieRoomId);
}
