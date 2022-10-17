package by.issoft.movieticketapp.mapper;

import by.issoft.movieticketapp.dto.OrderDto;
import by.issoft.movieticketapp.model.Order;
import by.issoft.movieticketapp.model.Ticket;
import by.issoft.movieticketapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "ticketId", source = "ticket", qualifiedByName = "getTicketId")
    @Mapping(target = "userId", source = "user", qualifiedByName = "getUserId")
    OrderDto mapToDto(Order order);

    @Named("getTicketId")
    static Long getTicketId(Ticket ticket) {
        return ticket.getId();
    }

    @Named("getUserId")
    static Long getUserId(User user) {
        return user.getId();
    }
}

