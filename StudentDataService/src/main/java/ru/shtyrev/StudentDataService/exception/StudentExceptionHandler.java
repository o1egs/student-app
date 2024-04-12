package ru.shtyrev.StudentDataService.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class StudentExceptionHandler {
    @ExceptionHandler(value = StudentException.class)
    public ResponseEntity<ExceptionResponse> handleCalculatorException(StudentException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setHttpStatus(BAD_REQUEST);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }
}
