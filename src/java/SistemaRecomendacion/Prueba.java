/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaRecomendacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author alex
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //La key del mapa representa la id del restaurante, mientras el set representa las ids de las etiquetas del restaurantes
        Map<Integer, Set<Integer>> restaurantesEtiquetas = new HashMap<>();
        Set<Integer> etiquetasPrimerRestaurante = new HashSet<>();
        etiquetasPrimerRestaurante.add(0);
        etiquetasPrimerRestaurante.add(2);
        //El restaurante con la id 1 tienes las etiquetas con la ids 0 y 2
        restaurantesEtiquetas.put(1, etiquetasPrimerRestaurante);
        Set<Integer> etiquetasSegundoRestaurante = new HashSet<>();
        etiquetasSegundoRestaurante.add(6);
        etiquetasSegundoRestaurante.add(2);
        //El restaurante con la id 12 tienes las etiquetas con la ids 6 y 2
        restaurantesEtiquetas.put(12, etiquetasSegundoRestaurante);
        
        TablaBooleana tablaBooleana = new TablaBooleana(restaurantesEtiquetas);
        
        //La key representa la id del usuario, y el value es un map, en el cual la id representa el producto y el value su valoración del usuario
        Map<Integer, Map<Integer, Integer>> valoracionesUsuariosProductos = new HashMap<>();
        Map<Integer, Integer> productos= new HashMap<>();
        productos.put(1, 5);
        productos.put(12, 3);
        //En este caso el usuario con id 123, valora el restaurante con la id 1 con un 5 y el restaurante de id 12 con un 3
        valoracionesUsuariosProductos.put(123, productos);
        Map<Integer, Integer> productos2= new HashMap<>();
        productos2.put(1, 1);
        //En este caso el usuario con id 2 valora el restaurante con la id 1 con un 1
        valoracionesUsuariosProductos.put(2, productos2);
        
        TablaValoracionesUsuarios tablaValoraciones = new TablaValoracionesUsuarios(valoracionesUsuariosProductos);
        
        //Recupero los productos valorados de cada usuario, la key es el id del usuario y el value es un array list de ids de los restaurantes
        HashMap<Integer, ArrayList<Integer>> productosValoradosByUsuario = tablaValoraciones.getProductosValoradosByUsuario();
        //Ahora creo los perfiles de los usuarios dados sus productos valorados, la key es la id del usuario y el array es el vector del perfil de usuario
        HashMap<Integer, int[]> perfilesUsuarios = tablaBooleana.getPerfilesUsuarios(productosValoradosByUsuario);
        int a = 1;
    }
    
}
