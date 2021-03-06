<%-- 
    Document   : adminUsuarios
    Created on : May 19, 2022, 8:00:38 AM
    Author     : Alex
--%>

<%@page import="Entidades.Rol"%>
<%@page import="Utilidades.Utilidades"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Index</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/adminUsuarios.css">
        <script type="module" src="public/src/js/adminUsuarios.js"></script>
    </head>
    <body>
        <jsp:include page="./componentes/header.jsp" />
        <main>
            <section>
                <%
                    ArrayList<Rol> rolesUsuario = (ArrayList<Rol>) session.getAttribute("roles");
                    if (session.getAttribute("usuario") != null && Utilidades.isRol("Admin", rolesUsuario)) {
                %>
                <article>
                    <h2>Buscar Usuarios</h2>
                    <hr/>
                    <form action="administrador">
                        <input id="buscador" type="text" name="buscador" placeholder="Pepe Garcia"/>
                        <input type="submit" name="buscarUsuarios" value="Buscar"/>
                    </form>
                </article>
                <article>
                    <h2>Lista de Usuarios</h2>
                    <hr/>
                    <section class="usuarios" id="sectionUsuario">

                    </section>
                </article>
                <%
                    } else {
                    %>
                    <p class="error">No tiene permiso para acceder a esta página o su sesión a caducado!</p>
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
