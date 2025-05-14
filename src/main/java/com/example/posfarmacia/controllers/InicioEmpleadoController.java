package com.example.posfarmacia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InicioEmpleadoController {
    @FXML
    private Pane contenedorPantallasEm;

    @FXML
    private Button ventasEm, productosEm, inventarioEm, comprasEm, corteEm, facturasEm, configEm;
    @FXML private Label horaFechaEm;
    @FXML private Label usuarioEm;
    @FXML private Button cerrarSesionEm;


    @FXML
    public void initialize() {
        iniciarReloj();
        cerrarSesionEm.setOnAction(e -> volverAlLogin());
        ventasEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/ventasEmpleado.fxml"));
        productosEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/productosEmpleado.fxml"));
        inventarioEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/inventarioEmpleado.fxml"));
        comprasEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/comprasEmpleado.fxml"));
        corteEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/corteEmpleado.fxml"));
        facturasEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/facturasEmpleado.fxml"));
        configEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/configuracionEmpleado.fxml"));
        // ...agrega para los otros botones también

    }

    private void iniciarReloj() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        Timeline reloj = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            horaFechaEm.setText(LocalDateTime.now().format(formato));
        }));
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
    }

    public void setUsuarioActual(String nombreUsuario) {
        usuarioEm.setText(nombreUsuario);
    }

    public void volverAlLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) cerrarSesionEm.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarPantalla(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent pantalla = loader.load();

            // Limpia el contenido actual y añade el nuevo
            contenedorPantallasEm.getChildren().clear();
            contenedorPantallasEm.getChildren().add(pantalla);

            // Ajusta el tamaño de la nueva vista al del contenedor
            AnchorPane.setTopAnchor(pantalla, 0.0);
            AnchorPane.setBottomAnchor(pantalla, 0.0);
            AnchorPane.setLeftAnchor(pantalla, 0.0);
            AnchorPane.setRightAnchor(pantalla, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
