package com.BankApp.demo.Controller.Dashboard;

import com.BankApp.demo.Controller.Loggin.LoginController;
import com.BankApp.demo.Controller.Register.RegisterController;
import com.BankApp.demo.Model.Transactions;
import com.BankApp.demo.Model.Users;
import com.BankApp.demo.Repository.AccountBalanceRepo;
import com.BankApp.demo.Repository.TransactionRepo;
import com.BankApp.demo.Repository.UserRepo;
import com.BankApp.demo.SpringFXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Component
public class DashboardController implements Initializable {
    private  RegisterController registerController;
    @FXML
    private AreaChart<?,?> areaChart;
    @FXML
    private StackedBarChart<?,?> stackedBarChart;
    @FXML
    private Text nameOnCard;
    @FXML
    private Text expiredDate;
    // the limit on card in the dashboard
    @FXML
    private Text amountOnCard;
    @FXML
    private Text balance;
    @FXML
    private Text insideTransfers;
    @FXML
    private Text clothes;
    @FXML
    private Text bills;
    @FXML
    private Text travel;
    @FXML
    private Text heathNbeauty;
    @FXML
    private Text food;
    @FXML
    private Text otherExpenses;

    private Stage stage;
    private Scene scene;
    private SpringFXMLLoader fxmlLoader;
    private UserRepo userRepo;
    private AccountBalanceRepo accountBalanceRepo;
    private LoginController loginController;
    private TransactionRepo transactionRepo;
    private List<Transactions> expenseTransactionList;

    @Autowired
    public DashboardController(SpringFXMLLoader fxmlLoader, UserRepo userRepo, LoginController loginController, AccountBalanceRepo accountBalanceRepo
            , RegisterController registerController, TransactionRepo transactionRepo) {
        this.fxmlLoader = fxmlLoader;
        this.userRepo = userRepo;
        this.loginController = loginController;
        this.accountBalanceRepo = accountBalanceRepo;
        this.registerController = registerController;
        this.transactionRepo = transactionRepo;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Integer id = userRepo.getUserIdByUserName(loginController.getUserName());
        expenseTransactionList = transactionRepo.findAllByUsersOrderByDate(getCurrentUser());


        //  set data for AreaChart
        //  Expenses Data
        addDataToExpenseChart();

        // set data for StackedBarChart
        // Income Data
        addDataToIncomeChart();


        // set Name on  card for users
        setNameOnCard();

        setLimitOnCard();


        setExpiredData();
//     Set Account Balance locates near the Expenses
//     Get Account Balance using Foreign Key in Account Balance table

       setBalance(accountBalanceRepo.getAccountBalancesByUsersId(getCurrentUser()).toString());


       setExpenseFields();

    }
    private void addDataToExpenseChart(){
        XYChart.Series series = new XYChart.Series();
        double[] expenseList = new double[12];

        for (Transactions t : expenseTransactionList) {
            if(t.getDate().getYear() == Year.now().getValue()){
                switch (t.getDate().getMonth()){
                    case Month.JANUARY -> expenseList[0] += t.getAmount();
                    case Month.FEBRUARY -> expenseList[1] += t.getAmount();
                    case Month.MARCH -> expenseList[2] += t.getAmount();
                    case Month.APRIL -> expenseList[3] += t.getAmount();
                    case Month.MAY -> expenseList[4] += t.getAmount();
                    case Month.JUNE -> expenseList[5] += t.getAmount();
                    case Month.JULY -> expenseList[6] += t.getAmount();
                    case Month.AUGUST -> expenseList[7] += t.getAmount();
                    case Month.SEPTEMBER -> expenseList[8] += t.getAmount();
                    case Month.OCTOBER -> expenseList[9] += t.getAmount();
                    case Month.NOVEMBER -> expenseList[10] += t.getAmount();
                    case Month.DECEMBER -> expenseList[11] += t.getAmount();
                }
            }
        }
        series.getData().add(new XYChart.Data("1", expenseList[0]));
        series.getData().add(new XYChart.Data("2", expenseList[1]));
        series.getData().add(new XYChart.Data("3", expenseList[2]));
        series.getData().add(new XYChart.Data("4", expenseList[3]));
        series.getData().add(new XYChart.Data("5", expenseList[4]));
        series.getData().add(new XYChart.Data("6", expenseList[5]));
        series.getData().add(new XYChart.Data("7", expenseList[6]));
        series.getData().add(new XYChart.Data("8", expenseList[7]));
        series.getData().add(new XYChart.Data("9", expenseList[8]));
        series.getData().add(new XYChart.Data("10", expenseList[9]));
        series.getData().add(new XYChart.Data("11", expenseList[10]));
        series.getData().add(new XYChart.Data("12", expenseList[11]));

        areaChart.setLegendVisible(false);
        areaChart.getData().add(series);
    }

