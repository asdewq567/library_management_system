package com.library.management_system.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Column(unique = true,nullable = false)
    private String name;

    @Column(unique=true,nullable=false)
    private String email;

    @Column(unique=true,nullable=false)
    private String phone;

    @Embedded
    private Address address;

    private boolean active;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans;
}
