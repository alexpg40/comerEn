/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alex
 */
public class ConexionBD {
     private Connection conexion = null;
    private String servidor = "";
    private String database = "";
    private String usuario = "";
    private String passwords = "";
    private String url = "";
    
    public ConexionBD(String servidor , String database, String usuario, String password){
        try{
            this.servidor = servidor;
            this.database = database;
            this.url = "jdbc:mysql://" + servidor + "/" + database;
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexion a Base de Datos " + url + "...... Ok");
        } catch (SQLException ex) {
            System.out.println("Fallo al realizar la conexión con la base de datos");
        } catch (ClassNotFoundException ex) {
            System.out.println("Clase no encotrada");
        }
    }
    
    public ConexionBD(){
        try{
            this.servidor = "localhost";
            this.database = "comeren";
            this.usuario = "root";
            this.passwords = "";
            this.url = "jdbc:mysql://" + servidor + "/" + database;
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, passwords);
        } catch (SQLException ex) {
            System.out.println("Fallo al realizar la conexión con la base de datos" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Clase no encotrada");
        }
    }
    
    public Connection getConexion(){
        return conexion;
    }
    
    public Connection cerrarConexion(){
        try{
            conexion.close();
            System.out.println("Cerrando conexion a " + url + "....... Ok");
        } catch (SQLException ex) {
            System.out.println("Fallo al intentar cerra la conexión con la base de datos");
        }
        conexion = null;
        return conexion;
    }
}
