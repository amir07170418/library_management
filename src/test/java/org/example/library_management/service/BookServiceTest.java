package org.example.library_management.service;

import org.example.library_management.dto.BookRequest;
import org.example.library_management.dto.BookResponse;
import org.example.library_management.dto.BookUpdate;
import org.example.library_management.exception.LibraryException;
import org.example.library_management.mapper.BookMapper;
import org.example.library_management.model.Book;
import org.example.library_management.model.BookStatus;
import org.example.library_management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;


    @Test
    void save() {
        Book book = new Book(1L,"java","ghomi","12235489",1400,BookStatus.AVAILABLE,null);
        BookRequest bookRequest = new BookRequest("java","ghomi","12235489",1400);
        BookResponse expectedResponse = new BookResponse(1L,"java","ghomi"
                ,"12235489",1400,BookStatus.AVAILABLE);


        when(bookMapper.toBook(bookRequest)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toBookResponse(book)).thenReturn(expectedResponse);

        BookResponse result = bookService.save(bookRequest);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(bookMapper).toBook(bookRequest);
        verify(bookRepository).save(book);
        verify(bookMapper).toBookResponse(book);
    }


    @Test
    void update() {
        Long id = 1L;

        BookUpdate update = new BookUpdate("java","ghomi",1400);
        Book book = new Book(1L,"java","ghomi","12235489",1400,BookStatus.AVAILABLE,null);
        BookResponse expectedResponse = new BookResponse(1L,"java","ghomi"
                ,"12235489",1400,BookStatus.AVAILABLE);


        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toBookResponse(book)).thenReturn(expectedResponse);

        BookResponse result = bookService.update(id, update);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(bookRepository).findById(id);
        verify(bookMapper).updateBook(update, book);
        verify(bookRepository).save(book);
        verify(bookMapper).toBookResponse(book);
    }



    @Test
    void deActivate() {
        Long id = 1L;

        Book book = new Book(1L,"java","ghomi","12235489",1400,BookStatus.AVAILABLE,null);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        bookService.deActivate(id);

        assertEquals(BookStatus.DEACTIVATED, book.getStatus());

        verify(bookRepository).save(book);
    }


    @Test
    void deActivateBorrowedBook() {
        Long id = 1L;

        Book book = new Book();
        book.setStatus(BookStatus.BORROWED);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        assertThrows(LibraryException.class,
                () -> bookService.deActivate(id));

        verify(bookRepository, never()).save(any(Book.class));
    }




    @Test
    void findById() {
        Long id = 1L;

        Book book = new Book(1L,"java","ghomi","12235489",1400,BookStatus.AVAILABLE,null);
        BookResponse expectedResponse = new BookResponse(1L,"java","ghomi"
                ,"12235489",1400,BookStatus.AVAILABLE);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toBookResponse(book)).thenReturn(expectedResponse);

        BookResponse result = bookService.findById(id);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(bookRepository).findById(id);
        verify(bookMapper).toBookResponse(book);
    }


    @Test
    void findByIdNotFound() {
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(LibraryException.class,
                () -> bookService.findById(id));

        verify(bookMapper, never()).toBookResponse(any(Book.class));
    }


    @Test
    void findByIsbn() {
        String isbn = "12235489";
        Book book = new Book(1L,"java","ghomi","12235489",1400,BookStatus.AVAILABLE,null);
        BookResponse expectedResponse = new BookResponse(1L,"java","ghomi"
                ,"12235489",1400,BookStatus.AVAILABLE);

        when(bookRepository.findByIsbn(isbn)).thenReturn(List.of(book));
        when(bookMapper.toBookResponse(book)).thenReturn(expectedResponse);

        List<BookResponse> result = bookService.findByIsbn(isbn);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedResponse, result.get(0));

        verify(bookRepository).findByIsbn(isbn);
        verify(bookMapper).toBookResponse(book);
    }

    @Test
    void findAll() {

        Book book = new Book(1L,"java","ghomi","12235489",1400,BookStatus.AVAILABLE,null);
        BookResponse expectedResponse = new BookResponse(1L,"java","ghomi"
                ,"12235489",1400,BookStatus.AVAILABLE);

        Page<Book> bookPage = new PageImpl<>(List.of(book));

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.toBookResponse(book)).thenReturn(expectedResponse);

        Page<BookResponse> result = bookService.findAll(0, 10, "id", "asc", null);

        assertNotNull(result);
        assertEquals(expectedResponse.getId(), result.getContent().get(0).getId());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalPages());

        verify(bookRepository).findAll(any(Pageable.class));
        verify(bookMapper).toBookResponse(book);
    }


    @Test
    void findAllByTitle() {
        String title = "Java";

        Book book = new Book(1L,"java","ghomi","12235489",1400,BookStatus.AVAILABLE,null);
        BookResponse expectedResponse = new BookResponse(1L,"java","ghomi"
                ,"12235489",1400,BookStatus.AVAILABLE);

        Page<Book> bookPage = new PageImpl<>(List.of(book));

        when(bookRepository.findByTitleContainingIgnoreCase(
                eq(title), any(Pageable.class))).thenReturn(bookPage);

        when(bookMapper.toBookResponse(book)).thenReturn(expectedResponse);

        Page<BookResponse> result =
                bookService.findAll(0, 10, "id", "desc", title);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(expectedResponse, result.getContent().get(0));

        verify(bookRepository).findByTitleContainingIgnoreCase(eq(title), any(Pageable.class));

        verify(bookMapper).toBookResponse(book);
    }
}