<%-- 
    Document   : OrderSummaryReport
    Created on : Apr 9, 2022, 11:18:09 PM
    Author     : New Yee Hao
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Collections"%>
<%@page import="entity.Order"%>
<%@page import="java.util.List"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%
    String reportTitle = "Order Summary Report";
    List<Order> orderList = (List<Order>) application.getAttribute("orderList");
    int pendingOrder = 0, packagingOrder = 0, shippingOrder = 0, deliveredOrder = 0, cancelledOrder = 0;
    double pendOrderSales = 0, packOrderSales = 0, shipOrderSales = 0, deliOrderSales = 0, cancOrderSales = 0;
    for (Order order : orderList) {
        if (order.getOrderStatus().equals("Pending")) {
            pendingOrder++;
            pendOrderSales += order.getTotalAmount();
        } else if (order.getOrderStatus().equals("Packaging")) {
            packagingOrder++;
            packOrderSales += order.getTotalAmount();
        } else if (order.getOrderStatus().equals("Shipping")) {
            shippingOrder++;
            shipOrderSales += order.getTotalAmount();
        } else if (order.getOrderStatus().equals("Delivered")) {
            deliveredOrder++;
            deliOrderSales += order.getTotalAmount();
        } else if (order.getOrderStatus().equals("Cancelled")) {
            cancelledOrder++;
            cancOrderSales += order.getTotalAmount();
        }
    }
    String[] orderStatus = {"Pending", "Packaging", "Shipping", "Delivered", "Cancelled"};
    int[] orderStatusCount = {pendingOrder, packagingOrder, shippingOrder, deliveredOrder, cancelledOrder};
    double[] orderSales = {pendOrderSales, packOrderSales, shipOrderSales, deliOrderSales, cancOrderSales};
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMMM dd yyyy");
    Date busiestDate = (Date) session.getAttribute("busiestDate");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">

            // Load the Visualization API and the corechart package.
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(drawChart);

            // Callback that creates and populates a data table,
            // instantiates the pie chart, passes in the data and
            // draws it.
            function drawChart() {

                // Create the data table.
                var data = google.visualization.arrayToDataTable([
                    ['Order Status', 'Count'],
                    ['Pending', <%= pendingOrder%>],
                    ['Packaging', <%= packagingOrder%>],
                    ['Shipping', <%= shippingOrder%>],
                    ['Delivered', <%= deliveredOrder%>],
                    ['Cancelled', <%= cancelledOrder%>]
                ]);

                // Set chart options
                var options = {
                    titlePosition: 'none',
                    'width': '100%',
                    'height': '350',
                    chartArea: {
                        left: 5,
                        top: 50,
                        width: '100%',
                        height: '350'
                    }};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
                chart.draw(data, options);
            }
        </script>
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/report.css" rel="stylesheet"/>
        <title>AG Grocery Market <%= reportTitle%></title>
    </head>
    <body>
        <div class="container-lg mx-auto">
            <div class="row pt-3">
                <div class="col-xl-3 col-lg-3 logoArea">
                    <img src="../assets/img/AGLogo/Logo4.png" class="logoImg" alt="Logo"/>
                </div>
                <div class="col-xl-9 col-lg-9">
                    <div class="card w-100 report-header">
                        <h2 class="card-title font-weight-bolder">AG Grocery Market</h2>
                        <p>No.999 Jalan Bintang, Bukit Sri Bintang, Kuala Lumpur, Federal Territory of Kuala Lumpur.</p>
                        <h4 class="font-weight-bold"><%=reportTitle%></h4>
                    </div>
                </div>
            </div>

            <h4 class="chart-title">Count of each Order Status</h4>

            <div id="chart_div"></div>
            <!--Div that will hold the pie chart-->

            <div style="text-align: center; margin-top: 2%;">
                <h4>Date that has most orders in: <span class="day"><%=sdf.format(busiestDate)%></span></h4>
            </div>
            <div class="row">
                <div class="col-xl-12 col-lg-12 col-md-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="table-responsive p-0">
                                <table class="table align-items-center mb-0 report-table" border="2">
                                    <thead>
                                        <tr class="table-row">
                                            <th class="text-uppercase text-secondary font-weight-bolder opacity-85">
                                                Order Status
                                            </th>
                                            <th class="text-uppercase text-secondary font-weight-bolder opacity-85">
                                                Count
                                            </th>
                                            <th class="text-uppercase text-secondary font-weight-bolder opacity-85">
                                                Total Amount(RM)
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            double ttlSales = 0;
                                            for (int i = 0; i < 5; i++) {
                                                ttlSales += orderSales[i];
                                        %>
                                        <tr>
                                            <td class="align-middle">
                                                <p class="text-xs mb-0"><%=orderStatus[i]%></p>
                                            </td>
                                            <td class="align-middle text-center text-sm">
                                                <p class="text-xs mb-0"><%=orderStatusCount[i]%></p>
                                            </td>
                                            <td class="align-middle text-center text-sm">
                                                <% if (orderStatus[i].equals("Cancelled")) {%>
                                                <p class="text-xs mb-0">(<%=String.format("%.2f", orderSales[i])%>)</p>
                                                <% } else {%>
                                                <p class="text-xs mb-0"><%=String.format("%.2f", orderSales[i])%></p>
                                                <% }%>
                                            </td>
                                        </tr>
                                        <% }%>
                                        <tr class="table-row">
                                            <td class="align-middle text-center font-weight-bolder" colspan="2">TOTAL SALES(RM)</td>
                                            <td class="align-middle font-weight-bolder text-center"><%=String.format("%.2f", ttlSales - orderSales[4])%></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xl-12 col-lg-12 col-md-6 col-sm-12 col-12 card">
                <div class="card-body" style="text-align:right">
                    <a href="ViewReports.jsp" class="btn btn-outline-primary">Back To Previous Page</a>
                </div>
            </div>
        </div>
    </body>
</html>
