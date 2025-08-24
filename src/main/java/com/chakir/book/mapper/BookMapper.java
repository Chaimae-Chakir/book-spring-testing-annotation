package com.chakir.book.mapper;

import com.chakir.book.entity.Book;
import com.chakir.book.dto.BookRequest;
import com.chakir.book.dto.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    Book mapToEntity(BookRequest request);

    BookResponse mapToResponse(Book book);
}
