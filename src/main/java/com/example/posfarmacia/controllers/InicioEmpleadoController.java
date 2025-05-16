package com.example.posfarmacia.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InicioEmpleadoController {
    @FXML
    private Pane contenedorPantallasEm;

    @FXML private Button ventasEm;
    @FXML private Button productosEm;
    @FXML private Button inventarioEm;
    @FXML private Button comprasEm;
    @FXML private Button corteEm;
    @FXML private Button facturasEm;
    @FXML private Button configEm;
    @FXML private Label horaFechaEm;
    @FXML private Label usuarioEm;
    @FXML private Button cerrarSesionEm;


    @FXML
    public void initialize() {
        iniciarReloj();
        cerrarSesionEm.setOnAction(e -> volverAlLogin());
        //ventasEm.setOnAction(e -> cargarVista());
        //ventasEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/ventasEmpleado.fxml"));
//        productosEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/productosEmpleado.fxml"));
//        inventarioEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/inventarioEmpleado.fxml"));
//        comprasEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/comprasEmpleado.fxml"));
//        corteEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/corteEmpleado.fxml"));
//        facturasEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/facturasEmpleado.fxml"));
//        configEm.setOnAction(event -> cargarPantalla("/com/example/posfarmacia/configuracionEmpleado.fxml"));
//        // ...agrega para los otros botones también

    }

//    private void cargarVista() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/productosEmpleado.fxml"));
//            Node vista = loader.load();
//            contenedorPantallasEm.getChildren().setAll(vista);
//
//            // Opcional: expandir el contenido al tamaño del Pane
//            vista.setLayoutX(0);
//            vista.setLayoutY(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

//    private void cargarPantalla(String rutaFXML) {
//        try {
//            URL url = getClass().getResource("/com/example/posfarmacia/ventasEmpleado.fxml");
//            System.out.println("URL encontrada: " + url);
//
//            System.out.println(getClass().getResource(rutaFXML));
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
//            Parent pantalla = loader.load();
//
//            // Limpia el contenido actual y añade el nuevo
//            contenedorPantallasEm.getChildren().clear();
//            contenedorPantallasEm.getChildren().add(pantalla);
//
//            // Ajusta el tamaño de la nueva vista al del contenedor
//            AnchorPane.setTopAnchor(pantalla, 0.0);
//            AnchorPane.setBottomAnchor(pantalla, 0.0);
//            AnchorPane.setLeftAnchor(pantalla, 0.0);
//            AnchorPane.setRightAnchor(pantalla, 0.0);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void abrirVentasEmpleado(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/ventasEmpleado.fxml"));
            Pane vistaVentas = loader.load();
            contenedorPantallasEm.getChildren().setAll(vistaVentas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void manejarClicBoton(ActionEvent event) {
        Button boton = (Button) event.getSource();
        String fxml = "";

        switch (boton.getId()) {
            case "ventasEm":
                fxml = "ventasEmpleado.fxml";
                break;
            case "productosEm":
                fxml = "productosEmpleado.fxml";
                break;
//            case "inventarioEm":
//                fxml = "inventarioEmpleado.fxml";
//                break;
            // Agrega más casos según los botones que tengas
        }

        if (!fxml.isEmpty()) {
            cargarVista(fxml);
        }
    }

    private void cargarVista(String nombreFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/" + nombreFXML));
            Parent root = loader.load();
            contenedorPantallasEm.getChildren().clear();
            contenedorPantallasEm.getChildren().add(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