    private void addDataToIncomeChart(){
        XYChart.Series series1 = new XYChart.Series();
        double[] incomeList = new double[12];
        List<Transactions> incomeTransactionList = transactionRepo.findAllByReceiverOrderByDate(getCurrentUserGmail());
        for (Transactions t : incomeTransactionList) {
            if (t.getDate().getYear() == Year.now().getValue()) {
                switch (t.getDate().getMonth()) {
                    case Month.JANUARY -> incomeList[0] += t.getAmount();
                    case Month.FEBRUARY -> incomeList[1] += t.getAmount();
                    case Month.MARCH -> incomeList[2] += t.getAmount();
                    case Month.APRIL -> incomeList[3] += t.getAmount();
                    case Month.MAY -> incomeList[4] += t.getAmount();
                    case Month.JUNE -> incomeList[5] += t.getAmount();
                    case Month.JULY -> incomeList[6] += t.getAmount();
                    case Month.AUGUST -> incomeList[7] += t.getAmount();
                    case Month.SEPTEMBER -> incomeList[8] += t.getAmount();
                    case Month.OCTOBER -> incomeList[9] += t.getAmount();
                    case Month.NOVEMBER -> incomeList[10] += t.getAmount();
                    case Month.DECEMBER -> incomeList[11] += t.getAmount();
                }
            }
        }
        series1.getData().add(new XYChart.Data("Jan", incomeList[0]));
        series1.getData().add(new XYChart.Data("Feb", incomeList[1]));
        series1.getData().add(new XYChart.Data("Mar", incomeList[2]));
        series1.getData().add(new XYChart.Data("Apr", incomeList[3]));
        series1.getData().add(new XYChart.Data("May", incomeList[4]));
        series1.getData().add(new XYChart.Data("Jun", incomeList[5]));
        series1.getData().add(new XYChart.Data("Jul", incomeList[6]));
        series1.getData().add(new XYChart.Data("Aug", incomeList[7]));
        series1.getData().add(new XYChart.Data("Sep", incomeList[8]));
        series1.getData().add(new XYChart.Data("Oct", incomeList[9]));
        series1.getData().add(new XYChart.Data("Nov", incomeList[10]));
        series1.getData().add(new XYChart.Data("Dec", incomeList[11]));

        stackedBarChart.setOpacity(1);
        stackedBarChart.setLegendVisible(false);
        stackedBarChart.getData().add(series1);
    }

    // set User's name on Card
    private void setNameOnCard(){
        nameOnCard.setText(userRepo.getUserFirstNameById(getCurrentUserId()).toUpperCase() + " " + userRepo.getUserLastNameById(getCurrentUserId()).toUpperCase());
    }
    // set limit money can spend on card of the users
    // default value will be $2000
    private void setLimitOnCard(){
        amountOnCard.setText("2000");
    }

