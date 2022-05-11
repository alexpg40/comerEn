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
        <script src="public/src/registro.js"></script>
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/registro.css">
    </head>
    <body>
        <jsp:include page="./header.jsp" />
        <main>
            <section>
                <h2>Registrar Sesión</h2>
                <hr/>
                <form action="controlador" method="get" id="formIniciarSesion">
                    <label>Nombre: <input type="text" name="nombre" placeholder="Pepe"/></label>
                    <label>Apellido: <input type="text" name="apellido" placeholder="Rodriguez"/></label>
                    <label>Correo electrónico: <input type="email" name="correo" placeholder="ejemplo@ejemplo.com"/></label>
                    <label>Contraseña: <input type="password" name="contrasena"/></label>
                    <input type="hidden" name="registrar" value="registro"/>
                    <label>Repite la contraseña: <input type="password" name="rContrasena"/></label>
                    <input type="submit" value="Registrar Sesion" name="registrar"/>
                </form>
            </section>
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>

