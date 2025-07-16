package com.library.management_system.dto;

import com.library.management_system.pojo.BookGenre;

public record BookResponseDto(
        Integer id,

        String isbn,

        String title,

        String authors,

        int publicationYear,

        BookGenre genre,

        boolean available
) {
}
