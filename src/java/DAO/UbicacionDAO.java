/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Ubicacion;
import Utilidades.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Alex
 */
public class UbicacionDAO {
    
    private Connection conexion;
    
    public UbicacionDAO(){
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
     * Recupera la ubicación de un restaurante dada su 
     * id
     * @param idRestaurante a buscar su ubicación
     * @return Ubicacion
     */
    public Ubicacion getUbicacionByIdRestaurante(int idRestaurante){
        Ubicacion ret = null;
        try {
            String sqlStr = "SELECT * FROM ubicacion WHERE idRestaurante =" + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret = new Ubicacion(result.getInt("idUbicacion"), result.getInt("idRestaurante"), 
                        result.getFloat("Lng"), result.getFloat("Lat"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Cambia la ubicación de un restaurante dada su id y la ubicacion 
     * @param idRestaurante
     * @param Lng Longitud de su ubicación
     * @param Lat Latitud de su ubicación
     */
    public void cambiarUbicacionByIdRestaurante(int idRestaurante, float Lng, float Lat){
       try {
            String sqlStr = "UPDATE ubicacion SET Lng = " + Lng + ", Lat = " + Lat + " WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el usuario" + ex.getMessage());
        } 
    }
    
    public int restauranteTieneUbicacion(int idRestaurante){
        int ret = 0;
        try {
            String sqlStr = "SELECT count(*) as tiene FROM ubicacion WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ResultSet executeQuery = smt.executeQuery(sqlStr);
            while(executeQuery.next()){
                ret = executeQuery.getInt("tiene");
            }
        } catch (SQLException ex) {
            System.out.println("Error al comprobar si el restaurante tiene ubicación" + ex.getMessage());
        }
        return ret;
    }
    
    public void insertarUbicacion(int idRestaurante, float Lng, float Lat){
       try {
            String sqlStr = "INSERT INTO ubicacion VALUES(NULL, " + Lng + ", " + Lat + ", " + idRestaurante + ")";
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al insertar la ubicacion" + ex.getMessage());
        } 
    }
    
}
