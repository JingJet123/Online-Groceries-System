<%-- 
    Document   : ViewReports
    Created on : Apr 7, 2022, 6:46:08 PM
    Author     : New Yee Hao
--%>

<%@page import="java.util.List"%>
<%@page import="entity.Category"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
    Date curDate = new Date();
    List<Category> categList = (List<Category>) application.getAttribute("categList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AG Market Generate Reports</title>

        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/StaffDashboard.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />
        <link href="../assets/css/viewReport.css" rel="stylesheet" />
    </head>
    <body>

        <jsp:include page="../common/StaffHeader.jsp">
            <jsp:param name="logoFlavicon" value="../assets/img/AGLogo/favicon.png"/>
            <jsp:param name="logoImg" value="../assets/img/AGLogo/BasketTrans.png"/>
            <jsp:param name="staffIndex" value="../ToStaffDashboard"/>
            <jsp:param name="maintainStaff" value="MaintainStaff.jsp" />
            <jsp:param name="prodPagination" value="../ProductPagination" />
            <jsp:param name="maintainCateg" value="../secureStaff/MaintainCategory.jsp#Category" />
            <jsp:param name="maintainSubCateg" value="../secureStaff/MaintainCategory.jsp#SubCategory" />
            <jsp:param name="maintainPromo" value="../RetrievePromotionItem?path=secureStaff/MaintainPromotion.jsp" />
            <jsp:param name="orderStatus" value="../SearchOrder"/> 
            <jsp:param name="comment" value="../ToComment"/>
            <jsp:param name="feedback" value="../ToFeedback"/> 
            <jsp:param name="viewCust" value="../RetrieveCustomer?path=secureAdmin/ViewCustomer.jsp" />
            <jsp:param name="viewSales" value="ViewSales.jsp" />
            <jsp:param name="viewReports" value="#" />
        </jsp:include>

        <section>
            <div class="row">
                <div class="col-xl-12 col-lg-12 col-md-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title font-weight-bolder">AG Grocery Market Sales Report</h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text">View details sales report according chosen period. </p>
                            <form action="../GenReport">
                                <input type="hidden" name="path" value="GenReport"/>
                                <label for="salesStartDate">Date Range: </label>
                                <input type="date" name="salesStartDate" min="2022-01-01" max="<%=sdf.format(curDate)%>" required/>

                                <label for="salesEndDate"> - </label>
                                <input type="date" name="salesEndDate" min="2022-01-01" max="<%=sdf.format(curDate)%>" required/>
                                <input type="submit" class="viewBtn" value="Generate & View" />
                            </form>
                        </div>
                    </div>
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title font-weight-bolder">AG Grocery Market Monthly Top 5 Sales Product Report</h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text">The top 5 sales product will be listed with their details according to the month.</p>
                            <form action="../GenReport">
                                <label for="month">Month: </label>
                                <input type="month" name="month" min="2022-01" max="<%=sdf2.format(curDate)%>" required/>
                                <p></p>
                                <label for="category">Product Category:</label>
                                <select name="category">
                                    <option value="All" selected>All</option>
                                    <% for (Category categ : categList) {
                                            if (!categ.getIsDeleted()) {%>
                                    <option value="<%=categ.getCategoryName()%>">
                                        <%=categ.getCategoryName()%>
                                    </option>
                                    <% }
                                        }%>
                                </select>
                                <input type="submit" class="viewBtn" value="Generate & View" />
                            </form>
                        </div>
                    </div>
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title font-weight-bolder">AG Grocery Market Order Summary Report</h5>
                        </div>
                        <div class="card-body">

                            <p class="card-text">The summary of all orders.</p>
                            <form action="../RetrieveOrder" >
                                <input type="hidden" name="path" value="GenReport"/>
                                <input type="submit" class="viewBtn" value="Generate & View" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>        
        <jsp:include page="../common/alertPop.jsp">
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType")%>" />
        </jsp:include>
    </body>
</html>
