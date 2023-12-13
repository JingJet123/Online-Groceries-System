<%-- 
    Document   : MyProfile
    Created on : Apr 3, 2022, 1:39:53 PM
    Author     : Lee Jing Jet
--%>
<%@page import="entity.Customer"%>
<%
    Customer customer = (Customer) session.getAttribute("customer");
%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />


        <!-- ========== Page Title ========== -->
        <title>AG Market</title>

        <!-- ========== Favicon Icon ========== -->
        <link
            rel="shortcut icon"
            href="assets/img/AGLogo/favicon.png"
            type="image/x-icon"
            />
        <link
            href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css"
            rel="stylesheet"
            />

        <!-- ========== Start Stylesheet ========== -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />
        <link href="style.css" rel="stylesheet" />
        <link href="layout.css" rel="stylesheet" />

        <link href="assets/css/profile.css" rel="stylesheet" />
        <!-- ========== For Cust Side Menu ========== -->
        <link href="assets/css/custSide.css" rel="stylesheet" />

        <!-- ========== End Stylesheet ========== -->
    </head>
    <body onload="endPreLoading()">
        <!-- Preloader Start -->
        <jsp:include page="common/preloader.jsp">
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png" />
        </jsp:include>
        <!-- Preloader Ends -->

        <jsp:include page="common/header.jsp">
            <jsp:param name="loginPath" value="login.jsp" />
            <jsp:param name="registerPath" value="register.jsp" />
            <jsp:param name="logoImgSrc" value="assets/img/AGLogo/Logo2.png" />
            <jsp:param name="indexPath" value="index.jsp" />
        </jsp:include>

        <!-- For Cust Side Menue -->
        <jsp:include page="common/custSideMenu.jsp">
            <jsp:param name="profilePath" value="#" />
            <jsp:param name="orderPath" value="ShowCustOrderList" />
            <jsp:param name="feedbackPath" value="ToFeedback?path=MyFeedback.jsp" />
            <jsp:param name="profileClass" value="nav__link active" />
            <jsp:param name="orderClass" value="nav__link " />
            <jsp:param name="feedbackClass" value="nav__link" />
        </jsp:include>

        <div class="page-content page-container" id="page-content">
            <div class="mt-5">
                <div class="row container d-flex justify-content-center">
                    <div class="col-6">
                        <div class="card user-card-full">
                            <div class="row m-l-0 m-r-0">
                                <div class="col-sm-4 bg-c-lite-green user-profile">
                                    <div class="card-block text-center text-white">
                                        <% if (customer.getProfileImg() != null) {%>
                                        <div class="m-b-25"> <img src="data:image/jpg;base64,<%=customer.encodeImageToString()%>" class="img-radius" id="profile" alt="User-Profile-Image"> </div>
                                            <% } else {%>
                                        <div class="m-b-25"> <img src="assets/img/profilePictureIcon.png" class="img-radius" id="profile" alt="User-Profile-Image"> </div>                                        
                                            <% }%> 
                                        <h6 class="f-w-900">User Name</h6>
                                        <p><%= customer.getUsername()%></</p> <i class=" mdi mdi-square-edit-outline feather icon-edit m-t-10 f-16"></i>
                                    </div>
                                </div>
                                <div class="col-sm-8">
                                    <div class="card-block">
                                        <h6 class="m-b-20 p-b-5 b-b-default f-w-600">Information</h6>
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <p class="m-b-10 f-w-600">Name</p>
                                                <h6 class="text-muted f-w-400"><%= customer.getCustName()%></h6>
                                            </div>
                                            <div class="col-sm-6">
                                                <p class="m-b-10 f-w-600">Email</p>
                                                <h6 class="text-muted f-w-400"><%= customer.getCustEmail()%></h6>
                                            </div>
                                            <div class="col-sm-6">
                                                <p class="m-b-10 f-w-600">Phone</p>
                                                <h6 class="text-muted f-w-400"><%= customer.getCustContact()%></h6>
                                            </div>
                                            <div class="col-sm-6">
                                                <p class="m-b-10 f-w-600">Address</p>
                                                <h6 class="text-muted f-w-400"> <%= customer.getCustAddress()%></h6>
                                            </div>
                                        </div>
                                    </div>
                                    <a id="myButton" class="btn btn-outline-success" href="EditInformation.jsp">Edit</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="assets/js/custCheckOrder.js"></script>
        <!-- For Cust Side Menue -->
        <script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
        <script src="assets/js/custSide.js"></script>

        <jsp:include page="common/alertPop.jsp">
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType")%>" />
        </jsp:include>

        <!-- View Cart -->
        <script src="assets/js/cartOpen.js"></script>

        <!-- jQuery Frameworks
                ============================================= -->
        <!-- Custom bootstrap -->
        <script src="assets/js/jquery-1.12.4.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/bootsnav.js"></script>
        <script src="assets/js/main.js"></script>
        <!-- Pre-loading -->
        <script src="assets/js/preloader.js"></script>

        <!-- End jQuery Frameworks
            ============================================= -->
    </body>
</html>

