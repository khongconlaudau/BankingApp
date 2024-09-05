package com.BankApp.demo.Repository;

import com.BankApp.demo.Model.Transactions;
import com.BankApp.demo.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transactions, Integer> {
    List<Transactions> findAllByUsersOrderByDate(Users users);

    List<Transactions> findAllByReceiverOrderByDate(String receiver);

    @Query ("SELECT t.expenses FROM Transactions as t WHERE t.users = :user")
    String getExpensesByUser(Users user);




}
