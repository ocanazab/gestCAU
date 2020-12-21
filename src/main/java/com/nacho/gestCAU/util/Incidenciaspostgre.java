package com.nacho.gestCAU.util;

import java.sql.Date;
import java.time.LocalDate;

public class Incidenciaspostgre {
    public int codIncidencia;
    public String descripcion;
    public Date fechaCreacion;
    public int indBorrado;
    public int indTraspaso;
    public String login;

    public Incidenciaspostgre() {
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

    public int getIndBorrado() {
        return indBorrado;
    }

    public void setIndBorrado(int indBorrado) {
        this.indBorrado = indBorrado;
    }

    public int getIndTraspaso() {
        return indTraspaso;
    }

    public void setIndTraspaso(int indTraspaso) {
        this.indTraspaso = indTraspaso;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}


