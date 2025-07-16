package com.library.management_system.mapper;

import com.library.management_system.pojo.Book;
import com.library.management_system.pojo.Loan;
import com.library.management_system.pojo.Member;
import com.library.management_system.dto.LoanRequestDto;
import com.library.management_system.dto.LoanResponseDto;
import com.library.management_system.repository.BookRepository;
import com.library.management_system.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoanMapper {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final FineMapper fineMapper;

    public LoanMapper(BookRepository bookRepository, MemberRepository memberRepository, FineMapper fineMapper) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.fineMapper = fineMapper;
    }

    public Loan toLoan(LoanRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Loan loan = new Loan();

        updateLoanFromDto(dto, loan);

        return loan;
    }

    public void updateLoanFromDto(LoanRequestDto dto, Loan loan) {
        Book book = bookRepository.findById(dto.bookId())
                .orElseThrow(() -> new NoSuchElementException("Book with ID " + dto.bookId() + " not found"));
        Member member = memberRepository.findById(dto.memberId())
                .orElseThrow(() -> new NoSuchElementException("Member with ID " + dto.memberId() + " not found"));
        loan.setBook(book);
        loan.setMember(member);
        loan.setCheckoutDate(dto.checkoutDate());
        loan.setDueDate(dto.dueDate());
    }

    public LoanResponseDto toLoanResponseDto(Loan loan){
        if (loan == null) {
            return null;
        }
        return new LoanResponseDto(
                loan.getId(),
                loan.getBook().getId(),
                loan.getMember().getId(),
                loan.getCheckoutDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                fineMapper.toFineResponseDto(loan.getFine())
        );
    }

    public List<LoanResponseDto> toLoanResponseDtoList(List<Loan> loans) {
        if (loans == null) {
            return null;
        }
        List<LoanResponseDto> list = new ArrayList<>(loans.size());
        for (Loan loan : loans) {
            list.add(toLoanResponseDto(loan));
        }

        return list;
    }
}
