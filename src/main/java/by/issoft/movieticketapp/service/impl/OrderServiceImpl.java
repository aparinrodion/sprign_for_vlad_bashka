package by.issoft.movieticketapp.service.impl;

import by.issoft.movieticketapp.exception.UserDoesntHaveOrder;
import by.issoft.movieticketapp.model.Order;
import by.issoft.movieticketapp.model.Ticket;
import by.issoft.movieticketapp.model.User;
import by.issoft.movieticketapp.repository.OrderRepository;
import by.issoft.movieticketapp.service.OrderService;
import by.issoft.movieticketapp.service.TicketService;
import by.issoft.movieticketapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final TicketService ticketService;

    @Transactional
    @Override
    public Order save(Long ticketId, String username) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        User user = userService.getUserByUsername(username);
        ticket = ticketService.save(ticket);
        Order order = new Order();
        order.setTicket(ticket);
        order.setUser(user);
        order.setCreationDateTime(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.getAllOrders();
    }

    @Override
    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.getOrdersByUsername(username);
    }

    @Override
    public void cancelOrder(String username, Long orderId) {
        if (!orderRepository.existsByIdAndUserUsername(orderId, username)) {
            throw new UserDoesntHaveOrder("User " + username + " doesnt have order " + orderId);
        }
        orderRepository.deleteById(orderId);
    }


}