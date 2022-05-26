<%-- 
    Document   : index
    Created on : May 2, 2022, 4:10:10 PM
    Author     : Alex
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Restaurante"%>
<%@page import="Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Index</title>
        <script src="public/src/index.js"></script>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/index.css">
    </head>
    <body>
        <%
            ArrayList<Restaurante> restaurauntes = (ArrayList<Restaurante>) request.getAttribute("restaurantes");
        %>
        <jsp:include page="./componentes/header.jsp" />
        <main>
            <jsp:include page="./componentes/ads.jsp" />
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
                <article id="restaurantes">
                    <h2>Restaurantes más Populares</h2>
                    <hr/>
                    <section id="restaurantesContainer">
                        <%
                            for (Restaurante restaurante : restaurauntes) {
                        %>
                        <a href="controlador?restaurante=<%=restaurante.getIdRestaurante()%>">
                            <article class="restaurante">
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
                        </a>
                        <%
                            }
                        %>
                    </section>
                </article>
            </section>
            <jsp:include page="./componentes/ads.jsp" />
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>
