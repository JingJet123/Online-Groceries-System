<%-- 
    Document   : header
    Created on : Mar 6, 2022, 11:53:54 PM
    Author     : Chuah Shee Yeap
--%>

<%@page import="entity.PromotionItem"%>
<%@page import="entity.Product"%>
<%@page import="java.util.*"%>
<%@page import="entity.CartItem"%>
<%@page import="entity.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header id="home">
    <nav class="navbar navbar-default navbar-sticky bootsnav">
        <%

            double totalPriceForCart = 0;
            int numOfCartItem = 0;

            List<CartItem> cartItemList = null; 
            if (session.getAttribute("cartItemList") != null) {

                cartItemList = (List<CartItem>) session.getAttribute("cartItemList");
                numOfCartItem = cartItemList.size();
                totalPriceForCart = (Double) session.getAttribute("totalPriceForCart");
            }

            List<PromotionItem> piList = null;
            List<PromotionItem> validPiList = null;
            if (application.getAttribute("allPromotionItemList") != null) {
                piList = (List<PromotionItem>) application.getAttribute("allPromotionItemList");
            }
            if (application.getAttribute("validPromotionItemList") != null) {
                validPiList = (List<PromotionItem>) application.getAttribute("validPromotionItemList");
            }
        %>
        <div class="container">
            <div class="attr-nav button">
                <ul>
                    <% if (session.getAttribute("customer") != null) {

                            Customer customer = (Customer) session.getAttribute("customer");%>
                    <li>
                        <a class="smooth-menu header-btn1" href="MyProfile.jsp"><%= customer.getCustName()%></a>
                    </li>
                    <% } else {%>

                    <li>
                        <a class="smooth-menu header-btn1" href="<%= request.getParameter("loginPath")%>">Login</a>
                    </li>
                    <li>
                        <a class="smooth-menu header-btn2" href="<%= request.getParameter("registerPath")%>">Register</a>
                        <a class="smooth-menu header-btn3-minimize" href="<%= request.getParameter("loginPath")%>">Login/Register</a>
                    </li>
                    <% }%>
                </ul>
            </div>

            <div class="navbar-header">
                <a class="navbar-brand smooth-menu header-logo" href="<%= request.getParameter("indexPath")%>">
                    <!--<a class="navbar-brand smooth-menu header-logo" href="index.jsp">-->
                    <img src="<%= request.getParameter("logoImgSrc")%>" alt="Logo">
                </a>
            </div>
            <!-- <div class="collapse navbar-collapse" id="navbar-menu"></div> -->
            <div class="" id="navbar-menu"> 
                <ul class="nav navbar-nav navbar-left" data-in="#" data-out="#" style="top: 30px; position: relative;">
                    <li>
                        <form class="form-inline" action="ToSearchProduct">
                            <input class="form-control header_search" name="productSearch" type="search" placeholder="Search..." aria-label="Search">
                                <button class="btn search_btn" type="submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                    </svg>
                                </button>
                        </form>
                    </li>
                    <li>
                        <!-- javascript:void(0) to prevent refreshing page -->
                        <a href="javascript:void(0)" class="cartIcon">
                            <i class='bx bxs-cart' style="font-size: 48px;"></i>
                            <span class='badge badge-danger' id='lblCartCount'><%= numOfCartItem%></span>
                        </a>
                    </li>
                </ul>
                <!-- Cart pop from right -->
                <div class="cart">
                    <h2 class="cart-title">Your Cart</h2>
                    <!-- Content -->
                    <div class="cart-content">
                        <%
                            if (cartItemList!= null) {
                                    
                                if (!cartItemList.isEmpty()) {

                                    Product tempP = new Product();
                                    List<Product> prodInPromo = tempP.findAllProductinPromo(validPiList);

                                    for (CartItem ci : cartItemList) {
                        %>
                        <div class="cart-box">
                             <a href="GetSingleProduct?prodId=<%=ci.getProduct().getProdId() %>">
                                 <img src="data:image/jpg;base64,<%= ci.getProduct().encodeImageToString()%>" alt="" class="cart-img"></a>
                                <div class="detail-box">
                                    <div class="cart-product-title"><%=ci.getProduct().getProdName()%></div>
                                    <div class="cart-price">
                                        <% if (prodInPromo.indexOf(ci.getProduct()) != -1) {
                                                PromotionItem promoIm = validPiList.get(prodInPromo.indexOf(ci.getProduct()));
                                        %>
                                        RM<span style="text-decoration: line-through;">
                                            <%=String.format("%.2f", ci.getProduct().getUnitPrice())%>/Unit
                                        </span><%=String.format(" -%.0f", promoIm.getPromoRate() * 100)%>%<br>
                                            RM <%=String.format("%.2f", promoIm.getPriceAfterDiscount())%>/Unit

                                            <% } else {%>
                                            RM <%=String.format("%.2f", ci.getProduct().getUnitPrice())%>/Unit
                                            <% }%>
                                    </div> 
                                    <input type="text" value="Qty: <%=ci.getQuantity()%>" readonly class="cart-quantity">
                                </div>
                                <!--Remove Cart Item--> 
                                <a onclick="return confirm('Remove <%=ci.getProduct().getProdName()%> From Cart?')" href="DeleteCartItem?path=cart.jsp&cartId=<%=ci.getCart().getCartId()%>&prodId=<%=ci.getProduct().getProdId()%>"><i class='bx bxs-trash-alt cart-remove'></i></a>
                        </div>
                        <% }
                            }%>

                    </div>
                    <div class="total">
                        <div class="total-title">Total</div>
                        <!-- Total after Discount -->
                        <div class="total-price">RM <%= String.format("%.2f", totalPriceForCart)%></div>
                    </div>
                    <a href="RetrieveCartItem">      
                        <button type="button" class="btn-buy" onclick="<% session.setAttribute("path", "cart.jsp");%>">View Details</button>
                    </a>
                    <% } else { %>
                    <button type="button" class="btn-buy" disabled>Empty</button>
                    <% }%>
                    <i class='bx bx-x' id="close-cart"></i>
                </div>
            </div>
        </div>
    </nav>
</header>