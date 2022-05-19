/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Utilidades.Utilidades;

/**
 *
 * @author Alex
 */
public class Usuario {
    
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String icono;
    private String contrasena;
    
    public Usuario(String nombre, String apellido, String correo, String contrasena){
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = Utilidades.convertirSHA256(contrasena);
    }
    
    public Usuario(int idUsuario, String nombre, String apellido, String correo, String contrasena){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = Utilidades.convertirSHA256(contrasena);
    }
    
    public Usuario(String icono, int idUsuario, String  nombre, String apellido, String correo){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.icono = icono;
        this.correo = correo;
    }
    
    public Usuario(int idUsuario, String  nombre, String apellido, String correo){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }
    
    
    
}
