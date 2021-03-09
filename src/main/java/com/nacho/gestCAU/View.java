package com.nacho.gestCAU;
import com.nacho.gestCAU.util.Mensajeria;
import com.nacho.gestCAU.util.R;

import com.nacho.gestCAU.util.TraspasoDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class View extends Application {

    public ArrayList<String> usuario = new ArrayList<String>();

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loaderLogin = new FXMLLoader();
        loaderLogin.setLocation(R.getUI("FormLogin.fxml"));
        loaderLogin.setController(new ControllerLogin());

        AnchorPane anchorPane = loaderLogin.load();

        Scene scene= new Scene(anchorPane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setTitle("Entrada al sistema");
        stage.show();
    }

    public void inicioCrearIncidencias (TraspasoDTO intercambio){
        FXMLLoader loaderCrearIncidencias = new FXMLLoader();
        loaderCrearIncidencias.setLocation(R.getUI("FormCrearIncidencias.fxml"));
        ControllerCreacion controlador = new ControllerCreacion();
        controlador.setData(intercambio.usuario, intercambio.baseDatos);
        loaderCrearIncidencias.setController(controlador);

        try {
            AnchorPane anchorPane = loaderCrearIncidencias.load();

            Scene scene= new Scene(anchorPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.setTitle("Creación de incidencias");

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inicioGestionIncidencias (TraspasoDTO intercambio){
        FXMLLoader loaderGestionIncidencias = new FXMLLoader();
        loaderGestionIncidencias.setLocation(R.getUI("FormGestionIncidencias.fxml"));
        ControllerGestion controlador = new ControllerGestion();
        controlador.setData(intercambio.usuario,intercambio.baseDatos);
        loaderGestionIncidencias.setController(controlador);

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

    public void inicioNuevousuario () throws IOException {

        FXMLLoader loaderNuevousuario = new FXMLLoader();
        loaderNuevousuario.setLocation(R.getUI("FormNewUser.fxml"));
        loaderNuevousuario.setController(new ControllerNewUser());

        AnchorPane anchorPane = null;
        try {
            anchorPane = loaderNuevousuario.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene= new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setTitle("Nuevo Usuario");

        stage.showAndWait();

    }


    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}
