/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Rol;
import Utilidades.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class RolDAO {
    
    private Connection conexion;
    
    public RolDAO(){
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
    
    /**
     * Recupera los roles de un usuario
     * @param idUsuario del usuario a buscar
     * @return ArrayList<Rol>
     */
    public ArrayList<Rol> obtenerRolesUsuario(int idUsuario){
        ArrayList<Rol> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT rol.idRol, rol.nombre FROM ((rol INNER JOIN usuario_rol ON rol.idRol = usuario_rol.idRol) "
             + "INNER JOIN usuario ON usuario.idUsuario = usuario_rol.idUsuario) WHERE usuario.idUsuario = " + idUsuario;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Rol(result.getInt("idRol"), result.getString("nombre")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar los roles del usuario" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Recupera los roles que no tiene un usuario
     * @param idUsuario del usuario a buscar
     * @return ArrayList<Rol>
     */
    public ArrayList<Rol> getRolesFaltantes(int idUsuario){
        ArrayList<Rol> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT rol.* FROM rol WHERE rol.idRol NOT IN (SELECT usuario_rol.idRol FROM usuario_rol WHERE idUsuario = " + idUsuario + ");";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Rol(result.getInt("idRol"), result.getString("nombre")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al los roles faltantes del usuario" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Añade un rol a un usuario en concreto
     * @param idUsuario a añadir rol
     * @param idRol del rol que añades al usuario
     */
    public void submitRolUsuario(int idUsuario, int idRol){
        try {
            String sqlStr = "INSERT INTO usuario_rol VALUES (" + idUsuario + ", " + idRol + ")";
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al insertar el rol del usuario" + ex.getMessage());
        }
    }
    
    /**
     * Borra un rol de un usuario determinado
     * @param idUsuario a borrar rol
     * @param idRol del rol que quieres borrar
     */
    public void deleteRolUsuario(int idUsuario, int idRol){
        try {
            String sqlStr = "DELETE FROM usuario_rol WHERE  idUsuario = " + idUsuario + " AND idRol = " + idRol;
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al borrar el rol del usuario" + ex.getMessage());
        }
    }
    
}
