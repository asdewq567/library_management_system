package com.library.management_system.controller;

import com.library.management_system.dto.BookRequestDto;
import com.library.management_system.dto.BookResponseDto;
import com.library.management_system.payload.ApiResponse;
import com.library.management_system.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/id/{bookId}")
    public ApiResponse<BookResponseDto> getBookById(@PathVariable("bookId") Integer bookId) {
        BookResponseDto book = bookService.getBookById(bookId);
        return ApiResponse.success(book);
    }

    @GetMapping("/isbn/{bookIsbn}")
    public ApiResponse<List<BookResponseDto>> getBookByTitle(@PathVariable("bookIsbn") String bookIsbn) {
        List<BookResponseDto> books = bookService.getBooksByIsbn(bookIsbn);
        return ApiResponse.success(books);
    }

    @GetMapping("/batch")
    public ApiResponse<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> books = bookService.getAllBooks();
        return ApiResponse.success(books);
    }

    @PostMapping
    public ApiResponse<BookResponseDto> addBook(@RequestBody BookRequestDto dto) {
        BookResponseDto newBook = bookService.addBook(dto);
        return ApiResponse.success(newBook);
    }

    @PutMapping("/update/{bookId}")
    public ApiResponse<BookResponseDto> updateBook(@RequestBody BookRequestDto dto, @PathVariable("bookId") Integer bookId) {
        BookResponseDto updatedBook = bookService.updateBook(dto, bookId);
        return ApiResponse.success(updatedBook);
    }

    @DeleteMapping("/delete/{bookId}")
    public ApiResponse<Void> deleteBook(@PathVariable("bookId") Integer bookId) {
        bookService.deleteBook(bookId);
        return ApiResponse.success();
    }
}
