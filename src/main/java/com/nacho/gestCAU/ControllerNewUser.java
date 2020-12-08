package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerNewUser {

    //Definición de variables
    public TextField txtUser;
    public TextField txtPass;
    public TextField txtPass2;
    public TextField txtNombre;
    public TextField txtApellidos;
    public TextField txtemail;

    public Button btnCancel;

    @FXML
    public void saveUser(){
        Mensajeria.mostrarInfo("Prueba","La base de datos seleccionada es: ");
    }

    public void cancel(){
            //Cerramos la pantalla de login
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
    }

    //Recibe los datos de otro formulario.
    public void recibirDatos(String datoRecibido) {
        Mensajeria.mostrarInfo("Prueba",datoRecibido);
    }

}
