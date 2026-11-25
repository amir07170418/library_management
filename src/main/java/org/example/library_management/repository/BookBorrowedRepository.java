package org.example.library_management.repository;

import org.example.library_management.model.BookBorrowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookBorrowedRepository extends JpaRepository<BookBorrowed, Long> {
}
