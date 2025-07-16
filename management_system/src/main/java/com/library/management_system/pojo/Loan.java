package com.library.management_system.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate checkoutDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL)
    @JsonBackReference
    private Fine fine;
}
