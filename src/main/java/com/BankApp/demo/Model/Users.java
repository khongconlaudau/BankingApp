package com.BankApp.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Integer id;

    @Column(
            nullable = false
    )
    private String fname;

    @Column(
            nullable = false
    )
    private String lname;

   @Column(
         nullable = false,
   //         unique = true,
           length = 30
   )
    private String userName;

    @Column(
            nullable = false,
            length = 60
    )
    private String password;

    @Column(
            nullable = false,
     //      unique = true,
            length = 7
    )
    private String chequingNumber;

    @Column(
          //  unique = true,
            nullable = false,
            length = 16
    )
    private Long phoneNumber;

    @Column(
            nullable = false,
            length = 4
    )
    private Integer pin;

    @Column(
            length = 40,
            nullable = false
    )
    private String gmail;

    @OneToMany(
            mappedBy = "users"
    )
    private List<AcountBalance> acountBalanceList;

}
