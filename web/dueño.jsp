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
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/dueño.css">
    </head>
    <body>
        <%
            ArrayList<Restaurante> restaurantes = new ArrayList<>();
        %>
        <jsp:include page="./header.jsp" />
        <main>
            <section>
                <h2>Tus Restaurantes</h2>
                <hr/>
                <section id="restaurantes">
                    <%
                        for (Restaurante restaurante : restaurantes) {
                    %>
                    <article class="restauranteContainer">
                        <article class="restaurantes">
                        
                        </article>
                        <article class="botones">
                            
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
