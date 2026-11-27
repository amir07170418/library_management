package org.example.library_management.repository;

import org.example.library_management.model.BookBorrowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookBorrowedRepository extends JpaRepository<BookBorrowed, Long> {
    @Query("select b FROM BookBorrowed b where b.book.id=:bookId AND " +
            "b.book.status=org.example.library_management.model.BookStatus.BORROWED AND" +
            " b.user.nationalId=:nationalId AND b.borrowEnd is NULL ")
    Optional<BookBorrowed> findActiveBorrowingByBookIdAndNationalId(@Param("bookId") Long bookId, @Param("nationalId")  String nationalId);
}
