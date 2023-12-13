<%-- 
    Document   : feedbackReply
    Created on : Mar 31, 2022, 5:21:10 PM
    Author     : Ng Eason
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.Feedback"%>
<%@page import="entity.FeedbackReply"%>
<%@page import="java.util.List"%>
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
        <title>AG Market Feedback Reply</title>

        <!-- ========== Favicon Icon ========== -->
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <!-- ========== Start Stylesheet ========== -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/StaffDashboard.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />
        <link href="../assets/css/FeedbackReply.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />

        <!-- ============ retrieve session object ========== -->
        <%
            List<FeedbackReply> feedbackReplyList = (List) session.getAttribute("feedbackReplyList");
            Feedback currentFeedback = (Feedback) session.getAttribute("currentFeedback");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY HH:MM");
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
                                        <li class="breadcrumb-item active"><a href="../ToFeedback">Feedback</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Feedback Reply </li>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="table-responsive p-0">
                                <table class="table align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th style="font-size: 30px;">Order ID : <%= currentFeedback.getOrderId().getOrderId()%><i class="las la-slash"></i>Category : <%= currentFeedback.getCategory()%></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="d-flex px-2 py-1">
                                                    <div>
                                                        <img src="data:image/jpg;base64,<%= currentFeedback.getCustId().encodeImageToString()%>" class="avatar avatar-sm me-3" alt="user1">
                                                    </div>
                                                    <div class="d-flex flex-column justify-content-center">
                                                        <h6 class="customer-name"><%= currentFeedback.getCustId().getCustName()%> (<%= simpleDateFormat.format(currentFeedback.getFdbkDate())%>)</h6>
                                                        <p class="feedback-message" style="width:100%;"><%= currentFeedback.getMessage()%></p>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <%for (FeedbackReply feedbackReply : feedbackReplyList) {%>
                                        <tr>
                                            <td>
                                                <div class="d-flex px-2 py-1" style="float:right;">
                                                    <div>
                                                        <img src="data:image/jpg;base64,<%= feedbackReply.getStfId().encodeImageToString()%>" class="avatars avatar-sm me-3" id="image" alt="user1">
                                                    </div>
                                                    <div class="reply-container">
                                                        <h6 class="staff-name">(<%=  simpleDateFormat.format(feedbackReply.getReplyDate())%>) <%=feedbackReply.getStfId().getStfName()%></h6>
                                                        <p class="reply-message"><%= feedbackReply.getMessage()%></p>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <%}%>
                                        <%-- a form with input and button for allowing users to add comment--%>
                                    <form action="../AddFeedbackReply">
                                        <tr>
                                            <td><input class="inputField" type="textfield" name="message" size="150" placeholder="Aa"/></td>
                                            <td><button class="submit-button" type="submit" name="reply"><i class="las la-caret-right" style="color:white;"></i></button></td>
                                        </tr>
                                    </form>
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