    // set expired date of the card ( being expired after 5 years created account(card))
    private void setExpiredData(){
        String expiredYear = new Integer((getCurrentUser().getCreatedDate().getYear() +5)).toString().substring(2,4);

        String expiredMonth = new Integer(getCurrentUser().getCreatedDate().getMonthValue()).toString();

        if (expiredMonth.length() == 1){
            expiredMonth = "0"+expiredMonth;
        }

        expiredDate.setText(expiredMonth + "/" + expiredYear);
    }
    private void setExpenseFields(){
        // set Expenses fields
        double insideTransfersAmount = 0.00;
        double clothesAmount = 0.00;
        double billsAmount = 0.00;
        double travelAmount = 0.00;
        double heathNbeautyAmount = 0.00;
        double foodAmount = 0.00;
        double otherExpensesAmount = 0.00;

        for (Transactions t : expenseTransactionList) {
            switch (t.getExpenses()) {
                case "Inside Transfer" -> insideTransfersAmount += t.getAmount();
                case "Clothes" -> clothesAmount += t.getAmount();
                case "Bills" -> billsAmount += t.getAmount();
                case "Travel" -> travelAmount += t.getAmount();
                case "HeathNBeauty" -> heathNbeautyAmount += t.getAmount();
                case "Food" -> foodAmount += t.getAmount();
                case "Others" -> otherExpensesAmount += t.getAmount();
            }
        }

        insideTransfers.setText(String.format("Inside Transfers: $%.2f", insideTransfersAmount));
        clothes.setText(String.format("Clothes: $%.2f", clothesAmount));
        bills.setText(String.format("Bills: $%.2f", billsAmount));
        travel.setText(String.format("Travel: $%.2f", travelAmount));
        heathNbeauty.setText(String.format("HeathNBeauty: $%.2f", heathNbeautyAmount));
        food.setText(String.format("Food: $%.2f", foodAmount));
        otherExpenses.setText(String.format("Others: $%.2f", otherExpensesAmount));
    }

    // switch to SavingAccount Scene
    @SneakyThrows
    @FXML
    public void switchToSavingAccount(ActionEvent event){
        Parent root = fxmlLoader.load("/fxml/SavingAccount.fxml");
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // switch to SendMoney Scene
    @SneakyThrows
    @FXML
    public void switchToSendMoney(javafx.event.ActionEvent event) {
        Parent root = fxmlLoader.load("/fxml/SendMoney.fxml");
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // switch to Transactions Scene
    @SneakyThrows
    @FXML
    public void switchToTransactions(javafx.event.ActionEvent event)  {
        Parent root = fxmlLoader.load("/fxml/Transactions.fxml");
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // switch to Account Scene
    @SneakyThrows
    @FXML
    public void switchToAccount(javafx.event.ActionEvent event) {
        Parent root = fxmlLoader.load("/fxml/Account.fxml");
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // get Id of the user account in database
  public Integer getCurrentUserId(){
        return  userRepo.getUserIdByUserName(loginController.getUserName());
  }

  public String getCurrentUserPin(){
        return userRepo.getUserPinById(getCurrentUserId());
  }

  public Users getCurrentUser(){
       return userRepo.getUsersById(getCurrentUserId());
      }

  public String getCurrentUserGmail(){
        return userRepo.getUserGmailById(getCurrentUserId());
  }

  public String getBalance() {
        return balance.getText();
    }

  public void setBalance(String balance){
        this.balance.setText(balance);
  }

  public String getCurrentUserFullName(){
        return userRepo.getUserFirstNameById(getCurrentUserId())+" "+ userRepo.getUserLastNameById(getCurrentUserId());
  }

  public String getCurrentUserName(){
     return userRepo.getUserNameById(getCurrentUserId());
  }

  public String getCurrentUserChequing(){
        return userRepo.getUserChequingNumberById(getCurrentUserId());
  }

  public String getCurrentUserPhoneN(){
        return userRepo.getUserPhoneNumberById(getCurrentUserId());
  }

}
