<%-- 
    Document   : EditInformation
    Created on : Apr 5, 2022, 9:58:40 PM
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
        <link rel="shortcut icon" href="assets/img/AGLogo/favicon.png" type="image/x-icon" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css" rel="stylesheet"/>

        <!-- ========== Start Stylesheet ========== -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />
        <link href="style.css" rel="stylesheet" />
        <link href="layout.css" rel="stylesheet" />

        <link href="assets/css/editInfo.css" rel="stylesheet" />
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
            <jsp:param name="profilePath" value="MyProfile.jsp" />
            <jsp:param name="orderPath" value="ShowCustOrderList" />
            <jsp:param name="feedbackPath" value="ToFeedback?path=MyFeedback.jsp" />
            <jsp:param name="profileClass" value="nav__link active" />
            <jsp:param name="orderClass" value="nav__link" />
            <jsp:param name="feedbackClass" value="nav__link" />
        </jsp:include>

        <div  style="margin-left: 28%; margin-right: 5%;" class="topPage-bc mt-5">
            <nav aria-label="breadcrumb">
                <ol style="margin-bottom: 0" class="breadcrumb">
                    <li class="breadcrumb-item"><a href="MyProfile.jsp">My Profile</a></li>
                    <li class="breadcrumb-item" aria-current="page">Edit Profile</li>
                </ol>
            </nav>
        </div>
        <div style="margin-left: 28%; margin-right: 5%; padding: 0% 2% ;" class=" rounded bg-white">
            <div class="p-3 py-5">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4 class="text-right">Profile Settings</h4>
                </div>
                <form action="EditCustomer" method="POST" enctype="multipart/form-data">
                    <div style="margin-left: 5%" class=" drop-zone">
                        <span class="drop-zone_prompt">Drag-and-Drop or Click file to upload</span>
                        <input type="file" name="photo" accept="image/*" class="drop-zone_text">
                    </div>
                    <div class="row mt-3">

                        <div class="col-md-6">
                            <label class="labels">Name</label>
                            <input required type="text" name="name" class="form-control" value="<%= customer.getCustName()%>"
                                   pattern="[A-Za-z\s]+[.]?[/]?[A-Za-z\s]*" title="This is not a valid name">
                        </div>
                        <div class="col-md-6">
                            <label class="labels">User Name</label>
                            <input required type="text" name="username" class="form-control" value="<%= customer.getUsername()%>">
                        </div>
                        <div class="col-md-12">
                            <label class="labels">Email</label>
                            <input required type="text" name="email" class="form-control" value="<%= customer.getCustEmail()%>">
                        </div>
                        <div class="col-md-12">
                            <label class="labels">Contact</label>
                            <input required type="text" name="contact" class="form-control" value="<%= customer.getCustContact()%>"
                                   pattern="\d{3}[-]\d{7,8}" title="This is not a valid contact number">
                        </div>
                        <div class="col-md-12">
                            <label class="labels">Address</label>
                            <input required type="text" name="address" class="form-control" value="<%= customer.getCustAddress()%>">
                        </div>
                    </div>
                    <div class="mt-5 text-center">
                        <input type="submit" class="btn btn-primary profile-button" value="Save Changes"/>
                    </div>
                </form>
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="assets/js/uploadImage.js"></script>
    </body>
</html>


