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
    
}
