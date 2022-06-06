/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaRecomendacion;

import DAO.UsuarioDAO;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Alex
 */
public class TablaPonderaciones {
    
    //Set con las ids de las etiquetas o caracteristicas del producto
    private Set<Integer> etiquetas;
    //Ponderación de las etiquetas
    private double[] IUF;
    /**
     * Map donde la clave es la id del usuario, y el double[] es un vector
     * con la ponderación del usuario de cada etiqueta
     */
    private Map<Integer, double[]> ponderacionesUsuario = new HashMap<>();
    
    public TablaPonderaciones(HashMap<Integer, int[]> perfilesUsuarios, Set<Integer> etiquetas){
        this.etiquetas = etiquetas;
        UsuarioDAO usuarioDao = new UsuarioDAO();
        int usuariosTotales = usuarioDao.getNUsuariosComentado();
        this.IUF = new double[etiquetas.size()];
        int usuariosEtiquetas = 0;
        int idEtiquetas = 0;
        int i = 0;
        Iterator<Integer> iterator = etiquetas.iterator();
        
        //Calculo la ponderación de las etiquetas basadas en su frecuencia
        while(iterator.hasNext()){
            idEtiquetas = iterator.next();
            usuariosEtiquetas = usuarioDao.getNUsuariosComentadoByidEtiqueta(idEtiquetas);
            double e = new Double(usuariosTotales)/new Double(usuariosEtiquetas);
            this.IUF[i] = Math.log10(e);
            i++;
        }
        usuarioDao.cerrarConexion();
        Set<Integer> keySet = perfilesUsuarios.keySet();
        Iterator<Integer> iterator1 = keySet.iterator();
        int idUsuario = 0;
        double totalPonderacion = 0;
        int[] perfilUsuario;
        double[] ponderacionUsuario;
        //Calculo la ponderación del cada usuario
        while(iterator1.hasNext()){
            idUsuario = iterator1.next();
            perfilUsuario = perfilesUsuarios.get(idUsuario);
            ponderacionUsuario = new double[perfilUsuario.length];
            totalPonderacion = 0;
            for(int x = 0; x < this.etiquetas.size(); x++){
                ponderacionUsuario[x] = perfilUsuario[x] * IUF[x];
                totalPonderacion += ponderacionUsuario[x];
            }
            for(int j = 0; j < perfilUsuario.length; j++){
                ponderacionUsuario[j] = ponderacionUsuario[j] / totalPonderacion;
            }
            this.ponderacionesUsuario.put(idUsuario, ponderacionUsuario);
        }
    }
    
    public double[] getPonderacionUsuario(int idUsuario){
        return this.ponderacionesUsuario.get(idUsuario);
    }
    
}
