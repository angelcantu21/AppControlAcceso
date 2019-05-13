package com.project.angelcanturamirez.appcontrolacceso.entidades;

import java.io.Serializable;

public class ResidenteModelo implements Serializable {

    private String id;
    private String nombre;
    private String apellidos;
    private String departamento;
    private String codigo;
    private String disponible;
    private String ausente;
    private String molestar;

    public ResidenteModelo(){

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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getAusente() {
        return ausente;
    }

    public void setAusente(String ausente) {
        this.ausente = ausente;
    }

    public String getMolestar() {
        return molestar;
    }

    public void setMolestar(String molestar) {
        this.molestar = molestar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
