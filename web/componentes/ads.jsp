<%@page import="Entidades.Usuario"%>
<%
    if (session.getAttribute("usuario") != null) {
        if ((Boolean) session.getAttribute("anuncios")) {
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