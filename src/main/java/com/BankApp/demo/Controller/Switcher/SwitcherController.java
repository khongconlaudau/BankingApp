package com.BankApp.demo.Controller.Switcher;

import com.BankApp.demo.SpringFXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SwitcherController {
    @FXML
    private SpringFXMLLoader loader;
    private Stage stage;
    private Scene scene;

    public SwitcherController(){}

    @Autowired
    public SwitcherController(SpringFXMLLoader loader) {
        this.loader = loader;
    }

    // switch Interface
    public void switchToLogin(ActionEvent event,String fxmlPath){
        Parent root = loader.load(fxmlPath);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



}
