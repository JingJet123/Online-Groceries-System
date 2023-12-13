<%-- 
    Document   : StaffViewOrder
    Created on : Mar 26, 2022
    Author     : Lee Jia Jie
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.OrderDetails"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.Order"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- ========== Page Title ========== -->
        <title>AG Market Search Order</title>

        <!-- ========== Favicon Icon ========== -->
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <!-- ========== Start Stylesheet ========== -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/StaffDashboard.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />
        <link href="../assets/css/UpdateOrder.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/overlay.css" rel="stylesheet" type="text/css" />
    </head>


    <body>

        <jsp:include page="../common/StaffHeader.jsp">
            <jsp:param name="logoFlavicon" value="../assets/img/AGLogo/favicon.png"/>
            <jsp:param name="logoImg" value="../assets/img/AGLogo/BasketTrans.png"/>
            <jsp:param name="staffIndex" value="../ToStaffDashboard"/>
            <jsp:param name="maintainStaff" value="../secureAdmin/MaintainStaff.jsp" />
            <jsp:param name="prodPagination" value="../ProductPagination" />
            <jsp:param name="maintainCateg" value="MaintainCategory.jsp#Category" />
            <jsp:param name="maintainSubCateg" value="MaintainCategory.jsp#SubCategory" />
            <jsp:param name="maintainPromo" value="../RetrievePromotionItem?path=secureStaff/MaintainPromotion.jsp" />
            <jsp:param name="orderStatus" value="../SearchOrder"/> 
            <jsp:param name="comment" value="../ToComment"/>
            <jsp:param name="feedback" value="../ToFeedback"/> 
            <jsp:param name="viewCust" value="../RetrieveCustomer?path=secureAdmin/ViewCustomer.jsp" />
            <jsp:param name="viewSales" value="../secureAdmin/ViewSales.jsp" />
            <jsp:param name="viewReports" value="../secureAdmin/ViewReports.jsp" />
        </jsp:include>

        <%
            List<OrderDetails> ordDetails = null;
            List<Order> order = (List<Order>) application.getAttribute("orderList");
            for (Order o : order) {
                if (o.getOrderId().equals(Long.parseLong(request.getParameter("orderId")))) {
                    ordDetails = o.getOrderDetailsList();
                }
            }
        %>


        <div class="col pt-1 pl-5" style="margin-top: 130px;">
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
                        <%for (OrderDetails ol : ordDetails) {%>
                        <tr>
                            <td>
                                <div class="d-flex align-items-center">
                                    <img
                                        src="data:image/jpg;base64,<%= ol.getProduct().encodeImageToString()%>"
                                        alt=""
                                        style="width: 45px; height: 45px"
                                        class="rounded-circle"
                                        />
                                </div>
                            </td>
                            <td>
                                <p class="fw-bold mb-1"><%=ol.getProduct().getProdName()%></p>
                                <p class="text-muted mb-0"><%=ol.getProduct().getSubCategoryId().getSubCategoryName()%></p>
                            </td>
                            <td>
                                <span class="badge badge-success rounded-pill"><%= ol.getQuantity()%></span>
                            </td>
                            <td><%=String.format("RM%.2f", ol.getSubTotal())%></td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
                <a style="float:right; margin-top: 1.5em;" href="../RetrieveOrder?path=secureStaff/UpdateOrder.jsp" class="updatebutton">Back</a>
            </div>
        </div>
    </body>
</html>
