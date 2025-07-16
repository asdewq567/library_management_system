package com.library.management_system.service;

import com.library.management_system.dto.BookRequestDto;
import com.library.management_system.dto.BookResponseDto;

import java.util.List;

public interface BookService {
    BookResponseDto getBookById(Integer bookId);
    List<BookResponseDto> getBooksByIsbn(String bookIsbn);
    List<BookResponseDto> getAllBooks();
    BookResponseDto addBook(BookRequestDto dto);
    BookResponseDto updateBook(BookRequestDto dto, Integer bookId);
    void deleteBook(Integer bookId);
}
