package com.nacho.gestCAU;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private void saveUser(){
        txtUser.setText("Usuario");
        txtPass.setText("Password");
        txtPass2.setText("Password2");
        txtNombre.setText("Nombre");
        txtApellidos.setText("Apellidos");
        txtEmail.setText("Correo");
    }

    @FXML
    private void cancel(){
            //Cerramos la pantalla de login
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
    }

    //Recibe los datos de otro formulario.
    public void recibirDatos(String datoRecibido) {
       txtEmail.setText(datoRecibido);
    }

}
