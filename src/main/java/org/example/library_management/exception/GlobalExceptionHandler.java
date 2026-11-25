package org.example.library_management.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

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
}
