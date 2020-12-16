package com.nacho.gestCAU.util;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class Incidenciasmysql {
    public SimpleStringProperty codIncidencia;
    public SimpleStringProperty descripcion;
    public SimpleDateFormat fechaCreacion;
    public SimpleDateFormat fechaSolucion;
    public SimpleStringProperty estado;
    public SimpleStringProperty indBorrado;
    public SimpleStringProperty codUsuario;
    public SimpleStringProperty codTincidencia;

    public Incidenciasmysql(SimpleStringProperty codIncidencia, SimpleStringProperty descripcion, SimpleDateFormat fechaCreacion, SimpleDateFormat fechaSolucion, SimpleStringProperty estado, SimpleStringProperty indBorrado, SimpleStringProperty codUsuario, SimpleStringProperty codTincidencia) {
        this.codIncidencia = codIncidencia;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaSolucion = fechaSolucion;
        this.estado = estado;
        this.indBorrado = indBorrado;
        this.codUsuario = codUsuario;
        this.codTincidencia = codTincidencia;
    }

    public Incidenciasmysql() {
    }

    public String getCodIncidencia() {
        return codIncidencia.get();
    }

    public SimpleStringProperty codIncidenciaProperty() {
        return codIncidencia;
    }

    public void setCodIncidencia(String codIncidencia) {
        this.codIncidencia.set(codIncidencia);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public SimpleStringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public SimpleDateFormat getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(SimpleDateFormat fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public SimpleDateFormat getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(SimpleDateFormat fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public String getEstado() {
        return estado.get();
    }

    public SimpleStringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public String getIndBorrado() {
        return indBorrado.get();
    }

    public SimpleStringProperty indBorradoProperty() {
        return indBorrado;
    }

    public void setIndBorrado(String indBorrado) {
        this.indBorrado.set(indBorrado);
    }

    public String getCodUsuario() {
        return codUsuario.get();
    }

    public SimpleStringProperty codUsuarioProperty() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario.set(codUsuario);
    }

    public String getCodTincidencia() {
        return codTincidencia.get();
    }

    public SimpleStringProperty codTincidenciaProperty() {
        return codTincidencia;
    }

    public void setCodTincidencia(String codTincidencia) {
        this.codTincidencia.set(codTincidencia);
    }
}
