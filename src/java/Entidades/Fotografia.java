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
public class Fotografia {
    
    private int idFotografia;
    private int idRestaurante;
    private String ubicacion;
    
    public Fotografia(int idFotografia, int idRestaurante, String ubicacion){
        this.idFotografia = idFotografia;
        this.idRestaurante = idRestaurante;
        this.ubicacion = ubicacion;
    }

    public int getIdFotografia() {
        return idFotografia;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    
}
