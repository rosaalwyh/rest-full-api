package rosa_alawiyah.rest_full_api_belajar.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import rosa_alawiyah.rest_full_api_belajar.model.Response;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<String>> constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.<String>builder()
                        .data("KO")
                        .errorMessages(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Response<String>> apiException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(Response.<String>builder()
                        .data("KO")
                        .errorMessages(exception.getReason())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> methodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Invalid input");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.<String>builder()
                        .data("KO")
                        .errorMessages(message)
                        .build());
    }
}
