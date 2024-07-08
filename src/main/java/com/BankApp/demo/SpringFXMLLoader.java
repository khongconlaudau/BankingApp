package com.BankApp.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class SpringFXMLLoader {
    private final ApplicationContext context;

    public SpringFXMLLoader(ApplicationContext context){
        this.context = context;
    }

    @SneakyThrows
    public Parent load(String fxmlPath)  {
        try (InputStream fxmlStream = getClass().getResourceAsStream(fxmlPath)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(context::getBean);
            loader.setLocation(getClass().getResource(fxmlPath));
            return loader.load(fxmlStream);
        }
    }
}
