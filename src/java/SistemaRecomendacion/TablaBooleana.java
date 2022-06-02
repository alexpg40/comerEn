/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaRecomendacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author alex
 */
public class TablaBooleana {
    
    //Set con las ids de las etiquetas o caracteristicas del producto
    private Set<Integer> etiquetas = new HashSet<>();
    //Set con las ids de los productos
    private Set<Integer> producto = new HashSet<>();
    /* 
    Los perfiles de los productos, son un vector de Rn, siendo n el numero de 
    etiquetas de todos los productos, siendo el valor de cada componente 1 o 0,
    1 para cuando el producto contiene la etiqueta, y 0 para cuando el 
    producto NO contiene la etíqueta.
    
    La key del map representa la id del producto, el array de int es el vector 
    del producto.
    */
    private Map<Integer, int[]> perfilesProductos = new HashMap<>();
    
    public TablaBooleana(Map<Integer, Set<Integer>> etiquetasProducto){
        //Recupero las etiquetas y las guardo en su atributo
        Collection<Set<Integer>> etiquetasSets = etiquetasProducto.values();
        Iterator<Set<Integer>> iterator = etiquetasSets.iterator();
        while(iterator.hasNext()){
            etiquetas.addAll(iterator.next());
        }
        //Guardo las ids de los productos
        producto = etiquetasProducto.keySet();
        Iterator<Integer> iteratorProducto = producto.iterator();
        //Por cado producto genero su perfil y lo añado al atributo de perfilesProducto
        while(iteratorProducto.hasNext()){
            int idProducto = iteratorProducto.next();
            perfilesProductos.put(idProducto, this.generarPerfilProducto(idProducto, etiquetasProducto));
        }
    }
    
    /**
     * Esta función genera un perfil de producto dado su id y las etiquetas
     * @param idProducto
     * @param etiquetasProducto
     * @return perfil del producto
     */
    private int[] generarPerfilProducto(int idProducto, Map<Integer, Set<Integer>> etiquetasProducto){
        int[] perfilProducto = new int[etiquetas.size()];
        Iterator<Integer> iteratorEtiqueta = etiquetas.iterator();
        int i = 0;
        while(iteratorEtiqueta.hasNext()){
           if(etiquetasProducto.get(idProducto).contains(iteratorEtiqueta.next())) {
               perfilProducto[i] = 1;
           } else {
               perfilProducto[i] = 0;
           }
           i++;
        }
        return perfilProducto;
    }
    
    /**
     * Esta función crea el perfil de usuario dado las ids de los productos.
     * El perfil de usuario es la suma de los vectores de los productos 
     * valorados por el usuario.
     * @param idProductos array con las ids de los productos que el usuario
     * ha valorado
     * @return perfil de usuario
     */
    public int[] getPerfilUsuario(int[] idProductos){
        int[] ret = new int[this.etiquetas.size()];
        for(int i = 0; i< idProductos.length; i++){
            int[] perfilProducto = this.perfilesProductos.get(idProductos[i]);
            for(int j = 0; j < etiquetas.size(); j++){
                ret[j] = ret[j] + perfilProducto[j];
            }
        }
        return ret;
    }
    
    public int[] getPerfilUsuario(ArrayList<Integer> idProductos){
        int[] ret = new int[this.etiquetas.size()];
        for(int i = 0; i< idProductos.size(); i++){
            int[] perfilProducto = this.perfilesProductos.get(idProductos.get(i));
            for(int j = 0; j < etiquetas.size(); j++){
                ret[j] = ret[j] + perfilProducto[j];
            }
        }
        return ret;
    }
    
    /**
     * Esta funcion devuelve los perfiles de todos los usuarios, dado un 
     * HashMap con su la id del usuario y los productos valorados
     * @param productosValorados HashMap donde la key es el idUsuario, y
     * el value es un ArrayList de ids con los productos valorados
     * @return HashMap de key con idUsuario y el value es el perfil de 
     * usuario
     */
    public HashMap<Integer, int[]> getPerfilesUsuarios(HashMap<Integer, ArrayList<Integer>> productosValorados){
        HashMap<Integer, int[]> ret = new HashMap<>();
        Iterator<Integer> iterator = productosValorados.keySet().iterator();
        while(iterator.hasNext()){
            Integer idUsuario = iterator.next();
            int[] perfilUsuario = this.getPerfilUsuario(productosValorados.get(idUsuario).stream().mapToInt(i -> i).toArray());
            ret.put(idUsuario, perfilUsuario);
        }
        return ret;
    }
    
    public Map<Integer, int[]> getPerfilesProducto(){
        return this.perfilesProductos;
    }
    
    public int[] getPerfilProducto(int idProducto){
        return this.perfilesProductos.get(idProducto);
    }
    
    public Set<Integer> getEtiquetas(){
        return this.etiquetas;
    }
    
}
