package by.issoft.movieticketapp.repository;

import by.issoft.movieticketapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from orders o join fetch o.user join fetch o.ticket where o.id=:id")
    Order findByIdFetchUserAndTicket(@Param("id") Long id);

    @Query("select o from orders o " +
            "join fetch o.ticket " +
            "join fetch o.user " +
            "join o.ticket.movieEvent " +
            "join o.ticket.movieEvent.movieRoom")
    List<Order> getAllOrders();

    @Query("select o from orders o " +
            "join fetch o.user " +
            "join fetch o.ticket " +
            "join o.ticket.movieEvent " +
            "where o.user.username=:username")
    List<Order> getOrdersByUsername(@Param("username") String username);

    boolean existsByIdAndUserUsername(Long id, String username);
}