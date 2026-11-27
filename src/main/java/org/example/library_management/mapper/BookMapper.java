package org.example.library_management.mapper;

import org.example.library_management.dto.BookRequest;
import org.example.library_management.dto.BookResponse;
import org.example.library_management.dto.BookUpdate;
import org.example.library_management.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponse toBookResponse(Book book);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "borrowedBooks", ignore = true)
    Book toBook(BookRequest bookRequest);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "borrowedBooks", ignore = true)
    Book updateBook(BookUpdate bookUpdate, @MappingTarget Book book);
}
