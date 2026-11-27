package org.example.library_management.controller;

import jakarta.validation.Valid;
import org.example.library_management.dto.BookRequest;
import org.example.library_management.dto.BookResponse;
import org.example.library_management.dto.BookUpdate;
import org.example.library_management.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping
    public BookResponse saveBook(@Valid @RequestBody BookRequest bookRequest) {
        return bookService.save(bookRequest);
    }
    @GetMapping
    public Page<BookResponse> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String title
    ) {
        return bookService.findAll(page, size, sortBy, direction, title);
    }
    @GetMapping("/{id}")
    public BookResponse findBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }
    @GetMapping("/isbn/{isbn}")
    public List<BookResponse> findBookByIsbn(@PathVariable String isbn) {
        return bookService.findByIsbn(isbn);
    }
    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdate bookUpdate) {
        return bookService.update(id, bookUpdate);
    }
    @PatchMapping("/{id}")
    public void deActivateBook(@PathVariable Long id) {
        bookService.deActivate(id);
    }
}
