package org.example.library_management.service;

import org.example.library_management.dto.BookRequest;
import org.example.library_management.dto.BookResponse;
import org.example.library_management.dto.BookUpdate;
import org.example.library_management.exception.LibraryException;
import org.example.library_management.mapper.BookMapper;
import org.example.library_management.model.Book;
import org.example.library_management.model.BookStatus;
import org.example.library_management.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }
    public BookResponse save(BookRequest bookRequest) {
        Book book = bookMapper.toBook(bookRequest);
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);
        return bookMapper.toBookResponse(book);
    }
    @Transactional
    public BookResponse update(Long id, BookUpdate bookUpdate) {
        Book book = bookRepository.findById(id).orElseThrow(()->new LibraryException(HttpStatus.NOT_FOUND, "Book not found"));
        bookMapper.updateBook(bookUpdate,book);
        bookRepository.save(book);
        return bookMapper.toBookResponse(book);
    }
    public void deActivate(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->new LibraryException(HttpStatus.NOT_FOUND, "Book not found"));
        if (book.getStatus().equals(BookStatus.BORROWED)) {
            throw  new LibraryException(HttpStatus.BAD_REQUEST, "Book has been borrowed");
        }else if (book.getStatus().equals(BookStatus.DEACTIVATED)) {
            throw  new LibraryException(HttpStatus.BAD_REQUEST, "Book has already deactivated");
        }
        book.setStatus(BookStatus.DEACTIVATED);
        bookRepository.save(book);
    }
    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->new LibraryException(HttpStatus.NOT_FOUND, "Book not found"));
        return bookMapper.toBookResponse(book);
    }
    public List<BookResponse> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).stream().map(bookMapper::toBookResponse).toList();
    }
    public Page<BookResponse> findAll(int page, int size,String sortBy, String direction,String title) {
        Sort sort =direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        if (title!=null && !title.isBlank()) {
            return bookRepository.findByTitleContainingIgnoreCase(title,pageable).map(bookMapper::toBookResponse);
        }
        return bookRepository.findAll(pageable).map(bookMapper::toBookResponse);
    }
    public Page<BookResponse> searchBooks(String title, String author, Integer year, Boolean available, Pageable pageable) {
        return bookRepository.searchBooks(title, author, year, available, pageable).map(bookMapper::toBookResponse);
    }

}
