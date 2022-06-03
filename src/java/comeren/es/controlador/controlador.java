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
import DAO.SuscripcionDAO;
import DAO.UbicacionDAO;
import DAO.UsuarioDAO;
import Entidades.Comentario;
import Entidades.Etiqueta;
import Entidades.Fotografia;
import Entidades.Restaurante;
import Entidades.Rol;
import Entidades.Suscripcion;
import Entidades.Ubicacion;
import Entidades.Usuario;
import SistemaRecomendacion.SistemaRecomendacion;
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

        
        //API
        if (request.getParameter("buscarRestaurantes") != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            RestauranteDAO restauranteDao = new RestauranteDAO();
            ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantes(request.getParameter("buscarRestaurantes"));
            if (session.getAttribute("usuario") != null) {
                SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantes, (int) session.getAttribute("idUsuario"));
            }
            restauranteDao.cerrarConexion();
            String json = new Gson().toJson(restaurantes);
            response.getWriter().write(json);
        } else if (request.getParameter("buscarEtiquetas") != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            EtiquetaDAO etiquetaDao = new EtiquetaDAO();
            ArrayList<Etiqueta> etiquitasByNombre = etiquetaDao.getEtiquitasByNombre(request.getParameter("buscarEtiquetas"));
            etiquetaDao.cerrarConexion();
            String json = new Gson().toJson(etiquitasByNombre);
            response.getWriter().write(json);
        } else if (request.getParameter("buscarRestaurantesCercanos") != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String[] split = request.getParameter("buscarRestaurantesCercanos").split("\\|");
            int radio = Integer.parseInt(request.getParameter("radio"));
            RestauranteDAO restauranteDao = new RestauranteDAO();
            ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantesCercanos(Double.parseDouble(split[0]), Double.parseDouble(split[1]), radio);
            if (session.getAttribute("usuario") != null) {
                SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantes, (int) session.getAttribute("idUsuario"));
            }
            restauranteDao.cerrarConexion();
            String json = new Gson().toJson(restaurantes);
            response.getWriter().write(json);
        } else if (request.getParameter("buscarLocalidades") != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            RestauranteDAO restauranteDao = new RestauranteDAO();
            ArrayList<String> localidadesRestaurantes = restauranteDao.getLocalidadesRestaurantes(request.getParameter("buscarLocalidades"));
            restauranteDao.cerrarConexion();
            String json = new Gson().toJson(localidadesRestaurantes);
            response.getWriter().write(json);
        } else if (request.getParameter("getRestaurantesPopulares") != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            RestauranteDAO restauranteDao = new RestauranteDAO();
            ArrayList<Restaurante> restaurantePopulares = restauranteDao.getRestaurantesPopulares();
            if (session.getAttribute("usuario") != null) {
                SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantePopulares, (int) session.getAttribute("idUsuario"));
            }
            restauranteDao.cerrarConexion();
            String json = new Gson().toJson(restaurantePopulares);
            response.getWriter().write(json);
        } else if (request.getParameter("filtrarRestaurantes") != null) {
            response.setContentType("application/json");
            String filtro = request.getParameter("valoracionMin");
            response.setCharacterEncoding("UTF-8");
            RestauranteDAO restauranteDao = new RestauranteDAO();
            ArrayList<Restaurante> restaurantes = null;
            if (request.getParameter("radio") != null) {
                String[] split = request.getParameter("filtrarRestaurantes").split("\\|");
                restaurantes = restauranteDao.getRestaurantesFiltrados(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Integer.parseInt(request.getParameter("radio")), request.getParameter("localidadFiltros"), Integer.parseInt(request.getParameter("valoracionMin")));
            } else {
                restaurantes = restauranteDao.getRestaurantesFiltrados(request.getParameter("localidadFiltros"), Integer.parseInt(request.getParameter("valoracionMin")));
            }
            if (session.getAttribute("usuario") != null) {
                SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantes, (int) session.getAttribute("idUsuario"));
            }
            restauranteDao.cerrarConexion();
            String json = new Gson().toJson(restaurantes);
            response.getWriter().write(json);
        }

        if (session.isNew()) {
            RestauranteDAO restauranteDao = new RestauranteDAO();
            ArrayList<Restaurante> restaurantesPopulares = restauranteDao.getRestaurantesPopulares();
            request.setAttribute("restaurantes", restaurantesPopulares);
            restauranteDao.cerrarConexion();
            request.setAttribute("restaurantes", restaurantesPopulares);
            rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        } else {
            String opcion = request.getParameter("opcion");
            String buscador = request.getParameter("buscador");
            String restaurante = request.getParameter("restaurante");
            String iniciarSesion = request.getParameter("iniciarSesion");
            String registro = request.getParameter("registrar");
            String actualizarCuenta = request.getParameter("actualizarCuenta");
            if (opcion != null) {
                if ("index".equals(opcion)) {
                    RestauranteDAO restauranteDao = new RestauranteDAO();
                    ArrayList<Restaurante> restaurantesPopulares = restauranteDao.getRestaurantesPopulares();
                    if (session.getAttribute("usuario") != null) {
                        SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantesPopulares, (int) session.getAttribute("idUsuario"));
                    }
                    request.setAttribute("restaurantes", restaurantesPopulares);
                    restauranteDao.cerrarConexion();
                    rd = request.getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                } else if ("session".equals(opcion)) {
                    if (session.getAttribute("usuario") != null) {
                        rd = request.getRequestDispatcher("/cuenta.jsp");
                        rd.forward(request, response);
                    } else {
                        rd = request.getRequestDispatcher("/login.jsp");
                        rd.forward(request, response);
                    }
                } else if ("logout".equals(opcion)) {
                    RestauranteDAO restauranteDao = new RestauranteDAO();
                    ArrayList<Restaurante> restaurantesPopulares = restauranteDao.getRestaurantesPopulares();
                    request.setAttribute("restaurantes", restaurantesPopulares);
                    if (session.getAttribute("usuario") != null) {
                        session.invalidate();
                        rd = request.getRequestDispatcher("/index.jsp");
                        rd.forward(request, response);
                    }
                } else if ("suscripcion".equals(opcion)) {
                    SuscripcionDAO suscripcionDao = new SuscripcionDAO();
                    ArrayList<Suscripcion> suscripciones = suscripcionDao.getSuscripciones();
                    Usuario usuario = (Usuario) session.getAttribute("usuario");
                    ArrayList<Suscripcion> suscripcionesByIdUsuario = suscripcionDao.getSuscripcionesByIdUsuario(usuario.getIdUsuario());
                    suscripcionDao.cerrarConexion();
                    request.setAttribute("suscripciones", suscripciones);
                    request.setAttribute("suscripciones_usuario", suscripcionesByIdUsuario);
                    rd = request.getRequestDispatcher("/suscripciones.jsp");
                    rd.forward(request, response);
                } else if ("dueno".equals(opcion)) {
                    rd = request.getRequestDispatcher("/dueno");
                    rd.forward(request, response);
                } else if ("admin".equals(opcion)) {
                    rd = request.getRequestDispatcher("/administrador");
                    rd.forward(request, response);
                }
            } else if (buscador != null && !buscador.equals("")) {
                RestauranteDAO restauranteDao = new RestauranteDAO();
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantes(buscador);
                if (session.getAttribute("usuario") != null) {
                    SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantes, (int) session.getAttribute("idUsuario"));
                }
                if (restaurantes.size() == 1) {
                    request.setAttribute("restaurante", restaurantes.get(0));
                    EtiquetaDAO etiquetaDao = new EtiquetaDAO();
                    ArrayList<Etiqueta> etiquetas = etiquetaDao.getEtiquitasByIdRestaurante(restaurantes.get(0).getIdRestaurante());
                    etiquetaDao.cerrarConexion();
                    FotografiaDAO fotografiaDao = new FotografiaDAO();
                    ArrayList<Fotografia> fotografias = fotografiaDao.getFotografiasByIdRestaurante(restaurantes.get(0).getIdRestaurante());
                    fotografiaDao.cerrarConexion();
                    ComentarioDAO comentarioDao = new ComentarioDAO();
                    ArrayList<Comentario> comentarios = comentarioDao.getComentariosByIdRestaurante(restaurantes.get(0).getIdRestaurante());
                    comentarioDao.cerrarConexion();
                    int valoracionMediaRestaurante = restauranteDao.valoracionMediaRestaurante(restaurantes.get(0).getIdRestaurante());
                    restauranteDao.cerrarConexion();
                    rd = request.getRequestDispatcher("/restaurante.jsp");
                    request.setAttribute("comentarios", comentarios);
                    request.setAttribute("fotografias", fotografias);
                    request.setAttribute("etiquetas", etiquetas);
                    request.setAttribute("valoracion", valoracionMediaRestaurante);
                    rd.forward(request, response);
                } else if (restaurantes.size() > 1) {
                    restauranteDao.cerrarConexion();
                    request.setAttribute("listaRestaurante", restaurantes);
                    rd = request.getRequestDispatcher("/listaRestaurantes.jsp");
                    rd.forward(request, response);
                } else if (restaurantes.isEmpty()) {
                    restauranteDao.cerrarConexion();
                    rd = request.getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                }
            } else if (iniciarSesion != null) {
                UsuarioDAO usuarioDao = new UsuarioDAO();
                String correo = request.getParameter("correo");
                String contraseña = Utilidades.convertirSHA256(request.getParameter("contrasena"));
                int idUsuario = usuarioDao.existeUsuario(correo, contraseña);
                if (idUsuario != 0) {
                    RolDAO rolDao = new RolDAO();
                    Usuario usuario = usuarioDao.getUsuarioByidUsuario(idUsuario);
                    session.setAttribute("usuario", usuario);
                    RestauranteDAO restauranteDao = new RestauranteDAO();
                    ArrayList<Restaurante> restaurantesPopulares = restauranteDao.getRestaurantesPopulares();
                    session.setAttribute("idUsuario", idUsuario);
                    ArrayList<Rol> rolesUsuario = rolDao.obtenerRolesUsuario(idUsuario);
                    SuscripcionDAO suscripcioDao = new SuscripcionDAO();
                    boolean tieneAnuncios = suscripcioDao.tieneAnuncios(idUsuario);
                    session.setAttribute("anuncios", tieneAnuncios);
                    session.setAttribute("roles", rolesUsuario);
                    if (session.getAttribute("usuario") != null) {
                        SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantesPopulares, (int) session.getAttribute("idUsuario"));
                    }
                    request.setAttribute("restaurantes", restaurantesPopulares);
                    if (rolesUsuario.isEmpty()) {
                        rd = request.getRequestDispatcher("/index.jsp");
                        rd.forward(request, response);
                    } else if (Utilidades.isRol("dueño", rolesUsuario)) {
                        rd = request.getRequestDispatcher("/dueno");
                        rd.forward(request, response);
                    } else if (Utilidades.isRol("admin", rolesUsuario)) {
                        rd = request.getRequestDispatcher("/administrador");
                        rd.forward(request, response);
                    }
                } else {
                    request.setAttribute("usuario_no_existe", "usuario_no_existe");
                    rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                }
                usuarioDao.cerrarConexion();
            } else if (registro != null) {
                UsuarioDAO usuarioDao = new UsuarioDAO();
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String correo = request.getParameter("correo");
                String contrasena = request.getParameter("contrasena");
                String rContrasena = request.getParameter("rContrasena");
                if (Utilidades.validarUsuario(nombre, apellido, correo, contrasena, rContrasena)) {
                    usuarioDao.registrarUsuario(new Usuario(nombre, apellido, correo, contrasena));
                    RestauranteDAO restauranteDao = new RestauranteDAO();
                    ArrayList<Restaurante> restaurantesPopulares = restauranteDao.getRestaurantesPopulares();
                    if (session.getAttribute("usuario") != null) {
                        SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantesPopulares, (int) session.getAttribute("idUsuario"));
                    }
                    request.setAttribute("restaurantes", restaurantesPopulares);
                    restauranteDao.cerrarConexion();
                    rd = request.getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("usuario_no_valido", "usuario_no_valido");
                    rd = request.getRequestDispatcher("/registro.jsp");
                    rd.forward(request, response);
                }
                usuarioDao.cerrarConexion();

            } else if (actualizarCuenta != null) {
                UsuarioDAO usuarioDao = new UsuarioDAO();
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String correo = request.getParameter("correo");
                String contrasena = request.getParameter("contrasena");
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                usuarioDao.actualizarUsuario(new Usuario(usuario.getIdUsuario(), nombre, apellido, correo, contrasena));
                usuarioDao.cerrarConexion();
                RestauranteDAO restauranteDao = new RestauranteDAO();
                ArrayList<Restaurante> restaurantesPopulares = restauranteDao.getRestaurantesPopulares();
                if (session.getAttribute("usuario") != null) {
                    SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantesPopulares, (int) session.getAttribute("idUsuario"));
                }
                request.setAttribute("restaurantes", restaurantesPopulares);
                restauranteDao.cerrarConexion();
                rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            } else if (restaurante != null) {
                RestauranteDAO restauranteDao = new RestauranteDAO();
                EtiquetaDAO etiquetaDao = new EtiquetaDAO();
                ComentarioDAO comentarioDao = new ComentarioDAO();
                UbicacionDAO ubicacionDao = new UbicacionDAO();
                FotografiaDAO fotografiaDao = new FotografiaDAO();
                ArrayList<Etiqueta> etiquitasByIdRestaurante = etiquetaDao.getEtiquitasByIdRestaurante(Integer.parseInt(restaurante));
                ArrayList<Comentario> comentariosByIdRestaurante = comentarioDao.getComentariosByIdRestaurante(Integer.parseInt(restaurante));
                Ubicacion ubicacion = ubicacionDao.getUbicacionByIdRestaurante(Integer.parseInt(restaurante));
                ArrayList<Fotografia> fotografiasByIdRestaurante = fotografiaDao.getFotografiasByIdRestaurante(Integer.parseInt(restaurante));
                Restaurante restauranteById = restauranteDao.getRestauranteById(Integer.parseInt(restaurante));
                int valoracionMediaRestaurante = restauranteDao.valoracionMediaRestaurante(Integer.parseInt(restaurante));
                comentarioDao.cerrarConexion();
                fotografiaDao.cerrarConexion();
                etiquetaDao.cerrarConexion();
                restauranteDao.cerrarConexion();
                request.setAttribute("valoracion", valoracionMediaRestaurante);
                request.setAttribute("restaurante", restauranteById);
                request.setAttribute("ubicacion", ubicacion);
                request.setAttribute("comentarios", comentariosByIdRestaurante);
                request.setAttribute("fotografias", fotografiasByIdRestaurante);
                request.setAttribute("etiquetas", etiquitasByIdRestaurante);
                rd = request.getRequestDispatcher("/restaurante.jsp");
                rd.forward(request, response);
            } else if (request.getParameter("etiquetas") != null) {
                RestauranteDAO restauranteDao = new RestauranteDAO();
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantesByEtiquetas(Integer.parseInt(request.getParameter("etiquetas")));
                if (session.getAttribute("usuario") != null) {
                    SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantes, (int) session.getAttribute("idUsuario"));
                }
                request.setAttribute("listaRestaurante", restaurantes);
                restauranteDao.cerrarConexion();
                rd = request.getRequestDispatcher("/listaRestaurantes.jsp");
                rd.forward(request, response);
            } else if (request.getParameter("localidad") != null) {
                RestauranteDAO restauranteDao = new RestauranteDAO();
                ArrayList<Restaurante> restaurantes = restauranteDao.getRestaurantesByLocalidad(request.getParameter("localidad"));
                if (session.getAttribute("usuario") != null) {
                    SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantes, (int) session.getAttribute("idUsuario"));
                }
                request.setAttribute("listaRestaurante", restaurantes);
                restauranteDao.cerrarConexion();
                rd = request.getRequestDispatcher("/listaRestaurantes.jsp");
                rd.forward(request, response);
            } else if (request.getParameter("recuperarContrasena") != null) {
                UsuarioDAO usuarioDao = new UsuarioDAO();
                int existeUsuario = usuarioDao.existeUsuario(request.getParameter("correo"));
                if (existeUsuario > 0) {
                    String nuevaContraseña = GeneradorContraseñas.getPassword();
                    usuarioDao.cambiarContraseña(Utilidades.convertirSHA256(nuevaContraseña), existeUsuario);
                    usuarioDao.cerrarConexion();
                    Correos correo = new Correos();
                    correo.sendEmail("Recuperarción contraseña - comerEn", "Se contraseña actual ha cambiado a " + nuevaContraseña, request.getParameter("correo"));
                    rd = request.getRequestDispatcher("/login.jsp");
                    System.out.println(request.getParameter("correo"));
                    request.setAttribute("correo_recuperar_contrasena", "correo_recuperar_contrasena");
                    rd.forward(request, response);
                } else {
                    usuarioDao.cerrarConexion();
                    request.setAttribute("no_existe_usuario", "no_existe_usuario");
                    rd = request.getRequestDispatcher("/recuperarContraseña.jsp");
                    rd.forward(request, response);
                }
            } else {
                RestauranteDAO restauranteDao = new RestauranteDAO();
                ArrayList<Restaurante> restaurantesPopulares = restauranteDao.getRestaurantesPopulares();
                if (session.getAttribute("usuario") != null) {
                    SistemaRecomendacion.calcularRecomendacionRestaurantes(restaurantesPopulares, (int) session.getAttribute("idUsuario"));
                }
                restauranteDao.cerrarConexion();
                request.setAttribute("restaurantes", restaurantesPopulares);
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
