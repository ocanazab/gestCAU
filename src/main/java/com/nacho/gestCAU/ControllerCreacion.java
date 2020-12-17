package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Incidenciaspostgre;
import com.nacho.gestCAU.util.Mensajeria;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.util.List;


public class ControllerCreacion {

    @FXML
    private DatePicker fechaIncidencia;

    @FXML
    private TextArea txtDescripcion;

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
        //Model modelo = new Model();
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

        //Conectamos a la base de datos
        modelo.conectarBD(bd);

        //Creamos la incidencia con los valores introducidos en el formulario.
        resultado = modelo.crearIncidencia(txtDescripcion.getText(),fechaIncidencia.getValue(),0,1,usu,bd);

        if(!resultado.isEmpty()){
            Mensajeria.mostrarError("Nueva Incidencia","Error al crear la incidencia."+"\n"+resultado);
        }else{
            Mensajeria.mostrarInfo("Nueva Incidencia","Incidencia creada correctamente");
        }
        //Aquí refrescaré el TableView para mostrar el nuevo registro.
        modelo.desconectarBD(bd);
    }

    @FXML
    private void modifIncidencia(){

    }

    @FXML
    private void removeIncidencia(){

    }

}
