package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Incidenciaspostgre;
import com.nacho.gestCAU.util.Mensajeria;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;


public class ControllerCreacion {

    @FXML
    private DatePicker fechaCreacion;

    @FXML
    private TextArea descripcion;

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
        List<Incidenciaspostgre> lista;
        Model modelo = new Model();
        /*lista = modelo.listaIncidencias(usu,bd);

        //Recorro la lista a modo de pruebas
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }*/

    }

    @FXML
    private void newIncidencia(){
        String resultado="";
        Model modelo = new Model();
        System.out.println(descripcion.getText());
        //System.out.println(fechaCreacion.getValue());
        System.out.println(usu);
        System.out.println(bd);
        //Creamos la incidencia con los valores introducidos en el formulario.
        //resultado = modelo.crearIncidencia(descripcion.getText(),fechaCreacion.getValue().toString(),"N","S",usu,bd);

        if(!resultado.isEmpty()){
            Mensajeria.mostrarError("Nueva Incidencia","Error al crear la incidencia."+"\n"+resultado);
        }else{
            Mensajeria.mostrarInfo("Nueva Incidencia","Incidencia creada correctamente");
        }
        //Aquí refrescaré el TableView para mostrar el nuevo registro.
    }

    @FXML
    private void modifIncidencia(){

    }

    @FXML
    private void removeIncidencia(){

    }

}
