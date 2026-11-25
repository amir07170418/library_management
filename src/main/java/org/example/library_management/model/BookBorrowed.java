package org.example.library_management.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BookBorrowed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private User user;
    private LocalDate borrowStart;
    private LocalDate borrowEnd;

    public BookBorrowed(Long id, Book book, User user, LocalDate borrowStart, LocalDate borrowEnd) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.borrowStart = borrowStart;
        this.borrowEnd = borrowEnd;
    }

    public BookBorrowed() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
