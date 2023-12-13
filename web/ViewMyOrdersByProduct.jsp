<%-- 
    Document   : ViewMyOrdersByProduct
    Created on : Apr 9, 2022, 12:49:00 AM
    Author     : Joey Kok Yen Ni
--%>

<%@page import="java.util.List"%>
<%@page import="entity.OrderDetails"%>
<%@page import="java.sql.Date"%>
<%@page import="entity.Customer"%>
<%
    Customer customer = (Customer) session.getAttribute("customer");
    String prodName = request.getParameter("keyword");
    List<OrderDetails> ods = null;
    if (session.getAttribute("matchedProductOrderDetails") != null) {
        ods = (List<OrderDetails>) session.getAttribute("matchedProductOrderDetails");
    }
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

        <link href="assets/css/custCheckOrder.css" rel="stylesheet" />
        <!-- ========== For Cust Side Menu ========== -->
        <link href="assets/css/custSide.css" rel="stylesheet" />

        <!-- ========== End Stylesheet ========== -->

        <style>

             .view-detail-btn {
                background: #10462b none repeat scroll 0 0;
                border: medium none;
                border-radius: 5px;
                box-shadow: inherit;
                color: #ffffff;
                font-family: "Poppins",sans-serif;
                font-weight: 700;
                padding: 8px;
                margin: 10px 1.0rem;
                text-transform: uppercase;
                width: fit-content;
                transition: 0.5s ease-in-out;
            }

            .view-detail-btn{
                background-color: #f8d152 !important;
            }

            .view-detail-btn:hover {
                background-color: #ff9028 !important;
                box-shadow: 4px 4px 3px 1px #6f6e6e;
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
            <jsp:param name="orderPath" value="ShowCustOrderList" />
            <jsp:param name="feedbackPath" value="MyFeedback.jsp" />
            <jsp:param name="profileClass" value="nav__link" />
            <jsp:param name="orderClass" value="nav__link active" />
            <jsp:param name="feedbackClass" value="nav__link" />
        </jsp:include>


        <div  style="margin-left: 28%; margin-right: 5%;" class="topPage-bc mt-5">
            <nav aria-label="breadcrumb">
                <ol style="margin-bottom: 0" class="breadcrumb">
                    <li class="breadcrumb-item"><a style="color: black" href="ShowCustOrderList">My Orders</a></li>
                    <li class="breadcrumb-item" aria-current="page">Search Order Result</li>
                </ol>
            </nav>
        </div>
        <div style="margin-left: 28%; margin-right: 5%; padding: 0% 2% ; min-height: 70vh" class=" rounded bg-white">
            <div class="p-3 py-5">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h2 class="text-right">Order Result For "<%=prodName%>"</h2>
                </div>
                <div class="container my-5">
                    <div class="shadow-4 rounded-5 overflow-hidden">
                        <% if (!ods.isEmpty()) { %>
                        <table class="table align-middle mb-0 bg-white">
                            <thead class="bg-light">
                                <tr>
                                    <th>Matches Product</th>
                                    <th>Order ID (Date)</th>
                                    <th>Order Status</th>
                                    <th>Order Amount</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (OrderDetails od : ods) {%>
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <img
                                                src="data:image/jpg;base64,<%=od.getProduct().encodeImageToString()%>"
                                                alt=""
                                                style="width: 45px; height: 45px"
                                                class="rounded-circle"
                                                />
                                            <div class="ms-3">
                                                <p class="fw-bold mb-1"><%=od.getProduct().getProdName()%>e</p>
                                                <p class="text-muted mb-0"><%=od.getProduct().getSupplier()%></p>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <p class="fw-bold mb-1"><%=od.getOrder().getOrderId()%></p>
                                        <p class="text-muted mb-0">Date:<%=od.getOrder().getOrderDate()%></p>
                                    </td>
                                    <td>
                                        <% if (od.getOrder().getOrderStatus().equals("Pending")) { %>
                                        <span class="badge badge-secondary rounded-pill">Pending</span>
                                        <%} else if (od.getOrder().getOrderStatus().equals("Packaging")) { %>
                                        <span class="badge badge-info rounded-pill">Packaging</span>
                                        <%} else if (od.getOrder().getOrderStatus().equals("Shipping")) { %>
                                        <span class="badge badge-primary rounded-pill">Shipping</span>
                                        <%} else if (od.getOrder().getOrderStatus().equals("Delivered")) { %>
                                        <span class="badge badge-success rounded-pill">Delivered</span>
                                        <%} else if (od.getOrder().getOrderStatus().equals("Cancelled")) { %>
                                        <span class="badge badge-danger rounded-pill">Cancelled</span>
                                        <% }%>
                                        <p class="text-muted mb-0">Paid:<%=od.getOrder().getPayment().getPayType()%></p>
                                    </td>
                                    <td><%= String.format("RM%.2f", od.getOrder().getTotalAmount())%></td>
                                    <td>
                                        <button class="view-detail-btn "><a href="GetOrderDetails?orderId=<%=od.getOrder().getOrderId()%>"><h5>View Details</h5></a></button>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                        <% } else {%>
                        <div class="row justify-content-center">
                        <img src="assets/img/no-record-found.png"/>
                        </div>
                        <% } %>
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
    </body>
</html>


