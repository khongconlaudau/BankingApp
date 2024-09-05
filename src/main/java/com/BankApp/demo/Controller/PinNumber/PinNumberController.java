package com.BankApp.demo.Controller.PinNumber;

import com.BankApp.demo.Controller.Dashboard.DashboardController;
import com.BankApp.demo.Controller.SendMoney.SendMoneyController;
import com.BankApp.demo.Controller.Transactions.TransactionController;
import com.BankApp.demo.Repository.AccountBalanceRepo;
import com.BankApp.demo.SpringFXMLLoader;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@Component
public class PinNumberController  {
    private SpringFXMLLoader loader;
    private DashboardController dashboardController;
    private AccountBalanceRepo accountBalanceRepo;
    private SendMoneyController sendMoneyController;
    private TransactionController transactionController;
    private Stage stage;
    private Scene scene;
    private String pin = "";
    private int limitedEnter = 0;
    @FXML
    private Text msgText;
    @FXML
    private Button button0,button1, button2, button3, button4, button5, button6, button7, button8, button9,delButton,enterButton,backButton;
    @FXML
    private Label unit1,unit2,unit3,unit4;


    @Autowired
    public PinNumberController(SpringFXMLLoader loader, DashboardController dashboardController,
                               AccountBalanceRepo accountBalanceRepo, SendMoneyController sendMoneyController,
                               TransactionController transactionController) {
        this.loader = loader;
        this.dashboardController = dashboardController;
        this.accountBalanceRepo = accountBalanceRepo ;
        this.sendMoneyController = sendMoneyController;
        this.transactionController = transactionController;
    }
    // go back to Send Money secene when click on beack button on the Top Left
    @FXML
    public void goBack( ActionEvent event) {
      // clear pin before go back
      pin = "";
      Parent root = loader.load("/fxml/SendMoney.fxml");
      stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
    }

    // go back to DashBoard
    public void backtoDashBoard(ActionEvent event){
        Parent root = loader.load("/fxml/Dashboard.fxml");
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // get user's input numbers in order from unit1 to unit4 (PIN)
    public void controlButton(Button button) {
        if(unit1.getText().isEmpty() || unit1.getText() ==null){
            unit1.setText(button.getText());
            pin += unit1.getText();
        } else if (unit2.getText().isEmpty() || unit2.getText() ==null){
            unit2.setText(button.getText());
            pin += unit2.getText();
        } else if(unit3.getText().isEmpty() || unit3.getText() ==null){
            unit3.setText(button.getText());
            pin += unit3.getText();
        } else if(unit4.getText().isEmpty() || unit4.getText() ==null){
            unit4.setText(button.getText());
            pin += unit4.getText();
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

    public void delete(ActionEvent event) {
        if(!(unit4.getText().isEmpty()) ){
            unit4.setText("");
            pin = pin.substring(0,3);
        } else if (!(unit3.getText().isEmpty())){
            unit3.setText("");
            pin = pin.substring(0,2);
        } else if (!(unit2.getText().isEmpty())){
            unit2.setText("");
            pin = pin.substring(0,1);
        } else if (!(unit1.getText().isEmpty())){
            unit1.setText("");
            pin = "";

        }
    }



    public void enterButton(ActionEvent event) throws InterruptedException {
        if(validPin()){

            sendMoneyController.confirmStage(event);
           boolean userConfirm = sendMoneyController.getConfirmChoice();
            if (!userConfirm){
                backtoDashBoard(event);
                pin = "";
            } else  {

                // sender (current user)
                double previousBalance = Double.parseDouble(dashboardController.getBalance());
                double availableBalance = previousBalance - sendMoneyController.getAmount();
                LocalDate now = LocalDate.now();

                // save the transaction to database
                transactionController.saveToTransactionToDB(now,sendMoneyController.getReceiver(),sendMoneyController.getSender(),sendMoneyController.getAmount()
                        ,sendMoneyController.getItem(),previousBalance,availableBalance, dashboardController.getCurrentUser());

                // update balance of the sender in database
                accountBalanceRepo.updateBalances(
                        availableBalance,
                        dashboardController.getCurrentUser()
                );

                // receiver
                // update balance of the receiver in database
                accountBalanceRepo.updateBalances(
                        accountBalanceRepo.getAccountBalancesByUsersId(
                                sendMoneyController.identifyReceiver())
                                + sendMoneyController.getAmount(), sendMoneyController.identifyReceiver()
                );


                // prompt msg to notify successful transfer
                msgText.setText("Send money is successful!");
                msgText.setFill(Color.GREEN);

                pin = "";
                PauseTransition pause = getPauseTransition(event);
                pause.play();
            }


        }else{
            if (limitedEnter <= 3) {msgText();
            limitedEnter++;}
            /* if limit enter time reaches to 4 times it will automatically disable all button
                and return back to DashBoard*/
            else if (limitedEnter ==4) {

                    msgText.setText("Incorrect Pin. Return to DashBoard after 3 second!");
                PauseTransition pause = getPauseTransition(event);
                pause.play();


            }
        }
    }

    private PauseTransition getPauseTransition(ActionEvent event) {
        Button[] buttons = {button0,button1,button2
                ,button3,button4,button5
                ,button6,button7,button8
                ,button9,delButton,enterButton,backButton};

        for(Button b : buttons){
            b.setDisable(true);
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e ->{
                    msgText.setText("Back to DashBoard in 3s!");
                    PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
                    pause2.setOnFinished(e2 -> {
                        msgText.setText("Back to DashBoard in 2s!");
                        PauseTransition pause3 = new PauseTransition(Duration.seconds(1));
                        pause3.setOnFinished(e3 -> {
                            msgText.setText("Back to DashBoard in 1s!");
                            PauseTransition pause4 = new PauseTransition(Duration.seconds(1));
                            pause4.setOnFinished(e4 -> {
                                backtoDashBoard(event);
                                limitedEnter = 0;
                            });
                            pause4.play();
                        });
                        pause3.play();
                    });
                    pause2.play();
                });
        return pause;
    }

    // check if the Pin is valid
    public boolean validPin(){
        if (pin.isEmpty() || !pin.equalsIgnoreCase(dashboardController.getCurrentUserPin())){
            return false;
        }
        return true;
    }

    public void msgText() throws InterruptedException {
        if (pin.isEmpty()) {
            msgText.setText("Please enter a valid Pin!");
            if (limitedEnter ==3){
                msgText.setText("Please enter a valid Pin. Last try!");
            }
        }
        else if (!pin.equalsIgnoreCase(dashboardController.getCurrentUserPin())) {
            msgText.setText("Incorrect Pin. Try again!");
            if (limitedEnter ==3){
                msgText.setText("Incorrect Pin. Last try!");
            }
        }
    }

}
