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
    private int idUsuario;
    private float precio;
    private String descripcion;

    public Suscripcion(int idSuscripcion, int idUsuario, float precio, String descripcion) {
        this.idSuscripcion = idSuscripcion;
        this.idUsuario = idUsuario;
        this.precio = precio;
        this.descripcion = descripcion;
    }
    
}
