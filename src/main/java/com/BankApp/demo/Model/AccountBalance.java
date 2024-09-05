package com.BankApp.demo.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Data
public class AccountBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(
            nullable = false
    )
    private Double balances;

    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(
            name = "user_id"
    )
    private Users users;

}
