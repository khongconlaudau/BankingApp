package com.BankApp.demo.Controller.Transactions;

import com.BankApp.demo.Controller.Dashboard.DashboardController;
import com.BankApp.demo.Model.Transactions;
import com.BankApp.demo.Model.Users;
import com.BankApp.demo.Repository.TransactionRepo;
import com.BankApp.demo.SpringFXMLLoader;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Component
public class TransactionController implements Initializable {
    private final DashboardController dashboardController;
    private TransactionRepo transactionRepo;
    private SpringFXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;

    @FXML
    private VBox vBox;

    @Autowired
    public TransactionController(TransactionRepo transactionRepo, SpringFXMLLoader fxmlLoader, DashboardController dashboardController) {
        this.transactionRepo = transactionRepo;
        this.fxmlLoader = fxmlLoader;
        this.dashboardController = dashboardController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Transactions> allTransactions = new ArrayList<>();
        allTransactions.addAll(transactionRepo.findAllByUsersOrderByDate(dashboardController.getCurrentUser()));
        allTransactions.addAll(transactionRepo.findAllByReceiverOrderByDate(dashboardController.getCurrentUserGmail()));

        allTransactions.sort((Transactions t1, Transactions t2) -> t2.getDate().compareTo(t1.getDate())
        );


        for (Transactions t : allTransactions) {
            if(t.getUsers().getId().equals(dashboardController.getCurrentUser().getId())){
                addToTransaction(t.getDate(), t.getReceiver(), t.getUsers().getGmail(), t.getAmount(), "red");
            }else if (t.getReceiver().equals(dashboardController.getCurrentUserGmail())){
                addToTransaction(t.getDate(), t.getReceiver(), t.getUsers().getGmail(), t.getAmount(), "green");
            }
        }
    }


    //  transaction for money sent
    public void addToTransaction(LocalDate now,String receiver, String sender,Double amount,String arrowColor) {
        //saveToTransactionToDB(now, receiver,sender,amount);

        HBox newHBox = new HBox();
        newHBox.getStyleClass().add("hbox");

        VBox arrowBox = new VBox();
        arrowBox.getStyleClass().add("arrowBox");

        FontAwesomeIconView rightArrow = new FontAwesomeIconView(FontAwesomeIcon.LONG_ARROW_RIGHT);


        FontAwesomeIconView leftArrow = new FontAwesomeIconView(FontAwesomeIcon.LONG_ARROW_LEFT);

        if (arrowColor.equals("red")){
            leftArrow.getStyleClass().add("redArrow");
            rightArrow.getStyleClass().add("defaultArrow");
        }else if (arrowColor.equals("green")) {
            rightArrow.getStyleClass().add("greenArrow");
            leftArrow.getStyleClass().add("defaultArrow");
        }
        Text date  = new Text(now.toString());
        date.setWrappingWidth(71);
        date.getStyleClass().add("date");

        arrowBox.getChildren().addAll(rightArrow,leftArrow);

        Label receiverLabel = new Label(receiver);
        receiverLabel.getStyleClass().add("label");

        Line seperator = new Line(0,0,20,0);
        seperator.getStyleClass().add("line");

        Label senderLabel = new Label(sender);
        senderLabel.getStyleClass().add("label");

        FontAwesomeIconView bell = new FontAwesomeIconView(FontAwesomeIcon.BELL);
        bell.getStyleClass().add("bell");

        Label amountLabel = new Label("$"+String.format("%.2f",amount));
        amountLabel.getStyleClass().add("moneyLabel");

        newHBox.getChildren().addAll(arrowBox,date,receiverLabel,seperator,senderLabel,bell,amountLabel);

        this.vBox.getChildren().add(newHBox);


    }

    public void saveToTransactionToDB(LocalDate now, String receiver, String sender,Double amount, String expenses,Double previousBalance, Double availableBalance,Users currentUser) {
        Transactions transaction = new Transactions();
        transaction.setDate(now);
        transaction.setReceiver(receiver);
        transaction.setSender(sender);
        transaction.setAmount(amount);
        transaction.setExpenses(expenses);
        transaction.setPreviousBalance(previousBalance);
        transaction.setAvailableBalance(availableBalance);
        transaction.setUsers(currentUser);
        transactionRepo.save(transaction);
    }

    public void goBack(ActionEvent event) {
        Parent root = fxmlLoader.load("/fxml/DashBoard.fxml");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}