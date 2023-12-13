<%-- 
Document : CancelConfirmation
Created on : Mar 28, 2022, 8:48:51 PM 
Author : Joey Kok Yen Ni 
--%> 
<%@page import="java.util.List"%>
<%@page import="entity.*"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%
    //Obtain session attribute to be displayed (Order details)
    List<OrderDetails> orderDetails = null;
    Payment payment = null;
    ShippingDetails shippingDetails = null;
    Order order = null;
    if (session.getAttribute("custHisOrderDetails") != null) {
        orderDetails = (List<OrderDetails>) session.getAttribute("custHisOrderDetails");
    }
    if (session.getAttribute("custHisPayment") != null) {
        payment = (Payment) session.getAttribute("custHisPayment");
    }
    if (session.getAttribute("custHisShip") != null) {
        shippingDetails = (ShippingDetails) session.getAttribute("custHisShip");
    }
    if (session.getAttribute("custHisOrder") != null) {
        order = (Order) session.getAttribute("custHisOrder");
    }
%>

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
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
            />

        <!-- ========== Start Stylesheet ========== -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />
        <link href="style.css" rel="stylesheet" />
        <link href="layout.css" rel="stylesheet" />

        <!-- ========== End Stylesheet ========== -->

        <style>
            body {
                background-color: hsl(0, 0%, 94%)
            }
            .buttons button{
                position: relative;
                height: 50px;
                width: 150px;
                border-radius: 50px;
                border: none;
                outline: none;
                background: #111;
                color: #fff;
                font-weight: 500;
                letter-spacing: 2px;
                text-transform: uppercase;
                cursor: pointer;
                transition: background 0.5s;
            }
            .buttons button:first-child:hover{
                background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);
                background-size: 400%;
            }
            .buttons button:last-child:hover{
                background: linear-gradient(90deg, #fa7199, #f5ce62, #e43603, #fa7199);
                background-size: 400%;
            }
            .buttons button:first-child:before,
            .buttons button:last-child:before{
                content: '';
                position: absolute;
                background: inherit;
                top: -5px;
                right: -5px;
                bottom: -5px;
                left: -5px;
                border-radius: 50px;
                filter: blur(20px);
                opacity: 0;
                transition: opacity 0.5s;
            }
            .buttons button:first-child:hover:before,
            .buttons button:last-child:hover:before{
                opacity: 1;
                z-index: -1;
            }
            .buttons button:hover{
                z-index: 1;
                animation: glow 8s linear infinite;
            }
            @keyframes glow {
                0%{
                    background-position: 0%;
                }
                100%{
                    background-position: 400%;
                }
            }
            .sure-box{
                background: #fff;
                margin: 2%;
                padding: 4%;
                box-shadow: 10px 10px rgb(177 174 174 / 15%);
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
        
        <!-- Breadcrumb at page top -->
        <div class="topPage-bc">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="index.jsp">Home</a></li>
                    <li class="breadcrumb-item"><a href="ShowCustOrderList">My Orders</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Cancel Order</li>
                </ol>
            </nav>
        </div>

        <!-- Any new contents place here -->
        <div class="container mt-5">
            <div class="row">

                <div class="col-3 benefit-center box">
                    <div class="benefit" id="tv">
                        <span class="benefit-icon"><i class='bx bxs-truck'></i></span>
                        <h4>Enjoy Free Shipping</h4><h4>7-days Returns</h4>
                    </div>

                    <div class="benefit" id="tv">
                        <span class="benefit-icon"><i class="bx bx-purchase-tag"></i></span>
                        <h4>Convenient Online</h4>
                    </div>

                    <div class="benefit" id="tv">
                        <span class="benefit-icon"><i class='bx bxs-category-alt' ></i></span>
                        <h4>Satisfy All One Needs</h4>
                    </div>

                    <div class="benefit" id="tv">
                        <span class="benefit-icon"><i class="bx bx-headphone"></i></span>
                        <h4>24/7 Service Support</h4>
                    </div>
                </div>

                <!--================ Cancel Order Acknowledgement ================-->
                <div class="col pt-1 pl-5">
                    <div class="row justify-content-center sure-box">
                        <h2>Are you sure to<span style="color: #ff3333"> cancel</span> the Order (ID:<%=order.getOrderId()%>)?</h2>
                        <div class="buttons mt-3 mb-3">
                            <button><a href="CancelOrder?orderId=<%=order.getOrderId()%>" onclick="return confirm('Important: This action cannot be undone.')"><h4 style="color:white; margin-bottom: 0">Yes</h4></a></button>
                            <button style="margin-left: 5px"><a href="ShowCustOrderList"><h4 style="color:white; margin-bottom: 0">No</h4></a></button>
                        </div>
                    </div>

                    <!--================ Order Details ================-->
                    <div class="shadow-4 rounded-5 overflow-hidden">
                        <table class="table align-middle mb-0 bg-white">
                            <thead class="bg-light">
                                <tr>
                                    <th>Item</th>
                                    <th>Item Name</th>
                                    <th>Quantity</th>
                                    <th>Subtotal (Exclude Discount)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%for (OrderDetails od : orderDetails) {%>
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <img
                                                src="data:image/jpg;base64,<%= od.getProduct().encodeImageToString()%>"
                                                alt=""
                                                style="width: 45px; height: 45px"
                                                class="rounded-circle"
                                                />
                                        </div>
                                    </td>
                                    <td>
                                        <p class="fw-bold mb-1"><%=od.getProduct().getProdName()%></p>
                                        <p class="text-muted mb-0"><%=od.getProduct().getSubCategoryId().getSubCategoryName()%></p>
                                    </td>
                                    <td>
                                        <span class="badge badge-success rounded-pill"><%=od.getQuantity()%></span>
                                    </td>
                                    <td><%=String.format("RM%.2f", od.getSubTotal())%></td>
                                </tr>
                                <% }%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="common/alertPop.jsp">
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType")%>" />
        </jsp:include>

        <jsp:include page="common/footer.jsp" >
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png"/>
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

        <!--View Cart--> 
        <script src="assets/js/cartOpen.js"></script>

        <!-- End jQuery Frameworks
        ============================================= -->
    </body>
</html>
