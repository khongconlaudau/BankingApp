package com.BankApp.demo.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

@Entity
@Data
public class AccountBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(
            nullable = false
    )
    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER,
    cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id"
    )
    private Users users;
}
