/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import Entidades.Rol;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class Utilidades {

    /**
     * Funcion para hashear una contraseña con SHA-256
     * @param password contraseña a hashear
     * @return contraseña hasheado
     */
    public static String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();

        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    /**
     * Esta funcion comprueba dado un ArrayList<Rol> que no contenga el rol
     * con el nombre pasado por parametro
     * @param nombre_rol a buscar
     * @param roles ArrayList<Rol> donde buscar
     * @return boolean
     */
    public static boolean isRol(String nombre_rol, ArrayList<Rol> roles) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getNombreRol().equalsIgnoreCase(nombre_rol)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validacion del usuario para el usuario del registro
     * @param nombre
     * @param apellido
     * @param correo
     * @param contrasena
     * @param rContrasena
     * @return 
     */
    public static boolean validarUsuario(String nombre, String apellido, String correo, String contrasena, String rContrasena){
        return validarNombre(nombre) && validarApellido(apellido) && validarCorreo(correo) && validarContrasena(contrasena) && validarRContrasena(contrasena, rContrasena);
    }
    
    public static boolean validarUsuario(String nombre, String apellido, String correo){
        return validarNombre(nombre) && validarApellido(apellido) && validarCorreo(correo);
    }
    
    public static boolean validarUsuario(String nombre, String apellido, String contraseña, String correo){
        return validarNombre(nombre) && validarApellido(apellido) && validarCorreo(correo) && validarContrasena(contraseña);
    }
    
    /**
     * Validación del nombre, tiene que ser mayor que 3 y estar vacio
     * @param nombre
     * @return 
     */
    private static boolean validarNombre(String nombre){
        return nombre.length() > 3 && !nombre.equals("");
    } 
    /**
     * Validación del apellido, tiene que ser mayor que 3 y estar vacio
     * @param apellido
     * @return 
     */
    private static boolean validarApellido(String apellido){
        return apellido.length() > 3 && !apellido.equals("");
    }
    
    /**
     * Validación del correo con un regex
     * @param correo
     * @return 
     */
    private static boolean validarCorreo(String correo){
        return !correo.matches("^[\\w-+]+(\\.[\\w-]{1,62}){0,126}@[\\w-]{1,63}(\\.[\\w-]{1,62})+/[\\w-]+$");
    }
    
    /**
     * Validación de contraseña tiene que contener un digito, un letra mayuscula
     * y minuscula, y caracter especial y debe terner una longitud de 8 o 
     * mayor
     * @param contrasena
     * @return 
     */
    private static boolean validarContrasena(String contrasena){
        return !contrasena.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }
    
    /**
     * Validación de la segunda contraseña, tiene que coincidir
     * con la primera contraseña
     * @param contrasena
     * @param rcontrasena
     * @return 
     */
    private static boolean validarRContrasena(String contrasena, String rcontrasena){
        return contrasena.equals(rcontrasena);
    }
    
    public static String crearEstrellas(int nEstrellas){
        String ret = "";
        for(int i = 0; i < nEstrellas; i++){
            ret += "★";
        }
        return ret;
    }
    
}
