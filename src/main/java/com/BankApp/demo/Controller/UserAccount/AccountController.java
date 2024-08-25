package com.BankApp.demo.Controller.UserAccount;

import com.BankApp.demo.Controller.Dashboard.DashboardController;
import com.BankApp.demo.Repository.UserRepo;
import com.BankApp.demo.SpringFXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AccountController implements Initializable {
    @FXML
    private Text fullName;
    @FXML
    private Text userName;
    @FXML
    private Text chequingInfor;
    @FXML
    private Text phoneNumber;
    @FXML
    private Text pinInfor       ;
    @FXML
    private Text gmailInfor;

    private SpringFXMLLoader loader;
    private Stage stage;
    private Scene scene;
    private UserRepo userRepo;
    private DashboardController dashboardController;




    @Autowired
    public AccountController(SpringFXMLLoader loader, UserRepo userRepo,
                             DashboardController dashboardController) {
        this.loader = loader;
        this.userRepo = userRepo;
        this.dashboardController = dashboardController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set Full name of user by using user's id
        String currentUserFullName = dashboardController.getCurrentUserFullName();
        fullName.setText("Full Name: "+ currentUserFullName);

        // set Username information by using user's id
        String currentUserName = dashboardController.getCurrentUserName();
        userName.setText("User Name: "+ currentUserName);

        // set user's Chequing by using user's id
        String currentUserChequing = dashboardController.getCurrentUserChequing();
        chequingInfor.setText("Chequing: "+ currentUserChequing);

        // set user's Phone Number by using user's id
        phoneNumber.setText("Phone Number: "+hidePhoneNumber());
        // set user's Pin by using user's id
        pinInfor.setText("Pin: ****" );

        // set user's Gmail by using user's id
        String currentUserGmail = dashboardController.getCurrentUserGmail();
        gmailInfor.setText("Gmail: "+ currentUserGmail);
    }

    // go back to Main Dashboard when click Home Button
    @FXML
    public void goBack(ActionEvent event){
        Parent root = loader.load("/fxml/Dashboard.fxml");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // convert Phone Number infor into hide character(*)
    public String hidePhoneNumber(){
        String pNumber = dashboardController.getCurrentUserPhoneN();
        char[]  formattedPNumber = pNumber.toCharArray();
        for(int i=0;i<formattedPNumber.length-3;i++){
            formattedPNumber[i] = '*';
        }
        return new String(formattedPNumber);
    }



}
