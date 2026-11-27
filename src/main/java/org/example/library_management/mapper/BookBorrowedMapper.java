package org.example.library_management.mapper;

import org.example.library_management.dto.BookBorrowedResponse;
import org.example.library_management.model.BookBorrowed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookBorrowedMapper {
    @Mapping(source = "book.id", target = "id")
    @Mapping(source = "user.nationalId", target = "nationalId")
    BookBorrowedResponse toBookBorrowedResponse(BookBorrowed bookBorrowed);
}