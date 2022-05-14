<%@page import="Utilidades.Utilidades"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Rol"%>
<%@page import="Entidades.Usuario"%>
<header>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        ArrayList<Rol> roles = (ArrayList<Rol>) session.getAttribute("roles");
    %>
    <header>
        <navbar>
            <a href="controlador?opcion=index">
                <img id="iconoWeb" alt='logo de comerEn' src='public/img/icono.svg'>
            </a>
            <a href="controlador?opcion=index">
                <h1>ComerEn</h1>
            </a>
            <%
                if (usuario != null) {
            %>
            <div class="logUser">
                <a href="controlador?opcion=session" class="usuarioLogged">
                    <img id="iconoSesion" alt='icono de sesión' src='public/img/iconoLogin.svg'>
                    Hola, <%=usuario.getNombre()%>
                </a>
                <div class="ventanaEmergente">
                     <%
                        if(Utilidades.isRol("dueño", roles)){
                            %>
                            <a href="controlador?opcion=dueno">
                                <img id='iconoDueño' alt='icono dueño' src="public/img/restaurante.png"> Tus Restaurantes
                            </a>
                            <%
                        }
                    %>
                    <%
                        if(Utilidades.isRol("admin", roles)){
                            %>
                            <a href="controlador?opcion=admin">
                                <img id='iconoAdmin' alt='icono administrador' src="public/img/restaurante.png"> Administrador
                            </a>
                            <%
                        }
                    %>
                    <a href="controlador?opcion=suscripcion">
                        <img id='iconoSuscripcion' alt='icono cerrar sesión' src="public/img/suscripcion.png"> Suscripciones
                    </a>
                    <a href="controlador?opcion=logout">
                        <img id='iconoLogout' alt='icono cerrar sesión' src="public/img/logout.png"> Cerrar Sesión
                    </a>
                </div>
            </div>
            <%
            } else {
            %>
            <a href="controlador?opcion=session">
                <img id="iconoSesion" alt='icono de sesión' src='public/img/iconoLogin.svg'>
            </a>
            <%
                }
            %>
        </navbar>
    </header>