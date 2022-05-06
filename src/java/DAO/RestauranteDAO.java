/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Restaurante;
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
public class RestauranteDAO {
    
    private Connection conexion;
    
    public RestauranteDAO(){
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
    
    public ArrayList<Restaurante> getRestaurantes(String nombre){
        ArrayList<Restaurante> ret = new ArrayList<>();
        try{
            String sqlStr = "SELECT * FROM restaurante WHERE nombre LIKE '%" + nombre + "%'";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"), 
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono")));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar los restaurantes!");
        }
        return ret;
    }
    
}
