package com.library.management_system.dto;

import java.time.LocalDate;

public record LoanRequestDto(
        Integer bookId,

        Integer memberId,

        LocalDate checkoutDate,

        LocalDate dueDate
) {
}
