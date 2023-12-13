<%-- 
Document : OrderCheckOut
Created on : Mar 7, 2022, 1:29:39 AM 
Author : Joey Kok Yen Ni
--%> 
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Calendar"%>
<%@page import="entity.*"%>
<%@page import="java.util.List"%>
<%@page errorPage="errorPage.jsp" contentType="text/html"
        pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="customer" class="entity.Customer" scope="session"></jsp:useBean>
<jsp:setProperty name="customer" property="*" />
<jsp:useBean id="order" class="entity.Order" scope="session"></jsp:useBean>

<%
    List<CartItem> cartItemList = (List) session.getAttribute("cartItemList");
    double totalPriceForCart = (Double) session.getAttribute("totalPriceForCart");
    
    order.setCustId(customer);
    double oriOrderTotal = 0;

    List<OrderDetails> odList = new ArrayList<OrderDetails>();
    for (int i = 0; i < cartItemList.size(); i++) {
        OrderDetails od = new OrderDetails();
        od.setProduct(cartItemList.get(i).getProduct());
        od.setQuantity(cartItemList.get(i).getQuantity());
        od.setSubTotal(od.calcSubTotal());
        odList.add(od);
        oriOrderTotal += od.getSubTotal();
    } 
    order.setOrderDetailsList(odList);
    order.setTotalAmount(totalPriceForCart);

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
                    <li class="breadcrumb-item"><a href="cart.jsp">Shopping Cart</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Check Out</li>
                </ol>
            </nav>
        </div>


        <div class="container-fluid">
            <div class="row">
                <div class="col-md-10 col-11 mx-auto">
                    <div class="row mt-5 gx-3">
                        <!-- left side div -->
                        <div class="col-md-12 col-lg-8 col-11 mx-auto main_cart mb-lg-0 mb-5 shadow">
                            <h2 class="py-4 font-weight-bold">Product List </h2><h5>Products are shown in original price.</h5>
                            <!-- ============Start product -->

                            <% for (OrderDetails od : order.getOrderDetailsList()) {%>

                            <hr />
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
                                                <h1 class="mb-4 product_name"><%= od.getProduct().getProdName()%></h1>
                                                <p class="mb-2">SUPPLIER:<%= od.getProduct().getSupplier()%></p>
                                                <p class="mb-2">CATEGORY:<%= od.getProduct().getSubCategoryId().getSubCategoryName()%></p>
                                                <p class="mb-3">UNIT PRICE:<%= od.getProduct().getUnitPrice()%></p>
                                            </div>
                                            <!-- quantity -->
                                            <div class="col-4">
                                                <h5>Quantity: <%= od.getQuantity()%></h5>
                                            </div>
                                        </div>
                                        <!-- price -->
                                        <div class="row">
                                            <div class="col-8 d-flex justify-content-between remove_wish"></div>
                                            <div class="col-4 d-flex justify-content-end price_money">
                                                <h3>RM<span id="itemval1"><%=String.format("%.2f", od.calcSubTotal())%></span></h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <% }%>
                            <!-- ============End product -->
                        </div>

                        <!-- right side div -->
                        <div class="col-md-12 col-lg-4 col-11 mx-auto mt-lg-0 mt-md-5">
                            <div class="right_side p-3 shadow bg-white">
                                <h2 class="product_name mb-5">Order Summary</h2>
                                <div class="price_indiv d-flex justify-content-between">
                                    <p>Cart amount</p>
                                    <p>RM <span id="product_total_amt"><%=String.format("%.2f", oriOrderTotal)%></span></p>
                                </div>
                                <div class="price_indiv d-flex justify-content-between">
                                    <p>Promotion Discount</p>
                                    <p>RM <span id="shipping_charge"><%=String.format("%.2f", oriOrderTotal - order.getTotalAmount())%></span></p>
                                </div>
                                <hr />
                                <div
                                    class="total-amt d-flex justify-content-between font-weight-bold"
                                    >
                                    <h4>Final amount</h4>
                                    <h4>RM <span id="total_cart_amt"><%=String.format("%.2f", order.getTotalAmount())%></span></h4>
                                </div>


                                <a href="PaymentForm.jsp?oriOrderTotal=<%=oriOrderTotal%>&discount=<%=oriOrderTotal-order.getTotalAmount()%>">
                                    <button type="submit" class="btn btn-primary text-uppercase">Checkout</button>
                                </a>
                            </div>
                            <div class="mt-3 shadow p-3 bg-white">
                                <div class="pt-4">
                                    <h5 class="mb-4">Expected delivery date</h5>
                                    <p> <%= order.displayDate(order.getEstimatedDeliveryDates()[0])%> - <%= order.displayDate(order.getEstimatedDeliveryDates()[1])%></p>

                                </div>
                            </div>
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

        <!-- End jQuery Frameworks
        ============================================= -->
    </body>
</html>
