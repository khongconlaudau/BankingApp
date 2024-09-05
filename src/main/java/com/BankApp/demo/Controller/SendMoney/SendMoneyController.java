package com.BankApp.demo.Controller.SendMoney;

import com.BankApp.demo.Controller.Dashboard.DashboardController;
import com.BankApp.demo.Model.Users;
import com.BankApp.demo.Repository.UserRepo;
import com.BankApp.demo.SpringFXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

@Component
public class SendMoneyController implements Initializable {
    private SpringFXMLLoader fxmlLoader;
    private UserRepo userRepo;
    private Stage stage;
    private Stage secondaryStage;
    private Scene scene;
    private boolean confirmChoice;
    @Setter
    @Getter
    private double amount = -1;
    private DashboardController dashboardController;
    @FXML
    private ChoiceBox<String> spendingBox;
    @FXML
    private TextField amountTField;
    @FXML
    private TextField sender;
    @FXML
    private TextField receiver;
    @FXML
    private Text msgText;

    @Autowired
    public SendMoneyController(SpringFXMLLoader fxmlLoader, UserRepo userRepo,DashboardController dashboardController) {
        this.fxmlLoader = fxmlLoader;
        this.userRepo = userRepo;
        this.dashboardController = dashboardController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initilize items for choice box
        String[] items = {"Inside Transfer","Clothes", "Bills", "Travel", "Heath&Beauty", "Food", "Others"};
        spendingBox.getItems().addAll(items);

        // Regex text field with Currency form
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        StringConverter<Double> stringConverter = new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                if (value != null) return decimalFormat.format(value);
                return "";
            }

            @SneakyThrows
            @Override
            public Double fromString(String text) {
                if (text.isEmpty()) {
                    return 0.0;
                }
                Number number = decimalFormat.parse(text);
                return number.doubleValue();
            }
        };
        TextFormatter<Double> formatter = new TextFormatter<>(stringConverter);
        amountTField.setTextFormatter(formatter);
    }

    // go back to Dashboard when click on Button home on the Top Left
    @SneakyThrows
    public void goBack(ActionEvent event) {
        Parent root = fxmlLoader.load("/fxml/DashBoard.fxml");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // When click on the continue button it will continue if all the needs are met
    @SneakyThrows
    public void continueTransfer(ActionEvent event) {
        if (meetAll()) {
            Parent root = fxmlLoader.load("/fxml/PinNumber.fxml");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else message();
    }

    public void confirmStage(ActionEvent event){
        confirmChoice = false;
        Parent root = fxmlLoader.load("/fxml/Confirmation.fxml");
        Scene scene = new Scene(root);

        secondaryStage = new Stage();
        secondaryStage.setScene(scene);
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        secondaryStage.initModality(Modality.WINDOW_MODAL);
        secondaryStage.initOwner(this.stage);

        secondaryStage.showAndWait();

    }

    @FXML
    public void clickedYes(ActionEvent event){
        confirmChoice = true;
        secondaryStage.close();

    }

    @FXML
    public void clickedNo(ActionEvent event){
        confirmChoice = false;
        secondaryStage.close();

    }



    // Check if the needs are met to switch to the Pin Scene
    public boolean meetAll() {
        if (!validAmount())  return false;
        else if (!validSender()) return false;
        else if (!validReceiver()) return false;
        else if (!boxIsSelected()) return false;
        return true;
    }

    // check if amount is valid number
    public boolean validAmount(){

        if (!amountTField.getText().isEmpty()) {
        amount = Double.parseDouble(amountTField.getText().replace(",",""));}
        return amount >0 && amount <= Double.parseDouble(dashboardController.getBalance()) && !(amountTField.getText().isEmpty());
    }
    // check if sender is a valid input or not
    public boolean validSender(){
        return !sender.getText().isEmpty() && !sender.getText().matches(".*\\d.*") && (sender.getText().length() >=2) && !sender.getText().isBlank();
    }

    // check if receiver exists or not
    public boolean validReceiver() {
        return (userRepo.existsByGmail(receiver.getText()) && !receiver.getText().isEmpty()) && !receiver.getText().equals(dashboardController.getCurrentUserGmail());
    }

    // check if spendingBox is selected or not
    public boolean boxIsSelected(){
        return !(spendingBox.getValue() == null);
    }

    // send msg if users input invalid inputs
    @FXML
    public void message() {
        if (!validAmount()) msgText.setText("Message: Please enter a valid amount.");
        else if(!validSender()) msgText.setText("Message: Please enter a valid sender.");
        else if(!validReceiver()) msgText.setText("Message: Please enter a valid receiver.");
        else if(!boxIsSelected()) msgText.setText("Message: Please select a item.");
    }


    // identify who gonna receive the money if the receiver is valid
    public Users identifyReceiver(){
        if (validReceiver()) return  userRepo.findUsersByGmail(receiver.getText());
        return null;
    }

    public String getReceiver(){
        return receiver.getText();
    }

    public String getSender(){
        return sender.getText();
    }

    public String getItem(){
        return spendingBox.getValue();
    }


    public boolean getConfirmChoice(){
        return confirmChoice;
    }
}
