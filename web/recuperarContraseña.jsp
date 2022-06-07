<%-- 
    Document   : login
    Created on : May 3, 2022, 6:40:32 AM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Login</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/login.css">
    </head>
    <body>
        <jsp:include page="./componentes/header.jsp" />
        <main>
            <section>
                <h2>Recuperar Contraseña</h2>
                <hr/>
                <form action="controlador" method="post" id="formIniciarSesion">
                    <label>Correo electrónico: <input type="email" name="correo"/></label>
                    <input type="submit" value="Enviar correo" name="recuperarContrasena"/>
                </form>
                <p>Si has olvidado la contraseña pulsa <a href="recuperarContraseña.jsp">aquí</a></p>
                <%
                    if (request.getAttribute("no_existe_usuario") != null) {
                %>
                <p class="error">Ese correo no esta registrado en la base de datos!</p>
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

