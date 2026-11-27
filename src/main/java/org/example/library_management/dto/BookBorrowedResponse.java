package org.example.library_management.dto;

import java.time.LocalDate;

public class BookBorrowedResponse {
    private Long id;
    private Long bookId;
    private String nationalId;
    private LocalDate borrowStart;
    private LocalDate borrowEnd;

    public BookBorrowedResponse(Long id, Long bookId, String nationalId, LocalDate borrowStart, LocalDate borrowEnd) {
        this.id = id;
        this.bookId = bookId;
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
                ", bookId='" + bookId + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", borrowStart=" + borrowStart +
                ", borrowEnd=" + borrowEnd +
                '}';
    }
}
