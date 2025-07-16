package com.library.management_system.pojo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fine extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "loan_id")
    @JsonManagedReference
    private Loan loan;

    private BigDecimal amount;

    private Boolean paid;
}