package by.issoft.movieticketapp.service;

import by.issoft.movieticketapp.dto.MostActiveUserStatisticsDto;

public interface MostActiveUserStatisticsService {
    MostActiveUserStatisticsDto mostActiveUsersInLastMonth(int movieRoomNumber);
}
