/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaRecomendacion;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Alex
 */
public class TablaPerfilesUsuario {
    
    private Set<Integer> etiquetas;
    private Set<Integer> usuarios;
    private HashMap<Integer, int[]> perfilesUsuarios;
    private int[] IUF;
    private int[] vectorPonderacion;
    
    public TablaPerfilesUsuario(Set<Integer> etiquetas, Set<Integer> usuarios, HashMap<Integer, int[]> perfilesUsuarios){
        this.etiquetas = etiquetas;
        this.usuarios = usuarios;
        this.perfilesUsuarios = perfilesUsuarios;
    }
    
    public void crearIUF(HashMap<Integer, Integer> productosValorados){
        int[] iuf = new int[etiquetas.size()];
        int nProductos = productosValorados.size();
        int nUsuarios = usuarios.size();
        for(int i = 0; i < nProductos; i++){
            
        }
        this.IUF = iuf;
    }
    
    public HashMap<Integer, Float> recomendacionProductosCoseno(){
        HashMap<Integer, Float> ret = new HashMap<>();
        
        return ret;
    }
    
}
