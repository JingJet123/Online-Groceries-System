<%-- 
    Document   : ViewCustomer
    Created on : Apr 4, 2022, 6:44:11 PM
    Author     : New Yee Hao
--%>

<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Customer"%>
<%@page import="java.util.List"%>
<%
    List<Customer> customerList = (List<Customer>) session.getAttribute("custList");
    if (session.getAttribute("sortedCustList") != null) {
        customerList = (List<Customer>) session.getAttribute("sortedCustList");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AG Market View Customer</title>
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/StaffDashboard.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />
        <link href="../assets/css/viewCust.css" rel="stylesheet" />
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
                                        <li class="breadcrumb-item active" aria-current="page">View Customer Records</li>
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
                                                <a href="../SortCustomer?criteria=CustName" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Customer Details</a>
                                            </th>
                                            <th>
                                                <a href="../SortCustomer?criteria=Contact" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Contact No.</a>
                                            </th>
                                            <th>
                                                <a href="../SortCustomer?criteria=Address" 
                                                   class="sorting text-center text-uppercase text-secondary font-weight-bolder opacity-85">Address</a>
                                            </th>
                                            <th>
                                                <a href="../SortCustomer?criteria=TotalOrder" 
                                                   class="sorting text-center text-uppercase text-secondary font-weight-bolder opacity-85">Total Order Made</a>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Customer cust : customerList) {%>
                                        <tr>
                                            <td>
                                                <div class="d-flex px-2 py-1">
                                                    <div>
                                                        <% if (cust.getProfileImg() != null) {%>
                                                        <img src="data:image/jpg;base64,<%=cust.encodeImageToString()%>" class="avatar avatar-sm me-3" alt="user1">
                                                        <% } else { %>
                                                        <img src="../assets/img/profile.png" class="avatar avatar-sm me-3" alt="Profile Image" style="color: black"/>
                                                        <% }%>
                                                    </div>
                                                    <div class="d-flex flex-column justify-content-center">
                                                        <h6 class="mb-0 font-weight-bold text-sm"><%=cust.getCustName()%></h6>
                                                        <p class="text-xs text-secondary mb-0"><%=cust.getCustEmail()%></p>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="align-middle">
                                                <p class="text-xs  mb-0"><%=cust.getCustContact()%></p>
                                            </td>
                                            <td class="align-middle text-center text-sm">
                                                <p class="text-xs mb-0"><%=cust.getCustAddress()%></p>
                                            </td>
                                            <td class="align-middle text-center">
                                                <span class="text-secondary text-xs font-weight-bold"><%=cust.getTotalOrderMade()%></span>
                                            </td>
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
    </body>
</html>
