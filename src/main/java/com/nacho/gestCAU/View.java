package com.nacho.gestCAU;
import com.nacho.gestCAU.util.R;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUI("FormLogin.fxml"));
        loader.setController(new ControllerLogin());

        AnchorPane anchorPane = loader.load();

        Scene scene= new Scene(anchorPane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setTitle("Entrada al sistema");
        stage.show();
    }

    public void inicioCrearIncidencias (){
        FXMLLoader loaderCrearIncidencias = new FXMLLoader();
        loaderCrearIncidencias.setLocation(R.getUI("FormCrearIncidencias.fxml"));
        loaderCrearIncidencias.setController(new ControllerCreacion());

        AnchorPane anchorPane = null;
        try {
            anchorPane = loaderCrearIncidencias.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene= new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setTitle("Creación de incidencias");
        stage.show();

    }

    public void inicioGestionIncidencias (){
        FXMLLoader loaderGestionIncidencias = new FXMLLoader();
        loaderGestionIncidencias.setLocation(R.getUI("FormGestionIncidencias.fxml"));
        loaderGestionIncidencias.setController(new ControllerGestion());

        AnchorPane anchorPane = null;
        try {
            anchorPane = loaderGestionIncidencias.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene= new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setTitle("Gestión de Incidencias");
        stage.show();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        //Model modelo = new Model();
        //modelo.desconectarBD("mysql");
        //modelo.desconectarBD("postgre");
    }

    public static void main(String[] args) {
        launch();
    }
}
