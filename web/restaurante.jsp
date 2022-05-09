<%-- 
    Document   : restauarnte
    Created on : May 3, 2022, 6:41:58 AM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Index</title>
        <link rel="stylesheet" href="styles/common.css">
    </head>
    <body>
        <%
            Usuario usuario = (Usuario) session.getAttribute("usuario");
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
                        <a href="controlador?opcion=logout">
                            <img id='iconoLogout' alt='icono cerrar sesión' src="public/img/logout.png"> Cerrar Sesión
                        </a>
                        <a href="controlador?opcion=suscripcion">
                            <img id='iconoSuscripcion' alt='icono cerrar sesión' src="public/img/suscripcion.png"> Suscripciones
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
        <aside class="ads">
            ADS
        </aside>
        <main>
            
        </main>
        <aside class="ads">
            ADS
        </aside>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>
