package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Incidenciaspostgre;
import com.nacho.gestCAU.util.Mensajeria;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.*;


public class ControllerCreacion {

    @FXML
    private DatePicker fechaIncidencia;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TableView tblIncidencias;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colFecha;

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
        ArrayList<Incidenciaspostgre> lista = new ArrayList<>();

        ObservableList<Incidenciaspostgre> data = FXCollections.observableArrayList();

        Model modelo = new Model();
        modelo.conectarBD(bd);

        lista = modelo.listaIncidencias(usu,bd);
        data = modelo.listaIncidencias2(usu,bd);

        //Recorro la lista a modo de pruebas
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("Fecha: " + lista.get(i).getFechaCreacion() + "\n" + "Descripcion: "+ lista.get(i).getDescripcion());
        }

        //Recorro los datos a modo de pruebas
        for (int i = 0; i < data.size(); i++) {
            colFecha.setCellValueFactory(new PropertyValueFactory("fecha_creacion"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        }
        tblIncidencias.setItems(null);
        tblIncidencias.setItems(data);

    }

    @FXML
    private void removeIncidencia(){

    }

}
