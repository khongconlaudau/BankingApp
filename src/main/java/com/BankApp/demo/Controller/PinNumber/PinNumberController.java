package com.BankApp.demo.Controller.PinNumber;

import com.BankApp.demo.SpringFXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PinNumberController  {
    private SpringFXMLLoader loader;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button button0,button1, button2, button3, button4, button5, button6, button7, button8, button9,delButton,enterButton;
    @FXML
    private Label unit1,unit2,unit3,unit4;

    @Autowired
    public PinNumberController(SpringFXMLLoader loader) {
        this.loader = loader;
    }
    // go back to Send Money secene when click on beack button on the Top Left
    @FXML
    public void goBack( ActionEvent event) {
      Parent root = loader.load("/fxml/SendMoney.fxml");
      stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
    }

    // zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz
    public void controlButton(Button button) {
        if(unit1.getText().isEmpty() || unit1.getText() ==null){
            unit1.setText(button.getText());
        } else if (unit2.getText().isEmpty() || unit2.getText() ==null){
            unit2.setText(button.getText());
        } else if(unit3.getText().isEmpty() || unit3.getText() ==null){
            unit3.setText(button.getText());
        } else if(unit4.getText().isEmpty() || unit4.getText() ==null){
            unit4.setText(button.getText());
        }
    }

    public void controlButton0() {
       controlButton(this.button0);
    }

    public void controlButton1(){
        controlButton(this.button1);
    }
    public void controlButton2() {
       controlButton(this.button2);
    }

    public void controlButton3() {
        controlButton(this.button3);
    }

    public void controlButton4() {
        controlButton(this.button4);
    }

    public void controlButton5() {
        controlButton(this.button5);
    }

    public void controlButton6() {
       controlButton(this.button6);
    }

    public void controlButton7() {
        controlButton(this.button7);
    }

    public void controlButton8() {
        controlButton(this.button8);
    }

    public void controlButton9() {
        controlButton(this.button9);
    }

    public void delet(ActionEvent event) {
        if(!(unit4.getText().isEmpty()) ){
            unit4.setText("");
        } else if (!(unit3.getText().isEmpty())){
            unit3.setText("");
        } else if (!(unit2.getText().isEmpty())){
            unit2.setText("");
        } else if (!(unit1.getText().isEmpty())){
            unit1.setText("");
        }
    }

    // continue to send Money after check Pin #
    public void enterButton() {

    }
    // check Pin


}
