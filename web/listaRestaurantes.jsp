<%-- 
    Document   : listaRestaurantes
    Created on : May 4, 2022, 8:23:09 PM
    Author     : Alex
--%>

<%@page import="Entidades.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Restaurante"%>
<%@page import="Entidades.Restaurante"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%-- 
    Document   : index
    Created on : May 2, 2022, 4:10:10 PM
    Author     : Alex
--%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Buscador</title>
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/listaRestaurantes.css">
    </head>
    <body>
        <jsp:include page="./header.jsp" />

        <main>
            <aside class="ads">
                ADS
            </aside>
            <section id="restaurantesContainer">
                <%
                    ArrayList<Restaurante> restaurantes = (ArrayList<Restaurante>) request.getAttribute("listaRestaurante");
                %>
                <h2>Resultados de "<%=request.getParameter("buscador")%>" - Nº resultados <%=restaurantes.size()%></h2>
                <hr/>
                <%
                    for (Restaurante restaurante : restaurantes) {
                %>
                <article class="restaurante">
                    <h3 class="nombreRestaurante"><%=restaurante.getNombre()%></h3>
                    <hr/>
                    <section class="bodyRestaurante">
                        <article>
                            <img class="imagenRestaurante" src="public/img/foto_temporal.jpg" alt="foto temporal del restaurante"/>
                        </article>
                        <article class="descripcionRestaurante">
                            <%=restaurante.getDescripcion()%>
                        </article>
                    </section>
                </article>
                <%
                    }
                %>
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
