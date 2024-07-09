package com.BankApp.demo.Repository;

import com.BankApp.demo.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface UserRepo extends JpaRepository<Users, Integer>  {
    boolean existsByUserName(String userName);

    boolean existsByPassword(String password);

    boolean existsByChequingNumber(String chequingNumber);

    boolean existsByPhoneNumber(Long phoneNumber);

    boolean existsByGmail(String gmail);

    @Query("SELECT u.id FROM Users AS u WHERE u.userName = :userName")
    Integer getUserIdByUserName(String userName);

    @Query("SELECT u.userName FROM Users AS u WHERE u.id = :id")
    String getUserNameById(Integer id);

    @Query("SELECT u.fname FROM Users AS u WHERE u.id = :id")
    String getUserFirstNameById(int id);

    @Query("SELECT u.lname FROM Users AS u WHERE u.id = :id")
    String getUserLastNameById(int id);

    @Query("SELECT u.chequingNumber FROM Users as u WHERE u.id = :id")
    String getUserChequingNumberById(int id);

    @Query("SELECT u.phoneNumber FROM Users as u WHERE u.id = :id")
    String getUserPhoneNumberById(int id);

    @Query("SELECT u.pin FROM Users as u WHERE u.id = :id")
    String getUserPinById(int id);

    @Query("SELECT u.gmail FROM Users as u WHERE u.id = :id")
    String getUserGmailById(int id);
}
