<%-- 
    Document   : restauarnte
    Created on : May 3, 2022, 6:41:58 AM
    Author     : Alex
--%>

<%@page import="Entidades.Rol"%>
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
        <link rel="stylesheet" href="public/styles/editarRestaurante.css">
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.8.0/dist/leaflet.css"
              integrity="sha512-hoalWLoI8r4UszCkZ5kL8vayOGVae1oxXe/2A4AO6J9+580uKHDO3JdHb7NzwwzK5xr/Fs0W40kiNHxM9vyTtQ=="
              crossorigin=""/>
        <script src="https://unpkg.com/leaflet@1.8.0/dist/leaflet.js"
                integrity="sha512-BB3hKbKWOc9Ez/TAwyWxNXeoV9c1v6FIeYiBieIWkpLjauysF18NzgR1MBNBXf8/KABdlkX68nAhlwcDFLGPCQ=="
        crossorigin=""></script>
        <script src="public/src/js/editarRestaurante.js"></script>
    </head>
    <body>
        <jsp:include page="./componentes/header.jsp" />
        <main>
            <jsp:include page="./componentes/ads.jsp" />
            <section>
                <%
                    ArrayList<Rol> rolesUsuario = (ArrayList<Rol>) session.getAttribute("roles");
                    if (session.getAttribute("usuario") != null && !rolesUsuario.isEmpty()) {
                        Restaurante restaurante = (Restaurante) request.getAttribute("restaurante");
                        ArrayList<Etiqueta> etiquetasFaltantes = (ArrayList<Etiqueta>) request.getAttribute("etiquetasFaltantes");
                        ArrayList<Etiqueta> etiquetas = (ArrayList<Etiqueta>) request.getAttribute("etiquetas");
                        ArrayList<Fotografia> fotografias = (ArrayList<Fotografia>) request.getAttribute("fotografias");
                        Ubicacion ubicacion = (Ubicacion) request.getAttribute("ubicacion");
                %>
                <h2><%=restaurante.getNombre()%></h2>
                <hr/>
                <article class="restauranteBody">
                    <article class="restauranteImg">
                        <img src="public/img/foto_temporal.jpg" alt="imagen restaurante" id="imagenRestaurante"/>
                    </article>
                    <article class="restauranteInfo">
                        <article class="etiquetas">
                            <h3 class="blanco">Gestionar Etiqueta</h3>
                            <%
                                if (!etiquetas.isEmpty()) {
                            %>
                            <form action="administrador">
                                <select name="etiqueta">
                                    <%
                                        for (Etiqueta etiqueta : etiquetas) {
                                    %>
                                    <option value="<%=etiqueta.getIdEtiqueta()%>"><%=etiqueta.getNombre()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                                <input type="hidden" name="idRestaurante" value="<%=restaurante.getIdRestaurante()%>"/>
                                <input type="submit" name="quitarEtiqueta" value="Quitar Etiqueta"/>
                            </form>
                            <%
                                }
                            %>
                            <%
                                if (!etiquetasFaltantes.isEmpty()) {
                            %>
                            <form action="administrador">
                                <select name="etiqueta">
                                    <%
                                        for (Etiqueta etiqueta : etiquetasFaltantes) {
                                    %>
                                    <option value="<%=etiqueta.getIdEtiqueta()%>"><%=etiqueta.getNombre()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                                <input type="hidden" name="idRestaurante" value="<%=restaurante.getIdRestaurante()%>"/>
                                <input type="submit" name="anadirEtiqueta" value="Añadir Etiqueta"/>
                            </form>
                            <%
                                }
                            %>
                        </article>
                        <article id="descripcionRestaurante">
                            <h3>Descripción</h3>
                            <hr/>
                            <form action="administrador">
                                <textarea name="descripcion"><%=restaurante.getDescripcion()%></textarea>
                                <input type="hidden" name="idRestaurante" value="<%=restaurante.getIdRestaurante()%>"/>
                                <input type="submit" name="cambiar_descripcion" value="Actualizar Descripcion"/>
                            </form>
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
                            <form method="post" action="subirFichero" enctype="multipart/form-data" >
                                <input type="hidden" name="idRestaurante" value="<%=restaurante.getIdRestaurante()%>"/>
                                <input type="file" name="file" id="file"/>
                                <input type="submit" value="Subir imagen"/>
                            </form>
                            <%
                                for (Fotografia foto : fotografias) {
                            %>
                            <img class="fotografiaRestaurante" src="<%=foto.getUbicacion()%>" alt="imagen del restaurante"/>
                            <%
                                }
                            %>
                        </article>
                    </article>
                    <%
                    } else {
                    %>
                    <article id="fotografiasContainer">
                        <h3>Fotografias</h3>
                        <hr/>
                        <article id="fotografiasRestaurante">
                            <form method="post" action="subirFichero" enctype="multipart/form-data" >
                                <input type="hidden" name="idRestaurante" value="<%=restaurante.getIdRestaurante()%>"/>
                                <input type="file" name="file" id="file"/>
                                <input type="submit" value="Subir imagen"/>
                            </form>
                        </article>
                    </article>
                    <%
                        }
                    %>
                </article>
                <script>
                    var idRestaurante = <%=restaurante.getIdRestaurante()%>;
                    <%
                        if (ubicacion != null) {
                    %>
                    initMapa(<%=ubicacion.getLat()%>, <%=ubicacion.getLng()%>)
                    <%
                    } else {
                    %>
                    initMapa(40.416962006055115, -3.7015176580147946)
                    <%
                        }
                    %>
                </script>
                <%
                } else {
                %>
                <p class="error">No tiene permiso para acceder a esta página o su sesión a caducado!</p>
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
