package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Incidenciaspostgre;
import com.nacho.gestCAU.util.Mensajeria;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;

import java.util.List;

public class Model {

    private Connection conexionPostgre = null;
    private Connection conexionMYSQL = null;


    public String conectarBD(String baseDatos){
        String fallo="";

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

    public List<Incidenciaspostgre> listaIncidencias(String usuario, String baseDatos) {
        //Muestra las incidencias del usuario conectado.

        List<Incidenciaspostgre> listado = null;
        Incidenciaspostgre incid = new Incidenciaspostgre();

        switch (baseDatos){
            case "postgre":
                try{
                    String sqlPostgre="select descripcion, fecha_creacion from incidencias";
                    PreparedStatement obtenerIncidencias=conexionPostgre.prepareStatement(sqlPostgre);
                    ResultSet incidencias=obtenerIncidencias.executeQuery();
                    while (incidencias.next()){
                        incid.setDescripcion(incidencias.getString(0));
                        incid.setFechaCreacion(incidencias.getString(1));
                        listado.add(incid);
                    }

                }catch(SQLException sqle){
                    Mensajeria.mostrarError("Listado de incidencias usuario","Error al obtener las incidencias: " + "\n" + sqle.getMessage());
                }
                break;
            case "mysql":
        }
        return listado;

    }

    public String crearIncidencia(String descripcion, LocalDate fechaCreacion, int indBorrado, int indTraspaso, String usuario, String baseDatos){
        String error="";
        //Mensajeria.mostrarInfo("Insertar incidencia",descripcion+fechaCreacion+indBorrado+indTraspaso+usuario+baseDatos);
        switch (baseDatos){
            case "postgre":
                try{
                    String sqlPostgre = "insert into incidencias (descripcion,fecha_creacion,ind_borrado,ind_traspaso,codusuario) values (?,?,?,?,?)";
                    PreparedStatement sentenciaInsert= conexionPostgre.prepareStatement(sqlPostgre);
                    sentenciaInsert.setString(1, descripcion);
                    sentenciaInsert.setDate(2, Date.valueOf(fechaCreacion));
                    sentenciaInsert.setInt(3, indBorrado);
                    sentenciaInsert.setInt(4, indTraspaso);
                    //Para probar. Revisar lo del codigo del usuario
                    sentenciaInsert.setInt(5, 1);
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

    public void desconectarBD(String baseDatos){

        switch(baseDatos){
            case "postgre":
                try{
                    conexionPostgre.close();
                    conexionPostgre=null;
                } catch (SQLException throwables) {

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
