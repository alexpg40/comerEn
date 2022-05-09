/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.sql.Time;

/**
 *
 * @author Alex
 */
public class Restaurante {
    
    private int idRestaurante;
    private int idDueño;
    private int idAdmin;
    private String nombre;
    private String descripcion;
    private Time horario_abre;
    private Time horario_cierre;
    private String icono;
    
    public Restaurante(int idRestaurante, int idDueño, int idAdmin, 
            String nombre, String descripcion, Time horario_abre, 
            Time horario_cierre, String icono){
        this.idRestaurante = idRestaurante;
        this.idDueño = idDueño;
        this.idAdmin = idAdmin;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horario_abre = horario_abre;
        this.horario_cierre = horario_cierre;
        this.icono = icono;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public int getIdDueño() {
        return idDueño;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Time getHorario_abre() {
        return horario_abre;
    }

    public Time getHorario_cierre() {
        return horario_cierre;
    }

    public String getIcono() {
        return icono;
    }
    
}
