package org.example.library_management.dto;

import jakarta.validation.constraints.NotBlank;

public class BookBorrowedRequest {
    @NotBlank
    private String isbn;
    @NotBlank
    private String nationalId;

    public BookBorrowedRequest(String isbn, String nationalId) {
        this.isbn = isbn;
        this.nationalId = nationalId;
    }

    public BookBorrowedRequest() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
                "isbn='" + isbn + '\'' +
                ", nationalId='" + nationalId + '\'' +
                '}';
    }
}
