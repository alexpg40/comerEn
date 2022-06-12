/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comeren.es.controlador;

import DAO.ComentarioDAO;
import DAO.EtiquetaDAO;
import DAO.FotografiaDAO;
import DAO.RestauranteDAO;
import DAO.RolDAO;
import DAO.UbicacionDAO;
import DAO.UsuarioDAO;
import Entidades.Comentario;
import Entidades.Etiqueta;
import Entidades.Fotografia;
import Entidades.Restaurante;
import Entidades.Rol;
import Entidades.Ubicacion;
import Entidades.Usuario;
import Utilidades.Correos;
import Utilidades.GeneradorContraseñas;
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
        
        if (session.isNew()) {
            RestauranteDAO restauranteDao = new RestauranteDAO();
            ArrayList<Restaurante> restaurantesPopulares = restauranteDao.getRestaurantesPopulares();
            request.setAttribute("restaurantes", restaurantesPopulares);
            restauranteDao.cerrarConexion();
            request.setAttribute("restaurantes", restaurantesPopulares);
            rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        } else if (request.getParameter("opcion") != null) {
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
                Usuario usuario = (Usuario) session.getAttribute("usuario_editar");
                if (Utilidades.validarUsuario(nombre, apellido, correo)) {
                    usuarioDao.actualizarUsuario(new Usuario(usuario.getIdUsuario(), nombre, apellido, correo));
                    rd = request.getRequestDispatcher("/adminUsuarios.jsp");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("error", "Datos para actualizar el usuario no son validos!");
                    rd = request.getRequestDispatcher("administrador?editarUsuario=" + usuario.getIdUsuario());
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
            ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantesAdmin(request.getParameter("buscador"));
            session.setAttribute("restaurantes", restaurantes);
            restauranteDao.cerrarConexion();
            session.setAttribute("paramBusqueda", request.getParameter("buscador"));
            rd = request.getRequestDispatcher("/listaRestaurantesAdmin.jsp");
            rd.forward(request, response);

        } else if (request.getParameter("editarRestaurante") != null) {
            String restaurante = request.getParameter("editarRestaurante");
            RestauranteDAO restauranteDao = new RestauranteDAO();
            EtiquetaDAO etiquetaDao = new EtiquetaDAO();
            ComentarioDAO comentarioDao = new ComentarioDAO();
            UbicacionDAO ubicacionDao = new UbicacionDAO();
            FotografiaDAO fotografiaDao = new FotografiaDAO();
            ArrayList<Etiqueta> etiquitasByIdRestaurante = etiquetaDao.getEtiquitasByIdRestaurante(Integer.parseInt(restaurante));
            ArrayList<Etiqueta> etiquitasFaltantesByidRestaurante = etiquetaDao.getEtiquitasFaltantesByidRestaurante(Integer.parseInt(restaurante));
            ArrayList<Comentario> comentariosByIdRestaurante = comentarioDao.getComentariosByIdRestaurante(Integer.parseInt(restaurante));
            Ubicacion ubicacion = ubicacionDao.getUbicacionByIdRestaurante(Integer.parseInt(restaurante));
            ArrayList<Fotografia> fotografiasByIdRestaurante = fotografiaDao.getFotografiasByIdRestaurante(Integer.parseInt(restaurante));
            Restaurante restauranteById = restauranteDao.getRestauranteById(Integer.parseInt(restaurante));
            int valoracionMediaRestaurante = restauranteDao.valoracionMediaRestaurante(Integer.parseInt(restaurante));
            comentarioDao.cerrarConexion();
            fotografiaDao.cerrarConexion();
            etiquetaDao.cerrarConexion();
            restauranteDao.cerrarConexion();
            request.setAttribute("etiquetasFaltantes", etiquitasFaltantesByidRestaurante);
            request.setAttribute("valoracion", valoracionMediaRestaurante);
            request.setAttribute("restaurante", restauranteById);
            request.setAttribute("ubicacion", ubicacion);
            request.setAttribute("comentarios", comentariosByIdRestaurante);
            request.setAttribute("fotografias", fotografiasByIdRestaurante);
            request.setAttribute("etiquetas", etiquitasByIdRestaurante);
            rd = request.getRequestDispatcher("/editarRestaurante.jsp");
            rd.forward(request, response);
        } else if (request.getParameter("eliminarRestaurante") != null) {
            RestauranteDAO restauranteDao = new RestauranteDAO();
            int borrarRestaurante = restauranteDao.borrarRestaurante(Integer.parseInt(request.getParameter("eliminarRestaurante")));
            if (borrarRestaurante == 0) {
                request.setAttribute("error", "No se pudo borrar el restaurante de la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            } else {
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantesAdmin((String) session.getAttribute("paramBusqueda"));
                session.setAttribute("restaurantes", restaurantes);
                rd = request.getRequestDispatcher("/listaRestaurantesAdmin.jsp");
                rd.forward(request, response);
            }
            restauranteDao.cerrarConexion();
        } else if (request.getParameter("ocultarRestaurante") != null) {
            RestauranteDAO restauranteDao = new RestauranteDAO();
            int ocultarRestaurante = restauranteDao.ocultarRestaurante(Integer.parseInt(request.getParameter("ocultarRestaurante")));
            if (ocultarRestaurante == 0) {
                request.setAttribute("error", "No se pudo ocultar el restaurante de la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            } else {
                System.out.println("param de busqueda ->" + (String) session.getAttribute("paramBusqueda"));
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantesAdmin((String) session.getAttribute("paramBusqueda"));
                restauranteDao.cerrarConexion();
                session.setAttribute("restaurantes", restaurantes);
                rd = request.getRequestDispatcher("/listaRestaurantesAdmin.jsp");
                rd.forward(request, response);
            }
        } else if (request.getParameter("mostrarRestaurante") != null) {
            RestauranteDAO restauranteDao = new RestauranteDAO();
            int ocultarRestaurante = restauranteDao.mostrarRestaurante(Integer.parseInt(request.getParameter("mostrarRestaurante")));
            if (ocultarRestaurante == 0) {
                request.setAttribute("error", "No se pudo mostrar el restaurante de la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            } else {
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantesAdmin((String) session.getAttribute("paramBusqueda"));
                session.setAttribute("restaurantes", restaurantes);
                rd = request.getRequestDispatcher("/listaRestaurantesAdmin.jsp");
                rd.forward(request, response);
            }
            restauranteDao.cerrarConexion();
        } else if (request.getParameter("crear_restaurante") != null) {
            Integer admin = (Integer) session.getAttribute("idUsuario");
            String nombreRestaurante = (String) request.getParameter("nombreRestaurante");
            String nombreDueno = (String) request.getParameter("nombreDueno");
            String apellidoDueno = (String) request.getParameter("correoDueno");
            String correoDueno = (String) request.getParameter("correoDueno");
            String contraseña = GeneradorContraseñas.getPassword();
            UsuarioDAO usuarioDao = new UsuarioDAO();
            RestauranteDAO restauranteDao = new RestauranteDAO();
            usuarioDao.registrarUsuario(new Usuario(nombreDueno, apellidoDueno, correoDueno, contraseña));
            int idUsuario = usuarioDao.existeUsuario(correoDueno, Utilidades.convertirSHA256(contraseña));
            if (idUsuario != 0) {
                restauranteDao.darAltaRestaurante(nombreRestaurante, idUsuario, admin);
                RolDAO rolDao = new RolDAO();
                rolDao.submitRolUsuario(idUsuario, 2);
                rolDao.cerrarConexion();
                Correos correos = new Correos();
                correos.sendEmail("Alta Dueño comerEn", "Has sido dado de alta en ComerEn con la contraseña " + contraseña, correoDueno);
                rd = request.getRequestDispatcher("/adminRestaurantes.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("error", "No se pudo crear el usuario en la base de datos correctamente!");
                rd = request.getRequestDispatcher("/paginaError.jsp");
                rd.forward(request, response);
            }
            usuarioDao.cerrarConexion();
        } else if (request.getParameter("quitarEtiqueta") != null) {
            EtiquetaDAO etiquetaDao = new EtiquetaDAO();
            etiquetaDao.borrarEtiquetaByIdRestaurante(Integer.parseInt((request.getParameter("etiqueta"))), Integer.parseInt((request.getParameter("idRestaurante"))));
            etiquetaDao.cerrarConexion();
            rd = request.getRequestDispatcher("administrador?editarRestaurante=" + Integer.parseInt((request.getParameter("idRestaurante"))));
            rd.forward(request, response);
        } else if (request.getParameter("anadirEtiqueta") != null) {
            EtiquetaDAO etiquetaDao = new EtiquetaDAO();
            etiquetaDao.insertarEtiquetaByIdRestaurante(Integer.parseInt((request.getParameter("etiqueta"))), Integer.parseInt((request.getParameter("idRestaurante"))));
            etiquetaDao.cerrarConexion();
            rd = request.getRequestDispatcher("administrador?editarRestaurante=" + Integer.parseInt((request.getParameter("idRestaurante"))));
            rd.forward(request, response);
        } else if (request.getParameter("cambiar_descripcion") != null) {
            RestauranteDAO restauranteDao = new RestauranteDAO();
            restauranteDao.cambiarDescripcion(Integer.parseInt(request.getParameter("idRestaurante")), request.getParameter("descripcion"));
            restauranteDao.cerrarConexion();
            rd = request.getRequestDispatcher("administrador?editarRestaurante=" + Integer.parseInt((request.getParameter("idRestaurante"))));
            rd.forward(request, response);
        } else if (request.getParameter("actualizarUbicacion") != null) {
            String[] split = request.getParameter("actualizarUbicacion").split(";");
            UbicacionDAO ubicacionDao = new UbicacionDAO();
            if(ubicacionDao.restauranteTieneUbicacion(Integer.parseInt(request.getParameter("idRestaurante"))) != 0){
                ubicacionDao.cambiarUbicacionByIdRestaurante(Integer.parseInt(request.getParameter("idRestaurante")), Float.parseFloat(split[0]), Float.parseFloat(split[1]));
            } else {
                ubicacionDao.insertarUbicacion(Integer.parseInt(request.getParameter("idRestaurante")), Float.parseFloat(split[0]), Float.parseFloat(split[1]));
            }
            ubicacionDao.cerrarConexion();
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
