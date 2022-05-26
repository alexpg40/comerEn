<%@page import="Entidades.Rol"%>
<%@page import="Utilidades.Utilidades"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Usuario"%>
<%
    if (session.getAttribute("usuario") != null) {
        if ((Boolean) session.getAttribute("anuncios") && !Utilidades.isRol("admin", (ArrayList<Rol>)session.getAttribute("roles"))) {
%>
<aside class="ads">
    ADS
</aside>
<%
    }
} else {
%>
<aside class="ads">
    ADS
</aside>
<%
    }

%>