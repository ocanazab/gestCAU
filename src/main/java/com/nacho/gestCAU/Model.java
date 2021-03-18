package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Incidenciasmysql;
import com.nacho.gestCAU.util.Incidenciaspostgre;
import com.nacho.gestCAU.util.Mensajeria;
import com.nacho.gestCAU.util.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;

import java.util.*;

public class Model {

    private Connection conexionPostgre = null;
    private Connection conexionMYSQL = null;


    public String conectarBD(String baseDatos){
        String fallo="";

        //Obtengo los datos de configuración desde fichero.
        String usuario="";
        String password="";
        String url="";
        String driver="";

        try {

            Properties config = new Properties();

            //config.load(new FileInputStream("src/main/resources/files/settings.properties"));
            config.load(R.getconfig("settings.properties"));

            switch (baseDatos){
                case "postgre":
                    usuario = config.getProperty("userPostgre");
                    password = config.getProperty("passPostgre");
                    url=config.getProperty("urlConexionPostgre");
                    driver=config.getProperty("driverPostgre");
                    break;
                case "mysql":
                    usuario = config.getProperty("userMysql");
                    password = config.getProperty("passMysql");
                    url=config.getProperty("urlConexionMysql");
                    driver=config.getProperty("driverMysql");
            }
        } catch (IOException ioe) {
            fallo=ioe.getMessage();
        } catch (NullPointerException np){
            fallo=np.getMessage();
        }


        switch (baseDatos){
            case "postgre":
                try{
                    Class.forName(driver).getDeclaredConstructor().newInstance();
                    conexionPostgre= DriverManager.getConnection(url,usuario,password);
                    /*Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();*/
                    /*conexionPostgre= DriverManager.getConnection("jdbc:postgresql://localhost:5432/gestIncidencias",
                            "postgres", "nacho;pili;tq");*/

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
                break;
            case "mysql":
                try {
                    Class.forName(driver);
                    conexionMYSQL = DriverManager.getConnection(url,usuario,password);
                    /*Class.forName("com.mysql.cj.jdbc.Driver");*/
                    /*conexionMYSQL = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestCAU?serverTimezone=UTC",
                            "nacho", "nachoCAU");*/
                } catch (ClassNotFoundException e) {
                    fallo=e.getMessage();
                } catch (SQLException e) {
                    fallo=e.getMessage();
                }
        }
        return fallo;
    }

    public String[] obtenerUser(String usuario, String pass, String baseDatos) throws SQLException {
        //Boolean res=false;
        String sql="SELECT login, password FROM usuarios WHERE login = ?";
        String [] resultado = new String[2];
        resultado[0]="";
        resultado[1]="";
        switch (baseDatos) {
            case "postgre":
                //Código para validar el usuario en la base de datos de Postgre
                PreparedStatement sentenciaPostgre = conexionPostgre.prepareStatement(sql);
                sentenciaPostgre.setString(1,usuario);
                ResultSet resultadoPostgre = sentenciaPostgre.executeQuery();
                if (resultadoPostgre.next()){
                    resultado[0]=resultadoPostgre.getString(1);
                    resultado[1]=resultadoPostgre.getString(2);
                    resultadoPostgre.close();
                }
                break;
            case "mysql":
                //Código para validar el usuario en la base de datos de MYSQL
                PreparedStatement sentenciaMysql = conexionMYSQL.prepareStatement(sql);
                sentenciaMysql.setString(1,usuario);
                ResultSet resultadoMysql = sentenciaMysql.executeQuery();
                if(resultadoMysql.next()){
                    resultado[0]=resultadoMysql.getString(1);
                    resultado[1]=resultadoMysql.getString(2);
                    resultadoMysql.close();
                }
        }
        return resultado;



    }
    public String crearUser(String usuario, String pass, String nombre, String apellidos, String email, String baseDatos){
        String error="";

        switch (baseDatos) {
            case "postgre":
                try{
                    String sql = "insert into usuarios (login,password,nombre,apellidos,email) VALUES (?,?,?,?,?)";
                    PreparedStatement sentenciaInsert= conexionPostgre.prepareStatement(sql);
                    sentenciaInsert.setString(1, usuario);
                    sentenciaInsert.setString(2, pass);
                    sentenciaInsert.setString(3, nombre);
                    sentenciaInsert.setString(4, apellidos);
                    sentenciaInsert.setString(5, email);
                    sentenciaInsert.executeUpdate();

                }catch (SQLException sqle){
                    error=sqle.getMessage();
                }
                break;
            case "mysql":
                try{
                    String sql = "insert into usuarios (login,password,nombre,apellidos,email) VALUES (?,?,?,?,?)";
                    PreparedStatement sentenciaInsert = conexionMYSQL.prepareStatement(sql);
                    sentenciaInsert.setString(1, usuario);
                    sentenciaInsert.setString(2, pass);
                    sentenciaInsert.setString(3, nombre);
                    sentenciaInsert.setString(4, apellidos);
                    sentenciaInsert.setString(5, email);
                    sentenciaInsert.executeUpdate();

                }catch (SQLException sqle){
                    error=sqle.getMessage();
                }
        }

        return error;
    }

    public ObservableList<Incidenciaspostgre> listaIncidencias(String usuario) {
        //Muestra las incidencias del usuario conectado.


        ObservableList<Incidenciaspostgre> data = FXCollections.observableArrayList();

        try{
            String sqlPostgre="select descripcion, fecha_creacion,codincidencia from incidencias where login='" + usuario + "' and ind_borrado=0 order by 3";
            PreparedStatement obtenerIncidencias=conexionPostgre.prepareStatement(sqlPostgre,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rsIncidencias=obtenerIncidencias.executeQuery();

            while (rsIncidencias.next()) {
                Incidenciaspostgre incid = new Incidenciaspostgre();
                incid.setDescripcion(rsIncidencias.getString("descripcion"));
                incid.setFechaCreacion(rsIncidencias.getDate("fecha_creacion"));
                incid.setCodIncidencia(rsIncidencias.getInt("codincidencia"));
                data.add(incid);
            }
        }catch(SQLException sqle){
                Mensajeria.mostrarError("Listado de incidencias usuario","Error al obtener las incidencias: " + "\n" + sqle.getMessage());
                data=null;
        }
        return data;

    }

    public ObservableList<Incidenciasmysql> listaGestionIncidencias() {

        ObservableList<Incidenciasmysql> data = FXCollections.observableArrayList();

        try{
            String sqlMysql="select codIncidencia, descripcion,fecha_creacion,estado,nombre,apellidos,email from incidencias order by fecha_creacion";
            PreparedStatement obtenerIncidencias=conexionMYSQL.prepareStatement(sqlMysql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rsIncidencias=obtenerIncidencias.executeQuery();

            while (rsIncidencias.next()) {
                Incidenciasmysql incid = new Incidenciasmysql();
                incid.setCodIncidencia(rsIncidencias.getInt("codIncidencia"));
                incid.setDescripcion(rsIncidencias.getString("descripcion"));
                incid.setFechaCreacion(rsIncidencias.getDate("fecha_creacion"));
                incid.setEstado(rsIncidencias.getString("estado"));
                incid.setNombre(rsIncidencias.getString("nombre"));
                incid.setApellidos(rsIncidencias.getString("apellidos"));
                incid.setEmail(rsIncidencias.getString("email"));
                data.add(incid);
            }
        }catch(SQLException sqle){
            Mensajeria.mostrarError("Listado de incidencias usuario","Error al obtener las incidencias: " + "\n" + sqle.getMessage());
            data=null;
        }
        return data;

    }

    public String crearIncidencia(String descripcion, LocalDate fechaCreacion, int indBorrado, int indTraspaso, String usuario, String baseDatos){
        String error="";

        switch (baseDatos){
            case "postgre":
                try{
                    String sqlPostgre = "insert into incidencias (descripcion,fecha_creacion,ind_borrado,ind_traspaso,login) values (?,?,?,?,?)";
                    PreparedStatement sentenciaInsert= conexionPostgre.prepareStatement(sqlPostgre);
                    sentenciaInsert.setString(1, descripcion);
                    sentenciaInsert.setDate(2, Date.valueOf(fechaCreacion));
                    sentenciaInsert.setInt(3, indBorrado);
                    sentenciaInsert.setInt(4, indTraspaso);
                    sentenciaInsert.setString(5, usuario);
                    sentenciaInsert.executeUpdate();
                }catch(SQLException sqle){
                    error = sqle.getMessage();
                    break;
                }
                break;
            case "mysql":
        }
        return error;
    }

    public String borrarIncidencia(String descripcion, LocalDate fechaCreacion, String usuario, String baseDatos){

        //Se trata de un borrado lógico.
        //En un desarrollo posterior se podrán recuperar los registros borrados.

        String error="";
        switch (baseDatos){
            case "postgre":
                try{
                    String sqlPostgre="update incidencias set ind_borrado=1, ind_traspaso=0 where descripcion=? and fecha_creacion=? and login=?";
                    PreparedStatement sentenciaDelete= conexionPostgre.prepareStatement(sqlPostgre);
                    sentenciaDelete.setString(1, descripcion);
                    sentenciaDelete.setDate(2, Date.valueOf(fechaCreacion));
                    sentenciaDelete.setString(3, usuario);
                    sentenciaDelete.executeUpdate();
                }catch(SQLException sqle){
                    error = sqle.getMessage();
                    break;
                }
                break;
            case "mysql":
        }
        return error;
    }

    public String updateIncidencia(String descripcion, LocalDate fechaCreacion, int codigoinci, String usuario, String baseDatos, String estado){

        //Para modificar una incidencia seleccionada

        String error="";
        switch (baseDatos){
            case "postgre":
                try{
                    String sqlPostgre="update incidencias set descripcion=?, fecha_creacion=? where login= ? and codincidencia=?";
                    PreparedStatement sentenciaUpdate= conexionPostgre.prepareStatement(sqlPostgre);
                    sentenciaUpdate.setString(1, descripcion);
                    sentenciaUpdate.setDate(2, Date.valueOf(fechaCreacion));
                    sentenciaUpdate.setString(3, usuario);
                    sentenciaUpdate.setInt(4,codigoinci);
                    sentenciaUpdate.executeUpdate();
                }catch(SQLException sqle){
                    error = sqle.getMessage();
                    break;
                }
                break;
            case "mysql":
                try{
                    String sqlMysql="update incidencias set descripcion=?, estado=?, fecha_creacion=?, nombre=?, apellidos=?, email=? where codincidencia=?";
                    PreparedStatement sentenciaUpdate= conexionMYSQL.prepareStatement(sqlMysql);
                    sentenciaUpdate.setString(1, descripcion);
                    sentenciaUpdate.setString(2,estado);

                    sentenciaUpdate.setDate(2, Date.valueOf(fechaCreacion));
                    sentenciaUpdate.setString(3, usuario);
                    sentenciaUpdate.setInt(4,codigoinci);
                    sentenciaUpdate.executeUpdate();
                }catch(SQLException sqle){
                    error = sqle.getMessage();
                }
        }
        return error;
    }

    public String traspasarIncidencias(){
        //Leo las incidencias generadas en Postgre que tienen el indicador de traspaso=1, ind_borrado=0 y las inserto en la tabla Incidencias
        //de Mysql. A continuación, les pongo el traspaso=0 para que no vuelvan a pasar.

        String error="";
        String resultado="";

        try{
            String sqlTraspaso="select incidencias.codincidencia,incidencias.descripcion, incidencias.fecha_creacion, usuarios.nombre, usuarios.apellidos, usuarios.email" +
                    " from incidencias, usuarios where usuarios.login=incidencias.login and incidencias.ind_traspaso=1 and incidencias.ind_borrado=0;";
            PreparedStatement traspasoIncidencias=conexionPostgre.prepareStatement(sqlTraspaso,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rsTraspasoIncidencias=traspasoIncidencias.executeQuery();

            while (rsTraspasoIncidencias.next()) {
                try{
                    String sqlMysql = "insert into incidencias (descripcion,fecha_creacion,nombre, apellidos, email,ind_borrado,estado) values (?,?,?,?,?,?,?)";
                    PreparedStatement sentenciaInsert= conexionMYSQL.prepareStatement(sqlMysql);
                    sentenciaInsert.setString(1, rsTraspasoIncidencias.getString("descripcion"));
                    sentenciaInsert.setDate(2, Date.valueOf(rsTraspasoIncidencias.getString("fecha_creacion")));
                    sentenciaInsert.setString(3,rsTraspasoIncidencias.getString("nombre"));
                    sentenciaInsert.setString(4,rsTraspasoIncidencias.getString("apellidos"));
                    sentenciaInsert.setString(5, rsTraspasoIncidencias.getString("email"));
                    sentenciaInsert.setInt(6,0);
                    sentenciaInsert.setString(7,"En curso");
                    sentenciaInsert.executeUpdate();

                    //Actualizo en postgre el registro traspasado para que no vuelva a insertarse en futuras ejecuciones.
                    String sqlPostgre="update incidencias set ind_traspaso=0 where codincidencia=?";
                    PreparedStatement sentenciaUpdate= conexionPostgre.prepareStatement(sqlPostgre);
                    sentenciaUpdate.setInt(1,rsTraspasoIncidencias.getInt("codincidencia"));
                    sentenciaUpdate.executeUpdate();

                }catch(SQLException sqle){
                    error = sqle.getMessage();
                }
            }
        }catch(SQLException sqle){
            resultado= sqle.getMessage();
        }
        return resultado;
    }

    public void desconectarBD(String baseDatos){

        switch(baseDatos){
            case "postgre":
                try{
                    conexionPostgre.close();
                    conexionPostgre=null;
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                }
                break;
            case "mysql":
                try{
                    conexionMYSQL.close();
                    conexionMYSQL=null;
                } catch (SQLException throwables) {

                }
        }

    }

}
