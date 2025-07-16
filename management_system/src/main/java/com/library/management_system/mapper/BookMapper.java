package com.library.management_system.mapper;

import com.library.management_system.pojo.Book;
import com.library.management_system.dto.BookRequestDto;
import com.library.management_system.dto.BookResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookMapper {
    public Book toBook(BookRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Book book = new Book();

        updateBookFromDto(dto, book);

        return book;
    }

    public void updateBookFromDto(BookRequestDto dto, Book book) {
        book.setIsbn(dto.isbn());
        book.setTitle(dto.title());
        book.setAuthors(dto.authors());
        book.setPublicationYear(dto.publicationYear());
        book.setGenre(dto.genre());
    }

    public BookResponseDto toBookResponseDto(Book book){
        if (book == null) {
            return null;
        }
        return new BookResponseDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthors(),
                book.getPublicationYear(),
                book.getGenre(),
                book.isAvailable()
        );
    }

    public List<BookResponseDto> toBookResponseDtoList(List<Book> books){
        if (books == null) {
            return null;
        }
        List<BookResponseDto> list = new ArrayList<>(books.size());
        for (Book book : books) {
            list.add(toBookResponseDto(book));
        }

        return list;
    }
}
