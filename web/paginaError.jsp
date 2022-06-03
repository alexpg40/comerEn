<%-- 
    Document   : suscripciones
    Created on : May 2, 2022, 4:10:10 PM
    Author     : Alex
--%>

<%@page import="Entidades.Suscripcion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Suscripciones</title>
        <script type="module" src="public/src/js/index.js"></script>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/paginaError.css">
    </head>
    <body>
        <jsp:include page="./componentes/header.jsp" />
        <main>
            <jsp:include page="./componentes/ads.jsp" />
            <section>
                <%
                    if(request.getAttribute("error") != null){
                        %>
                        <p class="error"><%=request.getAttribute("error")%></p>
                        <%
                    } else{
                        %>
                        <p class="error">Upps! Ha ocurrido un error!</p>
                        <%
                    } 
                %>
            </section>
            <jsp:include page="./componentes/ads.jsp" />
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>
