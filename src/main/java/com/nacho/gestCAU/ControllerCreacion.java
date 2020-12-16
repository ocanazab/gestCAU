package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class ControllerCreacion {

    public String usu;
    public String bd;

    public void setData(String usuario, String baseDatos){
        usu=usuario;
        bd=baseDatos;
    }

    @FXML
    public TextArea txtDescripcion;
    public Label lblUsuario;
    public Label lblbaseDatos;

    public void initialize() {
        Model modelo = new Model();
        modelo.listaIncidencias(usu,bd);

    }

}
