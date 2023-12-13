<%-- 
    Document   : Reports
    Created on : Apr 8, 2022, 10:00:58 PM
    Author     : New Yee Hao
--%>

<%@page import="entity.Category"%>
<%@page import="entity.Product"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%
    String reportTitle = "Sales Report";
    Date sDate = (Date) session.getAttribute("salesStartDate");
    Date eDate = (Date) session.getAttribute("salesEndDate");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    List<Category> categList = (List<Category>) application.getAttribute("categList");
    double[] totalSalesArray = new double[categList.size()];
    String[] categNameArray = new String[categList.size()];
    for (int i = 0; i < categList.size(); i++) {
        totalSalesArray[i] = categList.get(i).totalSalesOfACateg(categList.get(i).getCategoryId(), sDate, eDate);
        categNameArray[i] = categList.get(i).getCategoryName();
    }

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
                const totalSalesArray = [];
                const categNameArray = [];
            <% for (int i = 0; i < categList.size(); i++) {%>
                totalSalesArray[<%=i%>] = "<%=String.format("%.2f", totalSalesArray[i])%>";
                categNameArray[<%=i%>] = "<%=categNameArray[i]%>";
            <% }%>
                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Category');
                data.addColumn('number', 'Total Sales');
                data.addRows(totalSalesArray.length);

                for (let j = 0; j < totalSalesArray.length; j++) {
                    for (let k = 0; k < 2; k++) {
                        if (k === 0) {
                            data.setCell(j, k, categNameArray[j]);
                        } else {
                            data.setCell(j, k, totalSalesArray[j]);
                        }
                    }
                }

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


        <title>AG Grocery Market <%= reportTitle%></title>
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/report.css" rel="stylesheet"/>
    </head>
    <body>
        <div id="report">
            <div class="container-lg mx-auto" >
                <div class="row pt-3">
                    <div class="col-xl-3 col-lg-3 logoArea">
                        <img src="../assets/img/AGLogo/Logo4.png" class="logoImg" alt="Logo"/>
                    </div>
                    <div class="col-xl-9 col-lg-9">
                        <div class="card w-100 report-header">
                            <h2 class="card-title font-weight-bolder">AG Grocery Market</h2>
                            <p>No.999 Jalan Bintang, Bukit Sri Bintang, Kuala Lumpur, Federal Territory of Kuala Lumpur.</p>
                            <h4 class="font-weight-bold"><%=reportTitle%> (<%=sdf.format(sDate)%> - <%=sdf.format(eDate)%>)</h4>
                        </div>
                    </div>
                </div>
                <h4 class="chart-title">Total Sales of Each Category</h4>
                <!--Div that will hold the pie chart-->
                <div id="chart_div"></div>

                <div class="row">
                    <div class="col-xl-12 col-lg-12 col-md-6 col-sm-12 col-12">
                        <div class="card mb-4 w-100">
                            <div class="card-body px-0 pt-0 pb-2">
                                <div class="table-responsive p-0">
                                    <table class="table align-items-center mb-0 report-table" border="2">
                                        <thead>
                                            <tr class="table-row">
                                                <th class="text-uppercase text-secondary font-weight-bolder opacity-85">
                                                    Product Category
                                                </th>
                                                <th class="text-uppercase text-secondary font-weight-bolder opacity-85">
                                                    Total Sales(RM)
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%  double ttlSales = 0;
                                                for (int i = 0; i < categList.size(); i++) {
                                                    if (categList.get(i).totalSalesOfACateg(categList.get(i).getCategoryId(), sDate, eDate) != 0) {
                                                        ttlSales += categList.get(i).totalSalesOfACateg(categList.get(i).getCategoryId(), sDate, eDate);
                                            %>
                                            <tr>
                                                <td class="align-middle">
                                                    <p class="text-xs mb-0"><%=categList.get(i).getCategoryName()%></p>
                                                </td>
                                                <td class="align-middle text-center text-sm">
                                                    <p class="text-xs mb-0"><%=String.format("%.2f", categList.get(i).totalSalesOfACateg(categList.get(i).getCategoryId(), sDate, eDate))%></p>
                                                </td>
                                            </tr>
                                            <% }
                                                }%>
                                            <tr class="table-row">
                                                <td class="align-middle text-center font-weight-bolder">TOTAL(RM)</td>
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
