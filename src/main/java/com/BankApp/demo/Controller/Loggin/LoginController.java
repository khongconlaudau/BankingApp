package com.BankApp.demo.Controller.Loggin;

import com.BankApp.demo.Controller.Dashboard.DashboardController;
import com.BankApp.demo.Repository.UserRepo;
import com.BankApp.demo.SpringFXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginController {

    private UserRepo userRepo;
    private SpringFXMLLoader fxmlLoader;

    private Stage stage;
    private Scene scene;
    private boolean passwordVisible = false;
    private String user;
    private DashboardController dashboardController;
    @FXML
    private TextField userName;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label msgLabel;

    @FXML
    private Button signUpButton;

    @FXML
    private ToggleButton showButton;

    public LoginController(DashboardController dashboardController){
        this.dashboardController = dashboardController;
    }
    @Autowired
    public LoginController(UserRepo userRepo, SpringFXMLLoader fxmlLoader) {
        this.userRepo = userRepo;
        this.fxmlLoader = fxmlLoader;
    }


    // switch to Sign Up Account Scene
    @SneakyThrows
    @FXML
    public void signUp (ActionEvent event) {
        Parent root =  fxmlLoader.load("/fxml/SignUp.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void show(ActionEvent event){
        passwordVisible = !passwordVisible;
        // show passwd
        if (passwordVisible){
            passwordField.setPromptText("");
            passwordTextField.setPromptText("Password");
            passwordField.setDisable(true);
            passwordTextField.setDisable(false);
            passwordTextField.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordTextField.setVisible(true);
        }else {
            passwordTextField.setPromptText("");
            passwordField.setPromptText("Password");
            passwordTextField.setDisable(true);
            passwordField.setDisable(false);
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordTextField.setVisible(false);
        }
    }

    @SneakyThrows
    @FXML
    public void Login(ActionEvent event){
        if (userRepo.existsByUserName(userName.getText())){

            if (passwordVisible == true){
               if(userRepo.existsByPassword(passwordTextField.getText())) {
                   // save user account if login is successful
                   user = userName.getText();
                   switchToDashBoard(event);
               } else message();
            } else {
                if (userRepo.existsByPassword(passwordField.getText())){
                    // save user account if login is successful
                    user = userName.getText();
                  switchToDashBoard(event);
                }else message();
            }
        }else message();
    }


    @SneakyThrows
    public void switchToDashBoard(ActionEvent event){
        Parent root = fxmlLoader.load("/fxml/Dashboard.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void message() {
        if (!userRepo.existsByUserName(userName.getText())){
            msgLabel.setDisable(false);
            msgLabel.setText("Username does not exists");
        } else if (passwordVisible == true){
            if (!userRepo.existsByPassword(passwordTextField.getText())){
                msgLabel.setDisable(false);
                msgLabel.setText("Password does not exists");

            }
        } else  if (passwordVisible == false){
            if (!userRepo.existsByPassword(passwordField.getText())){
                msgLabel.setDisable(false);
                msgLabel.setText("Password does not exists");
            }
        }
    }
    // get Username to use for method set Name on Card  (in case username exists in database)
    public String getUserName(){
        return user;
    }
}
