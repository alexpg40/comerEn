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
public class Comentario {
    
    private int idComentario;
    private int idUsuario;
    private int idRestaurante;
    private String comentario;
    private int valoracion;
    
    public Comentario(int idComentario, int idUsuario, int idRestaurante, String comentario, int valoracion){
        this.idComentario = idComentario;
        this.idUsuario = idUsuario;
        this.idRestaurante = idRestaurante;
        this.comentario = comentario;
        this.valoracion = valoracion;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public String getComentario() {
        return comentario;
    }

    public int getValoracion() {
        return valoracion;
    }  
    
}
