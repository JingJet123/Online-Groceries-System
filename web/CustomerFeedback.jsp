<%-- 
    Document   : CustomerFeedback
    Created on : Apr 8, 2022, 7:49:56 PM
    Author     : Lee Jing Jet
--%>

<%@page import="entity.OrderDetails"%>
<%@page import="java.util.List"%>
<%@page import="entity.Order"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<Order> odList = (List<Order>) application.getAttribute("orderList");
    Order currentOrder = null;
    for (Order order : odList) {
        if (order.getOrderId().equals(Long.parseLong(request.getParameter("orderID")))) {
            currentOrder = order;
        }
    }
%>
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

        <link href="assets/css/CustomerFeedback.css" rel="stylesheet" />
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
            <jsp:param name="profileClass" value="nav__link" />
            <jsp:param name="orderClass" value="nav__link " />
            <jsp:param name="feedbackClass" value="nav__link active" />
        </jsp:include>

        <form action="CustomerFeedback">
            <div class="feedbackform">
                <div class="form-group col-md-6">
                    <div class="col-8 card-title">
                        <h4 class="mb-4">Order ID: <%=currentOrder.getOrderId()%></h4>
                        <p>Date: <%=currentOrder.getOrderDate()%></p>
                    </div>
                    <!-- quantity -->
                    <div class="form-group col-md-6">
                        <h5>Total Amount: RM<%= String.format("%.2f", currentOrder.getTotalAmount())%></h5>
                    </div>
                    <div class="form-group col-md-6">
                        <input type="hidden" name="orderId" value="<%=currentOrder.getOrderId()%>" />
                        <label for="inputState">Category</label>
                        <select name="category" id="inputState" class="form-control" required>
                            <option selected>Choose...</option>
                            <option value="Service">Service</option>
                            <option value="Delivery">Delivery</option>
                            <option value="Product">Product</option>
                            <option value="Refund">Refund</option>
                        </select>
                        <div class="form-group">
                            <label for="exampleFormControlTextarea1">Feedback</label>
                            <textarea name="message" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
                        </div>
                        <input type="submit" class="btn btn-primary fb-btn" value="Submit"/>
                    </div>
                </div>
        </form>

        <script src="assets/js/custCheckOrder.js"></script>
        <!-- For Cust Side Menue -->
        <script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
        <script src="assets/js/custSide.js"></script>

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

