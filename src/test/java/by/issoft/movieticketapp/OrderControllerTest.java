package by.issoft.movieticketapp;

import by.issoft.movieticketapp.model.Order;
import by.issoft.movieticketapp.model.Ticket;
import by.issoft.movieticketapp.model.User;
import by.issoft.movieticketapp.repository.OrderRepository;
import by.issoft.movieticketapp.service.TicketService;
import by.issoft.movieticketapp.service.UserService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;

    @Test
    public void saveOrder_ReturnsSavedOrder() throws Exception {
        String orderToSave = """
                {
                    "ticketId": 14,
                    "userId": 1
                }
                """;
        User user = userService.getById(1L);
        Ticket ticket = ticketService.getTicketById(14L);

        Order expectedOrder = Order.builder()
                .user(user)
                .ticket(ticket)
                .creationDateTime(LocalDateTime.now())
                .build();

        String response = this.mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(orderToSave))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer id = JsonPath.read(response, "$.id");
        Long longId = id.longValue();

        Order savedOrder = orderRepository.findByIdFetchUserAndTicket(longId);
        expectedOrder.setId(longId);

        assertEquals(expectedOrder.getUser(), savedOrder.getUser());
        assertEquals(expectedOrder.getTicket(), savedOrder.getTicket());
        assertEquals(expectedOrder.getCreationDateTime().truncatedTo(ChronoUnit.SECONDS),
                savedOrder.getCreationDateTime().truncatedTo(ChronoUnit.SECONDS));
    }
}
