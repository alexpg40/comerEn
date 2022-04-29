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
public class Ubicacion {
    
    private int idUbicacion;
    private int idRestaurante;
    private float Lng;
    private float Lat;

    public Ubicacion(int idUbicacion, int idRestaurante, float Lng, float Lat) {
        this.idUbicacion = idUbicacion;
        this.idRestaurante = idRestaurante;
        this.Lng = Lng;
        this.Lat = Lat;
    }
    
}
