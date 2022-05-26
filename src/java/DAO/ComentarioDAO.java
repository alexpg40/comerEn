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
    
}
