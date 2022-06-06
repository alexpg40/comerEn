/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaRecomendacion;

import DAO.EtiquetaDAO;
import DAO.RestauranteDAO;
import DAO.UsuarioDAO;
import Entidades.Etiqueta;
import Entidades.Restaurante;
import Utilidades.MapUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author alex
 */
public class SistemaRecomendacion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //PRUEBAS PARA TESTEAR EL SISTEMA DE RECOMENDACIÓN
        
        RestauranteDAO restauranteDao = new RestauranteDAO();
        ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantes("");
        restauranteDao.cerrarConexion();
        ArrayList<Restaurante> calcularRecomendacionRestaurantes = calcularRecomendacionRestaurantes(restaurantes, 7);
        int a = 0;

    }

    /**
     * Calcula la recomendación de los restaurantes dado un usuario
     * y los ordena para devolverlos en un ArrayList<Restaurante>
     * @param restaurantes ArrayList<Restaurante> en el que calcular la recomendación
     * de cada uno
     * @param idUsuario del usuario con el calcular la recomendación
     * @return ArrayList<Restaurante> ordenados basado en el calculado
     * del similitud
     */
    public static ArrayList<Restaurante> calcularRecomendacionRestaurantes(ArrayList<Restaurante> restaurantes, int idUsuario){
        
        Map<Integer, Double> calcularRecomendacion = calcularRecomendacion(restaurantes, idUsuario);
        Iterator<Integer> iterator = calcularRecomendacion.keySet().iterator();
        
        //Imprimo los resultados para poder tener mayor control
        while(iterator.hasNext()){
            int i = iterator.next();
            System.out.println(i + " - " + calcularRecomendacion.get(i));
        }
        
        //Ordeno los restaurantes segun el orden de similitud mayor similitud primero menor último
        Map<Integer, Double> sortByValue = MapUtil.sortByValue(calcularRecomendacion);
        Set<Integer> keySet = sortByValue.keySet();
        Object[] toArray =  keySet.toArray();
        
        ArrayList<Restaurante> ret = new ArrayList<>();
        for(int i = 0; i < toArray.length; i++){
            for(Restaurante restaurante : restaurantes){
                if(restaurante.getIdRestaurante() == (int) toArray[i]){
                    ret.add(restaurante);
                }
            }
        }
        for(Restaurante restaurante : restaurantes){
            if(!ret.contains(restaurante)) ret.add(restaurante);
        }
        
        return ret;
    }
    
    /**
     * Función que calcula de la recomendación de los restaurantes dado de un 
     * usuario
     * @param restaurantes ArrayList<Restaurante> en el que calcular la recomendación
     * de cada uno
     * @param idUsuario del usuario con el calcular la recomendación
     * @return Map<Integer, Doble> donde la key es la id de restaurante, y el double la
     * recomendación o similitud con el usuario
     */
    public static Map<Integer, Double> calcularRecomendacion(ArrayList<Restaurante> restaurantes, int idUsuario) {
        Map<Integer, Double> ret = new HashMap<>();
        
        //Crea tabla booleana con las etiquetas de las restaurantes
        TablaBooleana tablaBooleana = crearTablaBooleana(restaurantes);
        //Crea tabla booleana con las valoraciones de los usuarios de esos restaurantes
        TablaValoracionesUsuarios tablaValoraciones = crearTablaValoraciones(restaurantes);
        //Recupero los productos valorados de cada usuario
        HashMap<Integer, ArrayList<Integer>> productosValoradosByUsuario = tablaValoraciones.getProductosValoradosByUsuario();
        //Recupero los perfiles de usuarios dados los productos valorados de cada usuario
        HashMap<Integer, int[]> perfilesUsuarios = tablaBooleana.getPerfilesUsuarios(productosValoradosByUsuario);
        //Recu  pero las etiquetas de todos los restaurantes
        Set<Integer> etiquetas = tablaBooleana.getEtiquetas();

        //Recupero los ids de los productos no valorados del usuario
        ArrayList<Integer> productosNoValoradosByUsuario = tablaValoraciones.getProductosNoValoradosByUsuario(idUsuario);
        //Recupero los ids de los productos valorados del usuario
        ArrayList<Integer> productosValorados = tablaValoraciones.getProductosValoradosByUsuario(idUsuario);
        //Calculo el perfil del usuario con los productos valorados del usuario
        int[] perfilUsuario = tablaBooleana.getPerfilUsuario(productosValorados);
        
        //Creo tabla con las ponderaciones de cada usuario
        TablaPonderaciones tablaPonderaciones = new TablaPonderaciones(perfilesUsuarios, etiquetas);
        //Recupero la ponderacion del usuario
        double[] ponderacionUsuario = tablaPonderaciones.getPonderacionUsuario(idUsuario);
        double calcularSimilitudCosPonderacion = 0;

        //Calculo la similitud de los productos no valorados del usuario
        for (Integer i : productosNoValoradosByUsuario) {
            int[] perfilProducto = tablaBooleana.getPerfilProducto(i);
            calcularSimilitudCosPonderacion = calcularSimilitudCosPonderacion(perfilUsuario, perfilProducto, ponderacionUsuario);
            ret.put(i, calcularSimilitudCosPonderacion);
        }

        return ret;
    }

    /**
     * Crea una tabla booleana con las etiquetas de todos los restaurantes dados
     * @param restaurantes ArrayList<Restaurante> de los que buscar las etiquetas
     * @return tabla booleana con las etiquetas de todos los restaurantes
     */
    public static TablaBooleana crearTablaBooleana(ArrayList<Restaurante> restaurantes) {
        Map<Integer, Set<Integer>> restaurantesEtiquetas = new HashMap<>();
        EtiquetaDAO etiquetaDao = new EtiquetaDAO();
        int idRestaurante = 0;
        int idEtiqueta = 0;
        ArrayList<Etiqueta> etiquetas = null;
        Set<Integer> idsEtiquetas = null;
        for (Restaurante restaurante : restaurantes) {
            idRestaurante = restaurante.getIdRestaurante();
            etiquetas = etiquetaDao.getEtiquitasByIdRestaurante(idRestaurante);
            idsEtiquetas = new HashSet<>();
            for (Etiqueta etiqueta : etiquetas) {
                idEtiqueta = etiqueta.getIdEtiqueta();
                idsEtiquetas.add(idEtiqueta);
            }
            restaurantesEtiquetas.put(idRestaurante, idsEtiquetas);

        }
        etiquetaDao.cerrarConexion();
        return new TablaBooleana(restaurantesEtiquetas);
    }

    /**
     * Crea una tabla de valoraciones de los usuarios sobre los restaurantes dados
     * @param restaurantes ArrayList<Restaurantes> para recoger sus valoraciones
     * @return TablaValoracionesUsuarios 
     */
    public static TablaValoracionesUsuarios crearTablaValoraciones(ArrayList<Restaurante> restaurantes) {
        UsuarioDAO usuarioDao = new UsuarioDAO();
        Map<Integer, Map<Integer, Integer>> valoracionesUsuariosRestaurantes = new HashMap<>();
        int[] usuarioValoracion = null;
        Map<Integer, Integer> usuarioValoracionMap = null;
        int idRestaurante = 0;
        for (Restaurante restaurante : restaurantes) {
            usuarioValoracionMap = new HashMap<>();
            idRestaurante = restaurante.getIdRestaurante();
            usuarioValoracion = usuarioDao.getUsuariosValoradoRestaurante(idRestaurante);
            usuarioValoracionMap.put(idRestaurante, usuarioValoracion[1]);
            valoracionesUsuariosRestaurantes.put(usuarioValoracion[0], usuarioValoracionMap);
        }
        return new TablaValoracionesUsuarios(valoracionesUsuariosRestaurantes);
    }

    /**
     * Calcula la similitud o recomendación de un usuario (con su perfil), con un
     * restaurante (su perfil de producto) con la similitud del coseno
     * @param perfilUsuario int[] vector del perfil del usuario
     * @param perfilProducto int[] vector del perfil del producto
     * @return similitud entre el perfil de usuario y el perfil del producto
     */
    public static double calcularSimilitudCos(int[] perfilUsuario, int[] perfilProducto) {
        double ret = 0.0;
        int multiplicacion = 0;
        int sumaUsuario = 0;
        int sumaProducto = 0;
        for (int i = 0; i < perfilUsuario.length; i++) {
            multiplicacion += perfilUsuario[i] * perfilProducto[i];
            sumaUsuario += perfilUsuario[i];
            sumaProducto += perfilProducto[i];
        }
        ret = multiplicacion / (Math.sqrt(sumaUsuario) * Math.sqrt(sumaProducto));
        return ret;
    }

    /**
     * Calcula la similitud o recomendación de un usuario (con su perfil), con un
     * restaurante (su perfil de producto), y la ponderacion de ese usuario
     * con la similitud del coseno
     * @param perfilUsuario int[] vector del perfil del usuario
     * @param perfilProducto int[] vector del perfil del producto
     * @param ponderacion double[] ponderación de los etiquetas del producto y
     * usuario
     * @return similitud entre el perfil de usuario y el perfil del producto
     */
    public static double calcularSimilitudCosPonderacion(int[] perfilUsuario, int[] perfilProducto, double[] ponderacion) {
        double ret = 0.0;
        double multiplicacion = 0;
        int sumaUsuario = 0;
        int sumaProducto = 0;
        if(ponderacion == null){
            return 0.0;
        }
        for (int i = 0; i < perfilUsuario.length; i++) {
            multiplicacion += perfilUsuario[i] * perfilProducto[i] * ponderacion[i];
            sumaUsuario += perfilUsuario[i];
            sumaProducto += perfilProducto[i];
        }
        ret = multiplicacion / (Math.sqrt(sumaUsuario) * Math.sqrt(sumaProducto));

        if (new Double(ret).isNaN()) {
            return 0.0;
        }
        return ret;
    }

}
