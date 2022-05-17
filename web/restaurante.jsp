<%-- 
    Document   : restauarnte
    Created on : May 3, 2022, 6:41:58 AM
    Author     : Alex
--%>

<%@page import="Entidades.Etiqueta"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Restaurante"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Restaurante</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/restaurante.css">
    </head>
    <body>
        <%
           Restaurante restaurante = (Restaurante) request.getAttribute("restaurante");
           ArrayList<Etiqueta> etiquetas = (ArrayList<Etiqueta>) request.getAttribute("etiquetas");
         %>
        <jsp:include page="./header.jsp" />
        <main>
            <aside class="ads">
                ADS
            </aside>
            <section>
                <h2><%=restaurante.getNombre()%></h2>
                <hr/>
                <img src="public/img/foto_temporal.jpg" alt="imagen restaurante" id="imagenRestaurante"/>
                <article class="etiquetas">
                    <%
                      for(Etiqueta etiqueta : etiquetas){
                         %>
                         <article class="etiqueta"><%=etiqueta.getNombre()%></article>
                         <%
                      }    
                    %>
                </article>
                <article id="descripcionRestaurante">
                    <h3>Descripción</h3>
                    <hr/>
                    <p>
                        <%=restaurante.getDescripcion()%>
                    </p>
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
