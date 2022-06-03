<%-- 
    Document   : restauarnte
    Created on : May 3, 2022, 6:41:58 AM
    Author     : Alex
--%>

<%@page import="Entidades.Ubicacion"%>
<%@page import="Entidades.Comentario"%>
<%@page import="Entidades.Fotografia"%>
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
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.8.0/dist/leaflet.css"
              integrity="sha512-hoalWLoI8r4UszCkZ5kL8vayOGVae1oxXe/2A4AO6J9+580uKHDO3JdHb7NzwwzK5xr/Fs0W40kiNHxM9vyTtQ=="
              crossorigin=""/>
        <script src="https://unpkg.com/leaflet@1.8.0/dist/leaflet.js"
                integrity="sha512-BB3hKbKWOc9Ez/TAwyWxNXeoV9c1v6FIeYiBieIWkpLjauysF18NzgR1MBNBXf8/KABdlkX68nAhlwcDFLGPCQ=="
        crossorigin=""></script>
    </head>
    <body>
        <%
            Restaurante restaurante = (Restaurante) request.getAttribute("restaurante");
            ArrayList<Etiqueta> etiquetas = (ArrayList<Etiqueta>) request.getAttribute("etiquetas");
            ArrayList<Fotografia> fotografias = (ArrayList<Fotografia>) request.getAttribute("fotografias");
            ArrayList<Comentario> comentarios = (ArrayList<Comentario>) request.getAttribute("comentarios");
            Integer valoracion = (Integer) request.getAttribute("valoracion");
            Ubicacion ubicacion = (Ubicacion) request.getAttribute("ubicacion");
        %>
        <jsp:include page="./componentes/header.jsp" />
        <main>
            <jsp:include page="./componentes/ads.jsp" />
            <section>
                <h2><%=restaurante.getNombre()%></h2>
                <hr/>
                <article class="restauranteBody">
                    <article class="restauranteImg">
                        <img src="public/img/foto_temporal.jpg" alt="imagen restaurante" id="imagenRestaurante"/>
                        <span class="valoracion">
                            <%
                                for (int i = 0; i < valoracion; i++) {
                            %>
                            ★
                            <%
                                }
                            %>
                        </span>
                    </article>
                    <article class="restauranteInfo">
                        <article class="etiquetas">
                            <%
                                for (Etiqueta etiqueta : etiquetas) {
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
                    </article>
                    <article id="map">

                    </article>
                    <%
                        if (!fotografias.isEmpty()) {
                    %>
                    <article id="fotografiasContainer">
                        <h3>Fotografias</h3>
                        <hr/>
                        <article id="fotografiasRestaurante">
                            <%
                                for (Fotografia foto : fotografias) {
                            %>
                            <img class="fotografiaRestaurante" src="public/img/<%=foto.getUbicacion()%>" alt="imagen del restaurante"/>
                            <%
                                }
                            %>
                        </article>
                    </article>
                    <%
                        }
                        if (!comentarios.isEmpty()) {
                    %>
                    <article id="comentariosContainer">
                        <h3>Comentarios</h3>
                        <hr/>
                        <article id="comentariosRestaurante">
                                <form action="controlador" class="comentario">
                                    <label>Descripcion <textarea name="comentario"></textarea></label>
                                    <label>Valoracion <input type="number" value="3" name="valoracion"/></label>
                                    <input type="submit" value="Enviar Comentario" name="crear_comentario"/>
                                    <input type="hidden" name="restaurante" value=<%=restaurante.getIdRestaurante()%>/>
                                </form>
                            <%
                                for (Comentario comentario : comentarios) {
                            %>
                            <article class="comentario">
                                <article class="imgUsuario">
                                    <img src="public/img/iconoLogin.svg" alt="imagen del usuario"/>
                                </article>
                                <article class="bodyComentario">
                                    <h4>Pepe</h4>
                                    <hr/>
                                    <p>
                                        <%=comentario.getComentario()%>
                                    </p>
                                    <span class="valoracionComentario">
                                        <%
                                            for (int i = 0; i < comentario.getValoracion(); i++) {
                                        %>
                                        ★
                                        <%
                                            }
                                        %>
                                    </span>
                                </article>
                            </article>
                            <%
                                }
                            %>
                        </article>
                    </article>
                    <%
                        }
                    %>
                </article>
            </section>
            <jsp:include page="./componentes/ads.jsp" />
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
    <script>
        let punto = new L.LatLng(<%=ubicacion.getLat()%>, <%=ubicacion.getLng()%>);
        
        let map = new L.Map('map', {
            center: punto,
            zoom: 50,
        });

        L.marker(punto).addTo(map);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 18,
            attribution: '© OpenStreetMap'
        }).addTo(map);

    </script>
</html>
