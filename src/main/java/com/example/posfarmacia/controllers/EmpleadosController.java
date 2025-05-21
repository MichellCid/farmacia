package com.example.posfarmacia.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.posfarmacia.controllers.empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class EmpleadosController {
    @FXML private TableView<empleado> TEmpleados;
    @FXML private TableColumn<empleado, Integer> colIDE;
    @FXML
    private TableColumn<empleado, String> colNomE;
    @FXML private TableColumn<empleado, String> colRolE;
    @FXML private TableColumn<empleado, String> colStatusE;
    @FXML private TableColumn<empleado, String> colTelE;

    @FXML private TextField buscarE;
    @FXML private TextField nomE, usuE, contE, rolE, staE, dirE, telE, sueldoE, fechaInE;
    @FXML private Button infoE, EliminarE, editarE, guardarEdicionE, agregarE;

    private conexionBD conexion;

//    @FXML
//    private void agregarProducto() {
//        try {
//
//            String nombre = nomE.getText();
//            String usuario = usuE.getText();
//            String contraseña = contE.getText();
//            double precioVenta = pvPEm.getText();
//            int stock = Integer.parseInt(stockPEm.getText());
//            String categoria = catPEm.getValue();
//            String ubicacion = ubiPEm.getText(); // o `.getText()` si decides cambiar a TextField
//            String caducidad = cadPEm.getText(); // formato esperado: "yyyy-MM-dd"
//
//            // Validación básica
//            if (codigo.isEmpty() || nombre.isEmpty() || categoria == null || ubicacion == null) {
//                mostrarAlerta("Por favor llena todos los campos obligatorios.", Alert.AlertType.WARNING);
//                return;
//            }
//
//            String sql = "INSERT INTO productos (codigo, nombre, descripcion, precio_compra, precio_venta, stock, categoria, ubicacion, fecha_caducidad, estado) " +
//                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, true)";
//
//            try (Connection conn = conexion.getConexion();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setString(1, codigo);
//                stmt.setString(2, nombre);
//                stmt.setString(3, descripcion);
//                stmt.setDouble(4, precioCompra);
//                stmt.setDouble(5, precioVenta);
//                stmt.setInt(6, stock);
//                stmt.setString(7, categoria);
//                stmt.setString(8, ubicacion);
//                stmt.setDate(9, java.sql.Date.valueOf(caducidad)); // formato yyyy-MM-dd
//
//                stmt.executeUpdate();
//
//                //cargarProductos();
//
//                mostrarAlerta("Producto agregado correctamente.", Alert.AlertType.INFORMATION);
//                limpiarCampos();
//                // aquí podrías actualizar la tabla
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            mostrarAlerta("Error al agregar producto. Verifica los datos.", Alert.AlertType.ERROR);
//        }
//    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

//    private void limpiarCampos() {
//        codPEm.clear();
//        nomPEm.clear();
//        desPEm.clear();
//        pcPEm.clear();
//        pvPEm.clear();
//        stockPEm.clear();
//        cadPEm.clear();
//        catPEm.getSelectionModel().clearSelection();
//        ubiPEm.clear();
//    }
}
