package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import com.nacho.gestCAU.util.Cifrado;
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
    String passCifrada;
    final int claveCifrado=2;
    Boolean error;

    @FXML
    public void login() throws SQLException {
        //Codigo para iniciar el login.
        Mensajeria mensaje = new Mensajeria();
        Boolean error=false;
        Boolean resultadoConexion=false;


        if(txtUser.getText().isEmpty()) error=true;
        if(txtPass.getText().isEmpty()) error=true;
        if ((chkCrearIncidencias.isSelected()==false) && (chkGestIncidencias.isSelected()==false)) error=true;

        if (error){
            mensaje.mostrarError("Debes rellenar y seleccionar correctamente los datos");
        }else{

            Model modelo= new Model();

            //Ciframos la contraseña introducida.
            Cifrado cifraPass = new Cifrado();
            passCifrada=cifraPass.cifra(txtPass.getText(),claveCifrado);

            if(chkCrearIncidencias.isSelected()){
                //Conectamos a Postgre

                resultadoConexion = modelo.conectarBD("postgre");
                mensaje.mostrarInfo("1. Conectado a postgre");

                if (resultadoConexion){
                    //Una vez la conexion es correcta, deberemos validar el usuario en base de datos.
                    if(modelo.validarUser(txtUser.getText(),passCifrada,"postgre")){
                        View vistaCreacion = new View();
                        vistaCreacion.inicioCrearIncidencias();
                    }else{
                        mensaje.mostrarError("Credenciales incorrectos.");
                    }
                }else{
                    mensaje.mostrarError("Imposible conectar a la base de datos Postgre");
                }
            }
            if(chkGestIncidencias.isSelected()){
                //Conectamos a MYSQL
                resultadoConexion=modelo.conectarBD("mysql");
                mensaje.mostrarInfo("2. Conectado a MYSQL");

                if(resultadoConexion){
                    //Una vez la conexion es correcta, deberemos validar el usuario en base de datos.
                    if(modelo.validarUser(txtUser.getText(),passCifrada,"mysql")){
                        View vistaGestion = new View();
                        vistaGestion.inicioGestionIncidencias();
                    }else{
                        mensaje.mostrarError("Credenciales incorrectos.");
                    }
                }else{
                    mensaje.mostrarError("Imposible conectar a MySQL");
                }
            }
        }

    }
}
