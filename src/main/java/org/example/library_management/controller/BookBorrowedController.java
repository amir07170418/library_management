package org.example.library_management.controller;

import jakarta.validation.Valid;
import org.example.library_management.dto.BookBorrowedRequest;
import org.example.library_management.dto.BookBorrowedResponse;
import org.example.library_management.dto.BookResponse;
import org.example.library_management.service.BookBorrowedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/BookBorroweds")
public class BookBorrowedController {
    private final BookBorrowedService bookBorrowedService;

    public BookBorrowedController(BookBorrowedService bookBorrowedService) {
        this.bookBorrowedService = bookBorrowedService;
    }
    @PostMapping
    public BookBorrowedResponse save(@Valid @RequestBody BookBorrowedRequest bookBorrowedRequest){
        return bookBorrowedService.createBookBorrowed(bookBorrowedRequest);
    }
    @GetMapping
    public BookBorrowedResponse returnBook(@RequestParam Long bookId, @RequestParam String nationalId){
        return bookBorrowedService.returnBookBorrowed(bookId, nationalId);
    }
    @GetMapping("/search")
    public  Page<BookResponse> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Boolean available,
            Pageable pageable
    ) {
        return bookBorrowedService.searchBooks(title, author, year, available, pageable);
    }
}
