package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import com.nacho.gestCAU.util.Cifrado;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import javafx.scene.Node;
import javafx.stage.Stage;

import java.sql.SQLException;


public class ControllerLogin {

    //Defino las variables que guardaran el contenido de los campos en pantalla
    public TextField txtUser;
    public TextField txtPass;
    public RadioButton chkCrearIncidencias;
    public RadioButton chkGestIncidencias;
    public Button btnLogin;
    String passCifrada;
    final int claveCifrado=2;
    Boolean error;

    @FXML
    public void login() throws SQLException {
        //Codigo para iniciar el login.
        Mensajeria mensaje = new Mensajeria();
        Boolean error=false;
        String resultadoConexion="";


        if(txtUser.getText().isEmpty()) error=true;
        if(txtPass.getText().isEmpty()) error=true;
        if ((chkCrearIncidencias.isSelected()==false) && (chkGestIncidencias.isSelected()==false)) error=true;

        if (error){
            mensaje.mostrarError("Validación","Debes rellenar y seleccionar correctamente los datos");
        }else{

            Model modelo= new Model();

            //Ciframos la contraseña introducida.
            Cifrado cifraPass = new Cifrado();
            passCifrada=cifraPass.cifra(txtPass.getText(),claveCifrado);

            if(chkCrearIncidencias.isSelected()){
                //Conectamos a Postgre

                resultadoConexion = modelo.conectarBD("postgre");


                if (resultadoConexion.isEmpty()){
                    //Una vez la conexion es correcta, deberemos validar el usuario en base de datos.

                    //Obtenemos las credenciales almacenadas en BD y comparamos con los introducidos
                    String usuarioBD="";
                    String passBD="";
                    usuarioBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"postgre")[0];
                    passBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"postgre")[1];

                    if (usuarioBD.equals(txtUser.getText()) && passBD.equals(txtPass.getText())){
                        View vistaCreacion = new View();
                        vistaCreacion.inicioCrearIncidencias();

                    }else{
                        mensaje.mostrarError("Validación","Credenciales incorrectos.");
                        error=true;
                    }
                }else{
                    mensaje.mostrarError("Error en conexion a Postgre",resultadoConexion);
                    error=true;
                }
            }
            if(chkGestIncidencias.isSelected()){
                //Conectamos a MYSQL
                resultadoConexion=modelo.conectarBD("mysql");

                if (resultadoConexion.isEmpty()){
                    //Una vez la conexion es correcta, deberemos validar el usuario en base de datos.
                    //modelo.validarUser(txtUser.getText(),passCifrada,"postgre")

                    //Obtenemos las credenciales almacenadas en BD y comparamos con los introducidos
                    String usuarioBD="";
                    String passBD="";
                    usuarioBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"mysql")[0];
                    passBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"mysql")[1];

                    if (usuarioBD.equals(txtUser.getText()) && passBD.equals(txtPass.getText())){
                        View vistaCreacion = new View();
                        vistaCreacion.inicioGestionIncidencias();

                    }else{
                        mensaje.mostrarError("Validación","Credenciales incorrectos.");
                        error=true;
                    }
                }else{
                    mensaje.mostrarError("Error en conexion a Postgre",resultadoConexion);
                    error=true;
                }
            }
            if (!error){
                //Cerramos la pantalla de login
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.close();
            }
        }
    }
}
