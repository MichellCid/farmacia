module com.example.posfarmacia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.posfarmacia.controllers to javafx.fxml, javafx.base;
    exports com.example.posfarmacia;
}