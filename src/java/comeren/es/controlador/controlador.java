/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comeren.es.controlador;

import DAO.RestauranteDAO;
import DAO.RolDAO;
import DAO.UsuarioDAO;
import Entidades.Restaurante;
import Entidades.Rol;
import Entidades.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex
 */
public class controlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        RequestDispatcher rd = null;
        if(session.isNew()){
            rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        } else {
            String opcion = request.getParameter("opcion");
            String buscador = request.getParameter("buscador");
            String iniciarSesion = request.getParameter("iniciarSesion");
            String registro = request.getParameter("registrar");
            if(opcion != null){
                if("index".equals(opcion)){ 
                    rd = request.getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                } else if ("session".equals(opcion)){
                    if(session.getAttribute("roles") != null){
                        rd = request.getRequestDispatcher("/cuenta.jsp");
                        rd.forward(request, response);
                    } else {
                        rd = request.getRequestDispatcher("/login.jsp");
                        rd.forward(request, response);
                    }
                }
            } else if (buscador != null && !buscador.equals("")){
                RestauranteDAO restauranteDao = new RestauranteDAO();
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantes(buscador);
                restauranteDao.cerrarConexion();
                request.setAttribute("listaRestaurante", restaurantes);
                rd = request.getRequestDispatcher("/listaRestaurantes.jsp");
                rd.forward(request, response);
            } else if(iniciarSesion != null){
                UsuarioDAO usuarioDao = new UsuarioDAO();
                String correo = request.getParameter("correo");
                String contraseña = request.getParameter("contrasena");
                int idUsuario = usuarioDao.existeUsuario(correo, contraseña);
                if(idUsuario != 0){
                    RolDAO rolDao = new RolDAO();
                    Usuario usuario = usuarioDao.getUsuarioByidUsuario(idUsuario);
                    session.setAttribute("usuario", usuario);
                    ArrayList<Rol> rolesUsuario = rolDao.obtenerRolesUsuario(idUsuario);
                    session.setAttribute("roles", rolesUsuario);
                    rd = request.getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("usuario_no_existe", "usuario_no_existe");
                    rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                }
                usuarioDao.cerrarConexion();
            } else if(registro != null) {
                UsuarioDAO usuarioDao = new UsuarioDAO();
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String correo = request.getParameter("correo");
                String contrasena = request.getParameter("contrasena");
                usuarioDao.registrarUsuario(new Usuario(nombre, apellido, correo, contrasena));
                usuarioDao.cerrarConexion();
                rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            } else {
                rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            } 
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
