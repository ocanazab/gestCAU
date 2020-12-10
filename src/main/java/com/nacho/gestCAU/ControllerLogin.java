package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import com.nacho.gestCAU.util.Cifrado;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class ControllerLogin {

    //Defino las variables para FXML que guardaran el contenido de los campos en pantalla
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPass;
    @FXML
    private RadioButton chkCrearIncidencias;
    @FXML
    private RadioButton chkGestIncidencias;
    @FXML
    private Button btnLogin;

    //Otro tipo de variables
    String passCifrada;
    String baseDatos;

    public String getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }

    final int claveCifrado=2;


    @FXML
    private void login() throws SQLException {
        //Codigo para iniciar el login.

        boolean error=false;
        String resultadoConexion;


        if(txtUser.getText().isEmpty()) error=true;
        if(txtPass.getText().isEmpty()) error=true;
        if ((!chkCrearIncidencias.isSelected()) && (!chkGestIncidencias.isSelected())) error=true;

        if (error){
            Mensajeria.mostrarError("Validación","Debes rellenar y seleccionar correctamente los datos");
        }else{

            Model modelo= new Model();


            //Ciframos la contraseña introducida.
            Cifrado cifraPass = new Cifrado();
            passCifrada=cifraPass.cifra(txtPass.getText(),claveCifrado);

            //Si el usuario ha seleccionado la creación de incidencias
            if(chkCrearIncidencias.isSelected()){
                //Conectamos a Postgre
                resultadoConexion = modelo.conectarBD("postgre");


                if (resultadoConexion.isEmpty()){
                    //Obtenemos las credenciales almacenadas en BD y comparamos con los introducidos
                    String usuarioBD;
                    String passBD;
                    usuarioBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"postgre")[0];
                    passBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"postgre")[1];

                    if (usuarioBD.equals(txtUser.getText()) && passBD.equals(txtPass.getText())){
                        View vistaCreacion = new View();
                        vistaCreacion.inicioCrearIncidencias();

                    }else{
                        Mensajeria.mostrarError("Validación","Credenciales incorrectos.");
                        error=true;
                    }
                }else{
                    Mensajeria.mostrarError("Error en conexion a Postgre",resultadoConexion);
                    error=true;
                }
            }

            //Si el usuario ha seleccionado la gestión de incidencias
            if(chkGestIncidencias.isSelected()){
                //Conectamos a MYSQL
                resultadoConexion=modelo.conectarBD("mysql");

                if (resultadoConexion.isEmpty()){
                    //Una vez la conexion es correcta, deberemos validar el usuario en base de datos.
                    //modelo.validarUser(txtUser.getText(),passCifrada,"postgre")

                    //Obtenemos las credenciales almacenadas en BD y comparamos con los introducidos
                    String usuarioBD;
                    String passBD;
                    usuarioBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"mysql")[0];
                    passBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"mysql")[1];

                    if (usuarioBD.equals(txtUser.getText()) && passBD.equals(txtPass.getText())){
                        View vistaCreacion = new View();
                        vistaCreacion.inicioGestionIncidencias();

                    }else{
                        Mensajeria.mostrarError("Validación","Credenciales incorrectos.");
                        error=true;
                    }
                }else{
                    Mensajeria.mostrarError("Error en conexion a Postgre",resultadoConexion);
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
    @FXML
    private void Newuser() throws IOException {

            View vistaNuevousuario = new View();
            vistaNuevousuario.inicioNuevousuario();
        }
}
