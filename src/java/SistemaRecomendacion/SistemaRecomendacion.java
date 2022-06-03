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
        
        RestauranteDAO restauranteDao = new RestauranteDAO();
        ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantes("");
        restauranteDao.cerrarConexion();
        ArrayList<Restaurante> calcularRecomendacionRestaurantes = calcularRecomendacionRestaurantes(restaurantes, 7);
        int a = 0;

    }

    public static ArrayList<Restaurante> calcularRecomendacionRestaurantes(ArrayList<Restaurante> restaurantes, int idUsuario){
        
        Map<Integer, Double> calcularRecomendacion = calcularRecomendacion(restaurantes, idUsuario);

        Iterator<Integer> iterator = calcularRecomendacion.keySet().iterator();
        
        while(iterator.hasNext()){
            int i = iterator.next();
            System.out.println(i + " - " + calcularRecomendacion.get(i));
        }
        
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
    
    
    public static Map<Integer, Double> calcularRecomendacion(ArrayList<Restaurante> restaurantes, int idUsuario) {
        Map<Integer, Double> ret = new HashMap<>();
        
        TablaBooleana tablaBooleana = crearTablaBooleana(restaurantes);
        TablaValoracionesUsuarios tablaValoraciones = crearTablaValoraciones(restaurantes);
        HashMap<Integer, ArrayList<Integer>> productosValoradosByUsuario = tablaValoraciones.getProductosValoradosByUsuario();
        HashMap<Integer, int[]> perfilesUsuarios = tablaBooleana.getPerfilesUsuarios(productosValoradosByUsuario);
        Set<Integer> etiquetas = tablaBooleana.getEtiquetas();

        ArrayList<Integer> productosNoValoradosByUsuario = tablaValoraciones.getProductosNoValoradosByUsuario(idUsuario);
        ArrayList<Integer> productosValorados = tablaValoraciones.getProductosValoradosByUsuario(idUsuario);
        int[] perfilUsuario = tablaBooleana.getPerfilUsuario(productosValorados);

        TablaPonderaciones tablaPonderaciones = new TablaPonderaciones(perfilesUsuarios, etiquetas);

        double[] ponderacionUsuario = tablaPonderaciones.getPonderacionUsuario(idUsuario);

        double calcularSimilitudCosPonderacion = 0;

        for (Integer i : productosNoValoradosByUsuario) {
            int[] perfilProducto = tablaBooleana.getPerfilProducto(i);
            calcularSimilitudCosPonderacion = calcularSimilitudCosPonderacion(perfilUsuario, perfilProducto, ponderacionUsuario);
            ret.put(i, calcularSimilitudCosPonderacion);
        }

        return ret;
    }

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
