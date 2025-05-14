package com.example.posfarmacia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 518, 702);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }

    //public static void main(String[] args) {
      //  launch();
    //}

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Poner el ícono (logo) en la ventana
        Image icono = new Image(getClass().getResourceAsStream("/logoFarmacia.png"));
        stage.getIcons().add(icono);

        stage.setTitle("Sistema Punto de Venta");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}