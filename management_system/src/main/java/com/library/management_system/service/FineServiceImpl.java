package com.library.management_system.service;

import com.library.management_system.dto.FineResponseDto;
import com.library.management_system.mapper.FineMapper;
import com.library.management_system.pojo.Fine;
import com.library.management_system.pojo.Loan;
import com.library.management_system.repository.FineRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

@Service
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final FineMapper fineMapper;

    public FineServiceImpl(FineRepository fineRepository, FineMapper fineMapper) {
        this.fineRepository = fineRepository;
        this.fineMapper = fineMapper;
    }

    @Override
    public FineResponseDto getFineById(Integer fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new NoSuchElementException("Fine with ID " + fineId + " not found"));
        return fineMapper.toFineResponseDto(fine);
    }

    @Override
    public Fine addFine(Loan loan) {
        Fine fine = new Fine();
        fine.setLoan(loan);
        fine.setPaid(false);
        return fineRepository.save(fine);
    }

    @Override
    public void deleteFine(Integer fineId) {
        fineRepository.deleteById(fineId);
    }

    @Override
    public FineResponseDto calculateAmount(LocalDate returnDate, Integer fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new NoSuchElementException("Fine with ID " + fineId + " not found"));

        LocalDate dueDate = fine.getLoan().getDueDate();
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        BigDecimal amount = BigDecimal.ZERO;
        boolean paid = false;

        if (daysLate > 0) {
            amount = BigDecimal.valueOf(daysLate).multiply(BigDecimal.valueOf(2));
        } else {
            paid = true;
        }

        fine.setAmount(amount);
        fine.setPaid(paid);
        Fine savedFine = fineRepository.save(fine);
        return fineMapper.toFineResponseDto(savedFine);
    }

    @Override
    public FineResponseDto updatePaymentStatus(Integer fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new NoSuchElementException("Fine with ID " + fineId + " not found"));
        fine.setPaid(!fine.getPaid());
        Fine savedFine = fineRepository.save(fine);
        return fineMapper.toFineResponseDto(savedFine);
    }

}
