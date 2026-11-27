package org.example.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BookBorrowedRequest {
    @NotNull
    @Positive
    private Long bookId;
    @NotBlank
    private String nationalId;

    public BookBorrowedRequest(Long bookId, String nationalId) {
        this.bookId = bookId;
        this.nationalId = nationalId;
    }

    public BookBorrowedRequest() {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    @Override
    public String toString() {
        return "BookBorrowedRequest{" +
                "bookId='" + bookId + '\'' +
                ", nationalId='" + nationalId + '\'' +
                '}';
    }
}
