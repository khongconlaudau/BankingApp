package com.BankApp.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcountBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(
            nullable = false
    )
    private Double balance;

    @Column
    private Double transactions;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private Users users;
}
