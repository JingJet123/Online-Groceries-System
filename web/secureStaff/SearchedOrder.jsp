<%-- 
    Document   : feedbackReply
    Created on : Mar 31, 2022, 5:21:10 PM
    Author     : Ng Eason
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.Order"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>


<%@page import="java.sql.*"%>

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

        <script>
            function findOrders() {
                var checkbox = document.getElementsByName("orders");
                var checkedbox = [];
                console.log(checkbox.length);
                for (let i = 0; i < checkbox.length; i++) {
                    if (checkbox[i].checked) {
                        //console.log("A");
                        checkedbox.push(checkbox[i].value);
                    }
                }
                document.getElementById("orderId").value = checkbox.length > 0 ? checkedbox.toString() : null;
            }

            function openUpdate() {
                document.getElementById("update").style.height = "100%";
                findOrders();

                //document.getElementById("orderId").value = document.getElementById("orderid" + value).value;
            }
            function toView(value) {
                window.location.href = "StaffViewOrder.jsp?orderId=" + value;
                //window.location.replace("StaffViewOrder.jsp?orderId=" + "1");
            }

        </script>

        <%
            List<Order> searchOrderList = (List) session.getAttribute("searchOrderList");
            int oIndex = 0;
            String statuslight = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/YYYY HH:MM");
        %>



        <section>
            <form class="form-inline" style="justify-content: space-between;" action="../ToSearchOrder">
                <div>
                    <input class="form-control header_search" name="searchOrder" type="search" placeholder="Search..." aria-label="Search">
                    <button class="btn search_btn" type="submit">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                        </svg>
                    </button>  
                </div>

                <button type="button" onclick="openUpdate()" class="updatebutton">Update</button>
            </form>

            <div class="row" style="margin-top:2%;">
                <div class="col-xl-12 col-lg-12 col-md-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header pb-0">
                            <!-- Breadcrumb at page top -->
                            <div class="topPage-bc">
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="../ToStaffDashboard">Dashboard</a></li>
                                        <li class="breadcrumb-item"><a href="../SearchOrder">Update order</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Searched Result</li>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="table-responsive p-0">
                                <table class="table table-hover align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th>
                                                Order ID
                                            </th>
                                            <th>
                                                Order Date
                                            </th>
                                            <th>
                                                Customer Name
                                            </th>
                                            <th>
                                                Total Amount
                                            </th>
                                            <th>
                                                Order Status
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Order o : searchOrderList) {%>
                                    <form action="../UpdateOrder" method="get">
                                        <input type="hidden" id="orderid<%= oIndex%>" value="<%= o.getOrderId()%>" />
                                        <tr>
                                            <td class="id" onclick="toView(<%= o.getOrderId()%>)"><%= o.getOrderId()%></td>
                                            <td onclick="toView(<%= o.getOrderId()%>)"><%= simpleDateFormat.format(o.getOrderDate())%></td>
                                            <td onclick="toView(<%= o.getOrderId()%>)"><%= o.getCustId().getCustName()%></td>
                                            <td onclick="toView(<%= o.getOrderId()%>)"><%= String.format("%.2f", o.getTotalAmount())%></td>
                                            <td onclick="toView(<%= o.getOrderId()%>)"><%= o.getOrderStatus()%></td>
                                            <% if (o.getOrderStatus().equals("Packaging")) {
                                                    statuslight = "dot yellow";
                                                } else if (o.getOrderStatus().equals("Shipping")) {
                                                    statuslight = "dot yellow";
                                                } else if (o.getOrderStatus().equals("Delivered")) {
                                                    statuslight = "dot green";
                                                } else if (o.getOrderStatus().equals("Pending")) {
                                                    statuslight = "dot yellow";
                                                } else if (o.getOrderStatus().equals("Cancelled")) {
                                                    statuslight = "dot red";
                                                }
                                            %>
                                            <td><span class="statusOrder <%= statuslight%>"></span></td>
                                            <td class="action">
                                                <% if (!o.getOrderStatus().equals("Delivered") && !o.getOrderStatus().equals("Cancelled")) {%>
                                                <input type="checkbox" name="orders" value="<%= o.getOrderId()%>">
                                                <% } %>
                                            </td>
                                        </tr>     
                                    </form>
                                    <%}%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <div class="overlay" id="update">
            <a href="javascript:void(0)" class="closebtn" onclick="closeUpdate()">&times;</a>
            <div class="overlay-content">
                <form action="../UpdateOrder">
                    <input type="hidden" id="orderId" name="orderId"/>
                    <input type="hidden" id="updstt" name="updstt"/>
                    <table class="prodTable">
                        <thead>
                            <tr>
                                <th colspan="4">Select Status</th>
                            </tr>
                        </thead>
                        <tr>
                            <td><button type="submit" onclick="proceedUpdate(this.value)" name="updatestt" class="selection red" value="Cancelled">Cancelled</button></td>
                            <td><button type="submit" onclick="proceedUpdate(this.value)" name="updatestt" class="selection yellow" value="Packaging">Packaging</button></td>
                            <td><button type="submit" onclick="proceedUpdate(this.value)" name="updatestt" class="selection yellow" value="Shipping">Shipping</button></td>
                            <td><button type="submit" onclick="proceedUpdate(this.value)" name="updatestt" class="selection green" value="Delivered">Delivered</button></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <script>


            function proceedUpdate(value) {
                document.getElementById("updstt").value = document.getElementById(value).value;
            }

            function closeUpdate() {
                document.getElementById("update").style.height = "0%";
            }
        </script>
        <script src="../assets/js/catgDrop.js"></script>
    </body>
</html>
