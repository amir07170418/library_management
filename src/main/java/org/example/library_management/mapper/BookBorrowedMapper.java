package org.example.library_management.mapper;

import org.example.library_management.dto.BookBorrowedResponse;
import org.example.library_management.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookBorrowedMapper {
    @Mapping(source = "book.isbn",target = "isbn")
    @Mapping(source = "user.nationalId",target = "nationalId")
    BookBorrowedResponse toBookBorrowedResponse(Book book);
}
