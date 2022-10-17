package by.issoft.movieticketapp.controller.exceptionhandler;

import by.issoft.movieticketapp.exception.EntityNotFoundException;
import by.issoft.movieticketapp.exception.TicketIsAlreadyBought;
import by.issoft.movieticketapp.exception.UserCannotBeAdminOfCinemaException;
import by.issoft.movieticketapp.exception.WrongLoginCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //TODO fix exceptions
    @ExceptionHandler({EntityNotFoundException.class, TicketIsAlreadyBought.class, WrongLoginCredentialsException.class, UserCannotBeAdminOfCinemaException.class})
    public ResponseEntity<String> handleUserWithIdDoesntExist(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
