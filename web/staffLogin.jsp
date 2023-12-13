<%-- 
    Document   : staffLogin
    Created on : Mar 26, 2022
    Author     : Lee Jia Jie
--%>

<%@page import="entity.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AG Market Staff Login</title>

        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <link href="../assets/css/staffIndex.css" rel="stylesheet"/>
    </head>
    <% 
        Customer customer = (Customer) session.getAttribute("customer"); 
        if(customer != null) { 
    %>
    <script>
        alert("Please log out as a customer before you enter staff/admin page");
        window.location.href = "../index.jsp";
    </script>
    <%
        }
    %>
    
    <body>
        <div id="login-page">
            <div class="login">
                <h2 class="login-title">Login</h2>
                <p class="notice">Please login to access the system</p>
                <form class="form-login" action="j_security_check" method="POST">
                    <label for="username">Username</label>
                    <div class="input-username">
                        <i class="fas fa-user-shield icon"></i>
                        <input type="text" name="j_username" placeholder="Enter your username" required>
                    </div>
                    <label for="password">Password</label>
                    <div class="input-password">
                        <i class="fas fa-lock icon"></i>
                        <input type="password" name="j_password" placeholder="Enter your password" required>
                    </div>
                    <button type="submit"><i class="fas fa-door-open"></i> Sign in</button>
                </form>
            </div>
            <div class="background">

            </div>
        </div>

        <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    </body>
</html>
