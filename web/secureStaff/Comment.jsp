<%-- 
    Document   : Comment
    Created on : Apr 5, 2022, 11:20:58 PM
    Author     : Ng Eason
--%>

<%@page import="entity.Staff"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.Comment"%>
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
        <title>AG Market Comment</title>

        <!-- ========== Favicon Icon ========== -->
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <!-- ========== Start Stylesheet ========== -->
        <link href="../assets/css/Comment.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />

        <!-- ============ retrieve session object ========== -->
        <%
            List<Comment> commentList = (List) session.getAttribute("commentList");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/YYYY HH:MM");
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

        <%-- a sort by filter based on rating --%>
        <div class="drop-wrapper">
            <button class="sort-btn" data-target="#sort-drop">Sort By</button>
            <div class="sort-menu sort-drop" id="sort-drop">
                <a href="../CommentFilter?rating=0">
                    SHOW ALL
                </a>
                <a href="../CommentFilter?rating=5">
                    <% for (int i = 0; i < 5; i++) {%>
                    <i class="las la-star" id="star"></i>
                    <% } %>
                </a>
                <a href="../CommentFilter?rating=4">
                    <% for (int i = 0; i < 4; i++) {%>
                    <i class="las la-star" id="star"></i>
                    <% } %>
                </a>
                <a href="../CommentFilter?rating=3">
                    <% for (int i = 0; i < 3; i++) {%>
                    <i class="las la-star" id="star"></i>
                    <% } %>
                </a>
                <a href="../CommentFilter?rating=2">
                    <% for (int i = 0; i < 2; i++) {%>
                    <i class="las la-star" id="star"></i>
                    <% } %>
                </a>
                <a href="../CommentFilter?rating=1">
                    <i class="las la-star" id="star"></i>
                </a> 
            </div>
        </div>

        <%
            for (Comment comment : commentList) {
                if (!comment.getProdId().getIsDeleted()) {
        %>
        <table>
            <tr>
                <th>Rating :
                    <% for (int i = 0; i < comment.getRating(); i++) {%>
                    <i class="las la-star" id="star"></i>
                    <%}%>
                </th>
                <%-- if staff role not equal to 'A' delete button will be hidden--%>
                <% if (curStaff.getStfRole() == 'A') {%>
                <td><a onclick="return confirm('Comment ID : <%= comment.getCommentId()%> \nProduct ID : <%= comment.getProdId().getProdId()%> \nProduct Name : <%= comment.getProdId().getProdName()%>\n\n&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp Confirm to DELETE this Comment?')" href="../CommentDelete?commentID=<%=comment.getCommentId()%>"><button class="delete-button" name="deleteButton" type="submit" value="delete">DELETE</button></a></td>
                <%} else {%>
                <%}%>
            </tr>
            <tr>
                <td>Comment ID : <%= comment.getCommentId()%></td>
            </tr>
            <%--
            <tr>
                <td><img src="data:image/jpg;base64,<%= comment.getProdId().encodeImageToString()%>" alt="" class="product-img"> </td>
            </tr>
            --%>
            <tr>
                <td>Product Name : <%=comment.getProdId().getProdName()%></td>
            </tr> 
            <tr>
                <td>Customer Name : <%=comment.getCustId().getCustName()%></td>
            </tr>
            <tr>
                <td>Comment Date : <%= simpleDateFormat.format(comment.getCmtDate())%></td>
            </tr>
            <tr>
                <td>Comment Message: <%=comment.getMessage()%></td>
            </tr> 
            <tr>    
                <td><a href="../ToCommentReply?commentID=<%=comment.getCommentId()%>"><button class="replyButton">REPLY</button></a></td>
            </tr>
        </table>
        <%}
            }%>

        <!--         Dropdown button at category section (Note: Not work yet)-->
        <script src="../assets/js/catgDrop.js"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
        <script src="../assets/js/viewProdDropdown.js"></script>
        <script src="assets/js/pagination.js"></script>
    </body>
</html>
