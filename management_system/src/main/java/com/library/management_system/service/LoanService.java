package com.library.management_system.service;

import com.library.management_system.dto.LoanRequestDto;
import com.library.management_system.dto.LoanResponseDto;
import com.library.management_system.dto.ReturnDateDto;

import java.time.LocalDate;
import java.util.List;

public interface LoanService {
    LoanResponseDto getLoanById(Integer loanId);
    List<LoanResponseDto> getAllLoans();
    LoanResponseDto addLoan(LoanRequestDto loan);
    LoanResponseDto updateLoan(LoanRequestDto dto, Integer loanId);
    void deleteLoan(Integer loanId);
    LoanResponseDto updateReturnDate(ReturnDateDto dto, Integer loanId);
}
