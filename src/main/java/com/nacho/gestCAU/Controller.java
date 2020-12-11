package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import javax.swing.*;

public class Controller {

    //Defino las variables que guardaran el contenido de los campos en pantalla
    public TextField txtUser;
    public TextField txtPass;
    public RadioButton chkCrearIncidencias;
    public RadioButton chkGestIncidencias;
    Boolean error;

    @FXML
    public void login(Event event) {
        //Codigo para iniciar el login.
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Faltan datos");
        error=false;
        String resultadoConexion="";


        if (txtUser.getText().equals("")) error=true;
        if (txtPass.getText().equals("")) error=true;
        if ((chkCrearIncidencias.isSelected()==false) && (chkGestIncidencias.isSelected()==false)) error=true;

        if (error){
            alerta.setContentText("Debes rellenar y seleccionar correctamente los datos.");
            alerta.show();
        }else{
            Model modelo= new Model();
            if(chkCrearIncidencias.isSelected()){
                //Conectamos a Postgre

                resultadoConexion = modelo.conectarBD("postgre");

                if (resultadoConexion.isEmpty()){
                    //Una vez la conexion es correcta, deberemos validar el usuario en base de datos.
                    alerta.setContentText("Conexion a Postgre correcta. Despues validaremos");
                    alerta.show();
                    View vistaCreacion = new View();
                    vistaCreacion.inicioCrearIncidencias();
                }else{
                    Mensajeria.mostrarError("Error en la conexión a Postgre",resultadoConexion);
                }

            }
            if(chkGestIncidencias.isSelected()){
                //Conectamos a MYSQL
                resultadoConexion=modelo.conectarBD("mysql");

                if(resultadoConexion.isEmpty()){
                    //Una vez la conexion es correcta, deberemos validar el usuario en base de datos.
                    alerta.setContentText("Conexion a MYSQL correcta. Despues validaremos");
                    alerta.show();
                    //Mostramos el formulario de la gestión de incidencias
                    View vistaGestion = new View();
                    vistaGestion.inicioGestionIncidencias();
                }else{
                    Mensajeria.mostrarError("Error en la conexión a MySQL",resultadoConexion);
                }
            }
        }

    }
}
