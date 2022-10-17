package by.issoft.movieticketapp.exception;

import lombok.Data;

import java.util.Objects;

@Data
public class NotValidDtoErrorMessage {
    private static final Integer CODE_GENERATOR_VALUE = 1000;
    private String userMessage;
    private String devMessage;
    private Integer code;

    public void generateCode() {
        this.code = Math.abs(Objects.hash(userMessage, devMessage) % CODE_GENERATOR_VALUE);
    }
}
