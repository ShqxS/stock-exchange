package com.stock.exchange.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleError(Exception e) {
        log.error(String.format("Error Message: %s", e.getMessage()), e);
        ErrorResponse apiError = new ErrorResponse(500, e.getMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExpectedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> expectedException(ExpectedException ex) {
        ErrorResponse apiError = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<?> handleResourceNotFoundException(NotFoundException ex) {
        ErrorResponse apiError = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleError(MethodArgumentNotValidException e) {
        log.error(String.format("Error Message: %s", e.getMessage()), e);

        List<ErrorResponse> errors = e.getBindingResult().getFieldErrors().stream()
                .map(
                        fieldError ->
                                new ErrorResponse(
                                        400,
                                        "Invalid value submitted for field: "
                                                + fieldError.getField()
                                                + ", error message is: "
                                                + fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> onConstraintValidationException(ConstraintViolationException e) {
        log.error(String.format("Error Message: %s", e.getMessage()), e);
        final List<ErrorResponse> errors = e.getConstraintViolations().stream()
                .map(constraintViolation -> new ErrorResponse(400, "Invalid value submitted for field: "
                        + constraintViolation.getPropertyPath()
                        + ", error message is: "
                        + constraintViolation.getMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
