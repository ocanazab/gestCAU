package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class ControllerCreacion {

    private String usu;
    private String bd;

    public void setData(String usuario, String baseDatos){
        usu=usuario;
        bd=baseDatos;
    }

    @FXML
    private void initialize() {
        //Obtengo los datos de las incidencias del usuario conectado.
        //Los muestro en el TableView

        Model modelo = new Model();
        modelo.listaIncidencias(usu,bd);

    }

}
