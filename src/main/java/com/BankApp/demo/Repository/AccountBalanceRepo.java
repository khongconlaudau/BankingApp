package com.BankApp.demo.Repository;

import com.BankApp.demo.Model.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AccountBalanceRepo extends JpaRepository <AccountBalance, Integer>{

}
