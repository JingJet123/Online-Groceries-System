<%-- 
    Document   : alertPop
    Created on : Apr 3, 2022, 3:31:12 PM
    Author     : Joey Kok Yen Ni
--%>

<%
    if (request.getParameter("alert") != null) {
        if (request.getParameter("alert").equals("on")) {
%>    
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>
    function call() {
        swal({
            title: '<%= request.getParameter("alertMsg")%>',
            icon: '<%= request.getParameter("alertType")%>',
            button: 'OK'
        });
    }
    ;
    call();
    <% session.setAttribute("alert", "off"); %>
    <% session.setAttribute("alertMsg", ""); %>
    <% session.setAttribute("alertType", ""); %>
</script>
<%
        }
    }
%>
