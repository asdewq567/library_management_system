package com.library.management_system.repository;

import com.library.management_system.pojo.Book;
import com.library.management_system.pojo.Loan;
import com.library.management_system.pojo.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
