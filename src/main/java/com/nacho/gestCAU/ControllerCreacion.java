package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class ControllerCreacion {

    public String usu;
    public String bd;

    public void setData(String usuario, String baseDatos){

        //lblUsuario.setText(usuario);
        //lblbaseDatos.setText(baseDatos);
        usu=usuario;
        bd=baseDatos;
    }

    public void muestraDatos(){
        System.out.println(usu);
        System.out.println(bd);
    }


    @FXML
    public TextArea txtDescripcion;
    public Label lblUsuario;
    public Label lblbaseDatos;

    public void initialize() {
        Model modelo = new Model();
        modelo.listaIncidencias(usu,bd);
        muestraDatos();

    }

}
