package com.example.posfarmacia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InicioAdminController {

    @FXML private Button ventas;
    @FXML private Button productos;
    @FXML private Button inventario;
    @FXML private Button compras;
    @FXML private Button empleados;
    @FXML private Button cortes;
    @FXML private Button facturas;
    @FXML private Button reportes;
    @FXML private Button configuracion;
    @FXML private Pane contPantallas;
    @FXML private Label fechaHora;
    @FXML private Label usuario;
    @FXML private Button cerrarSesion;

    @FXML
    public void initialize() {
        iniciarReloj();
        cerrarSesion.setOnAction(e -> volverAlLogin());
        // Configura acciones para cada botón
        ventas.setOnAction(e -> cargarVista("ventas.fxml"));
        productos.setOnAction(e -> cargarVista("productos.fxml"));
        inventario.setOnAction(e -> cargarVista("inventario.fxml"));
        compras.setOnAction(e -> cargarVista("compras.fxml"));
        empleados.setOnAction(e -> cargarVista("empleados.fxml"));
        cortes.setOnAction(e -> cargarVista("cortes.fxml"));
        facturas.setOnAction(e -> cargarVista("facturas.fxml"));
        reportes.setOnAction(e -> cargarVista("reportes.fxml"));
        configuracion.setOnAction(e -> cargarVista("configuracion.fxml"));
    }

    private void cargarVista(String archivoFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/" + archivoFXML));
            Node vista = loader.load();
            contPantallas.getChildren().setAll(vista);

            // Opcional: expandir el contenido al tamaño del Pane
            vista.setLayoutX(0);
            vista.setLayoutY(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void iniciarReloj() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        Timeline reloj = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            fechaHora.setText(LocalDateTime.now().format(formato));
        }));
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
    }


    public void setUsuarioActual(String nombreUsuario) {
        usuario.setText(nombreUsuario);
    }

    public void volverAlLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) cerrarSesion.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
