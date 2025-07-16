package com.library.management_system.service;

import com.library.management_system.dto.ReturnDateDto;
import com.library.management_system.mapper.LoanMapper;
import com.library.management_system.pojo.Fine;
import com.library.management_system.pojo.Loan;
import com.library.management_system.dto.LoanRequestDto;
import com.library.management_system.dto.LoanResponseDto;
import com.library.management_system.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanMapper loanMapper;
    private final LoanRepository loanRepository;
    private final FineService fineService;

    public LoanServiceImpl(LoanMapper loanMapper, LoanRepository loanRepository, FineService fineService) {
        this.loanMapper = loanMapper;
        this.loanRepository = loanRepository;
        this.fineService = fineService;
    }

    @Override
    public LoanResponseDto getLoanById(Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("Loan with ID " + loanId + " not found"));
        return loanMapper.toLoanResponseDto(loan);
    }

    @Override
    public List<LoanResponseDto> getAllLoans(){
        List<Loan> loans = loanRepository.findAll();
        return loanMapper.toLoanResponseDtoList(loans);
    }

    @Override
    public LoanResponseDto addLoan(LoanRequestDto dto) {
        Loan loan = loanMapper.toLoan(dto);
        if (!loan.getBook().isAvailable()){
            throw new IllegalStateException("Book is unavailable at the moment");
        }
        loan.getBook().setAvailable(false);
        Loan savedLoan = loanRepository.save(loan);
        return loanMapper.toLoanResponseDto(savedLoan);
    }

    @Override
    public LoanResponseDto updateLoan(LoanRequestDto dto, Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("Loan with ID " + loanId + " not found"));
        loanMapper.updateLoanFromDto(dto, loan);
        Loan savedLoan = loanRepository.save(loan);
        return loanMapper.toLoanResponseDto(savedLoan);
    }

    @Override
    public void deleteLoan(Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("Loan with ID " + loanId + " not found"));
        loan.getBook().setAvailable(true);
        loanRepository.deleteById(loanId);

    }

    @Override
    public LoanResponseDto updateReturnDate(ReturnDateDto dto, Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("Loan with ID " + loanId + " not found"));
        loan.setReturnDate(dto.returnDate());
        Fine fine = fineService.addFine(loan);
        loan.setFine(fine);
        fineService.calculateAmount(dto.returnDate(), loan.getFine().getId());
        loan.getBook().setAvailable(true);
        Loan savedLoan = loanRepository.save(loan);
        return loanMapper.toLoanResponseDto(savedLoan);
    }
}
