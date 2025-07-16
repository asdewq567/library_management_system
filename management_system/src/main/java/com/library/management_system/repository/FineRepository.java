package com.library.management_system.repository;

import com.library.management_system.pojo.Fine;
import com.library.management_system.pojo.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<Fine, Integer> {
}
