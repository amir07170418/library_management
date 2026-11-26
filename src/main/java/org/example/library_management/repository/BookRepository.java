package org.example.library_management.repository;

import org.example.library_management.model.Book;
import org.example.library_management.model.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Optional<Book> findByIsbn(String isbn);
    @Query("""
        SELECT b FROM Book b
        WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')))
        AND (:year IS NULL OR b.publishYear = :year)
        AND (:available IS NULL OR
             (:available = true AND b.status = org.example.library_management.model.BookStatus.AVAILABLE))
        """)
    Page<Book> searchBooks(
            @Param("title") String title,
            @Param("author") String author,
            @Param("year") Integer year,
            @Param("available") Boolean available,
            Pageable pageable
    );
}
