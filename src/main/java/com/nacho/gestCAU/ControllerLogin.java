package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.sql.SQLException;


public class ControllerLogin {

    //Defino las variables que guardaran el contenido de los campos en pantalla
    public TextField txtUser;
    public TextField txtPass;
    public RadioButton chkCrearIncidencias;
    public RadioButton chkGestIncidencias;
    Boolean error;

    @FXML
    public void login() throws SQLException {
        //Codigo para iniciar el login.
        Mensajeria mensaje = new Mensajeria();
        Boolean error=false;
        Boolean resultadoConexion=false;


        /*if (txtUser.getText().equals("")) error=true;
        if (txtPass.getText().equals("")) error=true;*/
        if(txtUser.getText().isEmpty()) error=true;
        if(txtPass.getText().isEmpty()) error=true;
        if ((chkCrearIncidencias.isSelected()==false) && (chkGestIncidencias.isSelected()==false)) error=true;

        if (error){
            mensaje.mostrarError("Debes rellenar y seleccionar correctamente los datos");
        }else{

            Model modelo= new Model();
            if(chkCrearIncidencias.isSelected()){
                //Conectamos a Postgre

                resultadoConexion = modelo.conectarBD("postgre");

                if (resultadoConexion){
                    //Una vez la conexion es correcta, deberemos validar el usuario en base de datos.
                    if(modelo.validarUser(txtUser.getText(),"postgre")){
                        View vistaCreacion = new View();
                        vistaCreacion.inicioCrearIncidencias();
                    }else{
                        mensaje.mostrarError("Usuario incorrecto.");
                    }
                }else{
                    mensaje.mostrarError("Imposible conectar a la base de datos Postgre");
                }
            }
            if(chkGestIncidencias.isSelected()){
                //Conectamos a MYSQL
                resultadoConexion=modelo.conectarBD("mysql");

                if(resultadoConexion){
                    //Una vez la conexion es correcta, deberemos validar el usuario en base de datos.
                    if(modelo.validarUser(txtUser.getText(),"mysql")){
                        View vistaGestion = new View();
                        vistaGestion.inicioGestionIncidencias();
                    }else{
                        mensaje.mostrarError("Usuario incorrecto.");
                    }
                }else{
                    mensaje.mostrarError("Imposible conectar a MySQL");
                }
            }
        }

    }
}
