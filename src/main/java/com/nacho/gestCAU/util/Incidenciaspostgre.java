package com.nacho.gestCAU.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableArray;

import java.text.SimpleDateFormat;

public class Incidenciaspostgre  {
    public SimpleStringProperty codIncidencia;
    public SimpleStringProperty descripcion;
    public SimpleDateFormat fechaCreacion;
    public SimpleStringProperty indBorrado;
    public SimpleStringProperty indTraspaso;
    public SimpleStringProperty codUsuario;

    public Incidenciaspostgre(SimpleStringProperty codIncidencia, SimpleStringProperty descripcion, SimpleDateFormat fechaCreacion, SimpleStringProperty indBorrado, SimpleStringProperty indTraspaso, SimpleStringProperty codUsuario) {
        this.codIncidencia = codIncidencia;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.indBorrado = indBorrado;
        this.indTraspaso = indTraspaso;
        this.codUsuario = codUsuario;
    }

    public Incidenciaspostgre() {

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

    public String getIndBorrado() {
        return indBorrado.get();
    }

    public SimpleStringProperty indBorradoProperty() {
        return indBorrado;
    }

    public void setIndBorrado(String indBorrado) {
        this.indBorrado.set(indBorrado);
    }

    public String getIndTraspaso() {
        return indTraspaso.get();
    }

    public SimpleStringProperty indTraspasoProperty() {
        return indTraspaso;
    }

    public void setIndTraspaso(String indTraspaso) {
        this.indTraspaso.set(indTraspaso);
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
}
