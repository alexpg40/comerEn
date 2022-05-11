<%-- 
    Document   : index
    Created on : May 2, 2022, 4:10:10 PM
    Author     : Alex
--%>

<%@page import="Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Index</title>
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/cuenta.css">
        <script src="public/src/cuenta.js"></script>
    </head>
    <body>
        <jsp:include page="./header.jsp" />
        </header>
        <main>
            <section>
                <h2>Datos Cuenta</h2>
                <hr/>
                <img class="iconoPerfil" src="public/img/iconoLogin.svg" alt="icono perfil del usuario"/>
                <form action="controlador" method="post" id="formActualizarCuenta">
                    <label>Nombre: <input type="text" name="nombre" placeholder=<%=usuario.getNombre()%>></label>
                    <label>Apellido: <input type="text" name="apellido" placeholder=<%=usuario.getApellido()%>></label>
                    <label>Correo electrónico: <input type="email" name="correo" placeholder=<%=usuario.getCorreo()%>></label>
                    <label>Contraseña: <input type="password" name="contrasena"/></label>
                    <input type="submit" value="Actualizar cuenta" name="actualizarCuenta"/>
                </form>
            </section>
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>
