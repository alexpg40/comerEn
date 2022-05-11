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
        <script src="public/src/index.js"></script>
        <link rel="stylesheet" href="public/styles/common.css">
        <link rel="stylesheet" href="public/styles/index.css">
    </head>
    <body>
        <jsp:include page="./header.jsp" />
        <main>
            <aside class="ads">
                ADS
            </aside>
            <section>
                <article id="setionHeader">
                    <div>
                        <h2>Bienvenido a ComerEn, encuentra tu restaurante ideal.</h2>
                        <form>
                            <input type="text" name="buscador" placeholder="Restaurantes, ciudades, comida italiana...">
                            <input type="submit" value="Buscar">
                        </form>
                    </div>
                </article>
            </section>
            <aside class="ads">
                ADS
            </aside>  
        </main>
        <footer>
            © Alex Perez 2022
        </footer>
    </body>
</html>
