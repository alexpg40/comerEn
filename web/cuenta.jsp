<%-- 
    Document   : index
    Created on : May 2, 2022, 4:10:10 PM
    Author     : Alex
--%>

<%@page import="Entidades.Rol"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Sesion</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/cuenta.css">
        <script type="module" src="./public/src/js/cuenta.js"></script>
    </head>
    <body>
        <jsp:include page="./componentes/header.jsp" />
        <%
            Usuario usuario = (Usuario) session.getAttribute("usuario");
        %>
    </header>
    <main>
        <section>
            <%
                if (session.getAttribute("usuario") != null) {
            %>
            <h2>Datos Cuenta</h2>
            <hr/>
            <img class="iconoPerfil" src="public/img/iconoLogin.svg" alt="icono perfil del usuario"/>
            <form action="controlador" method="post" id="formActualizarCuenta">
                <label>Nombre: <input type="text" name="nombre" placeholder=<%=usuario.getNombre()%>></label>
                <label>Apellido: <input type="text" name="apellido" placeholder=<%=usuario.getApellido()%>></label>
                <label>Correo electrónico: <input type="email" name="correo" placeholder=<%=usuario.getCorreo()%>></label>
                <label>Contraseña: <input type="password" name="contrasena"/></label>
                <input type="submit" value="Actualizar cuenta" name="actualizarCuenta"/>
                <input type="hidden" name="idUsuario" value="<%=usuario.getIdUsuario()%>"/>
            </form>
            <p id="erroresContainer">
                <%
                    if (request.getAttribute("error") != null) {
                %>
            <p class="error"><%=request.getAttribute("error")%></p>
            <%
                }
            %>
            </p>
            <%
                } else {
                    %>
                    <p class="error">Necesita estar logeado para poder acceder aqui, tal vez la sesión caduco</p>
                    <%
                }
            %>        
        </section>
    </main>
    <footer>
        © Alex Perez 2022
    </footer>
</body>
</html>
