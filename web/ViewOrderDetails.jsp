<%-- Document : index Created on : Mar 7, 2022, 1:29:39 AM Author : Joey Kok Yen Ni --%> 
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Calendar"%>
<%@page import="entity.*"%>
<%@page import="java.util.List"%>
<%@page errorPage="errorPage.jsp" contentType="text/html"
        pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="customer" class="entity.Customer" scope="session"></jsp:useBean>
<jsp:setProperty name="customer" property="*" />
<%  List<OrderDetails> orderDetails = null;
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" integrity="sha512-PgQMlq+nqFLV4ylk1gwUOgm6CtIIXkKwaIHp/PAIWHzig/lKZSEGKEysh0TCVbHJXCLN7WetD8TFecIky75ZfQ==" crossorigin="anonymous" />

        <!-- ========== Start Stylesheet ========== -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />
        <link href="style.css" rel="stylesheet" />
        <link href="layout.css" rel="stylesheet" />

        <link href="assets/css/orderCheckOut.css" rel="stylesheet" />
        <!-- ========== End Stylesheet ========== -->
    </head>
    <body class="bg-light" onload="endPreLoading()">
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
                    <li class="breadcrumb-item active" aria-current="page">Order Details</li>
                </ol>
            </nav>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-10 col-11 mx-auto">
                    <div class="row mt-5 gx-3">
                        <!-- left side div -->
                        <div class="col-md-12 col-lg-8 col-11 mx-auto main_cart mb-lg-0 mb-5 shadow">

                            <h2 class="py-4 font-weight-bold">Order - <%=orderDetails.size()%> items (Order ID:<%=order.getOrderId()%>)</h2>

                            <% if (!orderDetails.isEmpty()) {
                                    for (OrderDetails od : orderDetails) {%>
                            <!--===================Start Product ==================-->
                            <div class="card p-4">

                                <div class="row">
                                    <!-- cart images div -->
                                    <div class="col-md-5 col-11 mx-auto bg-light d-flex justify-content-center align-items-center shadow product_img">
                                        <img src="data:image/jpg;base64,<%= od.getProduct().encodeImageToString()%>"
                                             class="img-fluid"
                                             alt="cart img" />
                                    </div>
                                    <!-- cart product details -->
                                    <div class="col-md-7 col-11 mx-auto px-4 mt-2">
                                        <div class="row">
                                            <!-- product name  -->
                                            <div class="col-8 card-title">
                                                <h1 class="mb-8 product_name"><%=od.getProduct().getProdName()%></h1>
                                                <p class="mb-2">SUPPLIER:<%= od.getProduct().getSupplier()%></p>
                                                <p class="mb-2">CATEGORY:<%= od.getProduct().getSubCategoryId().getSubCategoryName()%></p>
                                                <p class="mb-3">UNIT PRICE:<%= od.getProduct().getUnitPrice()%></p>
                                            </div>
                                            <!-- quantity-->
                                            <div class="col-4">
                                                <h5>Ordered Quantity: <%= od.getQuantity()%></h5>
                                            </div>
                                        </div>
                                        <!-- View Product -->
                                        <div class="row">
                                            <div class="col-8 d-flex justify-content-between remove_wish">
                                                <% if (order.getOrderStatus().equals("Delivered")) {                                                        %>
                                                <a href="GetSingleProduct?prodId=<%=od.getProduct().getProdId()%>"><button class="btn btn-primary text-uppercase">View/Rate Product</button></a>
                                                <% } else {%>
                                                <a href="GetSingleProduct?prodId=<%=od.getProduct().getProdId()%>"><button class="btn btn-primary text-uppercase">View Product</button></a>                                              
                                                <% }                                                    %>
                                            </div>
                                            <div class="col-4 d-flex justify-content-end price_money">
                                                <h3>RM<span id="itemval1"><%=String.format("%.2f", od.calcSubTotal())%></span></h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr/>
                            <!--===================End Product ==================-->
                            <%  }
                                }%>
                        </div>
                        <!-- right side div -->
                        <% String color = "#666666";
                            String odS = order.getOrderStatus();
                            if (odS.equals("Pending")) {
                                color = "#ffcc00";
                            }
                            if (odS.equals("Packaging")) {
                                color = "#009999";
                            }
                            if (odS.equals("Shipping")) {
                                color = "#0000ff";
                            }
                            if (odS.equals("Delivered")) {
                                color = "#009900";
                            }
                            if (odS.equals("Cancelled")) {
                                color = "#ff0000";
                            }
                        %>
                        <div class="col-md-12 col-lg-4 col-11 mx-auto mt-lg-0 mt-md-5">
                            <div class="right_side p-3 shadow bg-white">
                                <h3 class="product_name mb-3">Order Status</h3>
                                <div class="price_indiv d-flex " >
                                    <h4 style="color:<%=color%>"><%=order.getOrderStatus()%></h4>
                                </div>
                                <h3 class="product_name mb-3">Order Date</h3>
                                <div class="price_indiv d-flex ">
                                    <h4><%=order.getOrderDate()%></h4>
                                </div>
                                <h3 class="product_name mb-3">Total Order Amount</h3>
                                <div class="price_indiv d-flex ">
                                    <h4>RM<%=String.format("%.2f", order.getTotalAmount())%></h4>
                                </div>
                                <h3 class="product_name mb-3">Payment Method (ID: <%=payment.getPayId()%>)</h3>
                                <div class="price_indiv d-flex">
                                    <h4><%=payment.getPayType()%></h4>
                                </div>

                            </div>
                            <!-- Shipping Details-->
                            <div class="discount_code mt-3 shadow">
                                <div class="card">
                                    <div class="card-body">
                                        <a class="d-flex justify-content-between" data-toggle="collapse" href="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                                            Shipping Details 
                                            <span><i class="fas fa-chevron-down pt-1"></i></span>
                                        </a>
                                        <div class="collapse" id="collapseExample">
                                            <div class="mt-3">
                                                <h4>Shipping Address</h4>
                                                <p><%=shippingDetails.getShippingAddress()%></p> 

                                            </div>
                                            <% if (order.getOrderStatus().equals("Pending") || order.getOrderStatus().equals("Packaging")) {%>
                                            <div class="mt-3">
                                                <h4>Estimated Shipping Date</h4>
                                                <p><%= order.displayDate(order.getEstimatedDeliveryDates()[0])%> - <%= order.displayDate(order.getEstimatedDeliveryDates()[1])%> </p> 
                                            </div>
                                            <% } %>
                                            <% if (order.getOrderStatus().equals("Shipping")) {%>
                                            <div class="mt-3">
                                                <h4>Shipped Out At</h4>
                                                <p><%=shippingDetails.getShippingDate()%> </p> 
                                            </div>
                                            <% } %>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <% if (order.getOrderStatus().equals("Delivered") && order.getFeedbackList().isEmpty()) {%>
                            <div class="mt-3 shadow p-3 bg-white" style="text-align: center">
                                <div class="pt-1">
                                    <h5 class="mb-4">Provide Feedback</h5>
                                    <a href="CustomerFeedback.jsp?orderID=<%= order.getOrderId()%>"><button class="btn btn-secondary text-uppercase">Feedback</button></a>
                                </div>
                            </div>     
                            <% }%>
                            <% if (order.getOrderStatus().equals("Pending")) {%>
                            <div class="mt-3 shadow p-3 bg-white" style="text-align: center">
                                <div class="pt-1">
                                    <h5 class="mb-4">Cancel Order</h5>
                                    <a href="GetOrderDetails?orderId=<%=order.getOrderId()%>&requestTo=cancel" onclick="return confirm('Proceed to Cancellation Page?')"><button class="btn btn-danger text-uppercase">Cancel</button></a>
                                </div>
                            </div>     
                            <% }%>
                        </div>
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

        <!-- View Cart -->
        <script src="assets/js/cartOpen.js"></script>

        <!-- Accept Cookies 
        (Note: If got time need to make in java code)
               Need find ways to click only one time if a user has clicked before -->
        <!--<script src="assets/js/acceptDrop.js"></script>-->


        <!-- End jQuery Frameworks
        ============================================= -->
        <script type="text/javascript">
                                        var product_total_amt = document.getElementById("product_total_amt");
                                        var shipping_charge = document.getElementById("shipping_charge");
                                        var total_cart_amt = document.getElementById("total_cart_amt");
                                        var discountCode = document.getElementById("discount_code1");
                                        const decreaseNumber = (incdec, itemprice) => {
                                            var itemval = document.getElementById(incdec);
                                            var itemprice = document.getElementById(itemprice);
                                            console.log(itemprice.innerHTML);
                                            // console.log(itemval.value);
                                            if (itemval.value <= 0) {
                                                itemval.value = 0;
                                                alert("Negative quantity not allowed");
                                            } else {
                                                itemval.value = parseInt(itemval.value) - 1;
                                                itemval.style.background = "#fff";
                                                itemval.style.color = "#000";
                                                itemprice.innerHTML = parseInt(itemprice.innerHTML) - 15;
                                                product_total_amt.innerHTML =
                                                        parseInt(product_total_amt.innerHTML) - 15;
                                                total_cart_amt.innerHTML =
                                                        parseInt(product_total_amt.innerHTML) +
                                                        parseInt(shipping_charge.innerHTML);
                                            }
                                        };
                                        const increaseNumber = (incdec, itemprice) => {
                                            var itemval = document.getElementById(incdec);
                                            var itemprice = document.getElementById(itemprice);
                                            // console.log(itemval.value);
                                            if (itemval.value >= 5) {
                                                itemval.value = 5;
                                                alert("max 5 allowed");
                                                itemval.style.background = "red";
                                                itemval.style.color = "#fff";
                                            } else {
                                                itemval.value = parseInt(itemval.value) + 1;
                                                itemprice.innerHTML = parseInt(itemprice.innerHTML) + 15;
                                                product_total_amt.innerHTML =
                                                        parseInt(product_total_amt.innerHTML) + 15;
                                                total_cart_amt.innerHTML =
                                                        parseInt(product_total_amt.innerHTML) +
                                                        parseInt(shipping_charge.innerHTML);
                                            }
                                        };
                                        const discount_code = () => {
                                            let totalamtcurr = parseInt(total_cart_amt.innerHTML);
                                            let error_trw = document.getElementById("error_trw");
                                            if (discountCode.value === "thapa") {
                                                let newtotalamt = totalamtcurr - 15;
                                                total_cart_amt.innerHTML = newtotalamt;
                                                error_trw.innerHTML = "Hurray! code is valid";
                                            } else {
                                                error_trw.innerHTML = "Try Again! Valid code is thapa";
                                            }
                                        };
        </script>
    </body>
</html>
