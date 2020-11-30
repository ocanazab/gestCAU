package com.nacho.gestCAU.util;

import javafx.scene.control.Alert;

public class Mensajeria {

    public static void mostrarError(String mensaje){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.show();
    }
    public static void mostrarInfo(String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setContentText(mensaje);
        alerta.show();
    }
}
