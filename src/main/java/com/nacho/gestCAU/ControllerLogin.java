package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import com.nacho.gestCAU.util.Cifrado;


import com.nacho.gestCAU.util.TraspasoDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


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
    String passDescifrada;
    String baseDatos;

    //Objeto para el traspaso de datos entre controladores
    public TraspasoDTO intercambio = new TraspasoDTO();


    //Constantes
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
            Cifrado descifraPass = new Cifrado();


            //Si el usuario ha seleccionado la creación de incidencias
            if(chkCrearIncidencias.isSelected()){
                //Conectamos a Postgre
                resultadoConexion = modelo.conectarBD("postgre");


                if (resultadoConexion.isEmpty()){
                    baseDatos="postgre";
                    //Obtenemos las credenciales almacenadas en BD y comparamos con los introducidos
                    String usuarioBD;
                    String passBD;
                    usuarioBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"postgre")[0];
                    passBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"postgre")[1];

                    //Desencriptamos la contraseña:
                    passDescifrada=descifraPass.descifra(passBD,claveCifrado);

                    if (txtUser.getText().equals(usuarioBD)&&txtPass.getText().equals(passDescifrada)){
                        //Guardo el login de usuario recien creado y la base de datos.
                        intercambio.baseDatos="postgre";
                        intercambio.usuario=usuarioBD;


                        //Muestro la ventana de creación de incidencias.
                        View vistaCreacion = new View();
                        vistaCreacion.inicioCrearIncidencias(intercambio);

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
                baseDatos="mysql";
                //Conectamos a MYSQL
                resultadoConexion=modelo.conectarBD("mysql");

                if (resultadoConexion.isEmpty()){
                    //Una vez la conexion es correcta, deberemos validar el usuario en base de datos.
                    //Obtenemos las credenciales almacenadas en BD y comparamos con los introducidos
                    String usuarioBD;
                    String passBD;
                    usuarioBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"mysql")[0];
                    passBD=modelo.obtenerUser(txtUser.getText(),txtPass.getText(),"mysql")[1];

                    //Desencriptamos la contraseña:
                    passDescifrada=descifraPass.descifra(passBD,claveCifrado);

                    if (txtUser.getText().equals(usuarioBD)&&txtPass.getText().equals(passDescifrada)){
                        View vistaCreacion = new View();
                        vistaCreacion.inicioGestionIncidencias();

                    }else{
                        Mensajeria.mostrarError("Validación","Credenciales incorrectos.");
                        error=true;
                    }
                }else{
                    Mensajeria.mostrarError("Error en conexion a MySQL",resultadoConexion);
                    error=true;
                }
            }
            if (!error){
                //Cerramos la pantalla de login
                modelo.desconectarBD(baseDatos);
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
