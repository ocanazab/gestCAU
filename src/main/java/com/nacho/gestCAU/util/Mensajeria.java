package com.nacho.gestCAU.util;

import javafx.scene.control.Alert;

public class Mensajeria {

    public static void mostrarError(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.show();
    }
    public static void mostrarInfo(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.show();
    }
}
