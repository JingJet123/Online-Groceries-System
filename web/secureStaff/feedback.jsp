<%-- 
    Document   : staffViewFeedback
    Created on : Mar 29, 2022, 12:54:47 AM
    Author     : Ng Eason
--%>

<%@page import="entity.Staff"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Feedback"%>
<%@page import="java.util.List"%>
<%@page import="service.FeedbackService"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- ========== Page Title ========== -->
        <title>AG Market Feedback</title>

        <!-- ========== Favicon Icon ========== -->
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <!-- ========== Start Stylesheet ========== -->
        <link href="../assets/css/feedback.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />

        <!-- ============ retrieve session object ========== -->
        <%
            List<Feedback> feedbackList = (List) session.getAttribute("feedbackList");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY HH:MM");
            List<Staff> stfList = (List<Staff>) application.getAttribute("stfList");
            Staff curStaff = null;
            for (Staff stf : stfList) {
                if (stf.getUsername().equals(request.getRemoteUser())) {
                    session.setAttribute("currentStaff", stf);
                    curStaff = stf;
                }
            }
        %>

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

        <%-- drop down feedback based on category problem (filter) --%>
        <div class="drop-wrapper">
            <button class="sort-btn" data-target="#sort-drop">Sort By</button>
            <div class="sort-menu sort-drop" id="sort-drop">
                <a href="../FeedbackFilter?category=All">
                    SHOW ALL
                </a>
                <a href="../FeedbackFilter?category=Service">
                    Service Problem
                </a>
                <a href="../FeedbackFilter?category=Delivery">
                    Delivery Problem
                </a>
                <a href="../FeedbackFilter?category=Product">
                    Product Problem
                </a>
                <a href="../FeedbackFilter?category=Refund">
                    Refund Problem
                </a>
            </div>
        </div>


        <% for (Feedback feedback : feedbackList) {%>  
        <table>  
            <tr>
                <th>Category: <%=feedback.getCategory()%> </th>
                    <%-- if staff role not equal to 'A' delete button will be hidden--%>
                    <% if (curStaff.getStfRole() == 'A') {%>
                <td>
                    <a onclick="return confirm('Feedback ID : <%= feedback.getFeedbackId()%> \nOrder ID : <%= feedback.getOrderId().getOrderId()%> \nFeedback Category : <%= feedback.getCategory()%>\n\n&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp Confirm to DELETE this feedback?')" href="../FeedbackDelete?feedbackID=<%=feedback.getFeedbackId()%>">
                        <button name="deleteButton" class="delete-button" type="submit" value="delete">DELETE</button>
                    </a>
                </td>
                <% } else { %>
                <%}%>
            </tr>
            <tr>
                <% Long feedbackID = feedback.getFeedbackId();%>
                <td id="ID">Feedback ID: <%=feedback.getFeedbackId()%> </td>
            </tr>
            <tr>
                <td>Order ID: <%= feedback.getOrderId().getOrderId()%> </td>

            </tr>
            <tr>
                <td>Customer Name: <%= feedback.getCustId().getCustName()%></td>      
            </tr>
            <tr>
                <td>Feedback Date: <%= simpleDateFormat.format(feedback.getFdbkDate())%> </td>
            </tr>
            <tr>
                <td>Feedback Message: <%= feedback.getMessage()%> </td>
            </tr>
            <tr> 
                <td>
                    <a href="../ToFeedbackReply?feedbackID=<%=feedbackID%>"><button class="replyButton">REPLY</button></a></td>
            </tr>
        </table>
        <% }%>

        <!--         Dropdown button at category section (Note: Not work yet)-->
        <script src="../assets/js/catgDrop.js"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
        <script src="../assets/js/viewProdDropdown.js"></script>
        <script src="assets/js/pagination.js"></script>
    </body>
</html>
