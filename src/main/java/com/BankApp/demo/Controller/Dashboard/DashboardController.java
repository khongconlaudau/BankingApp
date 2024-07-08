package com.BankApp.demo.Controller.Dashboard;

import com.BankApp.demo.Controller.Loggin.LoginController;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class DashboardController implements Initializable {
    @FXML
    private AreaChart<?,?> areaChart;
    @FXML
    private StackedBarChart<?,?> stackedBarChart;
    @FXML
    private Text nameOnCard;
    // the limit on card in the dashboard
    @FXML
    private Text amount;
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
    private LoginController loginController;

    @Autowired
    public DashboardController(SpringFXMLLoader fxmlLoader,UserRepo userRepo,LoginController loginController) {
        this.fxmlLoader = fxmlLoader;
        this.userRepo = userRepo;
        this.loginController = loginController;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Integer id = userRepo.getUserIdByUserName(loginController.getUserName());
        //  set data for AreaChart
        XYChart.Series series = new XYChart.Series();

        series.getData().add(new XYChart.Data("1", 10));
        series.getData().add(new XYChart.Data("2", 22));
        series.getData().add(new XYChart.Data("3", 0));
        series.getData().add(new XYChart.Data("4", 40));
        series.getData().add(new XYChart.Data("5", 80));
        series.getData().add(new XYChart.Data("6", 0));
        series.getData().add(new XYChart.Data("7", 0));
        series.getData().add(new XYChart.Data("8", 0));
        series.getData().add(new XYChart.Data("9", 0));
        series.getData().add(new XYChart.Data("10", 0));
        series.getData().add(new XYChart.Data("11", 0));
        series.getData().add(new XYChart.Data("12", 0));

        areaChart.getData().add(series);

        // set data for StackedBarChart

        XYChart.Series series1 = new XYChart.Series();

        series1.getData().add(new XYChart.Data("Jan", 10));
        series1.getData().add(new XYChart.Data("Feb", 22));
        series1.getData().add(new XYChart.Data("Mar", 0));
        series1.getData().add(new XYChart.Data("Apr", 40));
        series1.getData().add(new XYChart.Data("May", 80));
        series1.getData().add(new XYChart.Data("Jun", 0));
        series1.getData().add(new XYChart.Data("Jul", 0));
        series1.getData().add(new XYChart.Data("Aug", 0));
        series1.getData().add(new XYChart.Data("Sep", 0));
        series1.getData().add(new XYChart.Data("Oct", 0));
        series1.getData().add(new XYChart.Data("Nov", 0));
        series1.getData().add(new XYChart.Data("Dec", 0));

        stackedBarChart.getData().add(series1);

        // set Name on card for users
        nameOnCard.setText(userRepo.getUserFirstNameById(id) + " " + userRepo.getUserLastNameById(id));

        // set limit money can spend on card of the users
        amount.setText("2000");

//        // if account is created successfully, the default balance will be $1000 for each account
//        // ***** WRONG ******
//        balance.setText("1000");
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
  public Integer getUserId(){
        return  userRepo.getUserIdByUserName(loginController.getUserName());
  }



}
