package by.issoft.movieticketapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserValidationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void saveUserWithNotValidEmail() throws Exception {
        String userToSave = """
                {
                        "email": "user_email_not_correct",
                        "firstName": "user_new_firstName",
                        "lastName": "user_lastName",
                        "phoneNumber": "+375(29)123-45-67",
                        "username": "user_username"
                    }
                """
                .replaceAll("\\s", "");

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(userToSave))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Email is not valid")))
                .andExpect(content().string(containsString("Not valid data in field email in class userDto")));
    }

    @Test
    public void saveUserWithNotValidPhoneNumber() throws Exception {
        String userToSave = """
                {
                        "email": "user_email@gmail.com",
                        "firstName": "user_new_firstName",
                        "lastName": "user_lastName",
                        "phoneNumber": "+1233211232",
                        "username": "user_username"
                    }
                """
                .replaceAll("\\s", "");

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(userToSave))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Phone number is not valid")))
                .andExpect(content().string(containsString("Not valid data in field phoneNumber in class userDto")));
    }

    @Test
    public void saveUserWithEmptyFields() throws Exception {
        String userToSave = """
                {
                        "email": "",
                        "firstName": "",
                        "lastName": "",
                        "phoneNumber": "",
                        "username": ""
                    }
                """
                .replaceAll("\\s", "");

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(userToSave))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Email cannot be empty")))
                .andExpect(content().string(containsString("First name cannot be empty")))
                .andExpect(content().string(containsString("Last name cannot be empty")))
                .andExpect(content().string(containsString("Username cannot be empty")))
                .andExpect(content().string(containsString("Phone number cannot be empty")));
    }
}
