package com.nacho.gestCAU;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    boolean primeraCarga=false;
    // Valores para el ChoiceBox
    ObservableList<String> valoresSistema = FXCollections.observableArrayList("Selecciona un valor", "Crear Incidencias", "Gestionar Incidencias");


    @FXML
    private void initialize() {

        comboSistema.setItems(valoresSistema);
        comboSistema.setValue("Selecciona un valor");

    }

    @FXML
    private void saveUser(){
        //Guardamos el usuario en la base de datos correspondiente.
        //Encriptamos la clave introducida.

    }

    @FXML
    private void cancel(){
            //Cerramos la pantalla de login
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
    }

    private boolean validarDatos(){
        boolean resultado=false;




        return resultado;

    }


}
