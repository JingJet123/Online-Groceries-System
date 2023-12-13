<%-- 
    Document   : Cart
    Created on : Apr 9, 2022, 11:18:09 PM
    Author     : Joey Kok Yen Ni
--%>

<%@page import="entity.*"%>
<%@page import="java.util.*"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
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
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <script src="https://kit.fontawesome.com/c6f1f55876.js" crossorigin="anonymous"></script>
        <link href='https://css.gg/shape-half-circle.css' rel='stylesheet'>

        <!-- ========== Swiper ========== -->
        <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tiny-slider/2.9.4/tiny-slider.css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tiny-slider/2.9.4/min/tiny-slider.js"></script>

        <!-- ========== Start Stylesheet ========== -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />    
        <link href="style.css" rel="stylesheet">
        <link href="layout.css" rel="stylesheet">
        <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css"/>
        <!-- ========== End Stylesheet ========== -->

        <style>
            .topPage-bc {
                margin-top: 20px !important;
            }

            @media only screen and (max-width: 1199px) {
                .topPage-bc {
                    margin-top: -20px !important
                }
            }

        </style>
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

        <!-- Scroll to Top Button -->
        <a id="top-btn" class="btn circle">
            <i class="fa fa-chevron-up"></i>
        </a>
        <!-- End Scroll to Top Button -->

        <%
            List<PromotionItem> validPiList = null;

            if (application.getAttribute("validPromotionItemList") != null) {
                validPiList = (List<PromotionItem>) application.getAttribute("validPromotionItemList");
            }
            double totalPriceForCart = 0;
            if (session.getAttribute("cartItemList") != null) {
                List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cartItemList");
                totalPriceForCart = (Double) session.getAttribute("totalPriceForCart");
                request.setAttribute("cartItemList", cartItemList);
    
        %>

        <!-- Breadcrumb at page top -->
        <div class="topPage-bc">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="index.jsp">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Shopping Cart</li>
                </ol>
            </nav>
        </div>

        <!-- Cart List -->
        <div class="container cart-page my-3">
            <div class="">
                <h1>Shopping Cart</h1>
                <div class="shopping-cart">
                    <div class="column-labels">
                        <label class="cartItem-image">Image</label>
                        <label class="cartItem-details">Product</label>
                        <label class="cartItem-price">Unit Price</label>
                        <label class="cartItem-quantity">Quantity</label>
                        <!-- <label class="cartItem-removal">Remove</label> -->
                        <label class="cartItem-line-price">Line Total</label>
                    </div>
                    <%                        Product tempP = new Product();
                        List<Product> prodInPromo = tempP.findAllProductinPromo(validPiList);
                        for (CartItem ci : cartItemList) {
                    %>
                    <div class="cartItem">
                        <div class="cartItem-image">
                            <a href="GetSingleProduct?prodId=<%=ci.getProduct().getProdId() %>">
                            <img src="data:image/jpg;base64,<%= ci.getProduct().encodeImageToString()%>">
                            </a>
                        </div>
                        <div class="cartItem-details">
                            <div class="cartItem-title"><%=ci.getProduct().getProdName()%>
                            </div>
                            <p class="cartItem-description"><%=ci.getProduct().getSubCategoryId().getSubCategoryName()%></p>

                            <a onclick="return confirm('Remove <%=ci.getProduct().getProdName()%> From Cart?')" href="DeleteCartItem?path=cart.jsp&cartId=<%=ci.getCart().getCartId()%>&prodId=<%=ci.getProduct().getProdId()%>" class="">
                                <button class="remove-cartItem">
                                    Remove 
                                    <!--<svg xmlns="http://www.w3.org/2000/svg" fill="#000000" viewBox="0 0 24 24" width="18px" height="18px"><path d="M 12 2 C 6.4889971 2 2 6.4889971 2 12 C 2 17.511003 6.4889971 22 12 22 C 17.511003 22 22 17.511003 22 12 C 22 6.4889971 17.511003 2 12 2 z M 12 4 C 16.430123 4 20 7.5698774 20 12 C 20 16.430123 16.430123 20 12 20 C 7.5698774 20 4 16.430123 4 12 C 4 7.5698774 7.5698774 4 12 4 z M 8.7070312 7.2929688 L 7.2929688 8.7070312 L 10.585938 12 L 7.2929688 15.292969 L 8.7070312 16.707031 L 12 13.414062 L 15.292969 16.707031 L 16.707031 15.292969 L 13.414062 12 L 16.707031 8.7070312 L 15.292969 7.2929688 L 12 10.585938 L 8.7070312 7.2929688 z"/></svg>-->                         
                                </button>
                            </a> 
                        </div>
                        <% if (prodInPromo.indexOf(ci.getProduct()) != -1) {
                                PromotionItem promoIm = validPiList.get(prodInPromo.indexOf(ci.getProduct()));
                        %>
                        <div class="cartItem-price">
                            <span style="text-decoration: line-through">
                                <%=String.format("%.2f", ci.getProduct().getUnitPrice())%></span><span style="padding: 5px; background-color: rgb(55, 219, 248);"><%=String.format(" -%.0f", promoIm.getPromoRate() * 100)%>%</span>

                            <br>RM <%=String.format("%.2f", promoIm.getPriceAfterDiscount())%>
                        </div>
                        <div class="cartItem-quantity">
                            <form>
                                <a href="ProdQtyIncDec?action=dec&qty=<%=ci.getQuantity()%>&cartId=<%=ci.getCart().getCartId()%>&prodId=<%=ci.getProduct().getProdId()%>&path=cart.jsp"><div class="value-button" id="decrease">-</div></a>
                                <input type="number" id="number" value="<%=ci.getQuantity()%>" disabled="true"/>
                                <a href="ProdQtyIncDec?action=inc&qty=<%=ci.getQuantity()%>&cartId=<%=ci.getCart().getCartId()%>&prodId=<%=ci.getProduct().getProdId()%>&path=cart.jsp"><div class="value-button" id="increase">+</div></a> 
                            </form>
                        </div>
                        <div class="cartItem-line-price"><%=String.format("%.2f", ci.calcSubTotalAfterDiscount(promoIm.getPromoRate()))%></div>

                        <% } else {%>

                        <div class="cartItem-price">
                            <span>
                                <%=String.format("%.2f", ci.getProduct().getUnitPrice())%></span>
                        </div>

                        <div class="cartItem-quantity">
                            <form>
                                <a href="ProdQtyIncDec?action=dec&qty=<%=ci.getQuantity()%>&cartId=<%=ci.getCart().getCartId()%>&prodId=<%=ci.getProduct().getProdId()%>&path=cart.jsp"><div class="value-button" id="decrease">-</div></a>
                                <input type="number" id="number" value="<%=ci.getQuantity()%>" disabled="true"/>
                                <a href="ProdQtyIncDec?action=inc&qty=<%=ci.getQuantity()%>&cartId=<%=ci.getCart().getCartId()%>&prodId=<%=ci.getProduct().getProdId()%>&path=cart.jsp"><div class="value-button" id="increase">+</div></a> 
                            </form>
                        </div>
                        <div class="cartItem-line-price"><%=String.format("%.2f", ci.getSubTotal())%></div>
                        <% }%>
                    </div>

                    <% } %>

                    <% }%>

                    <div class="totals">
                        <div class="totals-item">
                            <label>Subtotal</label>
                            <div class="totals-value" id="cart-subtotal"><%=String.format("%.2f", totalPriceForCart)%></div>
                        </div>
                    </div>     

                    <%if (totalPriceForCart != 0) {%>
                    <span class="checkOutWrapper"><a href="OrderCheckOut.jsp?totalPrice=<%=totalPriceForCart%>" class="checkOutBtn"></a></span>
                        <%}%>
                </div>
            </div>
        </div>

        <jsp:include page="common/alertPop.jsp">
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType")%>" />
        </jsp:include>

        <jsp:include page="common/footer.jsp" >
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png"/>
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
        <script src="https://kit.fontawesome.com/c6f1f55876.js" crossorigin="anonymous"></script>

        <!-- View Cart -->
        <script src="assets/js/cartOpen.js"></script>

        <!-- Dropdown button at category section (Note: Not work yet)-->
        <script src="assets/js/catgDrop.js"></script>

        <!-- Accept Cookies 
        (Note: If got time need to make in java code)
               Need find ways to click only one time if a user has clicked before -->
        <!--<script src="assets/js/acceptDrop.js"></script>-->
        <!-- End Accept Cookies -->


        <!-- End jQuery Frameworks
        ============================================= -->
    </body>


</html>



