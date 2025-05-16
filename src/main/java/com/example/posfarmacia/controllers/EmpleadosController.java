package com.example.posfarmacia.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.posfarmacia.controllers.empleado;


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


}
