<%-- 
    Document   : Top5SalesReport
    Created on : Apr 9, 2022, 9:04:15 PM
    Author     : New Yee Hao
--%>

<%@page import="entity.Comment"%>
<%@page import="entity.Product"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%
    String reportTitle = "Monthly Top 5 Sales Product Report";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMMM");
    List<Product> top5ProdList = (List<Product>) session.getAttribute("top5ProdList");
    List<Integer> itemSoldList = (List<Integer>) session.getAttribute("itemSoldList");
    String categName = (String) session.getAttribute("category");
    Date month = (Date) session.getAttribute("month");
    String displayMonth = sdf.format(month);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Load the AJAX API-->
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
                const itemSoldArray = [];
                const prodNameArray = [];
            <% for (int i = 0; i < top5ProdList.size(); i++) {%>
                itemSoldArray[<%=i%>] = "<%=itemSoldList.get(i)%>";
                prodNameArray[<%=i%>] = "<%=top5ProdList.get(i).getProdName()%>";
            <% }%>
                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Product');
                data.addColumn('number', 'Total Item Sold');
                data.addRows(itemSoldArray.length);

                for (let j = 0; j < itemSoldArray.length; j++) {
                    for (let k = 0; k < 2; k++) {
                        if (k === 0) {
                            data.setCell(j, k, prodNameArray[j]);
                        } else {
                            data.setCell(j, k, itemSoldArray[j]);
                        }
                    }
                }

                // Set chart options
                var options = {
                    titlePosition: 'none',
                    width: '75%',
                    height: '350',
                    chartArea: {
                        top: 20,
                        left: 150,
                        width: '75%',
                        height: '250'
                    }
                };

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
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
                        <h4 class="font-weight-bold"><%=reportTitle%> (<%=displayMonth%>)</h4>
                    </div>
                </div>
            </div>

            <% if (categName.equals("All")) {%>
            <h4 class="chart-title">Top 5 Sales Products of All Category</h4>
            <% } else {%>
            <h4 class="chart-title">Top 5 Sales Products for <%=categName%> Category</h4>
            <% } %>
            <div id="chart_div"></div>
            <!--Div that will hold the pie chart-->


            <div class="row">
                <div class="col-xl-12 col-lg-12 col-md-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="table-responsive p-0">
                                <table class="table align-items-center mb-0 report-table" border="2">
                                    <thead>
                                        <tr class="table-row">
                                            <th class="text-uppercase text-secondary font-weight-bolder opacity-85">
                                                Product Name
                                            </th>
                                            <th class="text-uppercase text-secondary font-weight-bolder opacity-85">
                                                Item Sold
                                            </th>
                                            <th class="text-uppercase text-secondary font-weight-bolder opacity-85">
                                                Overall Rating
                                            </th>
                                            <th class="text-uppercase text-secondary font-weight-bolder opacity-85">
                                                Total Sales(RM)
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            int ttlItemSold = 0;
                                            double ttlSales = 0;
                                            Comment cmt = new Comment();

                                            for (int i = 0; i < top5ProdList.size(); i++) {
                                                int overallRating = 0;
                                                ttlItemSold += itemSoldList.get(i);
                                                ttlSales += itemSoldList.get(i) * top5ProdList.get(i).getUnitPrice();
                                                if (!top5ProdList.get(i).getCommentList().isEmpty()) {
                                                    overallRating = cmt.getAverageRating(top5ProdList.get(i).getCommentList());
                                                }
                                        %>
                                        <tr>
                                            <td class="align-middle">
                                                <p class="text-xs mb-0"><%=top5ProdList.get(i).getProdName()%></p>
                                            </td>
                                            <td class="align-middle text-center text-sm">
                                                <p class="text-xs mb-0"><%=itemSoldList.get(i)%></p>
                                            </td>
                                            <td class="align-middle text-center text-sm">
                                                <p class="text-xs mb-0">
                                                    <%if (overallRating == 0) {%>
                                                    No rating yet
                                                    <% } else {
                                                        for (int j = 0; j < overallRating; j++) {
                                                    %>
                                                    <i class='fa fa-star' id="star" ></i>
                                                    <% }
                                                        }%>
                                                </p>
                                            </td>
                                            <td class="align-middle text-center text-sm">
                                                <p class="text-xs mb-0"><%=String.format("%.2f", itemSoldList.get(i) * top5ProdList.get(i).getUnitPrice())%></p>
                                            </td>
                                        </tr>
                                        <% }%>

                                        <tr class="table-row">
                                            <td class="align-middle text-center font-weight-bolder">TOTAL(RM)</td>
                                            <td class="align-middle font-weight-bolder text-center"><%=ttlItemSold%></td>
                                            <td></td>
                                            <td class="align-middle font-weight-bolder text-center"><%=String.format("%.2f", ttlSales)%></td>
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
    <script src="https://kit.fontawesome.com/c6f1f55876.js" crossorigin="anonymous"></script>
</html>
