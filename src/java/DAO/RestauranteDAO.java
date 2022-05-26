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
                        result.getString("icono"), result.getBoolean("oculto")));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar los restaurantes!" + ex.getMessage());
        }
        return ret;
    }
    
    public ArrayList<Restaurante> getRestaurantesbyIdDueño(int idDueño){
        ArrayList<Restaurante> ret = new ArrayList<>();
        try{
            String sqlStr = "SELECT * FROM restaurante WHERE idDueño = " + idDueño;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"), 
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto")));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar los restaurantes!");
        }
        return ret;
    }
    
    public Restaurante getRestauranteById(int idRestaurante){
        Restaurante ret = null;
        try{
            String sqlStr = "SELECT * FROM restaurante WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret = new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"), 
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto"));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar el restaurante!");
        }
        return ret;
    }
    
    public int borrarRestaurante(int idRestaurante){
        int ret = 0;
        try {
            String sqlStr = "DELETE FROM restaurante WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ret = smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al borrar el restaurante" + ex.getMessage());
        }
        return ret;
    }
    
    public int ocultarRestaurante(int idRestaurante){
        int ret = 0;
        try {
            String sqlStr = "UPDATE restaurante SET oculto = 1 WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ret = smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al ocultar el restaurante" + ex.getMessage());
        }
        return ret;
    }
    
    public int mostrarRestaurante(int idRestaurante){
        int ret = 0;
        try {
            String sqlStr = "UPDATE restaurante SET oculto = 0 WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ret = smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al mostrar el restaurante" + ex.getMessage());
        }
        return ret;
    }
    
    public int valoracionMediaRestaurante(int idRestaurante){
        int ret = 0;
        try {
            String sqlStr = "SELECT AVG(valoracion) FROM comentario WHERE idRestaurante = " + idRestaurante;
            System.out.println(sqlStr);
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            if(result.next()){
                ret = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar la valoracion del restaurante" + ex.getMessage());
        }
        return ret;
    }
    
}
