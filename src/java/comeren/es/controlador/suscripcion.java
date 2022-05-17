/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comeren.es.controlador;

import DAO.SuscripcionDAO;
import Entidades.Suscripcion;
import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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
public class suscripcion extends HttpServlet {

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
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        SuscripcionDAO suscripcionDao = new SuscripcionDAO();
        if (request.getParameter("comprar") != null) {
            int idSuscripcion = Integer.parseInt(request.getParameter("comprar"));
            suscripcionDao.comprarSuscripcion(idSuscripcion, usuario.getIdUsuario());
        } else if(request.getParameter("baja") != null){
            int idSuscripcion = Integer.parseInt(request.getParameter("baja"));
            suscripcionDao.anularSuscripcion(idSuscripcion, usuario.getIdUsuario());
        }
        ArrayList<Suscripcion> suscripciones = suscripcionDao.getSuscripciones();
        ArrayList<Suscripcion> suscripcionesByIdUsuario = suscripcionDao.getSuscripcionesByIdUsuario(usuario.getIdUsuario());
        request.setAttribute("suscripciones", suscripciones);
        request.setAttribute("suscripciones_usuario", suscripcionesByIdUsuario);
        suscripcionDao.cerrarConexion();
        rd = request.getRequestDispatcher("/suscripciones.jsp");
        rd.forward(request, response);
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
