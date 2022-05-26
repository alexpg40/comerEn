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
import Utilidades.Utilidades;
import com.google.gson.Gson;
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
public class administrador extends HttpServlet {

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
        if (request.getParameter("opcion") != null) {
            if (request.getParameter("opcion").equals("usuarios")) {
                rd = request.getRequestDispatcher("/adminUsuarios.jsp");
                rd.forward(request, response);
            } else if (request.getParameter("opcion").equals("restaurantes")) {
                rd = request.getRequestDispatcher("/adminRestaurantes.jsp");
                rd.forward(request, response);
            }
        } else if (request.getParameter("buscarUsuarios") != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            UsuarioDAO usuarioDao = new UsuarioDAO();
            ArrayList<Usuario> usuariosNotAdmin = usuarioDao.getUsuariosNotAdmin();
            usuarioDao.cerrarConexion();
            String json = new Gson().toJson(usuariosNotAdmin);
            response.getWriter().write(json);
        } else if (request.getParameter("buscarUsuario") != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            UsuarioDAO usuarioDao = new UsuarioDAO();
            ArrayList<Usuario> usuariosNotAdmin = usuarioDao.getUsuariosByNombre(request.getParameter("buscarUsuario"));
            usuarioDao.cerrarConexion();
            String json = new Gson().toJson(usuariosNotAdmin);
            response.getWriter().write(json);
        } else if (request.getParameter("editarUsuario") != null) {
            UsuarioDAO usuarioDao = new UsuarioDAO();
            Usuario usuarioByidUsuario = usuarioDao.getUsuarioByidUsuario(Integer.parseInt(request.getParameter("editarUsuario")));
            usuarioDao.cerrarConexion();
            session.setAttribute("usuario_editar", usuarioByidUsuario);
            RolDAO rolDao = new RolDAO();
            ArrayList<Rol> rolesFaltantes = rolDao.getRolesFaltantes(usuarioByidUsuario.getIdUsuario());
            ArrayList<Rol> obtenerRolesUsuario = rolDao.obtenerRolesUsuario(usuarioByidUsuario.getIdUsuario());
            rolDao.cerrarConexion();
            request.setAttribute("rolesFaltantes", rolesFaltantes);
            request.setAttribute("rolesUsuario", obtenerRolesUsuario);
            rd = request.getRequestDispatcher("/editarUsuario.jsp");
            rd.forward(request, response);
        } else if (request.getParameter("eliminarUsuario") != null) {
            UsuarioDAO usuarioDao = new UsuarioDAO();
            int ret = usuarioDao.borrarUsuario(Integer.parseInt(request.getParameter("eliminarUsuario")));
            if (ret == 0) {
                request.setAttribute("error", "No se pudo borrar el usuario de la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            }
            rd = request.getRequestDispatcher("/adminUsuarios.jsp");
            rd.forward(request, response);
        } else if (request.getParameter("quitarRol") != null) {
            RolDAO rolDao = new RolDAO();
            if (session.getAttribute("usuario_editar") != null) {
                Usuario usuarioEditar = (Usuario) session.getAttribute("usuario_editar");
                rolDao.deleteRolUsuario(usuarioEditar.getIdUsuario(), Integer.parseInt(request.getParameter("rol")));
                rd = request.getRequestDispatcher("/adminUsuarios.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("error", "No hay ningun usuario seleccionado para editar!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            }
        } else if (request.getParameter("insertarRol") != null) {
            RolDAO rolDao = new RolDAO();
            if (session.getAttribute("usuario_editar") != null) {
                Usuario usuarioEditar = (Usuario) session.getAttribute("usuario_editar");
                rolDao.submitRolUsuario(usuarioEditar.getIdUsuario(), Integer.parseInt(request.getParameter("rol")));
                rd = request.getRequestDispatcher("/adminUsuarios.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("error", "No hay ningun usuario seleccionado para editar!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            }
        } else if (request.getParameter("actualizarUsuario") != null) {
            if (session.getAttribute("usuario_editar") != null) {
                UsuarioDAO usuarioDao = new UsuarioDAO();
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String correo = request.getParameter("correo");
                if (Utilidades.validarUsuario(nombre, apellido, correo)) {
                    Usuario usuario = (Usuario) session.getAttribute("usuario_editar");
                    usuarioDao.actualizarUsuario(new Usuario(usuario.getIdUsuario(), nombre, apellido, correo));
                    rd = request.getRequestDispatcher("/adminUsuarios.jsp");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("error", "Datos para actualizar el usuario no son validos!");
                    rd = request.getRequestDispatcher("/paginaError.jsp");
                    rd.forward(request, response);
                }
                usuarioDao.cerrarConexion();
            } else {
                request.setAttribute("error", "No hay ningun usuario seleccionado para editar!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            }
        } else if (request.getParameter("buscarRestaurante") != null) {
            RestauranteDAO restauranteDao = new RestauranteDAO();
            ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantes(request.getParameter("buscador"));
            session.setAttribute("restaurantes", restaurantes);
            restauranteDao.cerrarConexion();
            session.setAttribute("paramBusqueda", request.getParameter("buscador"));
            rd = request.getRequestDispatcher("/listaRestaurantesAdmin.jsp");
            rd.forward(request, response);

        } else if (request.getParameter("editarRestaurante") != null) {
            RestauranteDAO restauranteDao = new RestauranteDAO();
            Restaurante restaurante = restauranteDao.getRestauranteById(Integer.parseInt(request.getParameter("editarRestaurante")));
            restauranteDao.cerrarConexion();
            request.setAttribute("restaurante", restaurante);
            rd = request.getRequestDispatcher("/editarRestaurante.jsp");
            rd.forward(request, response);
        } else if (request.getParameter("eliminarRestaurante") != null) {
            RestauranteDAO restauranteDao = new RestauranteDAO();
            int borrarRestaurante = restauranteDao.borrarRestaurante(Integer.parseInt(request.getParameter("eliminarRestaurante")));
            if(borrarRestaurante == 0){
                request.setAttribute("error", "No se pudo borrar el restaurante de la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            } else {
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantes((String)session.getAttribute("paramBusqueda"));
                session.setAttribute("restaurantes", restaurantes);
                rd = request.getRequestDispatcher("/listaRestaurantesAdmin.jsp");
                rd.forward(request, response);
            }
            restauranteDao.cerrarConexion();
        } else if(request.getParameter("ocultarRestaurante") != null){
            RestauranteDAO restauranteDao = new RestauranteDAO();
            int ocultarRestaurante = restauranteDao.ocultarRestaurante(Integer.parseInt(request.getParameter("ocultarRestaurante")));
            if(ocultarRestaurante == 0){
                request.setAttribute("error", "No se pudo ocultar el restaurante de la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            } else {
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantes((String)session.getAttribute("paramBusqueda"));
                restauranteDao.cerrarConexion();
                session.setAttribute("restaurantes", restaurantes);
                rd = request.getRequestDispatcher("/listaRestaurantesAdmin.jsp");
                rd.forward(request, response);
            }
        } else if(request.getParameter("mostrarRestaurante") != null){
            RestauranteDAO restauranteDao = new RestauranteDAO();
            int ocultarRestaurante = restauranteDao.mostrarRestaurante(Integer.parseInt(request.getParameter("mostrarRestaurante")));
            if(ocultarRestaurante == 0){
                request.setAttribute("error", "No se pudo mostrar el restaurante de la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            } else {
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantes((String)session.getAttribute("paramBusqueda"));
                session.setAttribute("restaurantes", restaurantes);
                rd = request.getRequestDispatcher("/listaRestaurantesAdmin.jsp");
                rd.forward(request, response);
            }
            restauranteDao.cerrarConexion();
        } else if(request.getParameter("crear_restaurante") != null){
        
        } else {
            rd = request.getRequestDispatcher("/adminRestaurantes.jsp");
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
