<%-- 
    Document   : login
    Created on : Mar 7, 2022, 1:29:39 AM
    Author     : Lee Jing Jet
--%>

<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<% session.setAttribute("path", "login.jsp"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AG Login</title>
        <link href="style.css" rel="stylesheet" />
        <link rel="shortcut icon" href="assets/img/AGLogo/favicon.png" type="image/x-icon">
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />
        <link href="assets/css/login.css" rel="stylesheet" />

    </head>
    <body class="bg-theme-small">

        <!-- Start Breadcrumb 
        ============================================= -->
        <div class="login-area">
            <div class="container">
                <div class="row">
                    <!--                <div class="col-lg-6 offset-lg-4">-->
                    <div class="col-lg-8">
                        <div class="login-box">
                            <div class="login">
                                <div class="content">
                                    <a href="index.jsp"><img src="assets/img/AGLogo/Logo4.png"  alt="Logo"></a>
                                    <form action="CustLogin" method="post">
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="form-group">
                                                    <input class="form-control" name="userInfo" placeholder="User Name / Email*" type="text" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="form-group">
                                                    <input class="form-control" name="password" placeholder="Password*" type="password" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <button type="submit" class="login-btn">Login</button>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="register">
                                        <p>Don't have an account? <a href="register.jsp">Register Now</a></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Breadcrumb -->
        <jsp:include page="common/alertPop.jsp">
            <jsp:param name="alert" value="<%= session.getAttribute("alert") %>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg") %>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType") %>" />
        </jsp:include>
    </body>
</html>
