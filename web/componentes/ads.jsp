<%@page import="Entidades.Usuario"%>
<%
    if (session.getAttribute("usuario") != null) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
    } else {
%>
<aside class="ads">
    ADS
</aside>
<%
    }

%>