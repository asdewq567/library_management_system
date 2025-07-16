package com.library.management_system.repository;

import com.library.management_system.pojo.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByIsbnContainingIgnoreCase(String isbn);
}
