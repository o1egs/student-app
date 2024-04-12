package ru.shtyrev.StudentDataService.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {
    private String message;
    private HttpStatus httpStatus;
}
