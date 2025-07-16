package com.library.management_system.controller;

import com.library.management_system.dto.ReturnDateDto;
import com.library.management_system.payload.ApiResponse;
import com.library.management_system.dto.LoanRequestDto;
import com.library.management_system.dto.LoanResponseDto;
import com.library.management_system.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/id/{loanId}")
    public ApiResponse<LoanResponseDto> getLoanById(@PathVariable("loanId") Integer loanId) {
        LoanResponseDto loan = loanService.getLoanById(loanId);
        return ApiResponse.success(loan);
    }

    @GetMapping("/batch")
    public ApiResponse<List<LoanResponseDto>> getAllLoans() {
        List<LoanResponseDto> loans = loanService.getAllLoans();
        return ApiResponse.success(loans);
    }

    @PostMapping
    public ApiResponse<LoanResponseDto> addLoan(@RequestBody LoanRequestDto dto) {
        LoanResponseDto newLoan = loanService.addLoan(dto);
        return ApiResponse.success(newLoan);
    }

    @PutMapping("/update/{loanId}")
    public ApiResponse<LoanResponseDto> updateLoan(@RequestBody LoanRequestDto dto, @PathVariable("loanId") Integer loanId) {
        LoanResponseDto updatedLoan = loanService.updateLoan(dto, loanId);
        return ApiResponse.success(updatedLoan);
    }

    @DeleteMapping("/delete/{loanId}")
    public ApiResponse<Void> deleteLoan(@PathVariable("loanId") Integer loanId) {
        loanService.deleteLoan(loanId);
        return ApiResponse.success();
    }

    @PatchMapping("/returnDate/{loanId}")
    public ApiResponse<LoanResponseDto> updateReturnDate(@RequestBody ReturnDateDto dto, @PathVariable("loanId") Integer loanId) {
        LoanResponseDto updatedLoan = loanService.updateReturnDate(dto, loanId);
        return ApiResponse.success(updatedLoan);
    }
}
