package org.example.library_management.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private Integer publishYear;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "book")
    private Set<BookBorrowed> borrowedBooks;

    public Book(Long id, String title, String author, String isbn, Integer publishYear
            , BookStatus status, Set<BookBorrowed> borrowedBooks) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishYear = publishYear;
        this.status = status;
        this.borrowedBooks = borrowedBooks;
    }

    public Book() {
    }

    public Set<BookBorrowed> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Set<BookBorrowed> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publishYear=" + publishYear +
                ", status=" + status +
                '}';
    }
}
