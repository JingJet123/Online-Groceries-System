<%-- 
    Document   : ViewSales
    Created on : Apr 4, 2022, 6:44:11 PM
    Author     : New Yee Hao
--%>

<%@page import="entity.Order"%>
<%@page import="entity.Product"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%
    Date curDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate = new Date();
    Date endDate = new Date();
    if (session.getAttribute("fStartDate") != null && session.getAttribute("fEndDate") != null) {
        startDate = (Date) session.getAttribute("fStartDate");
        endDate = (Date) session.getAttribute("fEndDate");
    }
    List<Product> prodList = null;
    if (session.getAttribute("filteredProdList") != null) {
        prodList = (List<Product>) session.getAttribute("filteredProdList");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AG Market View Sales</title>
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/StaffDashboard.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />
        <link href="../assets/css/viewCust.css" rel="stylesheet"/>
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
            <jsp:param name="viewSales" value="#" />
            <jsp:param name="viewReports" value="ViewReports.jsp" />
        </jsp:include>
        <section>
            <div class="row">
                <div class="col-xl-12 col-lg-12 col-md-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header pb-0">
                            <!-- Breadcrumb at page top -->
                            <div class="topPage-bc">
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="../ToStaffDashboard">Dashboard</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">View Sales Records</li>
                                    </ol>
                                </nav>
                            </div>
                            <div class="datePicker">
                                <form class="filterForm" action="../ViewSales">
                                    <label>Date Range: </label>
                                    <input type="date" name="startDate" min="2022-01-01" max="<%=sdf.format(curDate)%>" value="<%=sdf.format(startDate)%>"/>

                                    <label for="endDate"> - </label>
                                    <input type="date" name="endDate" min="2022-01-01" max="<%=sdf.format(curDate)%>" value="<%=sdf.format(endDate)%>" />
                                    <input class="submitBtn" type="submit" value="View" />
                                </form>
                            </div>
                        </div>
                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="table-responsive p-0">
                                <table class="table table-hover align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th>
                                                <a href="../SortSales?criteria=ProdName" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Product Details</a>
                                            </th>
                                            <th>
                                                <a href="../SortSales?criteria=UnitPrice" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Unit Price(RM)</a>
                                            </th>
                                            <th>
                                                <a href="../SortSales?criteria=ItemSold" 
                                                   class="sorting text-center text-uppercase text-secondary font-weight-bolder opacity-85">Item Sold</a>
                                            </th>
                                            <th>
                                                <a href="../SortSales?criteria=TotalProfit" 
                                                   class="sorting text-center text-uppercase text-secondary font-weight-bolder opacity-85">Total Profit(RM)</a>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            if (prodList != null && !prodList.isEmpty()) {
                                                int totalItemSold = 0;
                                                double totalProfit = 0;
                                                for (Product prod : prodList) {
                                                    totalItemSold += prod.getItemSold(startDate, endDate);
                                                    totalProfit += prod.getUnitPrice() * prod.getItemSold(startDate, endDate);
                                        %>
                                        <tr class="<%= (prod.getIsDeleted() ? "prodDeleted" : "")%>">
                                            <td>
                                                <div class="d-flex px-2 py-1">
                                                    <div>
                                                        <img src="data:image/jpg;base64,<%=prod.encodeImageToString()%>" class="avatar avatar-sm me-3" alt="user1">
                                                    </div>
                                                    <div class="d-flex flex-column justify-content-center">
                                                        <h6 class="mb-0 font-weight-bold text-sm">
                                                            <%=prod.getProdName()%>
                                                            <% if (prod.getIsDeleted()) { %>
                                                            <br/>
                                                            <span class="remark">Remark: This product has been deleted and just for record purpose.</span>
                                                            <% }%>
                                                        </h6>
                                                        <p class="text-xs text-secondary mb-0">Sub-Category: <%=prod.getSubCategoryId().getSubCategoryName()%></p>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="align-middle">
                                                <p class="text-xs font-weight-bold mb-0"><%=String.format("%.2f", prod.getUnitPrice())%></p>
                                                <p class="text-xs text-secondary mb-0">Stock: <%=prod.getStock()%></p>
                                            </td>
                                            <td class="align-middle text-center text-sm">
                                                <p class="text-xs mb-0"><%=prod.getItemSold(startDate, endDate)%></p>
                                            </td>
                                            <td class="align-middle text-center">
                                                <span class="text-secondary text-xs font-weight-bold">
                                                    <%=String.format("%.2f", prod.getUnitPrice() * prod.getItemSold(startDate, endDate))%>
                                                </span>
                                            </td>
                                        </tr>
                                        <% }%>
                                        <tr class="last-row">
                                            <td class="align-middle text-center font-weight-bold" colspan="2">TOTAL(RM)</td>
                                            <td class="align-middle font-weight-bolder text-center"><%=totalItemSold%></td>
                                            <td class="align-middle font-weight-bolder text-center"><%=String.format("%.2f", totalProfit)%></td>
                                        </tr>
                                        <% }%>
                                    </tbody>
                                </table>
                            </div>
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
