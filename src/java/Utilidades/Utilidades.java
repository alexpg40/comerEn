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

}
