<%-- 
    Document   : Error Page
    Created on : Mar 7, 2022, 1:29:39 AM
    Author     : Chuah Shee Yeap
--%>

<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- ========== Page Title ========== -->
        <title>AG Market</title>

        <!-- ========== Favicon Icon ========== -->
        <% if (request.getRemoteUser() != null) { %>
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">
        <% } else {%>
        <link rel="shortcut icon" href="assets/img/AGLogo/favicon.png" type="image/x-icon">
        <% } %>

        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>

        <!-- ========== Start Stylesheet ========== -->
        <% if (request.getRemoteUser() != null) { %>
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/bootsnav.css" rel="stylesheet" />
        <link href="../assets/css/responsive.css" rel="stylesheet" />    
        <link href="../style.css" rel="stylesheet">
        <link href="../layout.css" rel="stylesheet">
        <% } else {%>
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />    
        <link href="style.css" rel="stylesheet">
        <link href="layout.css" rel="stylesheet">
        <% } %>
        <!-- ========== End Stylesheet ========== -->


    </head>

    <body onload="endPreLoading()">
        <!-- Preloader Start -->
        <% if (request.getRemoteUser() != null) { %>
        <jsp:include page="common/preloader.jsp"> 
            <jsp:param name="faviconImgSrc" value="../assets/img/AGLogo/favicon.png" />       
        </jsp:include> 
        <% } else {%>
        <jsp:include page="common/preloader.jsp"> 
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png" />       
        </jsp:include> 
        <% } %>
        <!-- Preloader Ends -->

        <% if (request.getRemoteUser() != null) { %>
        <jsp:include page="common/header.jsp">
            <jsp:param name="loginPath" value="../login.jsp" />
            <jsp:param name="registerPath" value="../register.jsp" />
            <jsp:param name="logoImgSrc" value="../assets/img/AGLogo/Logo2.png" />
            <jsp:param name="indexPath" value="../index.jsp" />
        </jsp:include>
        <% } else { %>
        <jsp:include page="common/header.jsp">
            <jsp:param name="loginPath" value="login.jsp" />
            <jsp:param name="registerPath" value="register.jsp" />
            <jsp:param name="logoImgSrc" value="assets/img/AGLogo/Logo2.png" />
            <jsp:param name="indexPath" value="index.jsp" />
        </jsp:include>
        <% } %>

        <!--<img src="assets/img/AGLogo/LogoWordBg.png" class="img_back" alt="image">-->
        <div class="error-area">
            <div class="error-content">

                <% if (response.getStatus() == 404) {%>
                <% if (request.getRemoteUser() != null) { %>
                <img src="../assets/img/404error.png" alt="image123" />
                <% } else { %>
                <img src="assets/img/404error.png" alt="image" />
                <% }%>
                <h2>Oops! An Error Occurred !!!</h2>
                <h1><%= response.getStatus()%><br />Page Not Found</h1>
                <p>The page you are looking for might have been removed had its name changed or is temporarily
                    unavailable.
                </p>

                <% } else if (response.getStatus() == 403) {%>
                <h2>Oops! An Error Occurred !!!</h2>
                <% if (request.getRemoteUser() != null) { %>
                <img src="../assets/img/403error.png" alt="image" width="350px" />
                <% } else { %>
                <img src="assets/img/403error.png" alt="image" width="350px"/>
                <% }%>
                <h1><%= response.getStatus()%><br/>You Do Not Have Access!!!</h1>
                <p>Error details: Access is forbidden to the requested page</p>

                <%} else if (response.getStatus() == 500) {%>
                <h2>Oops! An Error Occurred !!!</h2>
                <% if (request.getRemoteUser() != null) { %>
                <img src="../assets/img/500error.png" alt="image" width="380px" />
                <% } else { %>
                <img src="assets/img/500error.png" alt="image" width="380px" />
                <% }%>
                <h1><%= response.getStatus()%><br />Internal Server Error</h1>
                <p>Error details: <%= exception.getMessage()%></p>

                <%} else {%>           
                <h2>Oops! Something Went Wrong !!!</h2>
                <h1>The error code is <%= response.getStatus()%></h1>
                <p>Please return to the home page by clicking the button below.</p>
                <% }%>

                <% if (request.getRemoteUser() != null) {%>
                <a href="../ToStaffDashboard"><button class="home-btn pulse">Back To Home</button></a>
                <% } else { %>
                <a href="index.jsp"><button class="home-btn pulse">Back To Home</button></a>
                <% }%>
            </div>
        </div>

        <% if (request.getRemoteUser() != null) { %>
        <jsp:include page="common/footer.jsp" >
            <jsp:param name="faviconImgSrc" value="../assets/img/AGLogo/favicon.png"/>
        </jsp:include>
        <% } else { %>
        <jsp:include page="common/footer.jsp" >
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png"/>
        </jsp:include>
        <% } %>


        <!-- jQuery Frameworks
        ============================================= -->
        <script src="https://kit.fontawesome.com/20cf2c878d.js" crossorigin="anonymous"></script>
        <!-- Custom bootstrap -->
        <% if (request.getRemoteUser() != null) {%>
        <script src="../assets/js/jquery-1.12.4.min.js"></script>
        <script src="../assets/js/bootstrap.min.js"></script>
        <script src="../assets/js/bootsnav.js"></script>
        <script src="../assets/js/main.js"></script>

        <!-- Pre-loading -->
        <script src="../assets/js/preloader.js"></script>

        <!-- View Cart -->
        <script src="../assets/js/cartOpen.js"></script>
        <% } else { %>
        <script src="assets/js/jquery-1.12.4.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/bootsnav.js"></script>
        <script src="assets/js/main.js"></script>

        <!-- Pre-loading -->
        <script src="assets/js/preloader.js"></script>

        <!-- View Cart -->
        <script src="assets/js/cartOpen.js"></script>
        <% }%>
        <!-- End jQuery Frameworks
        ============================================= -->
    </body>
</html>