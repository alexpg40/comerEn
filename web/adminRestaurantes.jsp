<%-- 
    Document   : administrador
    Created on : May 3, 2022, 6:43:09 AM
    Author     : Alex
--%>

<%@page import="Utilidades.Utilidades"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Rol"%>
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
        <link rel="stylesheet" href="public/styles/adminRestaurantes.css">
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
                    <h2>Buscar Restaurantes</h2>
                    <hr/>
                    <form action="administrador">
                        <input id="buscador" type="text" name="buscador" placeholder="Restaurante, ciudades, comida italiana..."/>
                        <input type="submit" name="buscarRestaurante" value="Buscar"/>
                    </form>
                </article>
                <article>
                    <h2>Crear Restaurante con Dueño</h2>
                    <hr/>
                    <form action="administrador" class="formCrearRestaurante">
                        <label>Nombre del restaurante<input type="text" name="nombreRestaurante" placeholder="El Bon Apettit"/></label>
                        <label>Nombre del dueño<input type="text" name="nombreDueno" placeholder="Pepe"/></label>
                        <label>Apellido/s del dueño<input type="text" name="apellidoDueno" placeholder="Garcia Vázquez"/></label>
                        <label>Correo del dueño<input type="email" name="correoDueno" placeholder="pepe_vazquez@gmail.com"/></label>
                        <input type="submit" name="crear_restaurante" value="Crear Restaurante con Dueño"/>
                    </form>
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
