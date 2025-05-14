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

public class productosController {

    @FXML private TableView<producto> tablaProductos;
    @FXML private TableColumn<producto, String> colCodigoP;
    @FXML private TableColumn<producto, String> colNomP;
    @FXML private TableColumn<producto, String> colDescP;
    @FXML private TableColumn<producto, Double> colPVP;
    @FXML private TableColumn<producto, Integer> colStockP;
    @FXML private TableColumn<producto, String> colUbiP;

    @FXML private TextField codigoP, nombreP, pcP, pvP, stockP, catP, ubiP, cadP, buscadorP;
    @FXML private TextArea descP;
    @FXML private Button agregarP;


    private ObservableList<producto> listaProductos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas
        colCodigoP.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNomP.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescP.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPVP.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        colStockP.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colUbiP.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        tablaProductos.setItems(listaProductos);

        // Cargar productos existentes
        cargarProductos();

        agregarP.setOnAction(event -> agregarProducto());

        cargarProductosBusqueda("");

        buscadorP.textProperty().addListener((obs, oldVal, newVal) -> {
            cargarProductosBusqueda(newVal);
        });
    }


    public void cargarProductos() {
        listaProductos.clear();
        String sql = "SELECT * FROM productos";

        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                producto p = new producto(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_compra"),
                        rs.getDouble("precio_venta"),
                        rs.getInt("stock"),
                        rs.getString("categoria"),
                        rs.getString("ubicacion"),
                        rs.getDate("fecha_caducidad").toLocalDate()
                );
                listaProductos.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los productos.");
        }
    }


    public void agregarProducto() {
        String codigo = codigoP.getText();
        String nombre = nombreP.getText();
        String descripcion = descP.getText();
        double precioCompra = Double.parseDouble(pcP.getText());
        double precioVenta = Double.parseDouble(pvP.getText());
        int stock = Integer.parseInt(stockP.getText());
        String categoria = catP.getText();
        String ubicacion = ubiP.getText();
        LocalDate caducidad = LocalDate.parse(cadP.getText());

        String sql = "INSERT INTO productos (codigo, nombre, descripcion, precio_compra, precio_venta, stock, categoria, ubicacion, fecha_caducidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setDouble(4, precioCompra);
            ps.setDouble(5, precioVenta);
            ps.setInt(6, stock);
            ps.setString(7, categoria);
            ps.setString(8, ubicacion);
            ps.setDate(9, java.sql.Date.valueOf(caducidad));

            ps.executeUpdate();
            cargarProductos(); // Refresca la tabla
            limpiarCampos();
            mostrarAlerta("Éxito", "Producto agregado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo agregar el producto.");
        }
    }


    private void limpiarCampos() {
        codigoP.clear();
        nombreP.clear();
        descP.clear();
        pcP.clear();
        pvP.clear();
        stockP.clear();
        catP.clear();
        ubiP.clear();
        cadP.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void eliminarProducto() {
        producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("","Selecciona un producto para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar el producto?");
        confirmacion.setContentText("ID: " + seleccionado.getId() + "\nNombre: " + seleccionado.getNombre() + "\nCódigo: " + seleccionado.getCodigo());

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try (Connection conn = conexionBD.getConexion();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM productos WHERE id = ?")) {

                stmt.setInt(1, seleccionado.getId());
                stmt.executeUpdate();
                cargarProductosBusqueda(""); // recarga la tabla
                mostrarAlerta("eliminacion Exitosa","Producto eliminado con éxito.");

            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error","Error al eliminar el producto.");
            }
        }
    }


    private void cargarProductosBusqueda(String filtro) {
        listaProductos.clear();
        String sql = "SELECT * FROM productos WHERE codigo LIKE ? OR nombre LIKE ? OR id LIKE ?";

        try (Connection conn = conexionBD.getConexion();  // usa tu clase de conexión
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + filtro + "%");
            stmt.setString(2, "%" + filtro + "%");
            stmt.setString(3, "%" + filtro + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                producto p = new producto();
                p.setId(rs.getInt("id"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecioCompra(rs.getDouble("precio_compra"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setStock(rs.getInt("stock"));
                p.setCategoria(rs.getString("categoria"));
                p.setUbicacion(rs.getString("ubicacion"));
                p.setFechaCaducidad(rs.getDate("fecha_caducidad").toLocalDate());
                listaProductos.add(p);
            }

            tablaProductos.setItems(listaProductos);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cargarProductoEnFormulario() {
        producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Sin selección", "Selecciona un producto para editar.");
            return;
        }

        nombreP.setText(seleccionado.getNombre());
        codigoP.setText(seleccionado.getCodigo());
        descP.setText(seleccionado.getDescripcion());
        pcP.setText(String.valueOf(seleccionado.getPrecioCompra()));
        pvP.setText(String.valueOf(seleccionado.getPrecioVenta()));
        stockP.setText(String.valueOf(seleccionado.getStock()));
        catP.setText(seleccionado.getCategoria());
        ubiP.setText(seleccionado.getUbicacion());
        cadP.setText(String.valueOf(seleccionado.getFechaCaducidad()));
    }

    @FXML
    private void guardarCambios() {
        producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Sin selección", "Selecciona un producto para actualizar.");
            return;
        }

        try (Connection conn = conexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE productos SET nombre = ?, codigo = ?, descripcion = ?,  precio_compra = ?, precio_venta = ?, stock = ?, categoria = ?, ubicacion = ? WHERE id = ?")) {

            stmt.setString(1, nombreP.getText());
            stmt.setString(2, codigoP.getText());
            stmt.setString(3, descP.getText());
            stmt.setDouble(4, Double.parseDouble(pcP.getText()));
            stmt.setDouble(5, Double.parseDouble(pvP.getText()));
            stmt.setInt(6, Integer.parseInt(stockP.getText()));
            stmt.setString(7, stockP.getText());
            stmt.setString(8, stockP.getText());
            stmt.setInt(9, seleccionado.getId());

            stmt.executeUpdate();
            cargarProductosBusqueda(""); // recarga la tabla
            mostrarAlerta("Actualizacion exitosa", "Producto actualizado con éxito.");

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error en actualizacion", "Error al actualizar el producto.");
        } catch (NumberFormatException e) {
            mostrarAlerta("Error en los datos", "Verifica los campos numéricos (precio y stock).");
        }
    }



}
