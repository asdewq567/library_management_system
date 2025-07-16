package com.library.management_system.dto;

import java.math.BigDecimal;

public record FineResponseDto(
        Integer id,

        BigDecimal amount,

        Boolean paid
) {
}
