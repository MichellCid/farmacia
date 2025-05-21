package com.example.posfarmacia.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProductosEmpleado {

    @FXML
    private TextField bucarPEm;
    @FXML
    private Button actualizarTEm;
    @FXML
    private TextField codPEm, nomPEm, pcPEm;
    @FXML
    private TextArea desPEm;
    @FXML
    private TextField pvPEm;
    @FXML
    private ComboBox<String> catPEm;
    @FXML
    private TextField ubiPEm, stockPEm, cadPEm;
    @FXML
    private TableView<producto> TProductosEm;
    @FXML
    private TableColumn<producto, Integer> colIDPEm;
    @FXML
    private TableColumn<producto, String> colCodPEm, colNomPEm, colDesPEm;
    @FXML
    private TableColumn<producto, Integer> colStockPEm;
    @FXML
    private TableColumn<producto, String> colUbiPEm;
    @FXML
    private TableColumn<producto, Double> colPrecioPEm;

    private conexionBD conexion;

    @FXML
    public void initialize() {

        colIDPEm.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCodPEm.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNomPEm.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDesPEm.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecioPEm.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        colStockPEm.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colUbiPEm.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

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
            catPEm.setItems(categorias);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void agregarProducto() {
        try {
            String codigo = codPEm.getText();
            String nombre = nomPEm.getText();
            String descripcion = desPEm.getText();
            double precioCompra = Double.parseDouble(pcPEm.getText());
            double precioVenta = Double.parseDouble(pvPEm.getText());
            int stock = Integer.parseInt(stockPEm.getText());
            String categoria = catPEm.getValue();
            String ubicacion = ubiPEm.getText(); // o `.getText()` si decides cambiar a TextField
            String caducidad = cadPEm.getText(); // formato esperado: "yyyy-MM-dd"

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
        codPEm.clear();
        nomPEm.clear();
        desPEm.clear();
        pcPEm.clear();
        pvPEm.clear();
        stockPEm.clear();
        cadPEm.clear();
        catPEm.getSelectionModel().clearSelection();
        ubiPEm.clear();
    }

    private void cargarProductos() {
        ObservableList<producto> productos = FXCollections.observableArrayList();

        String sql = "SELECT id, codigo, nombre, descripcion, precio_venta, stock, ubicacion FROM productos where estado = TRUE";

        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(new producto(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getInt("stock"),
                        rs.getString("ubicacion")
                ));
            }

            TProductosEm.setItems(productos);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buscarProducto(KeyEvent event) {
        String dato = bucarPEm.getText();
        if (dato == null || dato.trim().isEmpty()) {
            cargarProductos();
            return;
        }

        ObservableList<producto> listaFiltrada = FXCollections.observableArrayList();

        try (Connection conn = conexionBD.getConexion()) {
            String query = "SELECT * FROM productos WHERE estado = TRUE AND (" +
                    "CAST(id AS CHAR) LIKE ? OR " +
                    "LOWER(codigo) LIKE ? OR " +
                    "LOWER(nombre) LIKE ?)";

            PreparedStatement stmt = conn.prepareStatement(query);
            String filtro = "%" + dato.toLowerCase() + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                producto p = new producto(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getInt("stock"),
                        rs.getString("ubicacion")
                );
                listaFiltrada.add(p);
            }

            TProductosEm.setItems(listaFiltrada);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarVentanaDetalles() {
        producto seleccionado = TProductosEm.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un producto para ver detalles.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/DetallesProducto.fxml"));
            Parent root = loader.load();

            DetallesProductoController controller = loader.getController();
            controller.cargarDatos(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Detalles del Producto");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana anterior
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error al abrir la ventana de detalles.", Alert.AlertType.ERROR);
        }
    }




}