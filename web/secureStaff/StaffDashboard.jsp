<%-- 
    Document   : staffDashboard
    Created on : Mar 27, 2022, 9:43:58 PM
    Author     : Ng Eason
--%>

<%@page import="entity.*"%>
<%@page import="java.util.List"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Staff> stfList = (List<Staff>) application.getAttribute("stfList");
    List<Order> orderList = (List<Order>) application.getAttribute("orderList");
    List<Product> productList = (List<Product>) application.getAttribute("productList");
    List<Category> categList = (List<Category>) application.getAttribute("categList");
    List<SubCategory> subCategList = (List<SubCategory>) application.getAttribute("subCategList");
    List<Customer> custList = (List<Customer>) session.getAttribute("custList");
    int pendingOrder = 0, packagingOrder = 0, shippingOrder = 0, deliveredOrder = 0, cancelledOrder = 0, itemSold = 0;

    for (Order order : orderList) {
        if (order.getOrderStatus().equals("Pending")) {
            pendingOrder++;
        } else if (order.getOrderStatus().equals("Packaging")) {
            packagingOrder++;
        } else if (order.getOrderStatus().equals("Shipping")) {
            shippingOrder++;
        } else if (order.getOrderStatus().equals("Delivered")) {
            deliveredOrder++;
        } else if (order.getOrderStatus().equals("Cancelled")) {
            cancelledOrder++;
        }
        for (int index = 0; index < order.getOrderDetailsList().size(); index++) {
            if (!order.getOrderStatus().equals("Cancelled")) {
                itemSold += order.getOrderDetailsList().get(index).getQuantity();
            }
        }
    }

%>

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
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <!-- ========== Start Stylesheet ========== -->
        <link href="../assets/css/StaffDashboard.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />
    </head>
    <body>

        <!--
            param required for StaffHeader.jsp
            1. 	logoFlavicon (tab logo)
            2. 	logoImg (header top left logo)
            3. 	staffIndex (ToStaffDashboard)
            4. 	maintainStaff 
            5. 	prodPagination
            6. 	maintainCateg
            7. 	maintainSubCateg
            8. 	maintainPromo
            9. 	orderStatus
            10. comment
            11.	feedback
            12.	viewCust
            13.	viewSales
            14.	viewReports
        -->
        <jsp:include page="../common/StaffHeader.jsp">
            <jsp:param name="logoFlavicon" value="../assets/img/AGLogo/favicon.png"/>
            <jsp:param name="logoImg" value="../assets/img/AGLogo/BasketTrans.png"/>
            <jsp:param name="staffIndex" value="../ToStaffDashboard"/>
            <jsp:param name="maintainStaff" value="../secureAdmin/MaintainStaff.jsp" />
            <jsp:param name="prodPagination" value="../ProductPagination" />
            <jsp:param name="maintainCateg" value="MaintainCategory.jsp#Category" />
            <jsp:param name="maintainPromo" value="../RetrievePromotionItem?path=secureStaff/MaintainPromotion.jsp" />
            <jsp:param name="orderStatus" value="../SearchOrder"/> 
            <jsp:param name="comment" value="../ToComment"/>
            <jsp:param name="feedback" value="../ToFeedback"/> 
            <jsp:param name="viewCust" value="../RetrieveCustomer?path=secureAdmin/ViewCustomer.jsp" />
            <jsp:param name="viewSales" value="../secureAdmin/ViewSales.jsp" />
            <jsp:param name="viewReports" value="../secureAdmin/ViewReports.jsp" />
        </jsp:include>


        <section>
            <div class="cards">
                <div class="card-single">
                    <div>
                        <h2>Total Order</h2>
                        <h1><%=orderList.size()%></h1>
                    </div>
                    <div>
                        <span class="las la-shopping-cart"></span>
                    </div>
                </div>

                <div class="card-single">
                    <div>
                        <h2>Items Sold</h2>
                        <h1><%=itemSold%></h1>
                    </div>
                    <div>
                        <span class="las la-dollar-sign"></span>
                    </div>
                </div>

                <div class="card-single">
                    <div>
                        <h2>Total Products</h2>
                        <h1><%=productList.size()%></h1>
                    </div>
                    <div>
                        <span class="las la-boxes"></span>
                    </div>
                </div>

                <div class="card-single">
                    <div>
                        <h2>Total Customer</h2>
                        <h1><%=custList.size()%></h1>
                    </div>
                    <div>
                        <span class="las la-users"></span>
                    </div>
                </div>     
        </section> 

        <main>
            <div clas="order">
                <div class="order-status">
                    <div>
                        <h2>Order Pending</h2>
                        <h1><%=pendingOrder%></h1>
                    </div>
                    <div>
                        <span class="las la-box-open"></span>
                    </div>
                </div>
                <div class="order-status">
                    <div>
                        <h2>Order Packaging</h2>
                        <h1><%=packagingOrder%></h1>
                    </div>
                    <div>
                        <span class="las la-box-open"></span>
                    </div>
                </div>

                <div class="order-status">
                    <div>
                        <h2>Order Shipping</h2>
                        <h1><%=shippingOrder%></h1>
                    </div>
                    <div>
                        <span class="las la-shipping-fast"></span>
                    </div>
                </div>

                <div class="order-status">
                    <div>
                        <h2>Order Delivered</h2>
                        <h1><%=deliveredOrder%></h1>
                    </div>
                    <div>
                        <span class="las la-check-circle"></span>
                    </div>
                </div>

                <div class="order-status">
                    <div>
                        <h2>Order Cancelled</h2>
                        <h1><%=cancelledOrder%></h1>
                    </div>
                    <div>
                        <span class="las la-times-circle"></span>
                    </div>
                </div>
            </div>   
        </main>
    </body>

</html>
