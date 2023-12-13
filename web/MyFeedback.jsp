<%-- 
    Document   : MyFeedback
    Created on : Apr 8, 2022, 12:20:13 PM
    Author     : Lee Jing Jet
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.Order"%>
<%@page import="entity.Feedback"%>
<%@page import="entity.Customer"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<Feedback> fdbkList = (List<Feedback>) session.getAttribute("feedbackList");
    List<Feedback> custFdbkList = new ArrayList<Feedback>();
    Customer customer = (Customer) session.getAttribute("customer");

    for (Feedback fdbk : fdbkList) {
        if (customer.getCustId().equals(fdbk.getCustId().getCustId())) {
            custFdbkList.add(fdbk);
        }
    }
%>  
<html>
    <head>
        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="description" content="Coder FiftyThree" />

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

        <link href="assets/css/MyFeedback.css" rel="stylesheet" />
        <!-- ========== For Cust Side Menu ========== -->
        <link href="assets/css/custSide.css" rel="stylesheet" />

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
            <jsp:param name="profilePath" value="MyProfile.jsp" />
            <jsp:param name="orderPath" value="ShowCustOrderList" />
            <jsp:param name="feedbackPath" value="ToFeedback?path=MyFeedback.jsp" />
            <jsp:param name="profileClass" value="nav__link" />
            <jsp:param name="orderClass" value="nav__link " />
            <jsp:param name="feedbackClass" value="nav__link active" />
        </jsp:include>

        <% if (custFdbkList.size() != 0) {
                for (Feedback feedback : custFdbkList) {
        %>
        <div class="row">
            <!-- ============Start Order Record -->
            <hr />
            <div class="card p-4">
                <div class="row">
                    <!-- cart product details -->
                    <div class="col-md-12 col-3">
                        <div class="row">
                            <!-- product name  -->
                            <div class="col-8 card-title">
                                <h4 class="mb-4">Order ID: <%=feedback.getOrderId().getOrderId()%> | Feedback ID: <%=feedback.getFeedbackId()%> </h4>
                                <p> Date:<%= feedback.getFdbkDate()%></p>
                            </div>
                            <!-- quantity -->
                            <div class="col-4">
                                <h5>Total Amount: RM<%= String.format("%.2f", feedback.getOrderId().getTotalAmount())%></h5>
                            </div>
                        </div>
                        <!-- price -->
                        <div class="row">
                            <div class="col-8 d-flex justify-content-between"></div>
                            <div class="col-4 d-flex justify-content-end">
                                <div class="btn action-btn"><a href="ToFeedbackReply?feedbackID=<%=feedback.getFeedbackId()%>&path=FeedbackChat.jsp"><h5>View Feedback</h5></a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============End Order Record -->
        </div>
        <% }
        } else { %>          
        <div class="row justify-content-center default-padding">
            <h3>The Feedback is Empty</h3>
        </div>
        <% }%>

        <script src="assets/js/custCheckOrder.js"></script>
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
