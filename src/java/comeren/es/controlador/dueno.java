/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comeren.es.controlador;

import DAO.RestauranteDAO;
import Entidades.Restaurante;
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
public class dueno extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        RequestDispatcher rd = null;
        RestauranteDAO restauranteDao = new RestauranteDAO();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(request.getParameter("editar") != null){
            Restaurante restauranteById = restauranteDao.getRestauranteById(Integer.parseInt(request.getParameter("editar")));
            if(restauranteById == null){
                request.setAttribute("error", "No se podido encontrar el restaurante en la base de datos!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("restaurante", restauranteById);
                rd = request.getRequestDispatcher("/editarRestaurante.jsp");
                rd.forward(request, response);
            }
        } else if(request.getParameter("ocultar") != null){
            int ocultarRestaurante = restauranteDao.ocultarRestaurante(Integer.parseInt(request.getParameter("ocultar")));
            if(ocultarRestaurante == 0){
                request.setAttribute("error", "No se pudo ocultar el restaurante de la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            } else {
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantesbyIdDueño(usuario.getIdUsuario());
                session.setAttribute("restaurantes", restaurantes);
                rd = request.getRequestDispatcher("/dueño.jsp");
                rd.forward(request, response);
            }
        } else if(request.getParameter("eliminar") != null){
            int borrarRestaurante = restauranteDao.borrarRestaurante(Integer.parseInt(request.getParameter("eliminar")));
            if(borrarRestaurante == 0){
                request.setAttribute("error", "No se pudo borrar el restaurante de la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            } else {
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantesbyIdDueño(usuario.getIdUsuario());
                session.setAttribute("restaurantes", restaurantes);
                rd = request.getRequestDispatcher("/dueño.jsp");
                rd.forward(request, response);
            }
        } else if(request.getParameter("mostrar") != null){
            int ocultarRestaurante = restauranteDao.mostrarRestaurante(Integer.parseInt(request.getParameter("mostrar")));
            if(ocultarRestaurante == 0){
                request.setAttribute("error", "No se pudo mostrar el restaurante de la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            } else {
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantesbyIdDueño(usuario.getIdUsuario());
                session.setAttribute("restaurantes", restaurantes);
                rd = request.getRequestDispatcher("/dueño.jsp");
                rd.forward(request, response);
            }
        }else{
            ArrayList<Restaurante> restaurantesbyIdDueño = restauranteDao.getRestaurantesbyIdDueño(usuario.getIdUsuario());
            restauranteDao.cerrarConexion();
            session.setAttribute("restaurantes", restaurantesbyIdDueño);
            rd = request.getRequestDispatcher("/dueño.jsp");
            rd.forward(request, response);
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
