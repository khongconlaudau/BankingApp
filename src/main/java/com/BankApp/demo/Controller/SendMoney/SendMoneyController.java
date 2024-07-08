package com.BankApp.demo.Controller.SendMoney;

import com.BankApp.demo.Repository.UserRepo;
import com.BankApp.demo.SpringFXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
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
    private Scene scene;
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
    public SendMoneyController(SpringFXMLLoader fxmlLoader, UserRepo userRepo) {
        this.fxmlLoader = fxmlLoader;
        this.userRepo = userRepo;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initilize items for choice box
        String[] items = {"Clothes", "Bills", "Travel", "Heath&Beauty", "Food", "Others"};
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

    // Check if the needs are met to switch to the Pin Scene
    public boolean meetAll() {
        double amount = Double.parseDouble(amountTField.getText().replace(",",""));

        if (!validAmount())  return false;
        else if (!validSender()) return false;
        else if (!validReceiver()) return false;
        else if (!boxIsSelected()) return false;
        return true;
    }

    // check if amount is valid number
    public boolean validAmount(){
        double amount = Double.parseDouble(amountTField.getText().replace(",",""));
        return amount >0 && !(amountTField.getText() ==null);
    }
    // check if sender is a valid input or not
    public boolean validSender(){
        return !sender.getText().isEmpty() && !sender.getText().matches(".*\\d.*");
    }

    // check if receiver exists or not
    public boolean validReceiver() {
        return (userRepo.existsByGmail(receiver.getText()) && !receiver.getText().isEmpty());
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
}
