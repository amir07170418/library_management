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
@RequestMapping("/book-borrowings")
public class BookBorrowedController {
    private final BookBorrowedService bookBorrowedService;

    public BookBorrowedController(BookBorrowedService bookBorrowedService) {
        this.bookBorrowedService = bookBorrowedService;
    }
    @PostMapping
    public BookBorrowedResponse save(@Valid @RequestBody BookBorrowedRequest bookBorrowedRequest){
        return bookBorrowedService.createBookBorrowed(bookBorrowedRequest);
    }
    @PatchMapping("/{bookId}/return")
    public BookBorrowedResponse returnBook(@PathVariable Long bookId, @RequestParam String nationalId){
        return bookBorrowedService.returnBookBorrowed(bookId, nationalId);
    }
}
