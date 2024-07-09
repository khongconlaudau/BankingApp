package com.BankApp.demo.Controller.Register;

import com.BankApp.demo.Checker.CheckNewAccount;
import com.BankApp.demo.Model.AccountBalance;
import com.BankApp.demo.Model.Users;
import com.BankApp.demo.Repository.AccountBalanceRepo;
import com.BankApp.demo.Repository.UserRepo;
import com.BankApp.demo.SpringFXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
@Component

public class RegisterController {

    private AccountBalanceRepo accountBalanceRepo;
    private UserRepo userRepo;
    private SpringFXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private Random rand;
    private final double defaultBalance = 1000;
    @FXML
    private Button backButton;

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label chequingLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField repasswordField;

    @FXML
    private CheckBox chequingButton;

    @FXML
    private  TextField phoneNumber;

    @FXML
    private  TextField pin;

    @FXML
    private TextField gmail;

    @FXML
    private Button createdButton;

    @FXML
    private Label messageLabel;

    @Autowired
    public RegisterController(UserRepo userRepo, SpringFXMLLoader fxmlLoader, AccountBalanceRepo accountBalanceRepo) {
        this.userRepo = userRepo;
        this.fxmlLoader = fxmlLoader;
        this.accountBalanceRepo = accountBalanceRepo;
    }

    // go back to Log in Account (currently at SignUp Interface)
    @SneakyThrows
    @FXML
    void goBack(ActionEvent event) {
        Parent root = fxmlLoader.load("/fxml/Login.fxml");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public  String getFirstName(){
        return fnameField.getText();
    }

    @FXML
    public String getLastName(){
        return lnameField.getText();
    }

    @FXML
    public String getUsername(){
        return usernameField.getText();
    }

    @FXML
    public String getChequing(){
        return chequingLabel.getText();
    }

    @FXML
    public String getPassword(){
        return passwordField.getText();
    }

    @FXML
    public String getRePassword(){
        return repasswordField.getText();
    }


    public String getPhoneNumber(){
        return phoneNumber.getText();
    }


    public String getPin(){
        return  pin.getText();
    }

    public String getGmail(){
        return gmail.getText();
    }

    // check if addSaving box is selected


    // show Chequeing label and random text when click the checkbox Chequing
    @FXML
    private void addChequing(ActionEvent event){
        rand = new Random();
        if (chequingButton.isSelected()){
            chequingLabel.setDisable(false);
            chequingLabel.setText(getUsername().substring(0,1) + rand.nextInt(100000,999999));
        }

    }

    // message if something is not valid
    @FXML
    private void errorMessage(){
        CheckNewAccount check = new CheckNewAccount(this);
        if (!check.checkFirstName()) messageLabel.setText("Message :Please enter a valid first name");
        else if (!check.checkLastName()) messageLabel.setText("Message :Please enter a valid last name");
        else if (!check.checkUserName()) messageLabel.setText("Message :Please enter a valid user name ");
        else if (!check.checkPassword()) messageLabel.setText("Message :Please enter a valid password");
        else if (!check.checkRePassword()) messageLabel.setText("Message :The password entered is not the same");
        else if (!check.checkChequing()) messageLabel.setText("Message :Please enter a valid chequing");
        else if (!check.checkPhoneNumber()) messageLabel.setText("Message :Please enter a valid phone number");
        else if (!check.checkPin()) messageLabel.setText("Message :Please enter a valid Pin");
        else if (!check.checkGmail()) messageLabel.setText("Message :Please enter a valid Gmail");
        else if (userNameExists()) messageLabel.setText("Message :Username already exists");
        else if (chequingExists()) messageLabel.setText("Message :Chequing already exists");
        else if (phoneNumberExists()) messageLabel.setText("Message :Phone Number already exists");
        else if (gmailExists()) messageLabel.setText("Message :Gmail already exists");

    }

    // requirements to create a new account
    public boolean checkNewAccount(){
        CheckNewAccount check = new CheckNewAccount(this);
        return check.meetAll() && !userNameExists()
                &&!chequingExists() && !phoneNumberExists()
                && !gmailExists();
    }

    // create new account if meet the requirements
    @FXML
    public void createAccount(MouseEvent event){
        if (checkNewAccount()){
            userRepo.save(getNewUser());
            messageLabel.setText("Account created successfully");
            saveDefaultBalance();
            clear();
        } else errorMessage();}

    @FXML
    private void clear(){
        fnameField.clear();
        lnameField.clear();
        usernameField.clear();
        passwordField.clear();
        repasswordField.clear();
        chequingLabel.setText("");
        chequingButton.setSelected(false);
        phoneNumber.clear();
        pin.clear();
        gmail.clear();
    }

    public boolean userNameExists(){
        return userRepo.existsByUserName(usernameField.getText());
    }

    public boolean chequingExists(){
        return userRepo.existsByChequingNumber(chequingLabel.getText());
    }

    public boolean phoneNumberExists(){
        return userRepo.existsByPhoneNumber(Long.parseLong(getPhoneNumber()));
    }

    public boolean gmailExists(){
        if (gmail.getText() == null) return false;
        else return userRepo.existsByGmail(getGmail());
    }

    public Users getNewUser(){
        Users user = new Users();
        user.setFname(getFirstName());
        user.setLname(getLastName());
        user.setUserName(getUsername());
        user.setPassword(getPassword());
        user.setChequingNumber(getChequing());
        user.setPhoneNumber(Long.parseLong(getPhoneNumber()));
        user.setPin(Integer.parseInt(getPin()));
        user.setGmail(getGmail());
        return user;
    }

    // set Default Balance account for new user, $1000 since created new account"
    public void saveDefaultBalance(){
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setBalance(defaultBalance);
        accountBalance.setUsers(getNewUser());
        accountBalanceRepo.save(accountBalance);
    }
}
