<%-- 
    Document   : View Product
    Created on : Apr 9, 2022, 11:18:09 PM
    Author     : Lee Jing Jet
--%>

<%@page import="java.util.stream.Collectors"%>
<%@page import="entity.*"%>
<%@page import="java.util.*"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%
    int prodShowCount = 0;
    Product product = new Product();
    List<Product> prodList = null;
    if (application.getAttribute("productList") != null) {
        prodList = (List<Product>) application.getAttribute("productList");
    }

    List<Product> sortProdList = null;
    if ((List<Product>) session.getAttribute("sortProdList") != null) {
        sortProdList = (List<Product>) session.getAttribute("sortProdList");
    } else {
        sortProdList = new ArrayList<Product>(prodList);
    }

    if (sortProdList != null) {
        for (Product p : sortProdList) {
            if (p != null) {
                ++prodShowCount;
            }
        }
    }

    List<PromotionItem> validPiList = null;
    List<Product> prodInPromo = null;
    Product tempP = new Product();
    if (application.getAttribute("validPromotionItemList") != null) {
        validPiList = (List<PromotionItem>) application.getAttribute("validPromotionItemList");
        prodInPromo = tempP.findAllProductinPromo(validPiList);
    }

    List<Category> categList = null;
    if (application.getAttribute("categList") != null) {
        categList = (List<Category>) application.getAttribute("categList");
    }
    List<SubCategory> subCategList = null;
    if (application.getAttribute("subCategList") != null) {
        subCategList = (List<SubCategory>) application.getAttribute("subCategList");
    }

    session.setAttribute("prodList", prodList);
    session.setAttribute("sortProdList", sortProdList);
    session.setAttribute("path", "ViewProduct.jsp");

