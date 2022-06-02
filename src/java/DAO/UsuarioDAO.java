/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Usuario;
import Utilidades.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alex
 */
public class UsuarioDAO {
    
    private Connection conexion;
    
    public UsuarioDAO(){
        ConexionBD conexionBD = new ConexionBD();
        this.conexion = conexionBD.getConexion();
    }
    
    public void cerrarConexion(){
        try {
            this.conexion.close();
        } catch (SQLException ex) {
            System.out.println("Fallo al cerrar la conexión con la base de datos!");
        }
    }
    
    public int existeUsuario(String correo, String contraseña){
        int idUsuario = 0;
        try {
            String sqlStr = "SELECT idUsuario FROM usuario WHERE correo = '" + correo + "' AND contrasena = '" + contraseña + "'";
            System.out.println(sqlStr);
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                idUsuario = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el usuario" + ex.getMessage());
        }
        return idUsuario;
    }
    
    public int existeUsuario(String correo){
        int idUsuario = 0;
        try {
            String sqlStr = "SELECT idUsuario FROM usuario WHERE correo = '" + correo + "'";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                idUsuario = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el usuario" + ex.getMessage());
        }
        return idUsuario;
    }
    
    public Usuario getUsuarioByidUsuario(int idUsuario){
        Usuario ret = null;
        try {
            String sqlStr = "SELECT * FROM usuario WHERE idUsuario = " + idUsuario;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret = new Usuario(result.getString("icono"), result.getInt("idUsuario"), result.getString("nombre"), 
                        result.getString("apellido"), result.getString("correo"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    public void registrarUsuario(Usuario usuario){
        try {
            String sqlStr = "INSERT INTO usuario VALUES (null, '" + usuario.getNombre() + "', '" 
            + usuario.getApellido() + "', '" + usuario.getCorreo() + "', DEFAULT, '" + usuario.getContrasena() + "')";
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al insertar el usuario" + ex.getMessage());
        }
    }
    
    
    public void cambiarContraseña(String contraseña, int idUsuario){
        try {
            String sqlStr = "UPDATE usuario SET contrasena = '" + contraseña + "' WHERE idUsuario = " + idUsuario;
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
    }
    
    public void actualizarUsuario(Usuario usuario){
        try {
            String sqlStr = "UPDATE usuario SET nombre = '" + usuario.getNombre() + "', apellido = '" 
                    + usuario.getApellido() + "', correo = '" + usuario.getCorreo() +"'";
            if(usuario.getContrasena() != null){
                sqlStr+= " , contrasena = '" + Utilidades.Utilidades.convertirSHA256(usuario.getContrasena()) + "'";
            }
            sqlStr+= " WHERE idUsuario = " + usuario.getIdUsuario();
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
    }
    
    public ArrayList<Usuario> getUsuariosNotAdmin(){
        ArrayList<Usuario> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT usuario.* FROM usuario LEFT JOIN usuario_rol ON usuario.idUsuario = usuario_rol.idUsuario WHERE usuario_rol.idRol IS NULL OR usuario_rol.idRol != 1";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Usuario(result.getString("icono"), result.getInt("idUsuario"), result.getString("nombre"), 
                        result.getString("apellido"), result.getString("correo")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    public ArrayList<Usuario> getUsuariosByNombre(String nombre){
        ArrayList<Usuario> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT usuario.* FROM usuario LEFT JOIN usuario_rol ON usuario.idUsuario = usuario_rol.idUsuario WHERE (usuario_rol.idRol IS NULL OR usuario_rol.idRol != 1) AND usuario.nombre LIKE'%" + nombre + "%'";
            Statement smt = this.conexion.createStatement();
            System.out.println(sqlStr);
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Usuario(result.getString("icono"), result.getInt("idUsuario"), result.getString("nombre"), 
                        result.getString("apellido"), result.getString("correo")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    public int borrarUsuario(int idUsuario){
        int ret = 0;
        try {
            String sqlStr = "DELETE FROM usuario WHERE idUsuario = " + idUsuario;
            Statement smt = this.conexion.createStatement();
            ret = smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    public int[] getUsuariosValoradoRestaurante(int idRestaurante){
        
        int[] ret = new int[2];
        try {
            String sqlStr = "SELECT usuario.*, comentario.valoracion"
                    + " FROM usuario, comentario"
                    + " WHERE usuario.idUsuario = comentario.idUsuario"
                    + " AND comentario.idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            System.out.println(sqlStr);
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret[0] = result.getInt("idUsuario");
                ret[1] = result.getInt("valoracion");
            }
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    public int getNUsuariosComentado(){
        int ret = 0;
        try {
            String sqlStr = "SELECT DISTINCT COUNT(idUsuario) as numero FROM comentario";
            Statement smt = this.conexion.createStatement();
            System.out.println(sqlStr);
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret = result.getInt("numero");
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el numero de usuarios que han comentado" + ex.getMessage());
        }
        return ret;
    }
    
    public int getNUsuariosComentadoByidEtiqueta(int idEtiqueta){
        int ret = 0;
        try {
            String sqlStr = "SELECT COUNT(DISTINCT comentario.idUsuario) as numero "
                    + "FROM restaurante_etiqueta, comentario WHERE idEtiqueta = " + idEtiqueta + " "
                    + "AND comentario.idRestaurante = restaurante_etiqueta.idRestaurante "
                    + "AND comentario.valoracion >= 3";
            Statement smt = this.conexion.createStatement();
            System.out.println(sqlStr);
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret = result.getInt("numero");
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el numero de usuarios de la etiqueta" + ex.getMessage());
        }
        return ret;
    }
    
}
