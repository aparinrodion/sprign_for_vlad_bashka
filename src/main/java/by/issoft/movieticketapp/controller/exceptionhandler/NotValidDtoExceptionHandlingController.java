package by.issoft.movieticketapp.controller.exceptionhandler;

import by.issoft.movieticketapp.exception.NotValidDtoErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class NotValidDtoExceptionHandlingController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<NotValidDtoErrorMessage>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<NotValidDtoErrorMessage> errorMessages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> errorMessages.add(createErrorMessage(error)));

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private NotValidDtoErrorMessage createErrorMessage(ObjectError error) {
        String fieldName = ((FieldError) error).getField();
        String className = error.getObjectName();
        String devMessage = "Not valid data in field " + fieldName + " in class " + className;

        String userMessage = error.getDefaultMessage();

        NotValidDtoErrorMessage errorMessage = new NotValidDtoErrorMessage();
        errorMessage.setDevMessage(devMessage);
        errorMessage.setUserMessage(userMessage);
        errorMessage.generateCode();

        return errorMessage;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<NotValidDtoErrorMessage> handleDateTimeParseException(DateTimeParseException ex) {
        NotValidDtoErrorMessage errorMessage = new NotValidDtoErrorMessage();
        errorMessage.setDevMessage(ex.getMessage());
        errorMessage.setUserMessage("Wrong date and time format, should be yyyy-MM-dd HH:mm:ss");
        errorMessage.generateCode();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}