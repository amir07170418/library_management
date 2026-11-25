package org.example.library_management.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private HttpStatus status;
    private String message;
    private LocalDateTime time;

    public ExceptionResponse(HttpStatus status, String message, LocalDateTime time) {
        this.status = status;
        this.message = message;
        this.time = time;
    }

    public ExceptionResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
