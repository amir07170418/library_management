package org.example.library_management.service;

import jakarta.transaction.Transactional;
import org.example.library_management.dto.BookBorrowedRequest;
import org.example.library_management.dto.BookBorrowedResponse;
import org.example.library_management.dto.BookResponse;
import org.example.library_management.exception.LibraryException;
import org.example.library_management.mapper.BookBorrowedMapper;
import org.example.library_management.mapper.BookMapper;
import org.example.library_management.model.Book;
import org.example.library_management.model.BookBorrowed;
import org.example.library_management.model.BookStatus;
import org.example.library_management.model.User;
import org.example.library_management.repository.BookBorrowedRepository;
import org.example.library_management.repository.BookRepository;
import org.example.library_management.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookBorrowedService {
    private final BookBorrowedRepository bookBorrowedRepository;
    private final BookBorrowedMapper bookBorrowedMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookBorrowedService(BookBorrowedRepository bookBorrowedRepository, BookBorrowedMapper bookBorrowedMapper
            , BookRepository bookRepository, UserRepository userRepository) {
        this.bookBorrowedRepository = bookBorrowedRepository;
        this.bookBorrowedMapper = bookBorrowedMapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;

    }

    @Transactional
    public BookBorrowedResponse createBookBorrowed(BookBorrowedRequest request) {
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(() ->
                new LibraryException(HttpStatus.NOT_FOUND, "book not found"));
        if (book.getStatus().equals(BookStatus.BORROWED)) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "book already borrowed");
        } else if (book.getStatus().equals(BookStatus.DEACTIVATED)) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "book is deactivated");
        }
        User user = userRepository.findByNationalId(request.getNationalId()).orElseThrow(() ->
                new LibraryException(HttpStatus.NOT_FOUND, "user not found"));
        BookBorrowed bookBorrowed = new BookBorrowed();
        bookBorrowed.setBook(book);
        bookBorrowed.setUser(user);
        bookBorrowed.setBorrowStart(LocalDate.now());
        bookBorrowedRepository.save(bookBorrowed);
        book.setStatus(BookStatus.BORROWED);
        return bookBorrowedMapper.toBookBorrowedResponse(bookBorrowed);
    }

    @Transactional
    public BookBorrowedResponse returnBookBorrowed(Long bookId, String nationalId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new LibraryException(HttpStatus.NOT_FOUND, "book not found"));
        if (!book.getStatus().equals(BookStatus.BORROWED)) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "book is not borrowed");
        }
        User user = userRepository.findByNationalId(nationalId).orElseThrow(() -> new LibraryException(HttpStatus.NOT_FOUND, "user not found"));
        BookBorrowed bookBorrowed = bookBorrowedRepository.findActiveBorrowingByBookIdAndNationalId(bookId, nationalId).orElseThrow
                (() -> new LibraryException(HttpStatus.NOT_FOUND, "bookBorrowed not found"));
        bookBorrowed.setBorrowEnd(LocalDate.now());
        book.setStatus(BookStatus.AVAILABLE);
        bookBorrowedRepository.save(bookBorrowed);
        bookRepository.save(book);
        return bookBorrowedMapper.toBookBorrowedResponse(bookBorrowed);
    }


}
