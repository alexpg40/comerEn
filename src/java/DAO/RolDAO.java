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
    
}
