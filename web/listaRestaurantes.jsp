<%-- 
    Document   : listaRestaurantes
    Created on : May 4, 2022, 8:23:09 PM
    Author     : Alex
--%>

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
        <header>
            <navbar>
                <a href="controlador?opcion=index">
                    <img id="iconoWeb" alt='logo de comerEn' src='public/img/icono.svg'>
                </a>
                <a href="controlador?opcion=index">
                    <h1>ComerEn</h1>
                </a>
                <a href="controlador?opcion=session">
                    <img id="iconoSesion" alt='icono de sesión' src='public/img/iconoLogin.svg'>
                </a>
            </navbar>
        </header>
        <main>
            <aside class="ads">
                ADS
            </aside>
            <section>
                <%
                   ArrayList<Restaurante> restaurantes = (ArrayList<Restaurante>) request.getAttribute("listaRestaurante");
                 %>
                 <h2>Resultados de "<%=request.getParameter("buscador")%>" - Nº resultados <%=restaurantes.size()%></h2>
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
