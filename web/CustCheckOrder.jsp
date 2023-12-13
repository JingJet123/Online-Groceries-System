<%-- 
Document : CustCheckOrder 
Created on : Mar 29, 2022, 8:03:47 PM 
Author : Joey Kok Yen Ni
--%> 
<%@page import="java.util.*"%>
<%@page import="entity.*"%>
<%@page errorPage="errorPage.jsp" contentType="text/html"
        pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    //Get All orders placed by the customer
    List<Order> odList = null;
    if (session.getAttribute("checkOrderList") != null) {
        odList = (List<Order>) session.getAttribute("checkOrderList");
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

        <link href="assets/css/custCheckOrder.css" rel="stylesheet" />
        <!-- ========== For Cust Side Menu ========== -->
        <link href="assets/css/custSide.css" rel="stylesheet" />

        <!-- ========== End Stylesheet ========== -->

        <style>
            .sortingA h4:hover{
                color:#ff6600;
            }
            .sortingA h4{
                color:#005f40;
            }
            .sortingA span{
                color:#000;
            }
        </style>
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
            <jsp:param name="orderPath" value="#" />
            <jsp:param name="feedbackPath" value="ToFeedback?path=MyFeedback.jsp" />
            <jsp:param name="profileClass" value="nav__link" />
            <jsp:param name="orderClass" value="nav__link active" />
            <jsp:param name="feedbackClass" value="nav__link" />
        </jsp:include>


        <!--================= Customer Search Order Based on Product name ===================== --> 
        <div class="container  custCheck " style="background-color: rgb(255, 255, 255); min-height: 85vh; padding-top: 5%">
            <div class=" row justify-content-end">
                <a href="#popup1" style="margin-right: 10%;"> <button class="update-btn" style="width: 100%;">Search</button> </a>
            </div>
            
            <!--================= Sorting the orders ===================== --> 
            <div class="row align-items-end">
                <div class="col-5 col-md-3"></div>
                <div class="col-12 col-md-8">
                    <div style="padding:5px" class="row justify-content-end sortingA">
                        <h4 style="color: #000">Sort By: &nbsp&nbsp </h4>
                        <a href="ShowCustOrderList?custViewStatus=<%=session.getAttribute("custViewStatus")%>&sort=asc"><h4>Oldest</h4></a>
                        &nbsp<h4><span> |</span> </h4>&nbsp
                        <a href="ShowCustOrderList?custViewStatus=<%=session.getAttribute("custViewStatus")%>&sort=desc"><h4>Latest</h4></a>
                        &nbsp<h4><span> |</span> </h4>&nbsp
                        <a href="ShowCustOrderList?custViewStatus=<%=session.getAttribute("custViewStatus")%>&amount=highest"><h4>Highest Amount</h4></a>
                        &nbsp<h4><span> |</span> </h4>&nbsp
                        <a href="ShowCustOrderList?custViewStatus=<%=session.getAttribute("custViewStatus")%>&amount=lowest"><h4>Lowest Amount</h4></a>

                    </div>
                    <!--================= Status buttons ===================== -->
                    <div class="row justify-content-center">
                        <%  String status = "", pending = "", packaging = "", shipping = "", delivered = "", cancelled = "";
                            if (request.getParameter("status") != null) {
                                status = request.getParameter("status");
                            }
                            if (status.equals("Packaging")) {
                                packaging = "btn-active";
                            } else if (status.equals("Shipping")) {
                                shipping = "btn-active";
                            } else if (status.equals("Delivered")) {
                                delivered = "btn-active";
                            } else if (status.equals("Cancelled")) {
                                cancelled = "btn-active";
                            } else {
                                pending = "btn-active";
                            }
                        %>
                        <div class="col">
                            <!--If active pass in btn-active, else "" -->
                            <a class="status-btn <%=pending%> " href="ShowCustOrderList?custViewStatus=Pending">Pending</a>
                        </div>
                        <div class="col">
                            <a class="status-btn <%=packaging%>" href="ShowCustOrderList?custViewStatus=Packaging">Packaging</a>
                        </div>
                        <div class="col">
                            <a class="status-btn <%=shipping%>" href="ShowCustOrderList?custViewStatus=Shipping">Shipping</a>
                        </div>
                        <div class="col">
                            <a class="status-btn <%=delivered%>" href="ShowCustOrderList?custViewStatus=Delivered">Delivered</a>
                        </div>
                        <div class="col">
                            <a class="status-btn <%=cancelled%>" href="ShowCustOrderList?custViewStatus=Cancelled">Cancelled</a>
                        </div>
                    </div>

                    <% if (odList.size() != 0) {
                            for (Order order : odList) {

                    %>
                    <div class="row">
                        <!-- ============Start Order Record -->
                        <hr />
                        <div class="card p-4">
                            <div class="row">
                                <!-- product details -->
                                <div class="col-md-12 col-3">
                                    <div class="row">
                                        <!-- product name  -->
                                        <div class="col-8 card-title">
                                            <h4 class="mb-4">Order ID: <%=order.getOrderId()%></h4>
                                            <p>Date: <%=order.getOrderDate()%></p>
                                        </div>
                                        <!-- price -->
                                        <div class="col-4">
                                            <h5>Total Amount: RM<%= String.format("%.2f", order.getTotalAmount())%></h5>
                                        </div>
                                    </div>
                                    <!-- Action button -->
                                    <div class="row">
                                        <div class="col d-flex justify-content-end">
                                            <button class="view-detail-btn "><a href="GetOrderDetails?orderId=<%=order.getOrderId()%>"><h5>View Details</h5></a></button>
                                           <!-- Cancel button avaibale for pending order; Feedback button available for Delivered/Completed order -->
                                            <% if (status.equals("Pending")) {%>
                                            <div style="background-color: #ff6666" class="btn action-btn"><a href="GetOrderDetails?orderId=<%=order.getOrderId()%>&requestTo=cancel" onclick="return confirm('Proceed to Cancellation Page?')"><h5>Cancel</h5></a></div>
                                            <% }%>
                                            <% if (status.equals("Delivered") && order.getFeedbackList().isEmpty()) {%>
                                            <div style="background-color: #6666ff" class="btn action-btn"><a href="CustomerFeedback.jsp?orderID=<%= order.getOrderId()%>"><h5>Feedback</h5></div>
                                            <% }%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- ============End Order Record -->
                    </div>
                    <% }
                    } else { %>          
                    <div class="row justify-content-center default-padding">
                        <h3>The List is Empty</h3>
                    </div>
                    <% }%>
                </div>
            </div>
        </div>

        <!--======= Customer Search Order Based on Product name Form ============== --> 
        <div id="popup1" class="popup-overlay">
            <div class="popup-add">
                <a class="close-popup" href="#">&times;</a>
                <div class="drop-custom">  
                    <h2>Search Your Order History<br/> <span style="font-size: 22px; color: #ff6600"> By Product Name</span></h2>
                    
                    <div class="popup-content">
                        <form action="SearchOrderBasedOnProduct" class="form-popup" method="post">    
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="prodName">Product Name</label>
                                    <input min="3" type="text" class="form-control" id="prodName" placeholder="Product Name" name="prodName" required>
                                </div>
                            </div>
                            <div class="btn-row">
                                <input type="reset" name="reset-btn" class="reset-btn" value="Reset"/>
                                <input type="submit" name="update-btn" class="update-btn" value="Search"/>
                            </div>
                        </form>
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


        <!-- jQuery Frameworks
        ============================================= -->
        <!-- Custom bootstrap -->
        <script src="assets/js/jquery-1.12.4.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/bootsnav.js"></script>
        <script src="assets/js/main.js"></script>

        <!-- Pre-loading -->
        <script src="assets/js/preloader.js"></script>

        <!-- View Cart -->
        <script src="assets/js/cartOpen.js"></script>

        <!-- End jQuery Frameworks
        ============================================= -->
    </body>
</html>
