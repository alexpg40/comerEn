/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Fotografia;
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
public class FotografiaDAO {
 
    private Connection conexion;
    
    public FotografiaDAO(){
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
     * Recupera las fotografias de un restaurante
     * @param idRestaurante
     * @return ArrayList<Fotografia>
     */
    public ArrayList<Fotografia> getFotografiasByIdRestaurante(int idRestaurante){
        ArrayList<Fotografia> ret = new ArrayList<>();
        try{
            String sqlStr = "SELECT * FROM fotografia WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Fotografia(result.getInt("idFotografia"), result.getInt("idRestaurante"), 
                        result.getString("ubicacion")));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar los restaurantes!");
        }
        return ret;
    }
    
    /**
     * Subir una fotografia de un restaurante
     * @param idRestaurante 
     * @param ubicacion 
     */
    public void insertFotografia(int idRestaurante, String ubicacion){
        try{
            String sqlStr = "INSERT INTO fotografia VALUES(NULL, " + idRestaurante + ", '" + ubicacion + "')";
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar los restaurantes!");
        }
    }
    
}