%>

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


        <!-- Breadcrumb at page top -->
        <div class="topPage-bc">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="index.jsp">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Product</li>
                </ol>
            </nav>
        </div>
        <div class="container">
            <div class="row">
                <div class="sideCategBtn">
                    <span class="fas fa-bars"></span>
                </div>
                <div class="container sideCategBar sideCategPanel col-lg-2">
                    <div class="sideCateg-wrapper">
                        <div class="outer">
                            <div class="sideLogo"></div>
                            <div class="sideTitle">AG Market</div>
                        </div>
                        <div class="subTitle">Category List</div>

                        <ul>
                            <%                                int i = 1;
                                for (Category category : categList) {
                                    if (!category.getIsDeleted()) {
                            %>
                            <li>
                                <a href="javascript:void(0)" class="sideCateg<%=i%>">
                                    <% if (category.getCategoryName().equals("Chilled & Frozen Products")) { %>
                                    CAF Products
                                    <% } else {%>
                                    <%=category.getCategoryName()%>
                                    <% }%>
                                    <span class="fas fa-caret-down sideArrow<%=i%>"></span>
                                </a>
                                <ul class="sideSubCategList<%=i++%>">
                                    <% for (SubCategory subCateg : subCategList) {
                                            if(subCateg.getCategoryId().equals(category) && !subCateg.getIsDeleted()) {%>
                                    <li>
                                        <a href="ProdGetBySubCateg?subCategId=<%=subCateg.getSubCategoryId()%>">
                                            <%=subCateg.getSubCategoryName()%>
                                        </a>
                                    </li>
                                    <% }
                                        } %>
                                </ul>
                            </li>
                            <% }
                                }%>
                        </ul>
                        <div id="popup-btn-wrapper">
                            <a href="#popup1" class="popup-button">
                                <span>Your<br>Filter</span>
                            </a>
                            <p class="totalShow">Current Display:<br><span><%=prodShowCount%> Products</span></p>  
                        </div>
                    </div>               
                </div>

                <div id="popup1" class="popup-overlay">
                    <div class="popup-filter">
                        <h2>All Filters</h2>
                        <a class="close-popup" href="#">&times;</a>
                        <div class="popup-content">
                            <div class="accordion-bral">
                                <!-- Filter by Category -->
                                <div>
                                    <input class="ac-input" id="ac-1" name="accordion-1" type="checkbox" checked/>
                                    <label class="ac-label" for="ac-1">Category<i class="accordian-i"></i></label>
                                    <div class="article ac-content">
                                        <form action="ProdGetByCateg">
                                            <section class="check-wrapper">
                                                <%
                                                    int k = 0;
                                                    for (Category categ : categList) {
                                                        if (!categ.getIsDeleted()) {%>
                                                <input id='categ<%=k%>' type='checkbox' name="chkCateg" class="popup-check" value="<%=categ.getCategoryId()%>"/>
                                                <label for='categ<%=k%>'><span></span><%=categ.getCategoryName()%><ins><i><%=categ.getCategoryName()%></i></ins></label>
                                                        <%      ++k;
                                                                }
                                                            } %>
                                                <input type="submit" name="chkCateg-btn" class="chkCateg-btn" value="Go On!"/>
                                            </section>
                                        </form>
                                        <br>
                                    </div>
                                </div>
                                <!-- Filter by Price -->
                                <div>
                                    <input class="ac-input" id="ac-2" name="accordion-1" type="checkbox" checked/>
                                    <label class="ac-label" for="ac-2">Filter Price<i class="accordian-i"></i></label>
                                    <div class="article ac-content2">
                                        <form action="ProdGetByPriceRange">
                                            <div class="price-range-wrapper">
                                                <span>
                                                    <label for='priceText' class="RM-label">RM </label>
                                                    <input type="number" value="5" id="priceText" name="priceRange1" class="price-text-range" id="range1" min="0" max="1500"/>
                                                    <input type="number" value="500" id="priceText" name="priceRange2" class="price-text-range"  id="range2" min="0" max="1500"/>
                                                </span>    
                                                <input value="5" type="range" class="price-slider-range" id="sliderPoint1" min="0" max="1500" step="20" />
                                                <input value="500" type="range" class="price-slider-range" id="sliderPoint2" min="0" max="1500" step="20" />
                                                <input type="submit" name="chkCateg-btn2" class="chkCateg-btn2" value="Go On!"/>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <!-- Filter by Name -->
                                <div>
                                    <input class="ac-input" id="ac-3" name="accordion-1" type="checkbox" checked/>
                                    <label class="ac-label" for="ac-3">Filter Name<i class="accordian-i"></i></label>
                                    <div class="article ac-content3">
                                        <form action="ProdGetBySomeName">
                                            <input class="form-control filter_search" name="nameSearch" type="search" placeholder="Product Name..." aria-label="Search">
                                            <button class="btn filter_search_btn" type="submit">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                                </svg>
                                            </button>
                                            <input type="submit" name="chkCateg-btn3" class="chkCateg-btn3" value="Go On!"/>
                                        </form>
                                    </div>
                                </div>
                            </div>


                        </div>
                    </div>
                </div>

                <%
                    List<String> subCategNameList = new ArrayList<String>();
                    if (!sortProdList.isEmpty()) {
                        for (Product p : sortProdList) {
                            subCategNameList.add(p.getSubCategoryId().getSubCategoryName());
                        }
                    }
                    session.setAttribute("subCategNameList", subCategNameList);
                %>
                <section class="shop container col-lg-10">
                    <div class="categ-heading item-heading">
                        <h1>Enjoy Our Variety <br><span>Featured Items</h1>
                        <!--<a href="javascript:void(0)" class="seeAll-btn">Sort By<i class='bx bx-right-arrow-alt' ></i></a>-->
                        <div class="drop-wrapper">
                            <button class="sort-btn" data-target="#sort-drop">Sort By</button>
                            <div class="sort-menu sort-drop" id="sort-drop">
                                <a href="ProdSortBy?action=oriSet">Default</a><hr>
                                <a href="ProdSortBy?action=ascName">Name: A-Z</a>
                                <a href="ProdSortBy?action=desName">Name: Z-A</a>
                                <a href="ProdSortBy?action=ascPrice">Price: Hight-Low</a>
                                <a href="ProdSortBy?action=desPrice">Price: Low-High</a>
                            </div>
                        </div>
                    </div>
                    <!-- =====================Product Start =========================-->
                    <div class="row">
                        <%
                            if (!sortProdList.isEmpty()) {
                                for (Product prod : sortProdList) {
                                    if (!prod.getIsDeleted()) {
                        %>
                        <div class="shop-content col-md-3 col-sm-3 my-3">
                            <div class="product-box card w-100">
                                <a href="GetSingleProduct?prodId=<%=prod.getProdId()%>">
                                    <img src="data:image/jpg;base64,<%=prod.encodeImageToString()%>" alt="" class="product-img">
                                    <% if (prodInPromo.indexOf(prod) != -1) {
                                            PromotionItem promoIm = validPiList.get(prodInPromo.indexOf(prod));
                                    %> 
                                    <h2 class="product-title" style="padding-left: 10px;"><%=prod.getProdName()%><span style="background-color: #ff6666" >&nbsp;&nbsp;-<%=String.format("%.0f", promoIm.getPromoRate() * 100)%>%</span></h2>
                                    <span class="price" style="padding-left: 10px;"><span style="text-decoration: line-through ;">RM <%= String.format("%.2f", prod.getUnitPrice())%></span>&nbsp;&nbsp;RM <%= String.format("%.2f", prod.getOfferPrice(promoIm.getPromoRate()))%></span>

                                    <% } else {%> 
                                    <h2 class="product-title" style="padding-left: 10px;"><%=prod.getProdName()%></h2>
                                    <span class="price" style="padding-left: 10px;">RM <%= String.format("%.2f", prod.getUnitPrice())%></span>

                                    <% } %>
                                    <% if (prod.getStock() > 0) {
                                            if (session.getAttribute("cart") == null) {%>
                                    <a href="AddToCart?prodId=<%=prod.getProdId()%>&cartId=empty&path=ViewProduct.jsp"><i class='bx bx-shopping-bag add-cart'></i></a>
                                        <%} else {
                                            Cart cart = (Cart) session.getAttribute("cart");%>
                                    <a href="AddToCart?prodId=<%=prod.getProdId()%>&cartId=<%=cart.getCartId()%>&path=ViewProduct.jsp"><i class='bx bx-shopping-bag add-cart'></i></a>
                                        <%}
                                            }%>

                                </a>
                            </div>
                        </div>
                        <%     }
                            }
                        } else { %>
                        <div class="noProdListed shadow dark2">
                            Emm...It looks like no product listed.<br>Please try other option instead.
                        </div>   
                        <% }%>
                    </div>
                    <!-- =====================Product End =========================-->
                </section>  
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
        <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
        <script src="assets/js/viewProdDropdown.js"></script>
        <!--<script src="assets/js/pagination.js"></script>-->

        <!-- Price Range -->
        <script src="assets/js/prodFilter.js"></script>

        <!-- Accept Cookies 
        (Note: If got time need to make in java code)
               Need find ways to click only one time if a user has clicked before -->
        <!--<script src="assets/js/acceptDrop.js"></script>-->
        <!-- End Accept Cookies -->


        <!-- End jQuery Frameworks
        ============================================= -->
    </body>
</html>



