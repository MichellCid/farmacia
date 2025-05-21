package com.example.posfarmacia.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.posfarmacia.controllers.producto;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import com.example.posfarmacia.controllers.conexionBD;

public class productosController {

    @FXML private TableView<producto> tablaProductos;
    @FXML private TableColumn<producto, String> colCodigoP;
    @FXML private TableColumn<producto, String> colNomP;
    @FXML private TableColumn<producto, String> colDescP;
    @FXML private TableColumn<producto, Double> colPVP;
    @FXML private TableColumn<producto, Integer> colStockP;
    @FXML private TableColumn<producto, String> colUbiP;

    @FXML private TextField codigoP, nombreP, pcP, pvP, stockP, ubiP, cadP, buscadorP;
    @FXML private ComboBox <String>catP;
    @FXML private TextArea descP;
    @FXML private Button agregarP;

    private conexionBD conexion;

    @FXML
    public void initialize() {

        colCodigoP.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNomP.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescP.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPVP.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        colStockP.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colUbiP.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        cargarProductos();


        cargarCategorias();

    }

    private void cargarCategorias() {
        String sql = "SELECT nombreCat FROM categoria";
        try (Connection conn = conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ObservableList<String> categorias = FXCollections.observableArrayList();
            while (rs.next()) {
                categorias.add(rs.getString("nombreCat"));
            }
            catP.setItems(categorias);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void agregarProducto() {
        try {
            String codigo = codigoP.getText();
            String nombre = nombreP.getText();
            String descripcion = descP.getText();
            double precioCompra = Double.parseDouble(pcP.getText());
            double precioVenta = Double.parseDouble(pvP.getText());
            int stock = Integer.parseInt(stockP.getText());
            String categoria = catP.getValue();
            String ubicacion = ubiP.getText(); // o `.getText()` si decides cambiar a TextField
            String caducidad = cadP.getText(); // formato esperado: "yyyy-MM-dd"

            // Validación básica
            if (codigo.isEmpty() || nombre.isEmpty() || categoria == null || ubicacion == null) {
                mostrarAlerta("Por favor llena todos los campos obligatorios.", Alert.AlertType.WARNING);
                return;
            }

            String sql = "INSERT INTO productos (codigo, nombre, descripcion, precio_compra, precio_venta, stock, categoria, ubicacion, fecha_caducidad, estado) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, true)";

            try (Connection conn = conexion.getConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, codigo);
                stmt.setString(2, nombre);
                stmt.setString(3, descripcion);
                stmt.setDouble(4, precioCompra);
                stmt.setDouble(5, precioVenta);
                stmt.setInt(6, stock);
                stmt.setString(7, categoria);
                stmt.setString(8, ubicacion);
                stmt.setDate(9, java.sql.Date.valueOf(caducidad)); // formato yyyy-MM-dd

                stmt.executeUpdate();

                cargarProductos();

                mostrarAlerta("Producto agregado correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
                // aquí podrías actualizar la tabla
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error al agregar producto. Verifica los datos.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        codigoP.clear();
        nombreP.clear();
        descP.clear();
        pcP.clear();
        pvP.clear();
        stockP.clear();
        cadP.clear();
        catP.getSelectionModel().clearSelection();
        ubiP.clear();
    }

    private void cargarProductos() {
        ObservableList<producto> productos = FXCollections.observableArrayList();

        String sql = "SELECT codigo, nombre, descripcion, precio_venta, stock, ubicacion FROM productos where estado = TRUE";

        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(new producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getInt("stock"),
                        rs.getString("ubicacion")
                ));
            }

            tablaProductos.setItems(productos);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
