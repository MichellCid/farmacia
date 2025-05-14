package com.example.posfarmacia.controllers;

import com.example.posfarmacia.controllers.conexionBD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class loginController {

    @FXML
    private ComboBox<String> seleccionarUsuario;

    @FXML
    private PasswordField contraseña;

    @FXML
    private Button login;

    @FXML
    public void initialize() {
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        try (Connection conn = conexionBD.getConexion()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT usuario FROM usuarios WHERE activo = 1");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                seleccionarUsuario.getItems().add(rs.getString("usuario"));
            }
        } catch (Exception e) {
            mostrarError("Error al cargar usuarios.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin() {
        String usuarioSeleccionado = seleccionarUsuario.getValue();
        String claveIngresada = contraseña.getText();

        if (usuarioSeleccionado == null || claveIngresada.isEmpty()) {
            mostrarError("Por favor, completa todos los campos.");
            return;
        }

        try (Connection conn = conexionBD.getConexion()) {
            System.out.println("entra al try");
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM usuarios WHERE usuario = ? AND activo = 1"
            );
            stmt.setString(1, usuarioSeleccionado);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("entra al primer if");
                String claveReal = rs.getString("contraseña");

                if (claveIngresada.equals(claveReal)) {
                    System.out.println("entra al segundo if");
                    String rol = rs.getString("rol");

                    FXMLLoader loader;
                    if ("admin".equalsIgnoreCase(rol)) {
                        //loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/inicioAdmin.fxml"));
                        loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/inicioAdmin.fxml"));
                        Parent root = loader.load();
                        InicioAdminController controller = loader.getController();
                        controller.setUsuarioActual(usuarioSeleccionado);


                        Stage stage = (Stage) login.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.centerOnScreen();
                    } else {
                        //loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/inicioEmpleado.fxml"));
                        loader = new FXMLLoader(getClass().getResource("/com/example/posfarmacia/inicioEmpleado.fxml"));
                        Parent root = loader.load();
                        InicioEmpleadoController controller = loader.getController();
                        controller.setUsuarioActual(usuarioSeleccionado);


                        Stage stage = (Stage) login.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.centerOnScreen();
                    }

                } else {
                    mostrarError("Contraseña incorrecta.");
                }

            } else {
                mostrarError("Usuario no encontrado.");
            }

        } catch (Exception e) {
            mostrarError("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de inicio de sesión");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
