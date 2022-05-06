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
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/login.css">
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
            <section>
                <h2>Iniciar Sesión</h2>
                <hr/>
                <form action="controlador" method="post" id="formIniciarSesion">
                    <label>Correo electrónico: <input type="email" name="correo"/></label>
                    <label>Contraseña: <input type="password" name="contrasena"/></label>
                    <input type="submit" value="Iniciar Sesion" name="iniciarSesion"/>
                </form>
                <%
                if(request.getAttribute("usuario_no_existe") != null){
                    %>
                    <p class="error">Ese usuario no exite, comprueba que los datos sean correctos</p>
                    <%
                } 
                %>
                <p>Si no tienes una cuenta, puedes registrarte<a href="registro.jsp">aquí</a></p>
            </section>
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>

