package org.example.library_management.service;

import org.example.library_management.dto.BookBorrowedRequest;
import org.example.library_management.dto.BookBorrowedResponse;
import org.example.library_management.dto.BookResponse;
import org.example.library_management.mapper.BookBorrowedMapper;
import org.example.library_management.mapper.BookMapper;
import org.example.library_management.model.Book;
import org.example.library_management.model.BookBorrowed;
import org.example.library_management.model.BookStatus;
import org.example.library_management.model.User;
import org.example.library_management.repository.BookBorrowedRepository;
import org.example.library_management.repository.BookRepository;
import org.example.library_management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookBorrowedServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookBorrowedRepository bookBorrowedRepository;

    @Mock
    private BookBorrowedMapper bookBorrowedMapper;

    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookBorrowedService bookBorrowedService;

    @Test
    void createBookBorrowed() {

        Long bookId = 1L;
        String nationalId = "1234567890";

        Book book = new Book();
        book.setId(bookId);
        book.setStatus(BookStatus.AVAILABLE);

        User user = new User();
        user.setNationalId(nationalId);

        BookBorrowedRequest bookBorrowedRequest = new BookBorrowedRequest(bookId, nationalId);
        when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(book));

        when(userRepository.findByNationalId(nationalId))
                .thenReturn(Optional.of(user));
        BookBorrowedResponse expectedResponse = new BookBorrowedResponse(1L, bookId, nationalId, LocalDate.now(), null);

        when(bookBorrowedMapper.toBookBorrowedResponse(any(BookBorrowed.class))).thenReturn(expectedResponse);

        BookBorrowedResponse result = bookBorrowedService.createBookBorrowed(bookBorrowedRequest);

        assertEquals(BookStatus.BORROWED, book.getStatus());
        assertNotNull(result);
        verify(bookBorrowedRepository).save(any(BookBorrowed.class));
    }

    @Test
    void returnBookBorrowed() {
        Long bookId = 1L;
        String nationalId = "1234567890";

        Book book = new Book();
        book.setId(bookId);
        book.setStatus(BookStatus.BORROWED);

        User user = new User();
        user.setNationalId(nationalId);

        BookBorrowed bookBorrowed = new BookBorrowed(1L, book, user, LocalDate.now(), null);
        BookBorrowedResponse response = new BookBorrowedResponse(1L, bookId, nationalId, LocalDate.now(), LocalDate.now());
        when(bookBorrowedRepository.findActiveBorrowingByBookIdAndNationalId(bookId, nationalId)).thenReturn(Optional.of(bookBorrowed));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findByNationalId(nationalId)).thenReturn(Optional.of(user));
        when(bookBorrowedMapper.toBookBorrowedResponse(any(BookBorrowed.class))).thenReturn(response);


        BookBorrowedResponse result = bookBorrowedService.returnBookBorrowed(bookId, nationalId);

        assertEquals(response.getBorrowEnd(), result.getBorrowEnd());
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        verify(bookBorrowedRepository).findActiveBorrowingByBookIdAndNationalId(bookId, nationalId);
        verify(bookBorrowedRepository).save(any(BookBorrowed.class));
        assertNotNull(bookBorrowed.getBorrowEnd());

    }

    @Test
    void searchBooks() {

        String title = "Java";
        String author = "John";
        String isbn = "1234567890";
        Integer year = 2024;
        Boolean available = true;

        Pageable pageable = PageRequest.of(0, 10);

        Book book = new Book();
        book.setId(1L);
        book.setTitle(title);

        BookResponse expectedResponse = new BookResponse(1L, title, author, isbn, year, BookStatus.AVAILABLE);

        Page<Book> bookPage = new PageImpl<>(List.of(book));

        when(bookRepository.searchBooks(title, author, year, available, pageable)).thenReturn(bookPage);

        when(bookMapper.toBookResponse(book)).thenReturn(expectedResponse);

        Page<BookResponse> result = bookBorrowedService.searchBooks(title, author, year, available, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(title, result.getContent().get(0).getTitle());

        verify(bookRepository).searchBooks(title, author, year, available, pageable);
        verify(bookMapper).toBookResponse(book);
    }
}