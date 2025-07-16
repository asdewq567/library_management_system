package com.library.management_system.mapper;

import com.library.management_system.dto.FineResponseDto;
import com.library.management_system.pojo.Fine;
import com.library.management_system.dto.FineRequestDto;
import com.library.management_system.pojo.Loan;
import com.library.management_system.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FineMapper {

    private final LoanRepository loanRepository;

    public FineMapper(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Fine toFine(FineRequestDto dto){
        if (dto == null) {
            return null;
        }

        Loan loan = loanRepository.findById(dto.loanId())
                .orElseThrow(() -> new NoSuchElementException("Loan with ID " + dto.loanId() + " not found"));

        Fine fine = new Fine();

        fine.setLoan(loan);

        return fine;
    }

    public FineResponseDto toFineResponseDto(Fine fine){
        if (fine == null) {
            return null;
        }
        return new FineResponseDto(
                fine.getId(),
                fine.getAmount(),
                fine.getPaid()
        );
    }

    public List<FineResponseDto> toFineResponseDtoList(List<Fine> fines) {
        if (fines == null) {
            return null;
        }
        List<FineResponseDto> list =new ArrayList<>(fines.size());
        for (Fine fine : fines) {
            list.add((toFineResponseDto(fine)));
        }

        return  list;
    }
}
