<%-- 
    Document   : administrador
    Created on : May 3, 2022, 6:43:09 AM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>comerEn - Index</title>
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/administrador.css">
    </head>
    <body>
        <jsp:include page="./header.jsp" />
        <main>
            <section>
                <article>
                    <h2>Buscar Restaurantes</h2>
                    <hr/>
                    <form action="administrador">
                        <input id="buscador" type="text" name="buscador" placeholder="Restaurante, ciudades, comida italiana..."/>
                        <input type="submit" name="buscar" value="Buscar"/>
                    </form>
                </article>
                <article>
                    <h2>Crear Restaurante con Dueño</h2>
                    <hr/>
                    <form action="administrador" class="formCrearRestaurante">
                        <label>Nombre del restaurante<input type="text" name="nombreRestaurante" placeholder="El Bon Apettit"/></label>
                        <label>Nombre del dueño<input type="text" name="nombreDueno" placeholder="Pepe"/></label>
                        <label>Apellido/s del dueño<input type="text" name="apellidoDueno" placeholder="Garcia Vázquez"/></label>
                        <label>Correo del dueño<input type="email" name="correoDueno" placeholder="pepe_vazquez@gmail.com"/></label>
                        <input type="submit" name="crear_restaurante" value="Crear Restaurante con Dueño"/>
                    </form>
                </article>
            </section>
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>
