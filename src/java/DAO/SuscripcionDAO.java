/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Suscripcion;
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
public class SuscripcionDAO {
    
    private Connection conexion;
    
    public SuscripcionDAO(){
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
     * Consigue todas las suscripciones disponibles de la base de
     * datos
     * @return ArrayList<Suscripcion
     */
    public ArrayList<Suscripcion> getSuscripciones(){
        ArrayList<Suscripcion> ret = new ArrayList<>();  
        try{
            String sqlStr = "SELECT * FROM suscripcion";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Suscripcion(result.getInt("idSuscripcion"), result.getFloat("precio"), 
                        result.getString("descripcion"), result.getString("categoria")));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar las suscripciones!");
        }
        return ret;
    }
    
    /**
     * Recupera las suscripciones que ha comprado un usuario
     * @param idUsuario del usuario a buscar suscripciones
     * @return ArrayList<Suscripcion>
     */
    public ArrayList<Suscripcion> getSuscripcionesByIdUsuario(int idUsuario){
        ArrayList<Suscripcion> ret = new ArrayList<>();
        try{
            String sqlStr = "SELECT suscripcion.* FROM suscripcion INNER JOIN usuario_suscripcion ON"
                + " suscripcion.idSuscripcion = usuario_suscripcion.idSuscripcion "
                + "WHERE usuario_suscripcion.idUsuario = " + idUsuario;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Suscripcion(result.getInt("idSuscripcion"), result.getFloat("precio"), 
                        result.getString("descripcion"), result.getString("categoria")));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar las suscripciones!");
        }
        return ret;
    }
    
    /**
     * Un usuario compra una suscripción
     * @param idSuscripcion a comprar
     * @param idUsuario del comprador
     */
    public void comprarSuscripcion(int idSuscripcion, int idUsuario){
         try {
            String sqlStr = "INSERT INTO usuario_suscripcion VALUES (" + idUsuario + ", " + idSuscripcion + ")";
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al insertar la suscripcion" + ex.getMessage());
        }
    }
    
    /**
     * Elimina un suscripció de un usuario
     * @param idSuscripcion a eliminar
     * @param idUsuario del usuario a quitar
     */
    public void anularSuscripcion(int idSuscripcion, int idUsuario){
        try {
            String sqlStr = "DELETE FROM usuario_suscripcion WHERE idUsuario =" + idUsuario + " AND idSuscripcion = " + idSuscripcion;
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al insertar la suscripcion" + ex.getMessage());
        }
    }
    
    /**
     * Comprueba si el usuario tiene un tipo de suscripcion de 
     * anuncios, lo que devuelve un boolean si tiene que ver anunios
     * @param idUsuario a buscar
     * @return boolean
     */
    public boolean tieneAnuncios(int idUsuario){
        try{
            String sqlStr = "SELECT suscripcion.* FROM suscripcion INNER JOIN usuario_suscripcion ON suscripcion.idSuscripcion"
                    + " = usuario_suscripcion.idSuscripcion WHERE usuario_suscripcion.idUsuario = " + idUsuario
                    + " AND suscripcion.categoria = 'publicidad'";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            return !result.next();  
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar las suscripciones!");
        }
        return true;
    }
    
}
