package by.issoft.movieticketapp;

import by.issoft.movieticketapp.model.MovieEvent;
import by.issoft.movieticketapp.model.MovieRoom;
import by.issoft.movieticketapp.repository.MovieEventRepository;
import by.issoft.movieticketapp.service.MovieRoomService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieEventControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieEventRepository movieEventRepository;
    @Autowired
    private MovieRoomService movieRoomService;

    @Test
    public void getAll_ReturnsAllMovieRoomEvents() throws Exception {
        String expectedAnswer = "[" +
                "{" +
                "\"id\":1," +
                "\"movieRoomId\":1," +
                "\"filmName\":\"Filth\"," +
                "\"dateTime\":\"2022-09-15 21:30\"" +
                "}," +
                "{" +
                "\"id\":2," +
                "\"movieRoomId\":1," +
                "\"filmName\":\"Split\"," +
                "\"dateTime\":\"2022-09-16 22:30\"" +
                "}," +
                "{" +
                "\"id\":3," +
                "\"movieRoomId\":3," +
                "\"filmName\":\"The French Dispatch\"," +
                "\"dateTime\":\"2022-09-20 21:30\"" +
                "}" +
                "]";

        mockMvc.perform(get("/movie-room-event"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(expectedAnswer));
    }

    @Test
    public void getFreeTickets_ReturnsFreeTicketsForMovieRoomEvent() throws Exception {
        String expectedAnswer = """
                [
                     {
                         "id": 14,
                         "movieRoomEventId": 3,
                         "seatRow": 1,
                         "seatNumber": 4
                     }
                 ]
                 """.replaceAll("\\s", "");

        mockMvc.perform(get("/movie-room-event/free/3"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(expectedAnswer));
    }

    @Test
    public void save_ReturnsMovieRoomEvent() throws Exception {
        String movieRoomEventToSave = """
                {
                    "movieRoomId":2,
                    "filmName":"Fast and Furious 2",
                    "capacity":3,
                    "dateTime":"2022-10-10 14:30"
                }
                """;
        LocalDateTime dateTime = LocalDateTime.of(2022, 10, 10, 14, 30, 0);

        MovieRoom movieRoom = movieRoomService.getById(2L);

        MovieEvent expectedMovieEvent = MovieEvent.builder()
                .movieRoom(movieRoom)
                .filmName("Fast and Furious 2")
                .dateTime(dateTime)
                .build();

        String response = this.mockMvc.perform(post("/movie-room-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(movieRoomEventToSave))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer id = JsonPath.read(response, "$.id");
        Long longId = id.longValue();

        expectedMovieEvent.setId(longId);
        assertTrue(movieEventRepository.findById(longId).isPresent());
        MovieEvent savedMovieEvent = movieEventRepository.findById(longId).get();

        assertEquals(expectedMovieEvent, savedMovieEvent);
    }
}
