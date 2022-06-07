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

    public RestauranteDAO() {
        ConexionBD conexionBD = new ConexionBD();
        this.conexion = conexionBD.getConexion();
    }

    public void cerrarConexion() {
        try {
            this.conexion.close();
        } catch (SQLException ex) {
            System.out.println("Fallo al cerrar la conexión con la base de datos!");
        }
    }

    /**
     * Recupera los restaurantes dado un nombre
     * @param nombre
     * @return ArrayList<Restaurante>
     */
    public ArrayList<Restaurante> getRestaurantes(String nombre) {
        ArrayList<Restaurante> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT * FROM restaurante WHERE nombre LIKE '%" + nombre + "%'";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while (result.next()) {
                ret.add(new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"),
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto"), result.getString("localidad")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar los restaurantes!" + ex.getMessage());
        }
        return ret;
    }

    /**
     * Recupera los restaurantes de un dueño
     * @param idDueño
     * @return ArrayList<Restaurante>
     */
    public ArrayList<Restaurante> getRestaurantesbyIdDueño(int idDueño) {
        ArrayList<Restaurante> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT * FROM restaurante WHERE idDueño = " + idDueño;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while (result.next()) {
                ret.add(new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"),
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto"), result.getString("localidad")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar los restaurantes!");
        }
        return ret;
    }

    /**
     * Recupera un restaurante dado su idRestauarnte
     * @param idRestaurante a buscar
     * @return Restaurante
     */
    public Restaurante getRestauranteById(int idRestaurante) {
        Restaurante ret = null;
        try {
            String sqlStr = "SELECT * FROM restaurante WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while (result.next()) {
                ret = new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"),
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto"), result.getString("localidad"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar el restaurante!");
        }
        return ret;
    }

    /**
     * Borra un restaurante dado su id
     * @param idRestaurante
     * @return int si ha sido borrado
     */
    public int borrarRestaurante(int idRestaurante) {
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

    /**
     * Oculto un restauarnte dado su id poniendo oculto = 1
     * @param idRestaurante
     * @return int si ha sido ocultado
     */
    public int ocultarRestaurante(int idRestaurante) {
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

    /**
     * Desoculta un restaurante dado su id poniendo oculto = 0
     * @param idRestaurante
     * @return int si ha sido ocultado
     */
    public int mostrarRestaurante(int idRestaurante) {
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

    /**
     * Calcula la valoración media dado el idRestaurante
     * @param idRestaurante
     * @return 
     */
    public int valoracionMediaRestaurante(int idRestaurante) {
        int ret = 0;
        try {
            String sqlStr = "SELECT AVG(valoracion) FROM comentario WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            if (result.next()) {
                ret = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar la valoracion del restaurante" + ex.getMessage());
        }
        return ret;
    }

    /**
     * Da de alta un restauarnte con su dueño
     * @param nombreRestaurante 
     * @param idDueño
     * @param idAdmin
     * @return si ha sido dado de alta
     */
    public int darAltaRestaurante(String nombreRestaurante, int idDueño, int idAdmin) {
        int ret = 0;
        try {
            String sqlStr = "INSERT INTO restaurante(idRestaurante, nombre, idDueño, idAdmin) VALUES(NULL, '" + nombreRestaurante + "', " + idDueño + ", " + idAdmin + ")";
            Statement smt = this.conexion.createStatement();
            ret = smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al crear el restaurante" + ex.getMessage());
        }
        return ret;
    }

    /**
     * Recupera los 5 restaurantes más polulares con mejor valoración
     * @return ArrayList<Restaurante>
     */
    public ArrayList<Restaurante> getRestaurantesPopulares() {
        ArrayList<Restaurante> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT restaurante.*, AVG(comentario.valoracion) "
                    + "FROM restaurante INNER JOIN comentario "
                    + "ON restaurante.idRestaurante = comentario.idRestaurante "
                    + "WHERE restaurante.oculto = 0 GROUP BY idRestaurante "
                    + "ORDER BY AVG(comentario.valoracion) DESC "
                    + "LIMIT 5";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while (result.next()) {
                ret.add(new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"),
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto"), result.getString("localidad")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar los restaurantes!" + ex.getMessage());
        }
        return ret;
    }

    /**
     * Recupera los restaurantes más cercanos dados una ubicación y un radio
     * @param lng double Longitud de la ubicación
     * @param lat double Latitud de la ubicación
     * @param radio int km de radio
     * @return ArrayList<Restaurante>
     */
    public ArrayList<Restaurante> getRestaurantesCercanos(double lng, double lat, int radio) {
        ArrayList<Restaurante> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT restaurante.*, "
                    + "st_distance_sphere(point(Lng, Lat), point(" + lng + ", " + lat + ")) / 1000 as distance "
                    + "FROM ubicacion, restaurante "
                    + "WHERE ubicacion.idRestaurante = restaurante.idRestaurante "
                    + "HAVING distance <= " + radio
                    + " ORDER BY distance ASC";
            System.out.println(sqlStr);
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while (result.next()) {
                ret.add(new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"),
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto"), result.getString("localidad")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar los restaurantes!" + ex.getMessage());
        }
        return ret;
    }

    /**
     * Recupera las localidades de todos los restaurantes dado un nombre
     * @param localidad
     * @return ArrayList<String>
     */
    public ArrayList<String> getLocalidadesRestaurantes(String localidad) {
        ArrayList<String> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT DISTINCT localidad FROM restaurante WHERE localidad LIKE '%" + localidad + "%'";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            System.out.println(sqlStr);
            while (result.next()) {
                ret.add(result.getString("localidad"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar los restaurantes!" + ex.getMessage());
        }
        return ret;
    }
    /**
     * Recupera las localidades de todos los restaurantes dado un nombre
     * @param localidad
     * @return ArrayList<String>
     */
    public ArrayList<String> getLocalidadesRestaurantes() {
        ArrayList<String> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT DISTINCT localidad FROM restaurante";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            System.out.println(sqlStr);
            while (result.next()) {
                ret.add(result.getString("localidad"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar los restaurantes!" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Recupera todos los restaurantes que contengan la id de Etiqueta
     * @param idEtiqueta
     * @return ArrayList<Restaurante>
     */
    public ArrayList<Restaurante> getRestaurantesByEtiquetas(int idEtiqueta) {
        ArrayList<Restaurante> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT DISTINCT restaurante.* FROM restaurante, restaurante_etiqueta "
                    + "WHERE restaurante_etiqueta.idRestaurante = restaurante.idRestaurante and restaurante_etiqueta.idEtiqueta = " + idEtiqueta;
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while (result.next()) {
                ret.add(new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"),
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto"), result.getString("localidad")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar los restaurantes!" + ex.getMessage());
        }
        return ret;
    }

    /**
     * Recupera los restaurantes con esa localidad
     * @param localidad
     * @return ArrayList<Restaurante>
     */
    public ArrayList<Restaurante> getRestaurantesByLocalidad(String localidad) {
        ArrayList<Restaurante> ret = new ArrayList<>();
        try {
            String sqlStr = "SELECT * FROM restaurante WHERE localidad = '" + localidad + "'";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while (result.next()) {
                ret.add(new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"),
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto"), result.getString("localidad")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar los restaurantes!");
        }
        return ret;
    }

    /**
     * Filtra los restaurantes según la ubicación, el radio, la localidad o la valoración
     * @param lng Longitud de la ubicación
     * @param lat Latitud de la ubicación
     * @param radio km de radio
     * @param localidad del restaurante
     * @param valoracion del restaurante
     * @return ArrayList<Restaurante>
     */
    public ArrayList<Restaurante> getRestaurantesFiltrados(double lng, double lat, int radio, String localidad, int valoracion) {
        ArrayList<Restaurante> ret = new ArrayList<>();
        try {
            String where = "WHERE ubicacion.idRestaurante = Restaurante_Valoracion.idRestaurante AND valoracion >= " + valoracion;
            if(!localidad.equals("Todas")){
                where += " AND localidad = '" + localidad + "'";
            }
            String sqlStr = "SELECT Restaurante_Valoracion.*, "
                    + "st_distance_sphere(point(Lng, Lat), point(" + lng + ", " + lat + ")) / 1000 as distance "
                    + "FROM Restaurante_Valoracion, ubicacion "
                    + where
                    + " HAVING distance <= " + radio
                    + " ORDER BY distance ASC";
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while (result.next()) {
                ret.add(new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"),
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto"), result.getString("localidad")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar los restaurantes!" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Recupera los restaurantes filtrados segun la localidad o la valoración
     * @param localidad
     * @param valoracion
     * @return ArrayList<Restaurante>
     */
    public ArrayList<Restaurante> getRestaurantesFiltrados(String localidad, int valoracion) {
        ArrayList<Restaurante> ret = new ArrayList<>();
        try {
            String where = "WHERE valoracion >= " + valoracion;
            if(!localidad.equals("Todas")){
                where += " AND localidad = '" + localidad + "'";
            }
            String sqlStr = "SELECT Restaurante_Valoracion.* "
                    + "FROM Restaurante_Valoracion "
                    + where;
            System.out.println(sqlStr);
            Statement smt = this.conexion.createStatement();
            ResultSet result = smt.executeQuery(sqlStr);
            while (result.next()) {
                ret.add(new Restaurante(result.getInt("idRestaurante"), result.getInt("idDueño"),
                        result.getInt("idAdmin"), result.getString("nombre"), result.getString("descripcion"),
                        result.getTime("horario_abre"), result.getTime("horario_cierra"),
                        result.getString("icono"), result.getBoolean("oculto"), result.getString("localidad")));
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar recuperar los restaurantes!" + ex.getMessage());
        }
        return ret;
    }
    
    /**
     * Función que actualiza la descripción de un restaurante
     * @param idRestaurante
     * @param descripcion 
     */
    public void cambiarDescripcion(int idRestaurante, String descripcion){
        try {
            String sqlStr = "UPDATE restaurante SET descripcion = '" + descripcion + "' WHERE idRestaurante = " + idRestaurante;
            Statement smt = this.conexion.createStatement();
            smt.executeUpdate(sqlStr);
        } catch (SQLException ex) {
            System.out.println("Error al ocultar el restaurante" + ex.getMessage());
        }
    }
    
}
