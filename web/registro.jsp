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
        <link rel="stylesheet" href="public/styles/registro.css">
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
                <h2>Registrar Sesión</h2>
                <hr/>
                <form action="controlador" method="post" id="formIniciarSesion">
                    <label>Nombre: <input type="text" name="nombre" placeholder="Pepe"/></label>
                    <label>Apellido: <input type="text" name="apellido" placeholder="Rodriguez"/></label>
                    <label>Correo electrónico: <input type="email" name="correo" placeholder="ejemplo@ejemplo.com"/></label>
                    <label>Contraseña: <input type="password" name="contrasena"/></label>
                    <label>Repite la contraseña: <input type="password" name="rContrasena"/></label>
                    <input type="submit" value="Iniciar Sesion" name="iniciarSesion"/>
                </form>
            </section>
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>

