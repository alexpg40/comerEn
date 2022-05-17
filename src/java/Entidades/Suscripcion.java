/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author Alex
 */
public class Suscripcion {
    
    private int idSuscripcion;
    private float precio;
    private String descripcion;
    private int duracion;

    public Suscripcion(int idSuscripcion, float precio, String descripcion, int duracion) {
        this.idSuscripcion = idSuscripcion;
        this.precio = precio;
        this.descripcion = descripcion;
        this.duracion = duracion;
    }

    public int getIdSuscripcion() {
        return idSuscripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDuracion() {
        return duracion;
    }
    
}
