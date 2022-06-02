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
public class TablaValoracionesUsuarios {
 
    //Set con las ids de los usuarios
    private Set<Integer> usuarios = new HashSet<>();
    //Set con las ids de los productos
    private Set<Integer> productos = new HashSet<>();
    /*
    HashMap en el que la key son las ids de los usuarios, y el value
    es la valoracion de todos los productos, pudiendo ser un número 
    entero entre el 1 y el 5, o null.
    
    Null indica que el producto no ha sido valorado por el usuario
    */
    private Map<Integer, Integer[]> valoracionesUsuario = new HashMap<>();
    
    public TablaValoracionesUsuarios(Map<Integer, Map<Integer, Integer>> usuarioValoracionProductos){
        this.usuarios = usuarioValoracionProductos.keySet();
        Collection<Map<Integer, Integer>> productos = usuarioValoracionProductos.values();
        Iterator<Map<Integer, Integer>> iteratorProductos = productos.iterator();
        while(iteratorProductos.hasNext()){
            Map<Integer, Integer> producto = iteratorProductos.next();
            this.productos.addAll(producto.keySet());
        }
        Iterator<Integer> iterator = usuarios.iterator();
        while(iterator.hasNext()){
            int idUsuario = iterator.next();
            valoracionesUsuario.put(idUsuario, generarValoracionUsuario(idUsuario, usuarioValoracionProductos));
        }
        
    }
    
    /**
     * Esta función crea la valoración de usuario de todos los productos
     * @param idUsuario
     * @param usuarioValoracionProductos
     * @return Integer[] con la valoración de usuario
     */
    private Integer[] generarValoracionUsuario(int idUsuario,Map<Integer, Map<Integer, Integer>> usuarioValoracionProductos){
        Integer[] valoracion = new Integer[productos.size()];
        Iterator<Integer> iterator = productos.iterator();
        int i = 0;
        while(iterator.hasNext()){
            int idProducto = iterator.next();
            if(usuarioValoracionProductos.get(idUsuario).containsKey(idProducto)){
                valoracion[i] = usuarioValoracionProductos.get(idUsuario).get(idProducto);
            } else {
                valoracion[i] = null;
            }
            i++;
        }
        return valoracion;
    }
    
    private Integer[] getValoracionesUsuario(int idUsuario){
        return this.valoracionesUsuario.get(idUsuario);
    }
    
    /**
     * Esta función consigue los productos valorados por un usuario
     * @param idUsuario
     * @return ArrayList<Integer> con los productos valorados
     * por el usuario
     */
    public ArrayList<Integer> getProductosValoradosByUsuario(int idUsuario){
        ArrayList<Integer> ret = new ArrayList<>();
        Integer[] valoraciones = this.getValoracionesUsuario(idUsuario);
        for(int i = 0; i < valoraciones.length; i++){
            if(valoraciones[i] != null){
                ret.add((Integer) productos.toArray()[i]);
            }
        }
        return ret;
    }
    
    
    public ArrayList<Integer> getProductosNoValoradosByUsuario(int idUsuario){
        ArrayList<Integer> ret = new ArrayList<>();
        Integer[] valoraciones = this.getValoracionesUsuario(idUsuario);
        for(int i = 0; i < valoraciones.length; i++){
            if(valoraciones[i] == null){
                ret.add((Integer) productos.toArray()[i]);
            }
        }
        return ret;
    }
    
    /**
     * Esta función recupera los productos valorados de todos los 
     * usuarios
     * @return HashMap<Integer, ArrayList<Integer>> donde la
     * key es la id del usuario, y value es valoración del usuario
     */
    public HashMap<Integer, ArrayList<Integer>> getProductosValoradosByUsuario(){
        HashMap<Integer, ArrayList<Integer>> ret = new HashMap<>();
        Object[] idUsuarios = this.usuarios.toArray();
        for(int i = 0; i < idUsuarios.length; i++){
            ArrayList<Integer> idsProductos = new ArrayList<>();
            Integer[] valoracionUsuario = this.valoracionesUsuario.get(idUsuarios[i]);
            for(int j = 0; j < valoracionUsuario.length; j++){
                if(valoracionUsuario[j] != null){
                    idsProductos.add((Integer) this.productos.toArray()[j]);
                }
            }
            ret.put((Integer) idUsuarios[i], idsProductos);
        }
        return ret;
    }
    
    public int getNUsuarios(){
        return this.usuarios.size();
    }
    
    public int getNUsuariosEtiqueta(int idEtiqueta){
        return 0;
    }
    
}
