package com.library.management_system.service;

import com.library.management_system.dto.FineResponseDto;
import com.library.management_system.pojo.Fine;
import com.library.management_system.pojo.Loan;

import java.time.LocalDate;

public interface FineService {
    FineResponseDto getFineById(Integer fineId);
    Fine addFine(Loan loan);
    void deleteFine(Integer fineId);
    FineResponseDto calculateAmount(LocalDate returnDate, Integer fineId);
    FineResponseDto updatePaymentStatus(Integer fineId);
}
