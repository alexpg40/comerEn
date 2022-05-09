<%-- 
    Document   : index
    Created on : May 2, 2022, 4:10:10 PM
    Author     : Alex
--%>

<%@page import="Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Index</title>
        <script src="public/src/index.js"></script>
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/index.css">
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
        <main>
            <aside class="ads">
                ADS
            </aside>
            <section>
                <article id="setionHeader">
                    <div>
                        <h2>Bienvenido a ComerEn, encuentra tu restaurante ideal.</h2>
                        <form>
                            <input type="text" name="buscador" placeholder="Restaurantes, ciudades, comida italiana...">
                            <input type="submit" value="Buscar">
                        </form>
                    </div>
                </article>
            </section>
            <aside class="ads">
                ADS
            </aside>  
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>
