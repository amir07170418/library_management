package org.example.library_management.dto;

import java.time.LocalDate;

public class BookBorrowedResponse {
    private Long id;
    private String isbn;
    private String nationalId;
    private LocalDate borrowStart;
    private LocalDate borrowEnd;

    public BookBorrowedResponse(Long id, String isbn, String nationalId, LocalDate borrowStart, LocalDate borrowEnd) {
        this.id = id;
        this.isbn = isbn;
        this.nationalId = nationalId;
        this.borrowStart = borrowStart;
        this.borrowEnd = borrowEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookBorrowedResponse() {
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

    public LocalDate getBorrowStart() {
        return borrowStart;
    }

    public void setBorrowStart(LocalDate borrowStart) {
        this.borrowStart = borrowStart;
    }

    public LocalDate getBorrowEnd() {
        return borrowEnd;
    }

    public void setBorrowEnd(LocalDate borrowEnd) {
        this.borrowEnd = borrowEnd;
    }

    @Override
    public String toString() {
        return "BookBorrowedResponse{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", borrowStart=" + borrowStart +
                ", borrowEnd=" + borrowEnd +
                '}';
    }
}
