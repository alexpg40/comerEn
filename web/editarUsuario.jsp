<%-- 
    Document   : index
    Created on : May 2, 2022, 4:10:10 PM
    Author     : Alex
--%>

<%@page import="Entidades.Rol"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Editar Usuario</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/editarUsuario.css">
        <script type="module" src="public/src/js/editarUsuario.js"></script>
    </head>
    <body>
        <jsp:include page="./componentes/header.jsp" />
        <%
            Usuario usuario = (Usuario) session.getAttribute("usuario_editar");
            ArrayList<Rol> rolesFaltantes = (ArrayList<Rol>) request.getAttribute("rolesFaltantes");
            ArrayList<Rol> rolesUsuario = (ArrayList<Rol>) request.getAttribute("rolesUsuario");
        %>
    </header>
    <main>
        <section>
            <h2>Datos Cuenta</h2>
            <hr/>
            <article class="formulario">
                <img class="iconoPerfil" src="public/img/<%=usuario.getIcono()%>" alt="icono perfil del usuario"/>
                <form action="administrador" method="post" id="formActualizarCuenta">
                    <label>Nombre: <input type="text" name="nombre" placeholder=<%=usuario.getNombre()%>></label>
                    <label>Apellido: <input type="text" name="apellido" placeholder=<%=usuario.getApellido()%>></label>
                    <label>Correo electrónico: <input type="email" name="correo" placeholder=<%=usuario.getCorreo()%>></label>
                    <input type="submit" value="Actualizar cuenta" name="actualizarUsuario"/>
                </form>
                <p id="erroresContainer">
                    <%
                        if (request.getAttribute("error") != null) {
                    %>
                <p class="error"><%=request.getAttribute("error")%></p>
                <%
                    }
                %>
            </article>
        </section>
        <section>
            <h2>Gestion de Roles</h2>
            <hr/>
            <%
                if (!rolesUsuario.isEmpty()) {
            %>
            <article>
                <article class="formulario">
                    <form id="formQuitarRol" action="administrador">
                        <label>Quitar rol 
                            <select name="rol">
                                <%
                                    for (Rol rol : rolesUsuario) {
                                %>
                                <option value="<%=rol.getIdRol()%>"><%=rol.getNombreRol()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </label>
                        <input type="submit" name="quitarRol" value="Quitar Rol"/></label>
                    </form>
                </article>
            </article>
            <%
                }
            %>
            <%
                if (!rolesFaltantes.isEmpty()) {
            %>
            <article>
                <article class="formulario">
                    <form id="formInsertarRol" action="administrador">
                        <label>Rol a insertar: 
                            <select name="rol">
                                <%
                                    for (Rol rol : rolesFaltantes) {
                                %>
                                <option value="<%=rol.getIdRol()%>"><%=rol.getNombreRol()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </label>
                        <input type="submit" name="insertarRol" value="Insertar Rol"/></label>
                    </form>     
                </article>
            </article>
            <%
                }
            %>
        </section>
    </main>
    <footer>
        © Alex Perez 2022
    </footer>
</body>
</html>
