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
        <link rel="stylesheet" href="public/styles/suscripciones.css">
    </head>
    <body>
        <jsp:include page="./componentes/header.jsp" />
        <main>
            <jsp:include page="./componentes/ads.jsp" />
            <section>
                <%
                    if (session.getAttribute("usuario") != null) {
                %>
                <article class="suscripciones">
                    <h2>Tus Suscripciones</h2>
                    <hr/>
                    <section class="tusSuscripciones">
                        <%
                            ArrayList<Suscripcion> tusSuscripciones = (ArrayList<Suscripcion>) request.getAttribute("suscripciones_usuario");
                            if (tusSuscripciones.isEmpty()) {
                        %>
                        <p>No tienes ninguna suscripcióna activada</p>
                        <%
                        } else {
                            for (Suscripcion suscripcion : tusSuscripciones) {
                        %>
                        <article class="tus_suscripcion">
                            <img src="public/img/ads.png" alt="icono de la suscripcion"/>
                            <article class="descripcion"><%=suscripcion.getDescripcion()%></article>
                            <article class="botones">
                                <a href="suscripcion?baja=<%=suscripcion.getIdSuscripcion()%>" class="baja">
                                    Anular
                                </a>
                            </article>
                        </article>
                        <%
                                }
                            }
                        %>
                    </section>
                </article>
                <article class="comprar_suscripciones">
                    <h2>Comprar Suscripciones</h2>
                    <hr/>
                    <section class="tusSuscripciones">
                        <%
                            ArrayList<Suscripcion> suscripciones = (ArrayList<Suscripcion>) request.getAttribute("suscripciones");
                            if (suscripciones.isEmpty()) {
                        %>
                        <p>No se han encontrado ninguna suscripción disponible</p>
                        <%
                        } else {
                            for (Suscripcion suscripcion : suscripciones) {
                        %>
                        <article class="suscripcion">
                            <img src="public/img/ads.png" alt="icono de la suscripcion"/>
                            <hr/>
                            <article><%=suscripcion.getDescripcion()%></article>
                            <hr/>
                            <a href="suscripcion?comprar=<%=suscripcion.getIdSuscripcion()%>"><article class="precio"><%=suscripcion.getPrecio()%> €</article></a>
                        </article>
                        <%
                                }
                            }
                        %>
                    </section>
                </article>
                <%
                    } else {
                    %>
                    <p class="error">Necesita estar logeado o su sesión a caducado!</p>
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
