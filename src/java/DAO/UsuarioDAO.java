/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Usuario;
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
public class UsuarioDAO {
    
    private Connection conexion;
    
    public UsuarioDAO(){
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
     * Esta función comprueba si un usuario existe para poder 
     * logearse en la aplicación
     * @param correo del usuario
     * @param contraseña del usuario
     * @return int 0 no existe id del usuario si existe
     */
    public int existeUsuario(String correo, String contraseña){
        int idUsuario = 0;
        try {
            String sqlStr = "SELECT idUsuario FROM usuario WHERE correo = '" + correo + "' AND contrasena = '" + contraseña + "'";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                idUsuario = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el usuario" + ex.getMessage());
        }
        return idUsuario;
    }
    
    /**
     * Comprueba que el correo del usuario este registrado 
     * en la base de datos
     * @param correo a buscar
     * @return int 0 no existe id del usuario si existe
     */
    public int existeUsuario(String correo){
        int idUsuario = 0;
        try {
            String sqlStr = "SELECT idUsuario FROM usuario WHERE correo = '" + correo + "'";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                idUsuario = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el usuario" + ex.getMessage());
        }
        return idUsuario;
    }
    
    /**
     * Recupera el usuario dada su id
     * @param idUsuario a buscar
     * @return Usuario
     */
    public Usuario getUsuarioByidUsuario(int idUsuario){
        Usuario ret = null;
        try {
            String sqlStr = "SELECT * FROM usuario WHERE idUsuario = " + idUsuario;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret = new Usuario(result.getString("icono"), result.getInt("idUsuario"), result.getString("nombre"), 
                        result.getString("apellido"), result.getString("correo"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Registra un usuario en la base de datos
     * @param usuario a registrar
     */
    public void registrarUsuario(Usuario usuario){
        try {
            String sqlStr = "INSERT INTO usuario VALUES (null, '" + usuario.getNombre() + "', '" 
            + usuario.getApellido() + "', '" + usuario.getCorreo() + "', DEFAULT, '" + usuario.getContrasena() + "')";
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al insertar el usuario" + ex.getMessage());
        }
    }
    
    /**
     * Cambia la contraseña de un usuario
     * @param contraseña a cambiar
     * @param idUsuario al que cambiar la contraseña
     */
    public void cambiarContraseña(String contraseña, int idUsuario){
        try {
            String sqlStr = "UPDATE usuario SET contrasena = '" + contraseña + "' WHERE idUsuario = " + idUsuario;
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
    }
    
    /**
     * Actualiza los datos de un usuario
     * @param usuario
     */
    public void actualizarUsuario(Usuario usuario){
        try {
            String sqlStr = "UPDATE usuario SET nombre = '" + usuario.getNombre() + "', apellido = '" 
                    + usuario.getApellido() + "', correo = '" + usuario.getCorreo() +"'";
            if(usuario.getContrasena() != null){
                sqlStr+= " , contrasena = '" + Utilidades.Utilidades.convertirSHA256(usuario.getContrasena()) + "'";
            }
            sqlStr+= " WHERE idUsuario = " + usuario.getIdUsuario();
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
    }
    
    /**
     * Recupera los usuario que no tengan el rol de administrador
     * @return ArrayList<Usuario>
     */
    public ArrayList<Usuario> getUsuariosNotAdmin(){
        ArrayList<Usuario> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT usuario.* FROM usuario LEFT JOIN usuario_rol ON usuario.idUsuario = usuario_rol.idUsuario WHERE usuario_rol.idRol IS NULL OR usuario_rol.idRol != 1";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Usuario(result.getString("icono"), result.getInt("idUsuario"), result.getString("nombre"), 
                        result.getString("apellido"), result.getString("correo")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Recupera los usuario que contenga esa nombre
     * @param nombre a buscar
     * @return ArrayList<Usuario>
     */
    public ArrayList<Usuario> getUsuariosByNombre(String nombre){
        ArrayList<Usuario> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT usuario.* FROM usuario LEFT JOIN usuario_rol ON usuario.idUsuario = usuario_rol.idUsuario WHERE (usuario_rol.idRol IS NULL OR usuario_rol.idRol != 1) AND usuario.nombre LIKE'%" + nombre + "%'";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret.add(new Usuario(result.getString("icono"), result.getInt("idUsuario"), result.getString("nombre"), 
                        result.getString("apellido"), result.getString("correo")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Borra un usuario de la base de datos con esa id
     * @param idUsuario
     * @return int
     */
    public int borrarUsuario(int idUsuario){
        int ret = 0;
        try {
            String sqlStr = "DELETE FROM usuario WHERE idUsuario = " + idUsuario;
            Statement smt = this.conexion.createStatement();
            ret = smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Recupera la valoraci
     * @param idRestaurante
     * @return 
     */
    public int[] getUsuariosValoradoRestaurante(int idRestaurante){
        
        int[] ret = new int[2];
        try {
            String sqlStr = "SELECT usuario.*, comentario.valoracion"
                    + " FROM usuario, comentario"
                    + " WHERE usuario.idUsuario = comentario.idUsuario"
                    + " AND comentario.idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret[0] = result.getInt("idUsuario");
                ret[1] = result.getInt("valoracion");
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar la valoracion del usuario" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Recupera el numero de usuarios con han comentado en total
     * en toda la aplicación
     * @return int n de usuarios
     */
    public int getNUsuariosComentado(){
        int ret = 0;
        try {
            String sqlStr = "SELECT DISTINCT COUNT(idUsuario) as numero FROM comentario";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret = result.getInt("numero");
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el numero de usuarios que han comentado" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Recupera el numero de usuarios con han comentado en un restaurante
     * que contenga la etiqueta
     * @param idEtiqueta a buscar
     * @return int numero de restaurantes
     */
    public int getNUsuariosComentadoByidEtiqueta(int idEtiqueta){
        int ret = 0;
        try {
            String sqlStr = "SELECT COUNT(DISTINCT comentario.idUsuario) as numero "
                    + "FROM restaurante_etiqueta, comentario WHERE idEtiqueta = " + idEtiqueta + " "
                    + "AND comentario.idRestaurante = restaurante_etiqueta.idRestaurante "
                    + "AND comentario.valoracion >= 3";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while(result.next()){
                ret = result.getInt("numero");
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar el numero de usuarios de la etiqueta" + ex.getMessage());
        }
        return ret;
    }
    
}
