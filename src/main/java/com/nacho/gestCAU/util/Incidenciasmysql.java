package com.nacho.gestCAU.util;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Incidenciasmysql {
    public int codIncidencia;
    public String descripcion;
    public Date fechaCreacion;
    public Date fechaSolucion;
    public String estado;
    public int indBorrado;
    public String nombre;
    public String apellidos;
    public String email;
    public String codUsuario;
    public int codTincidencia;

    public Incidenciasmysql(int codIncidencia, String descripcion, Date fechaCreacion, Date fechaSolucion, String estado, int indBorrado, String nombre, String apellidos, String email, String codUsuario, int codTincidencia) {
        this.codIncidencia = codIncidencia;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaSolucion = fechaSolucion;
        this.estado = estado;
        this.indBorrado = indBorrado;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.codUsuario = codUsuario;
        this.codTincidencia = codTincidencia;
    }

    public Incidenciasmysql() {
    }

    public int getCodIncidencia() {
        return codIncidencia;
    }

    public void setCodIncidencia(int codIncidencia) {
        this.codIncidencia = codIncidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIndBorrado() {
        return indBorrado;
    }

    public void setIndBorrado(int indBorrado) {
        this.indBorrado = indBorrado;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public int getCodTincidencia() {
        return codTincidencia;
    }

    public void setCodTincidencia(int codTincidencia) {
        this.codTincidencia = codTincidencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
