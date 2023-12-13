<%-- 
    Document   : StaffLoginError
    Created on : Mar 26, 2022
    Author     : Lee Jia Jie
--%>

<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- ========== Page Title ========== -->
        <title>Login Error</title>

        <!-- ========== Favicon Icon ========== -->
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <!-- ========== Start Stylesheet ========== -->
        <link href="../assets/css/StaffLoginError.css" rel="stylesheet">
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/bootsnav.css" rel="stylesheet" />
        <link href="../assets/css/responsive.css" rel="stylesheet" />    
        <link href="../style.css" rel="stylesheet">
        <link href="../layout.css" rel="stylesheet">

    </head>
    <body>

        <jsp:include page="common/header.jsp">
            <jsp:param name="loginPath" value="../login.jsp" />
            <jsp:param name="registerPath" value="../register.jsp" />
            <jsp:param name="logoImgSrc" value="../assets/img/AGLogo/Logo2.png" />
            <jsp:param name="indexPath" value="../index.jsp" />
        </jsp:include>

        <div>
            <img src="../assets/img/loginError.png" alt="image" class="errorImage"/>
            <h1>Oops! Login Failed</h1>
            <p class="error-text">Something is technically wrong that's why you are not able to log in.</p>
            <p class="error-text">Why don't you try again? This time it's gonna work :)</p>
            <a href="../ToStaffDashboard"><button class="errorButton">Back to Login Page</button></a>
        </div>


        <jsp:include page="common/footer.jsp" >
            <jsp:param name="faviconImgSrc" value="../assets/img/AGLogo/favicon.png"/>
        </jsp:include>
        <!-- jQuery Frameworks
         ============================================= -->
        <!-- Custom bootstrap -->
        <script src="../assets/js/jquery-1.12.4.min.js"></script>
        <script src="../assets/js/bootstrap.min.js"></script>
        <script src="../assets/js/bootsnav.js"></script>
        <script src="../assets/js/main.js"></script>

        <script src="https://kit.fontawesome.com/c6f1f55876.js" crossorigin="anonymous"></script>

        <!-- View Cart -->
        <script src="../assets/js/cartOpen.js"></script>

        <!-- End jQuery Frameworks
        ============================================= -->
    </body>

</html>
