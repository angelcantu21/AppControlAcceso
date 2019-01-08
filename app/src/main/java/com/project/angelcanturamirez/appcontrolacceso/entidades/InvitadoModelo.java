package com.project.angelcanturamirez.appcontrolacceso.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;

public class InvitadoModelo implements Serializable{

    private String nombre;
    private String fecha;
    private String caducidad;

    public InvitadoModelo(String nombre, String fecha, String caducidad) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.caducidad = caducidad;
    }

    public InvitadoModelo(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }

}
