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

    public static String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();

        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static boolean isRol(String nombre_rol, ArrayList<Rol> roles) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getNombreRol().equalsIgnoreCase(nombre_rol)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validarUsuario(String nombre, String apellido, String correo, String contrasena, String rContrasena){
        return validarNombre(nombre) && validarApellido(apellido) && validarCorreo(correo) && validarContrasena(contrasena) && validarRContrasena(contrasena, rContrasena);
    }
    
    public static boolean validarUsuario(String nombre, String apellido, String correo){
        return validarNombre(nombre) && validarApellido(apellido) && validarCorreo(correo);
    }
    
    private static boolean validarNombre(String nombre){
        return nombre.length() > 3 && !nombre.equals("");
    } 
    
    private static boolean validarApellido(String apellido){
        return apellido.length() > 3 && !apellido.equals("");
    }
    
    private static boolean validarCorreo(String correo){
        return !correo.matches("^[\\w-+]+(\\.[\\w-]{1,62}){0,126}@[\\w-]{1,63}(\\.[\\w-]{1,62})+/[\\w-]+$");
    }
    
    private static boolean validarContrasena(String contrasena){
        return !contrasena.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }
    
    private static boolean validarRContrasena(String contrasena, String rcontrasena){
        return contrasena.equals(rcontrasena);
    }
    
}
