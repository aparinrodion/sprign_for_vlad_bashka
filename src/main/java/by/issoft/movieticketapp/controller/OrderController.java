package by.issoft.movieticketapp.controller;

import by.issoft.movieticketapp.dto.OrderDto;
import by.issoft.movieticketapp.mapper.OrderMapper;
import by.issoft.movieticketapp.model.Order;
import by.issoft.movieticketapp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Operation(summary = "Create ticket order")
    @ApiResponse(responseCode = "200", description = "Order created",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrderDto.class))})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping
    public OrderDto save(@RequestBody @Valid OrderDto orderDto, Principal principal) {
        String username = principal.getName();
        Long ticketId = orderDto.getTicketId();
        Order order = orderService.save(ticketId, username);
        return orderMapper.mapToDto(order);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    public List<OrderDto> getUsersOrders(Principal principal) {
        String username = principal.getName();
        List<Order> orders = orderService.getOrdersByUsername(username);
        return orders.stream()
                .map(orderMapper::mapToDto)
                .toList();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/cancel/{id}")
    public void cancelOrder(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        orderService.cancelOrder(username, id);
    }
}