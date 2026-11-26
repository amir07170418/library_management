package org.example.library_management.repository;

import org.example.library_management.model.Book;
import org.example.library_management.model.BookBorrowed;
import org.example.library_management.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookBorrowedRepository extends JpaRepository<BookBorrowed, Long> {
    @Query("select b FROM BookBorrowed b where b.book.id=:bookId AND b.book.status=:status AND b.user.nationalId=:nationalId")
    Optional<BookBorrowed> findByBookAndStatus(@Param("bookId") Long bookId
            , @Param("status") BookStatus status,@Param("nationalId")  String nationalId);
}
