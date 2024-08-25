package com.BankApp.demo.Repository;

import com.BankApp.demo.Model.AccountBalance;
import com.BankApp.demo.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface AccountBalanceRepo extends JpaRepository <AccountBalance, Integer>{
    @Query("SELECT a.balances FROM AccountBalance AS a WHERE a.users = :user")
    Double  getAccountBalancesByUsersId(@Param("user") Users user);

    @Transactional
    @Modifying
    @Query("UPDATE AccountBalance as a SET a.balances = :balances WHERE a.users = :user")
    void updateBalances(@Param("balances") Double balances, @Param("user") Users user);

}
