/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Comentario;
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
public class ComentarioDAO {
    
    private Connection conexion;
    
    public ComentarioDAO(){
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
     * Recupera los comentarios de un restaurante
     * @param idRestaurante a buscar  comentarios
     * @return ArrayList<Comentario> del restaurante
     */
    public ArrayList<Comentario> getComentariosByIdRestaurante(int idRestaurante){
        ArrayList<Comentario> ret = new ArrayList<>();
        try{
            String sqlStr = "SELECT * FROM comentario WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Comentario(result.getInt("idComentario"), result.getInt("idUsuario"), 
                        result.getInt("idRestaurante"), result.getString("comentario"), 
                        result.getInt("valoracion")));
            }
        } catch(SQLException ex){
            System.out.println("Error al intentar recuperar los restaurantes!");
        }
        return ret;
    }
    
    /**
     * Inserta en la base de datos un comentario de un usuario
     * @param idUsuario de la persona que ha escrito el comentario
     * @param idRestaurante del restaurante que ha comentado
     * @param valoracion nota que le ha puesto al restaurante
     * @param descripcion del restaurante
     */
    public void insertarComentario(int idUsuario, int idRestaurante, int valoracion, String descripcion){
        try{
            String sqlStr = "INSERT INTO comentario "
                    + "VALUES(NULL," + idUsuario + ", " + idRestaurante + ", '" + descripcion + "', " + valoracion + ")";
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch(SQLException ex){
            System.out.println("Error al intentar insertar el comentario");
        }
    }
    
}
