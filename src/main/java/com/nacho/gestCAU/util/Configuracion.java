package com.nacho.gestCAU.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {

    private String driver="";
    private String urlConexion="";
    private String usuario="";
    private String pass="";
    private String baseDat="";
    private String fichero="";



    public static String obtenerdriver(String fichero, String baseDat) throws IOException {

        Properties config = new Properties();
        String driver="";
        config.load(new FileInputStream(fichero));

        switch (baseDat){
            case "posgre":
                driver=config.getProperty("driverPosgre");
                break;
            case "mysql":
                driver=config.getProperty("driverMysql");
        }
        return driver;
    }

    public static String obtenerURL(String fichero, String baseDat) throws IOException {

        Properties config = new Properties();
        String url="";
        config.load(new FileInputStream(fichero));

        switch (baseDat){
            case "posgre":
                url=config.getProperty("urlConexionPosgre");
                break;
            case "mysql":
                url=config.getProperty("urlConexionMysql");
        }
        return url;
    }

    /*
    //Obtengo los datos de conexión del fichero de propiedades
    Properties configuracion = new Properties();
        try {
        configuracion.load(new FileInputStream("configuracion.props"));
        usuario = configuracion.getProperty("user");
        password = configuracion.getProperty("password");
        servidor = configuracion.getProperty("server");
        puerto = Integer.valueOf(configuration.getProperty("port"));
    } catch (FileNotFoundException fnfe ) {
        fnfe.printStackTrace();
    } catch (IOException ioe) {
        ioe.printStackTrace();
    }*/

    public Configuracion(String baseDat, String fichero) {
        this.baseDat = baseDat;
        this.fichero = fichero;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrlConexion() {
        return urlConexion;
    }

    public void setUrlConexion(String urlConexion) {
        this.urlConexion = urlConexion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
