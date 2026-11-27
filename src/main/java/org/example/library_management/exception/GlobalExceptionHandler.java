package org.example.library_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LibraryException.class)
    public ResponseEntity<ExceptionResponse> handleLibraryException(LibraryException libraryException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(libraryException.getMessage());
        exceptionResponse.setStatus(libraryException.getStatus());
        exceptionResponse.setTime(LocalDateTime.now());
        return ResponseEntity.status(libraryException.getStatus()).body(exceptionResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                message,
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(response);
    }
}
