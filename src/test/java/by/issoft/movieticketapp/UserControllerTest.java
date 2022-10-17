package by.issoft.movieticketapp;

import by.issoft.movieticketapp.model.User;
import by.issoft.movieticketapp.repository.UserRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAll_ReturnsAllUsers() throws Exception {
        String getAllExpectedAnswer = """
                [
                    {
                        "id": 1,
                        "email": "ivan_ivanov@gmail.com",
                        "firstName": "ivan",
                        "lastName": "ivanov",
                        "phoneNumber": "+375(29)123-45-67",
                        "username": "ivan_444"
                    },
                    {
                        "id": 2,
                        "email": "anton_antonov@gmail.com",
                        "firstName": "anton",
                        "lastName": "antonov",
                        "phoneNumber": "+375(29)765-43-21",
                        "username": "anton_anton_anton"
                    },
                    {
                        "id": 3,
                        "email": "petr_petrov@gmail.com",
                        "firstName": "petr",
                        "lastName": "petrov",
                        "phoneNumber": "+375(29)333-22-66",
                        "username": "petr_petrov123"
                    }
                ]"""
                .replaceAll("\\s", "");

        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(getAllExpectedAnswer));
    }

    @Test
    public void getUserById_ReturnsExistingUser() throws Exception {
        String getUserByIdExpectedAnswer_1 = """
                {
                        "id": 1,
                        "email": "ivan_ivanov@gmail.com",
                        "firstName": "ivan",
                        "lastName": "ivanov",
                        "phoneNumber": "+375(29)123-45-67",
                        "username": "ivan_444"
                }"""
                .replaceAll("\\s", "");
        String getUserByIdExpectedAnswer_2 = """
                {
                        "id": 2,
                        "email": "anton_antonov@gmail.com",
                        "firstName": "anton",
                        "lastName": "antonov",
                        "phoneNumber": "+375(29)765-43-21",
                        "username": "anton_anton_anton"
                }"""
                .replaceAll("\\s", "");


        this.mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(getUserByIdExpectedAnswer_1));
        this.mockMvc.perform(get("/users/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(getUserByIdExpectedAnswer_2));
    }

    @Test
    public void getUserById_ReturnsErrorMessage() throws Exception {
        int notExistingId = 100;

        this.mockMvc.perform(get("/users/" + notExistingId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format("User with id %d doesnt exist", notExistingId)));
    }

    @Test
    public void updateUserById_ReturnsUpdatedUser() throws Exception {
        String updatedUser = """
                {
                        "id": 3,
                        "email": "petr_petrov_new_email@gmail.com",
                        "firstName": "petr",
                        "lastName": "petrov",
                        "phoneNumber": "+375(29)333-22-66",
                        "username": "petr_petrov123"
                    }
                """.replaceAll("\\s", "");
        User expectedUpdatedUser = User.builder()
                .id(3L)
                .firstName("petr")
                .lastName("petrov")
                .email("petr_petrov_new_email@gmail.com")
                .phoneNumber("+375(29)333-22-66")
                .username("petr_petrov123")
                .build();

        this.mockMvc.perform(put("/users/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(updatedUser))
                .andExpect(status().isOk());

        assertTrue(userRepository.findById(3L).isPresent());
        User user = userRepository.findById(3L).get();
        assertEquals(expectedUpdatedUser, user);

        User userBeforeUpdate = User.builder()
                .id(3L)
                .firstName("petr")
                .lastName("petrov")
                .email("petr_petrov@gmail.com")
                .phoneNumber("+375(29)333-22-66")
                .username("petr_petrov123")
                .build();
        userRepository.save(userBeforeUpdate);
    }

    @Test
    public void updateUserByNotExistingId_ReturnsErrorMessage() throws Exception {
        String updatedUser = """
                {
                        "id":200,
                        "email": "user3_new_email@gmail.com",
                        "firstName": "user3_new_firstName",
                        "lastName": "user3_lastName",
                        "phoneNumber": "+80(44)200-22-99",
                        "username": "user3_username"
                    }
                """.replaceAll("\\s", "");

        this.mockMvc.perform(put("/users/200")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(updatedUser))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User with id 200 doesnt exist"));
    }

    @Test
    public void saveUser_ReturnsSavedUser() throws Exception {
        String userToSave = """
                {
                        "email": "kirill_kirillov@gmail.com",
                        "firstName": "kirill",
                        "lastName": "kirillov",
                        "phoneNumber": "+80(44)444-44-44",
                        "username": "kirillll"
                    }
                """
                .replaceAll("\\s", "");
        User expectedUser = User.builder()
                .firstName("kirill")
                .lastName("kirillov")
                .email("kirill_kirillov@gmail.com")
                .phoneNumber("+80(44)444-44-44")
                .username("kirillll")
                .build();

        String response = this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(userToSave))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer id = JsonPath.read(response, "$.id");
        Long longId = id.longValue();

        expectedUser.setId(longId);
        assertTrue(userRepository.findById(longId).isPresent());
        User savedUser = userRepository.findById(longId).get();

        assertEquals(expectedUser, savedUser);

        userRepository.deleteById(longId);
    }

    @Test
    public void deleteUser() throws Exception {
        User expectedUser = User.builder()
                .firstName("kirill")
                .lastName("kirillov")
                .email("kirill_kirillov@gmail.com")
                .phoneNumber("+80(44)444-44-44")
                .username("kirillll")
                .build();
        expectedUser = userRepository.save(expectedUser);

        this.mockMvc.perform(delete("/users/" + expectedUser.getId()));

        assertFalse(userRepository.existsById(expectedUser.getId()));
    }
}