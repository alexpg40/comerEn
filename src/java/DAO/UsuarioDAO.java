/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Usuario;
import Utilidades.ConexionBD;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            String sqlStr = "SELECT idUsuario FROM Usuario WHERE correo = '" + correo + "' AND contrasena = '" + contraseña + "'";
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
    
    public Usuario getUsuarioByidUsuario(int idUsuario){
        Usuario ret = null;
        try {
            String sqlStr = "SELECT * FROM Usuario WHERE idUsuario = " + idUsuario;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret = new Usuario(result.getInt("idUsuario"), result.getString("nombre"), 
                        result.getString("apellido"), result.getString("correo"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    public void registrarUsuario(Usuario usuario){
        try {
            String sqlStr = "INSERT INTO Usuario VALUES (null, '" + usuario.getNombre() + "', '" 
            + usuario.getApellido() + "', '" + usuario.getCorreo() + "', '" + usuario.getContrasena() + "')";
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al insertar el usuario" + ex.getMessage());
        }
    }
    
    
    public void actualizarUsuario(Usuario usuario){
        try {
            String sqlStr = "UPDATE usuario SET nombre = '" + usuario.getNombre() + "', apellido = '" 
                    + usuario.getApellido() + "', correo = '" + usuario.getCorreo() + "', contrasena = '" + 
                    usuario.getContrasena()+ "' WHERE idUsuario = " + usuario.getIdUsuario();
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
    }
    
}
