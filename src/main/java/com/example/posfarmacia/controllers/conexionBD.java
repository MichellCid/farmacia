package com.example.posfarmacia.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/farmacia";
    private static final String USUARIO = "admin";
    private static final String CONTRASEÑA = "123";

    private static Connection conexion;

    private conexionBD() {
        // Constructor privado para evitar instanciación
    }

    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        }
        return conexion;
    }
}

