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
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/cuenta.css">
        <script src="public/src/cuenta.js"></script>
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
            <section>
                <h2>Datos Cuenta</h2>
                <hr/>
                <img class="iconoPerfil" src="public/img/iconoLogin.svg" alt="icono perfil del usuario"/>
                <form action="controlador" method="post" id="formActualizarCuenta">
                    <label>Nombre: <input type="text" name="nombre" placeholder=<%=usuario.getNombre()%>></label>
                    <label>Apellido: <input type="text" name="apellido" placeholder=<%=usuario.getApellido()%>></label>
                    <label>Correo electrónico: <input type="email" name="correo" placeholder=<%=usuario.getCorreo()%>></label>
                    <label>Contraseña: <input type="password" name="contrasena"/></label>
                    <input type="submit" value="Actualizar cuenta" name="actualizarCuenta"/>
                </form>
            </section>
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>
