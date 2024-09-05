package com.BankApp.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false)
    private LocalDate date;

    @Column (nullable = false)
    private String receiver;

    @Column (nullable = false)
    private String sender;

    @Column (nullable = false)
    private Double amount;

    @Column (nullable = false)
    private String expenses;

    @Column (nullable = false)
    private Double previousBalance;

    @Column (nullable = false)
    private Double availableBalance;

    @ManyToOne (
        cascade = CascadeType.MERGE
    )
    @JoinColumn (
            name = "user_id"
    )
    private Users users;

}
