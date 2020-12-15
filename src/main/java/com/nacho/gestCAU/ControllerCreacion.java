package com.nacho.gestCAU;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCreacion implements Initializable {
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblbaseDatos;


    @FXML
    public void setData(String usuario, String baseDatos){

        lblUsuario.setText(usuario);
        lblbaseDatos.setText(baseDatos);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model modelo = new Model();
        modelo.listaIncidencias(lblUsuario.getText(),lblbaseDatos.getText());
    }
}
