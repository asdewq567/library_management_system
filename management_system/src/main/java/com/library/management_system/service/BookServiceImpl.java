package com.library.management_system.service;

import com.library.management_system.dto.BookRequestDto;
import com.library.management_system.mapper.BookMapper;
import com.library.management_system.pojo.Book;
import com.library.management_system.dto.BookResponseDto;
import com.library.management_system.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookResponseDto getBookById(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with ID " + bookId + " not found"));
        return bookMapper.toBookResponseDto(book);
    }

    @Override
    public List<BookResponseDto> getBooksByIsbn(String bookIsbn) {
        List<Book> books = bookRepository.findAllByIsbnContainingIgnoreCase(bookIsbn);
        return bookMapper.toBookResponseDtoList(books);
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.toBookResponseDtoList(books);
    }

    @Override
    public BookResponseDto addBook(BookRequestDto dto) {
        Book book = bookMapper.toBook(dto);
        book.setAvailable(true);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookResponseDto(savedBook);
    }

    @Override
    public BookResponseDto updateBook(BookRequestDto dto, Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with ID " + bookId + " not found"));
        bookMapper.updateBookFromDto(dto, book);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookResponseDto(savedBook);
    }

    @Override
    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
    }
}
