package com.nacho.gestCAU;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ControllerCreacion {
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblbaseDatos;


    @FXML
    private void initialize() {

    }

    public void setData(String usuario, String baseDatos){

        lblUsuario.setText(lblUsuario.getText() + usuario);
        lblbaseDatos.setText(lblbaseDatos.getText() + baseDatos);

    }

}
