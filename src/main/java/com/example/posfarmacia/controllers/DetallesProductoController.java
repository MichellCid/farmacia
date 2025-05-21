package com.example.posfarmacia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.posfarmacia.controllers.producto;

import java.time.format.DateTimeFormatter;

public class DetallesProductoController {

    @FXML private Label lblID;
    @FXML private Label lblCodigo;
    @FXML private Label lblNombre;
    @FXML private Label lblDes;
    @FXML private Label lblPC;
    @FXML private Label lblPV;
    @FXML private Label lblStock;
    @FXML private Label lblCat;
    @FXML private Label lblUbi;
    @FXML private Label lblFC;
    @FXML private Label lblEstado;

    public void cargarDatos(producto seleccionado) {
        lblID.setText(String.valueOf(seleccionado.getId()));
        lblCodigo.setText(seleccionado.getCodigo());
        lblNombre.setText(seleccionado.getNombre());
        lblDes.setText(seleccionado.getDescripcion());
        lblPC.setText(String.format("$ %.2f", seleccionado.getPrecioCompra()));
        lblPV.setText(String.format("$ %.2f", seleccionado.getPrecioVenta()));
        lblStock.setText(String.valueOf(seleccionado.getStock()));
        lblCat.setText(seleccionado.getCategoria());
        lblUbi.setText(seleccionado.getUbicacion());

        if (seleccionado.getFechaCaducidad() != null) {
            // Suponiendo que es LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            lblFC.setText(seleccionado.getFechaCaducidad().format(formatter));
        } else {
            lblFC.setText("Sin fecha");
        }

        lblEstado.setText(seleccionado.isEstado() ? "Activo" : "Inactivo");
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) lblID.getScene().getWindow();
        stage.close();
    }
}
