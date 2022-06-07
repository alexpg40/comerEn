/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Etiqueta;
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
public class EtiquetaDAO {
    
    private Connection conexion;
    
    public EtiquetaDAO(){
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
     * Recupera los etiquetas de un restaurante
     * @param idRestaurante a buscar
     * @return ArrayList<Etiqueta>
     */
    public ArrayList<Etiqueta> getEtiquitasByIdRestaurante(int idRestaurante){
        ArrayList<Etiqueta> ret = new ArrayList<>();
        try{
            String sqlStr = "SELECT etiqueta.* FROM etiqueta INNER JOIN restaurante_etiqueta ON etiqueta.idEtiqueta = restaurante_etiqueta.idEtiqueta "
                    + "WHERE restaurante_etiqueta.idRestaurante =" + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Etiqueta(result.getInt("idEtiqueta"), result.getString("nombre")));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar las etiquetas del restaurante!");
        }
        return ret;
    }
    
    /**
     * Recupera las etiquetas que contengo el nombre del parametro
     * @param nombre de la etiquetas a buscar
     * @return ArrayList<Etiqueta>
     */
    public ArrayList<Etiqueta> getEtiquitasByNombre(String nombre){
        ArrayList<Etiqueta> ret = new ArrayList<>();
        try{
            String sqlStr = "SELECT * from etiqueta WHERE nombre LIKE '%" + nombre + "%'";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Etiqueta(result.getInt("idEtiqueta"), result.getString("nombre")));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar las etiquetas del restaurante!");
        }
        return ret;
    }
    
    /**
     * Recuperar las etiquetas faltantes del restaurante
     * @param idRestaurante a buscar
     * @return ArrayList<Etiqueta>
     */
    public ArrayList<Etiqueta> getEtiquitasFaltantesByidRestaurante(int idRestaurante){
        ArrayList<Etiqueta> ret = new ArrayList<>();
        try{
            String sqlStr = "SELECT * FROM etiqueta WHERE etiqueta.idEtiqueta NOT IN (SELECT idEtiqueta FROM restaurante_etiqueta WHERE idRestaurante = " + idRestaurante+ ")";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Etiqueta(result.getInt("idEtiqueta"), result.getString("nombre")));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar las etiquetas faltantes del restaurante!");
        }
        return ret;
    }
    
    public void borrarEtiquetaByIdRestaurante(int idEtiqueta, int idRestaurante){
        try{
            String sqlStr = "DELETE FROM restaurante_etiqueta WHERE idRestaurante = " + idRestaurante + " AND idEtiqueta = " + idEtiqueta;
            System.out.println(sqlStr);
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch(SQLException ex){
            System.out.println("Error al borrar la etiqueta del restaurante!" + ex.getMessage());
        }
    }
    
    public void insertarEtiquetaByIdRestaurante(int idEtiqueta, int idRestaurante){
        try{
            String sqlStr = "INSERT INTO restaurante_etiqueta VALUES(" + idRestaurante + ", " + idEtiqueta + ")";
            System.out.println(sqlStr);
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch(SQLException ex){
            System.out.println("Error al insertar la etiqueta del restaurante!" + ex.getMessage());
        }
    }
    
}
