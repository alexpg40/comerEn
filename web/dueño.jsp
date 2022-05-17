<%-- 
    Document   : dueño
    Created on : May 3, 2022, 6:43:19 AM
    Author     : Alex
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Restaurante"%>
<%@page import="Entidades.Restaurante"%>
<%@page import="Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Dueño</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/dueño.css">
    </head>
    <body>
        <%
            ArrayList<Restaurante> restaurantes = (ArrayList<Restaurante>) session.getAttribute("restaurantes");
        %>
        <jsp:include page="./header.jsp" />
        <main>
            <section class="container">
                <h2>Tus Restaurantes</h2>
                <hr/>
                <section id="restaurantes">
                    <%
                        for (Restaurante restaurante : restaurantes) {
                    %>
                    <article class="restauranteContainer">
                        <article class="restaurantes">
                            <h3 class="nombreRestaurante"><%=restaurante.getNombre()%></h3>
                            <hr/>
                            <section class="bodyRestaurante">
                                <article>
                                    <img class="imagenRestaurante" src="public/img/foto_temporal.jpg" alt="foto temporal del restaurante"/>
                                </article>
                                <article class="containerDescripcion">
                                    <article class="descripcionRestaurante">
                                        <%=restaurante.getDescripcion()%>
                                    </article>
                                    <article class="valoracionRestaurante">
                                        ★★★★★
                                    </article>
                                </article>
                            </section>
                        </article>
                        <article class="botones">
                            <a href="dueno?ocultar=<%=restaurante.getIdRestaurante()%>" class="botonOcultar">
                                Ocultar
                            </a>
                            <a href="dueno?editar=<%=restaurante.getIdRestaurante()%>" class="botonEditar">
                                Editar
                            </a>
                            <a href="dueno?eliminar=<%=restaurante.getIdRestaurante()%>" class="botonEliminar">
                                Eliminar
                            </a>
                            <a href="controlador?restaurante=<%=restaurante.getIdRestaurante()%>" class="botonVer">
                                Ver
                            </a>
                        </article>
                    </article>
                    <%
                        }
                    %>
                </section>
            </section>
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>
