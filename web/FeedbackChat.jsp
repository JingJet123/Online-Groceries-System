<%-- 
    Document   : FeedbackChart
    Created on : Apr 9, 2022, 1:40:06 AM
    Author     : Lee Jing Jet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.Feedback"%>
<%@page import="entity.FeedbackReply"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
  

        <!-- ========== Page Title ========== -->
        <title>AG Market</title>

        <!-- ========== Favicon Icon ========== -->
        <link
            rel="shortcut icon"
            href="assets/img/AGLogo/favicon.png"
            type="image/x-icon"
            />
        <link
            href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css"
            rel="stylesheet"
            />

        <!-- ========== Start Stylesheet ========== -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />
        <link href="style.css" rel="stylesheet" />
        <link href="layout.css" rel="stylesheet" />
        <link href="assets/css/FeedbackChat.css" rel="stylesheet"> 
<!--        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">-->


        <!-- ========== For Cust Side Menu ========== -->
        <link href="assets/css/custSide.css" rel="stylesheet" />

        <%
            List<FeedbackReply> feedbackReplyList = (List) session.getAttribute("feedbackReplyList");
            Feedback currentFeedback = (Feedback) session.getAttribute("currentFeedback");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY HH:MM");
        %>  

        <!-- ========== End Stylesheet ========== -->
    </head>
    <body onload="endPreLoading()">
        <!-- Preloader Start -->
        <jsp:include page="common/preloader.jsp">
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png" />
        </jsp:include>
        <!-- Preloader Ends -->

        <jsp:include page="common/header.jsp">
            <jsp:param name="loginPath" value="login.jsp" />
            <jsp:param name="registerPath" value="register.jsp" />
            <jsp:param name="logoImgSrc" value="assets/img/AGLogo/Logo2.png" />
            <jsp:param name="indexPath" value="index.jsp" />
        </jsp:include>

        <!-- For Cust Side Menue -->
        <jsp:include page="common/custSideMenu.jsp">
            <jsp:param name="profilePath" value="#" />
            <jsp:param name="orderPath" value="ShowCustOrderList" />
            <jsp:param name="feedbackPath" value="ToFeedback?path=MyFeedback.jsp" />
            <jsp:param name="profileClass" value="nav__link" />
            <jsp:param name="orderClass" value="nav__link " />
            <jsp:param name="feedbackClass" value="nav__link active" />
        </jsp:include>

        <section>
            <div class="row">
                <div class="col-xl-12 col-lg-12 col-md-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="table-responsive p-0">
                                <table class="table align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th>Order ID : <%= currentFeedback.getOrderId().getOrderId()%><i class="las la-slash"></i> | Category : <%= currentFeedback.getCategory()%></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="d-flex px-2 py-1">
                                                    <div>
                                                        <img src="data:image/jpg;base64,<%= currentFeedback.getCustId().encodeImageToString()%>" class="avatar avatar-sm me-3" alt="user1">
                                                    </div>
                                                    <div class="feedback-container">
                                                        <h6 class="customer-name">(<%= simpleDateFormat.format(currentFeedback.getFdbkDate())%>)<%= currentFeedback.getCustId().getCustName()%></h6>
                                                        <p class="feedback-message"><%= currentFeedback.getMessage()%></p>
                                                        <% System.out.println(currentFeedback.getMessage()); %>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <%for (FeedbackReply feedbackReply : feedbackReplyList) {%>
                                        <tr>
                                            <td>
                                                <div class="d-flex px-2 py-1">
                                                    <div>
                                                        <img src="data:image/jpg;base64,<%= feedbackReply.getStfId().encodeImageToString()%>" class="avatars avatar-sm me-3" id="image" alt="user1">
                                                    </div>
                                                    <div class="reply-container">
                                                        <h6 class="staff-name"> <%=feedbackReply.getStfId().getStfName()%> (<%=  simpleDateFormat.format(feedbackReply.getReplyDate())%>)</h6>
                                                        <p class="reply-message"><%= feedbackReply.getMessage()%></p>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>

                            </div>  
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- For Cust Side Menue -->
        <script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
        <script src="assets/js/custSide.js"></script>

        <!-- jQuery Frameworks
                ============================================= -->
        <!-- Custom bootstrap -->
        <script src="assets/js/jquery-1.12.4.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/bootsnav.js"></script>
        <script src="assets/js/main.js"></script>
        <!-- Pre-loading -->
        <script src="assets/js/preloader.js"></script>

        <!-- End jQuery Frameworks
            ============================================= -->
    </body>
</html>
