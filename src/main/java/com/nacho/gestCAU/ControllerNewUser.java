package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.nacho.gestCAU.util.Cifrado;

import java.util.ArrayList;
import java.util.List;

public class ControllerNewUser {

    //Definición de variables
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPass;
    @FXML
    private TextField txtPass2;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private ChoiceBox comboSistema;

    @FXML
    private void initialize() {
        // Valores para el ChoiceBox
        ObservableList<String> valoresSistema = FXCollections.observableArrayList("Selecciona un valor", "Crear Incidencias", "Gestionar Incidencias");

        comboSistema.setItems(valoresSistema);
        comboSistema.setValue("Selecciona un valor");

    }

    @FXML
    private void saveUser(){
        //Guardamos el usuario en la base de datos correspondiente.
        List<String> resultado = new ArrayList<>();
        String errores="";
        String passwd;
        String baseDatos="";
        Cifrado cifrarPass = new Cifrado();
        final int claveCifrado=2;
        Model modelo = new Model();

        //Valido los datos introducidos en el formulario.
        resultado=validarDatos();


        if(!resultado.isEmpty()){
            for(int i=0;i<resultado.size();i++){
                errores=errores + resultado.get(i) + "\n";
            }
            Mensajeria.mostrarError("Error en la validación",errores);
        }else{
            //Encripto la contraseña para almacenarla en la base de datos.
            passwd=cifrarPass.cifra(txtPass.getText(),claveCifrado);

            //Me quedo con el valor de la base de datos donde insertar el usuario, además de conectar a la correspondiente

            switch ((String)comboSistema.getValue()){
                case "Crear Incidencias":
                    baseDatos="postgre";
                    Mensajeria.mostrarInfo("Postgre","postgre");
                case "Gestionar Incidencias":
                    baseDatos="mysql";
                    Mensajeria.mostrarInfo("MYSQL","mysql");
            }
            //errores = modelo.conectarBD(baseDatos);
            //errores = modelo.crearUser(txtUser.getText(),passwd,txtNombre.getText(),txtApellidos.getText(),txtEmail.getText(),baseDatos);
            Mensajeria.mostrarInfo("Base Datos",baseDatos + "\n" + comboSistema.getValue());

            if(!errores.isEmpty()){
                Mensajeria.mostrarError("Error al guardar el usuario",errores);
            }else{
                Mensajeria.mostrarInfo("Usuario dado de alta","Usuario: " + txtUser.getText()+ " dado de alta satisfactoriamente."+"\n"+"En la pantalla de login introduce las credenciales.");
                cancel();
                modelo.desconectarBD(baseDatos);
            }
        }
    }

    @FXML
    private void cancel(){
            //Cerramos la pantalla de login
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
    }

    private List<String> validarDatos(){
        //Valido solamente los obligatorios.

        List<String> errores = new ArrayList<>();

        if (txtUser.getText().isEmpty()){
            errores.add("El usuario no puede estar vacio.");

        }

        if(txtPass.getText().isEmpty()||txtPass2.getText().isEmpty()){
            errores.add("La contraseña no puede estar en blanco");
        }else if(!txtPass.getText().equals(txtPass2.getText())){
            errores.add("Las contraseñas deben de ser iguales");
        }

        if(txtNombre.getText().isEmpty()){
            errores.add("El nombre no puede estar vacio.");
        }

        if(comboSistema.getValue().equals("Selecciona un valor")){
            errores.add("Debes seleccionar un valor para el sistema.");
        }

        return errores;

    }


}
