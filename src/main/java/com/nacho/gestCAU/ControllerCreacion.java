package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Incidenciaspostgre;
import com.nacho.gestCAU.util.Mensajeria;
import javafx.application.Platform;
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

    private String refrescaTabla(){
        String resultado="";

        //Muestro las incidencias
        ObservableList<Incidenciaspostgre> lista = FXCollections.observableArrayList();

        Model modelo = new Model();
        modelo.conectarBD(bd);

        lista = modelo.listaIncidencias(usu,bd);

        if(lista!=null){
            //Relleno el TableView
            for (int i = 0; i < lista.size(); i++) {
                colFecha.setCellValueFactory(new PropertyValueFactory("fechaCreacion"));
                colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
            }
            tblIncidencias.setItems(null);
            tblIncidencias.setItems(lista);

        }else{
            resultado="Error";
        }
        modelo.desconectarBD(bd);
        return resultado;
    }

    @FXML
    private void initialize() {
        //Inicializo el DatePicker
        fechaIncidencia.setEditable(false);
        fechaIncidencia.setValue(LocalDate.now());

        String resultado="";

        //Actualizo el TableView
        resultado=refrescaTabla();

        if(resultado.equals("Error")){
            Platform.exit();
        }

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
                modelo.desconectarBD(bd);
            }
            //Aquí refrescaré el TableView para mostrar el nuevo registro.
            refrescaTabla();
        }else{
            Mensajeria.mostrarError("Validación","Debes de rellenar una descripción.");
        }
    }

    @FXML
    private void modifIncidencia(){



    }

    @FXML
    private void removeIncidencia(){

    }



}
