package com.BankApp.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {
	private ConfigurableApplicationContext context;
	private SpringFXMLLoader loader;
	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void init() throws Exception {
		context = SpringApplication.run(Main.class);
		loader = context.getBean(SpringFXMLLoader.class);

	}
	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(loader.load("/fxml/Login.fxml"));
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void stop() throws Exception {
		context.close();
	}
}
