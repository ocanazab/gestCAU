package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Mensajeria;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    //private Connection conexionPostgre, conexionMYSQL;
    private Connection conexionPostgre = null;
    private Connection conexionMYSQL = null;


    public String conectarBD(String baseDatos){
        String fallo="";
        Mensajeria mensaje = new Mensajeria();
        switch (baseDatos){
            case "postgre":
                try{

                    Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
                    conexionPostgre= DriverManager.getConnection("jdbc:postgresql://localhost:5432/gestIncidencias",
                            "postgres", "nacho;pili;tq");
                }catch (ClassNotFoundException e){
                    fallo=e.getMessage();
                } catch (SQLException throwables) {
                    fallo=throwables.getMessage();
                } catch (IllegalAccessException e) {
                    fallo=e.getMessage();
                } catch (InstantiationException e) {
                    fallo=e.getMessage();
                } catch (NoSuchMethodException e) {
                    fallo=e.getMessage();
                } catch (InvocationTargetException e) {
                    fallo=e.getMessage();
                }
            case "mysql":
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conexionMYSQL = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestCAU?serverTimezone=UTC",
                            "nacho", "nachoCAU");
                } catch (ClassNotFoundException e) {
                    fallo=e.getMessage();
                } catch (SQLException e) {
                    fallo=e.getMessage();
                }
        }
        return fallo;
    }
    
    /*public void desconectarBD(String baseDatos){

        switch(baseDatos){
            case "postgre":
                try{
                    conexionPostgre.close();
                    conexionPostgre=null;

                } catch (SQLException throwables) {

                }
            case "mysql":
                try{
                    conexionMYSQL.close();
                    conexionMYSQL=null;

                } catch (SQLException throwables) {

                }
        }

    }*/
    
    public Boolean validarUser(String usuario, String pass, String baseDatos) throws SQLException {
        Boolean res=false;
        String sql="SELECT login, password FROM usuarios WHERE login = ?";
        switch (baseDatos) {
            case "postgre":
                //Código para validar el usuario en la base de datos de Postgre
                PreparedStatement sentenciaPostgre = conexionPostgre.prepareStatement(sql);
                sentenciaPostgre.setString(1,usuario);
                ResultSet resultadoPostgre = sentenciaPostgre.executeQuery();
                if (resultadoPostgre.next()){
                    res=true;
                    resultadoPostgre.close();
                }
            case "mysql":
                //Código para validar el usuario en la base de datos de MYSQL
                PreparedStatement sentenciaMysql = conexionMYSQL.prepareStatement(sql);
                sentenciaMysql.setString(1,usuario);
                ResultSet resultadoMysql = sentenciaMysql.executeQuery();
                if(resultadoMysql.next()){
                    //El usuario se ha encontrado. Vamos a comprobar la pass

                    res=true;
                    resultadoMysql.close();
                }
        }
        return res;

    }
    
    


}
