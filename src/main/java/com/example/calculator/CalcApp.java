package com.example.calculator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class CalcApp extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalcApp.class.getResource("calc-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SmartCalc 3.0");
        try(InputStream iconStream = getClass().getResourceAsStream("icon-calc-ext.png")) {
            Image image = new Image(Objects.requireNonNull(iconStream));
            stage.getIcons().add(image);
        } catch (Exception e) {
            System.err.println("Error: icon image not found");
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}