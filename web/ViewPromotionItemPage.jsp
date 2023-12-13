<%-- 
    Document   : ViewPromotionItemPage
    Created on : Apr 10, 2022, 4:41:04 PM
    Author     : Joey Kok Yen Ni
--%>
<%@page import="entity.Cart"%>
<%@page import="entity.PromotionItem"%>
<%@page import="entity.Product"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- ========== Page Title ========== -->
        <title>AG Market</title>

        <!-- ========== Favicon Icon ========== -->
        <link rel="shortcut icon" href="assets/img/AGLogo/favicon.png" type="image/x-icon">
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>

        <!-- ========== Swiper ========== -->
        <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css"/>

        <!-- ========== Start Stylesheet ========== -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />
        <link href="style.css" rel="stylesheet" />
        <link href="layout.css" rel="stylesheet" />
        <link href="assets/css/SearchProduct.css" rel="stylesheet" />

        <!-- ========== End Stylesheet ========== -->

        <%
            List<PromotionItem> validPiList = null;
            List<Product> prodInPromo = null;
            Product tempP = new Product();
            if (application.getAttribute("validPromotionItemList") != null) {
                validPiList = (List<PromotionItem>) application.getAttribute("validPromotionItemList");
                prodInPromo = tempP.findAllProductinPromo(validPiList);
            }
        %>
    </head>
    <body onload="endPreLoading()">
        <jsp:include page="common/preloader.jsp"> 
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png" />
        </jsp:include> 
        <!-- Preloader Ends -->

        <jsp:include page="common/header.jsp">
            <jsp:param name="loginPath" value="login.jsp" />
            <jsp:param name="registerPath" value="register.jsp" />
            <jsp:param name="logoImgSrc" value="assets/img/AGLogo/Logo2.png" />
            <jsp:param name="indexPath" value="#" />
        </jsp:include>

        <!-- Breadcrumb at page top -->
        <div class="topPage-bc">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="index.jsp">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Promotion Item</li>
                </ol>
            </nav>
        </div>

        <!--===========================Searched Products====================-->

        <section class="shop container">
            <div style="padding-top: 0" class="categ-heading item-heading ">
                <h1>Enjoy the Best Deal at <br> <span>AG Market</h1></div>
            <div class="col-lg-12 swiper-container">
                    <a href="RetrievePromotionItem?path=ViewPromotionItemPage.jsp">
                    <div class="tab-content swiper mySwiper">
                        <div class="swiper-wrapper">
                            <div class="swiper-slide"><img src="assets/img/slider/slide1.png" alt="slider"/></div>
                            <div class="swiper-slide"><img src="assets/img/slider/slide2.png" alt="slider"/></div>
                            <div class="swiper-slide"><img src="assets/img/slider/slide3.png" alt="slider"/></div>
                            <div class="swiper-slide"><img src="assets/img/slider/slide4.png" alt="slider"/></div>
                            <div class="swiper-slide"><img src="assets/img/slider/slide5.png" alt="slider"/></div>
                            <div class="swiper-slide"><img src="assets/img/slider/slide6.png" alt="slider"/></div>
                            <div class="swiper-slide"><img src="assets/img/slider/slide7.png" alt="slider"/></div>
                        </div>
                        <!--              <div class="swiper-button-next"></div>
                                      <div class="swiper-button-prev"></div>-->
                        <div class="swiper-pagination"></div>
                    </div>
                    </a>
                </div> 
            <% if (!validPiList.isEmpty()) { %>
            <div class="row">
                <% for (Product prod : prodInPromo) {%>
                <div class="shop-content col-md-3 col-sm-3 my-3">
                    <a href="GetSingleProduct?prodId=<%=prod.getProdId()%>">
                        <div class="product-box card w-100">
                            <img src="data:image/jpg;base64,<%=prod.encodeImageToString()%>" alt="" class="product-img">
                            <%      PromotionItem promoIm = validPiList.get(prodInPromo.indexOf(prod));%>
                            <h2 class="product-title" style="padding-left: 10px;"><%=prod.getProdName()%><span style="background-color: #ff6666" >&nbsp;&nbsp;-<%=String.format("%.0f", promoIm.getPromoRate() * 100)%>%</span></h2>
                            <span class="price" style="padding-left: 10px;"><span style="text-decoration: line-through ;">RM <%= String.format("%.2f", prod.getUnitPrice())%></span>&nbsp;&nbsp;RM <%= String.format("%.2f", prod.getOfferPrice(promoIm.getPromoRate()))%></span>

                            <% if (prod.getStock() > 0) {
                                    if (session.getAttribute("cart") == null) {%>
                            <a href="AddToCart?prodId=<%=prod.getProdId()%>&cartId=empty&path=index.jsp"><i class='bx bx-shopping-bag add-cart'></i></a>
                                <% } else {
                                    Cart cart = (Cart) session.getAttribute("cart");%>
                            <a href="AddToCart?prodId=<%=prod.getProdId()%>&cartId=<%=cart.getCartId()%>&path=index.jsp"><i class='bx bx-shopping-bag add-cart'></i></a>
                                <% } %>
                          <% } %>
                        </div>
                    </a>
                </div>
                <%}%>
            </div>
            <% }else {%>
            <div class="row justify-content-center default-padding">
                <h2>Emm...It looks like no product listed.</h2>
            </div>
            <% }%>

        </section>

        <jsp:include page="common/footer.jsp" >
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png"/>
        </jsp:include>

        <jsp:include page="common/alertPop.jsp">
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType")%>" />
        </jsp:include>


        <!-- jQuery Frameworks
         ============================================= -->
        <!-- Custom bootstrap -->
        <script src="assets/js/jquery-1.12.4.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/bootsnav.js"></script>
        <script src="assets/js/main.js"></script>

        <!-- Pre-loading & Back-To-Top -->
        <script src="assets/js/preloader.js"></script>
        <script src="assets/js/backToTop.js"></script>

        <!-- Swiper -->
        <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
        <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
        <script src="assets/js/slider.js"></script>

        <!-- View Cart -->
        <script src="assets/js/cartOpen.js"></script>

    </body>
</html>

