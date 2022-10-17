package by.issoft.movieticketapp.service;

import by.issoft.movieticketapp.model.Order;

import java.util.List;

public interface OrderService {
    Order save(Long ticketId, String username);

    List<Order> getAll();

    List<Order> getOrdersByUsername(String username);

    void cancelOrder(String username, Long orderId);
}
