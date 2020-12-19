package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Incidenciaspostgre;
import com.nacho.gestCAU.util.Mensajeria;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
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
        fechaIncidencia.setEditable(false);
        fechaIncidencia.setValue(LocalDate.now());
    }

    @FXML
    private void newIncidencia(){
        String resultado="";
        Boolean validar=true;

        Model modelo = new Model();

        if (txtDescripcion.getText().isEmpty()){
            validar=false;
        }

        if(validar){
            //Conectamos a la base de datos

            modelo.conectarBD(bd);

            //Creamos la incidencia con los valores introducidos en el formulario.
            resultado = modelo.crearIncidencia(txtDescripcion.getText(),fechaIncidencia.getValue(),0,1,usu,bd);

            if(!resultado.isEmpty()){
                Mensajeria.mostrarError("Nueva Incidencia","Error al crear la incidencia."+"\n"+resultado);
            }else{
                Mensajeria.mostrarInfo("Nueva Incidencia","Incidencia creada correctamente");
                txtDescripcion.setText("");
            }
            //Aquí refrescaré el TableView para mostrar el nuevo registro.

        }else{
            Mensajeria.mostrarError("Validación","Debes de rellenar una descripción.");
        }
    }

    @FXML
    private void modifIncidencia(){
        List<Incidenciaspostgre> lista;

        Model modelo = new Model();
        modelo.conectarBD(bd);

        lista = modelo.listaIncidencias(usu,bd);

        //Recorro la lista a modo de pruebas
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }
    }

    @FXML
    private void removeIncidencia(){

    }

}
