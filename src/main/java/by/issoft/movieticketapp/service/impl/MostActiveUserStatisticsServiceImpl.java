package by.issoft.movieticketapp.service.impl;

import by.issoft.movieticketapp.dto.MostActiveUserStatisticsDto;
import by.issoft.movieticketapp.model.MovieEvent;
import by.issoft.movieticketapp.model.Order;
import by.issoft.movieticketapp.model.User;
import by.issoft.movieticketapp.service.MostActiveUserStatisticsService;
import by.issoft.movieticketapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MostActiveUserStatisticsServiceImpl implements MostActiveUserStatisticsService {
    private final OrderService orderService;

    @Override
    public MostActiveUserStatisticsDto mostActiveUsersInLastMonth(int movieRoomNumber) {
        List<Order> orders = orderService.getAll();
        LocalDate currentDate = LocalDate.now();

        LocalDate prevMonthDate = currentDate.minusMonths(1);
        LocalDate firstDay = prevMonthDate.withDayOfMonth(1);
        LocalDate lastDay = prevMonthDate.withDayOfMonth(prevMonthDate.lengthOfMonth());

        Month prevMonth = prevMonthDate.getMonth();
        int prevYear = prevMonthDate.getYear();

        Map<User, Long> userAndVisits = orders.stream()
                .filter(order -> isEventInPrevMonth(order, prevMonth, prevYear))
                .filter(order -> isEventInMovieRoom(order, movieRoomNumber))
                .collect(Collectors.groupingBy(Order::getUser, Collectors.counting()));

        Map.Entry<User, Long> mostActiveUserAndCount = Collections.max(userAndVisits.entrySet(), Map.Entry.comparingByValue());
        User mostActiveUser = mostActiveUserAndCount.getKey();
        long numberOfVisits = mostActiveUserAndCount.getValue();

        return MostActiveUserStatisticsDto.builder()
                .firstName(mostActiveUser.getFirstName())
                .lastName(mostActiveUser.getLastName())
                .email(mostActiveUser.getEmail())
                .numberOfVisits(numberOfVisits)
                .startPeriod(firstDay)
                .endPeriod(lastDay)
                .movieRoomNumber(movieRoomNumber)
                .build();
    }

    private boolean isEventInMovieRoom(Order order, int movieRoomNumber) {
        MovieEvent movieEvent = order.getTicket().getMovieEvent();
        return movieEvent.getMovieRoom().getMovieRoomNumber() == movieRoomNumber;
    }

    private boolean isEventInPrevMonth(Order order, Month prevMonth, int prevYear) {
        LocalDateTime eventDateTime = order.getTicket().getMovieEvent().getDateTime();
        return eventDateTime.getMonth() == prevMonth
                && eventDateTime.getYear() == prevYear;
    }
}