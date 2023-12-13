<%-- 
    Document   : index
    Created on : Mar 7, 2022, 1:29:39 AM
    Author     : Chuah Shee Yeap
--%>

<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.*"%>
<%@page import="java.util.*"%>

<%
    List<Product> prodList = null;
    Product product = new Product();
    List<Product> randProdList = null;
    if (application.getAttribute("productList") != null) {
        prodList = (List<Product>) application.getAttribute("productList");
        randProdList = product.randomProducts(prodList);
    }
    session.setAttribute("prodList", prodList);
    session.setAttribute("path", "index.jsp");

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

        <!-- ========== End Stylesheet ========== -->
        <script src="https://kit.fontawesome.com/20cf2c878d.js" crossorigin="anonymous"></script>

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
                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                </ol>
            </nav>
        </div>
        <!-- Drop-down-right Category and Slider -->
        <div class="container category-list">
            <div class="row" style="padding-top: 5%">
                <div class="dropdown col-lg-4">               
                    <button class="btn btn-secondary dropdown-toggle drop-btn" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Category
                        <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 24 24"><path fill="none" d="M0 0h24v24H0V0z"/><path d="M8.71 11.71l2.59 2.59c.39.39 1.02.39 1.41 0l2.59-2.59c.63-.63.18-1.71-.71-1.71H9.41c-.89 0-1.33 1.08-.7 1.71z"/></svg>
                    </button>

                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton"> 
                        <% int i = 1;
                            for (Category category : categList) {
                                if (!category.getIsDeleted()) {%>
                        <a href="ProdGetByCateg?categId=<%=category.getCategoryId()%>" class="dropdown-item categ-main" data-target="#drop-right<%=i%>"><%= category.getCategoryName()%>
                            <div class="drop-menu drop-right" id="drop-right<%=i++%>">
                                <% for (SubCategory subCategory : subCategList) {
                                        if (subCategory.getCategoryId().equals(category) && !subCategory.getIsDeleted()) {%>
                                <a href="ProdGetBySubCateg?subCategId=<%=subCategory.getSubCategoryId()%>" class="dropdown-item categ-sub"><%= subCategory.getSubCategoryName()%></a>
                                <% }
                                    } %>
                            </div>
                        </a>
                        <% }
                            } %>
                    </div>         

                </div>
                <div class="col-lg-8 swiper-container">
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
            </div>
        </div>
        <!-- End Category and Slider -->

        <!--<a href="InsertRecordToDB">Insert Record</a>-->

        <!--Benefits  -->
        <section class="sec-benefits">
            <div class="cust-heading">
                <h2>Our Passionate's</h2>
            </div>  
            <div class="benefit-center box">
                <div class="benefit" id="tv">
                    <span class="benefit-icon"><i class='bx bxs-truck'></i></span>
                    <h4>Enjoy Free Shipping</h4>
                    <span class="text">Order capped at RM 200 or within specific period</span>
                </div>

                <div class="benefit" id="tv">
                    <span class="benefit-icon"><i class="bx bx-purchase-tag"></i></span>
                    <h4>Convenient</h4>
                    <span class="text">Shop online with AG, easily deliver to your doorstep</span>
                </div>

                <div class="benefit" id="tv">
                    <span class="benefit-icon"><i class='bx bxs-category-alt' ></i></span>
                    <h4>Satisfy All One Needs</h4>
                    <span class="text">Provides all-in-one and one-for-all category</span>
                </div>

                <div class="benefit" id="tv">
                    <span class="benefit-icon"><i class="material-icons" style="font-size: 64px;">event_note</i></span>
                    <h4>Event Launching Culture</h4>
                    <span class="text">Maintain a joyful moment while browsing</span>
                </div>

                <div class="benefit" id="tv">
                    <span class="benefit-icon"><i class="bx bx-headphone"></i></span>
                    <h4>24/7 Service Support</h4>
                    <span class="text">We're always be there to solve your doubt</span>
                </div>

                <div class="benefit" id="tv">
                    <span class="benefit-icon"><i class="fa-solid fa-arrow-rotate-left"></i></span>
                    <h4>7-days Returns</h4>
                    <span class="text">Provide refund upon specified rules</span>
                </div>
            </div>
        </section>
        <!-- End Benefits -->

        <!-- Div Categories -->
        <section class="categories" id="categories">
            <div class="categ-heading">
                <h1>Browse Our Popular | Hottest <br><span>Categories</h1>
                <a href="ViewProduct.jsp" class="seeAll-btn">See All<i class='bx bx-right-arrow-alt' ></i></a>
            </div>
            <!-- Container Content -->
            <div class="categories-container">
                <%
                    int j = 1, stock = 0, item = 0;
                    for (Category categ : categList) {
                        if (!categ.getIsDeleted()) {
                            for (Product p : prodList) {
                                String categName = p.getSubCategoryId().getCategoryId().getCategoryName();
                                if (categ.getCategoryName().equals(categName)) {
                                    stock += p.getStock();
                                    ++item;
                                }
                            }

                %>
                <a href="ProdGetByCateg?categId=<%=categ.getCategoryId()%>">
                    <div class="box box<%=j%>">
                        <img src="assets/img/specific/categ<%=j%>.png" alt="" class="product-img">
                        <h2><%=categ.getCategoryName()%></h2>
                        <span><%=item%> Items<br><%=stock%> Stock</span>
                        <i class='bx bx-right-arrow-alt' ></i>
                    </div>
                </a>
                <% ++j;
                        }
                        stock = 0;
                        item = 0;
                    } %>
            </div>
        </section>
        <!-- End Div Categories -->

        <!-- About Us -->
        <div class="about-section">
            <div class="container about-wrapper">
                <div class="content-section">
                    <div class="title">
                        <h1>About Us</h1>
                    </div>
                    <div class="content">
                        <h3>We're Focused. We're Passionate. We Love What We Do.</h3>
                        <p>
                            AG Market is a modern grocery store with a variety of products available for sale in retail. The market sells basic food products, non-food products, daily necessities as well as others that are in the range of product types that can be generally found in a grocery store.
                            <br><cite>--- AG MARKET TEAM ---</cite>
                        </p>
                        <button class="custom-btn readMore-btn"><span>Read More</span></button>
                    </div>
                    <div class="social">
                        <a target="_blank" href="https://discord.gg/BND3amjwJs" class="cs">
                            <span class="icon icon-discord"></span>
                        </a>
                        <a target="_blank" href="https://twitter.com/AGMarket4" class="cs">
                            <span class="icon icon-twitter"></span>
                        </a>
                        <a target="_blank" href="https://t.me/+q2CLxJhj1zFmYWFl" class="cs">
                            <span class="icon icon-telegram"></span>
                        </a>
                        <a target="_blank" href="https://www.facebook.com/AG-Market-104424522196094" class="cs">
                            <span class="icon1 icon-facebook"></span>
                        </a>
                    </div>
                </div>
                <div class="image-section thecard">
                    <div class="thefront">
                        <img src="assets/img/AGLogo/LogoBg2.png" class="object">
                    </div>
                    <div class="theback">
                        <img src="assets/img/AGLogo/LogoWordBg.png" class="object">
                    </div>
                </div>     
            </div>
        </div>

        <!--======================= Item Display with add to cart ============================-->
        <section class="shop container">
            <div class="categ-heading item-heading">
                <h1>Enjoy Our Variety <br><span>Featured Items</h1>
                <a href="ViewProduct.jsp" class="seeAll-btn">See All<i class='bx bx-right-arrow-alt' ></i></a>
            </div>
            <div class="row">
                <%
                    if (!randProdList.isEmpty()) {
                        for (Product prod : randProdList) {
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
                            <a href="AddToCart?prodId=<%=prod.getProdId()%>&cartId=empty&path=index.jsp"><i class='bx bx-shopping-bag add-cart'></i></a>
                                <%} else {
                                    Cart cart = (Cart) session.getAttribute("cart");%>
                            <a href="AddToCart?prodId=<%=prod.getProdId()%>&cartId=<%=cart.getCartId()%>&path=index.jsp"><i class='bx bx-shopping-bag add-cart'></i></a>
                                <%}
                                    }%>
                        </a>
                    </div>
                </div> 
                <% }
                    }
                } else { %>
                <div class="noProdListed shadow dark2">
                    Emm...It looks like no product listed.<br>Please try other option instead.
                </div>   
                <% }%>
            </div>
            <div class="viewMore">
                <a href="ViewProduct.jsp">
                    <div class="more-btn fas"> 
                        <i class='bx bxs-right-arrow-circle'></i>
                        <span>Click To View More Items...</span>
                    </div> 
                </a>
                <div class="object">
                    <i class="gg-shape-half-circle semiCicle-icon-up"></i>
                    <i class="gg-shape-half-circle semiCicle-icon-up"></i>
                    <i class="gg-shape-half-circle semiCicle-icon-up"></i>
                </div>
            </div> 
        </section> 
        <!-- Toast Message when a item is added -->
        <div id="toast">
            <div id="toast-img">ADD</div>
            <div id="description">You've successfully added this item...</div> 
        </div>
        <!--=============================== End Item Display with add to cart =================================-->

        <!-- Product Image Tiny Slider -->
        <section id="slider" class="shadow dark2">
            <div class="container">
                <div class="subcontainer">
                    <div class="slider-wrapper">
                        <div>
                            <h2>Frequently Searched Products</h2>
                        </div>
                        <br>
                        <div class="my-slider">

                            <div>
                                <div class="slide">
                                    <div class="slide-img img-1"><a href="GetSingleProduct?prodId=1">View Product</a></div><br>
                                    <div>
                                        <h3>Cass Beer</h3>
                                        <p>--- Alcohol ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-2"><a href="GetSingleProduct?prodId=12">View Product</a></div><br>
                                    <div>
                                        <h3>Yeo's Chrysanthemum Tea Drink</h3>
                                        <p>--- Coffee Tea ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-3"><a href="GetSingleProduct?prodId=21">View Product</a></div><br>
                                    <div>
                                        <h3>MariGold Apple Juice</h3>
                                        <p>--- Juice ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-4"><a href="GetSingleProduct?prodId=51">View Product</a></div><br>
                                    <div>
                                        <h3>Anchor Butter</h3>
                                        <p>--- Dairy ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-5"><a href="GetSingleProduct?prodId=64">View Product</a></div><br>
                                    <div>
                                        <h3>PacificWest Fish Fillets</h3>
                                        <p>--- Frozen Food ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-6"><a href="GetSingleProduct?prodId=70">View Product</a></div><br>
                                    <div>
                                        <h3>HaagenDazs Vanilla</h3>
                                        <p>--- Ice Cream ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-7"><a href="GetSingleProduct?prodId=89">View Product</a></div><br>
                                    <div>
                                        <h3>Anggur Hitem Tanpe Biji</h3>
                                        <p>--- Fruits ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-8"><a href="GetSingleProduct?prodId=127">View Product</a></div><br>
                                    <div>
                                        <h3>Sweet Peas 130G</h3>
                                        <p>--- Vegetables ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-9"><a href="GetSingleProduct?prodId=118">View Product</a></div><br>
                                    <div>
                                        <h3>Salmon Block - 2 Pieces</h3>
                                        <p>--- Seafood ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-10"><a href="GetSingleProduct?prodId=132">View Product</a></div><br>
                                    <div>
                                        <h3>Clorox Tru Blu Toilet Bowl Cleaner</h3>
                                        <p>--- Home Cleaning ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-11"><a href="GetSingleProduct?prodId=140">View Product</a></div><br>
                                    <div>
                                        <h3>Equip Marble+ 5 Piece Cookware Set Marble</h3>
                                        <p>--- Kitchen Utensils ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-12"><a href="GetSingleProduct?prodId=150">View Product</a></div><br>
                                    <div>
                                        <h3>Clorox Bleach Original</h3>
                                        <p>--- Laundry ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-13"><a href="GetSingleProduct?prodId=188">View Product</a></div><br>
                                    <div>
                                        <h3>Pocky Family Pack Strawberry Flavour</h3>
                                        <p>--- Canned Goods ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-14"><a href="GetSingleProduct?prodId=201">View Product</a></div><br>
                                    <div>
                                        <h3>Tai Sun Cocktail Nuts</h3>
                                        <p>--- Dried Nuts ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-15"><a href="GetSingleProduct?prodId=189">View Product</a></div><br>
                                    <div>
                                        <h3>Ritter Sport Dark Whole Hazelnuts</h3>
                                        <p>--- Chocolates & Candies ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-16"><a href="GetSingleProduct?prodId=225">View Product</a></div><br>
                                    <div>
                                        <h3>Jasmine Super Special Tempatan Rice</h3>
                                        <p>--- Grains ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-17"><a href="GetSingleProduct?prodId=236">View Product</a></div><br>
                                    <div>
                                        <h3>San Remo Spirals</h3>
                                        <p>--- Noodles ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-18"><a href="GetSingleProduct?prodId=262">View Product</a></div><br>
                                    <div>
                                        <h3>Brahims Kuah Kurma</h3>
                                        <p>--- Spice Seasoning ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-19"><a href="GetSingleProduct?prodId=281">View Product</a></div><br>
                                    <div>
                                        <h3>Clairol Herbal Essence Shampoo Ardan Oil</h3>
                                        <p>--- Hair Care ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-20"><a href="GetSingleProduct?prodId=305">View Product</a></div><br>
                                    <div>
                                        <h3>Dettol Antibacteria Wipes</h3>
                                        <p>--- Sanitary ---</p>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div class="slide">
                                    <div class="slide-img img-21"><a href="GetSingleProduct?prodId=319">View Product</a></div><br>
                                    <div>
                                        <h3>Sari Rania Gold Krim Pelembab Malam</h3>
                                        <p>--- Skin Care ---</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> 
        </section>
        <!-- End Product Image Tiny Slider -->

        <div class="semiCircle-wrapper object">
            <i class="gg-shape-half-circle semiCicle-icon-down"></i>
            <i class="gg-shape-half-circle semiCicle-icon-down"></i>
            <i class="gg-shape-half-circle semiCicle-icon-down"></i>
        </div> 


        <!-- Our Customers -->
        <div class="container cust-area">
            <div class="row">
                <div class="cust-heading">
                    <h2>Our Customer's</h2>
                </div>
                <div class="cust-wrapper">
                    <div class="box">
                        <div class="stars">
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star-half' ></i>
                        </div>
                        <p>I'm happy with the daily necessities got from online AG Market. They did a great joy to satisfy my need and excellently give me the best assistance to get exactly what I was looking for.</p>
                        <h2>Alimama Bayun</h2>
                        <img src="assets/img/specific/homeCustProfile1.jpg" alt="">
                    </div>
                    <div class="box">
                        <div class="stars">
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                        </div>
                        <p>AG Market provides the highest levels service, high-quality goods that I can ever wish for. All information is smooth and straightforward. Why can't I say something praise ?</p>
                        <h2>Macrosoft Technology</h2>
                        <img src="assets/img/specific/homeCustProfile2.jpg" alt="">
                    </div>
                    <div class="box">
                        <div class="stars">
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                            <i class='bx bxs-star' ></i>
                        </div>
                        <p>The details of goods and terms provided by AG Market are impressive. I truly love this top notch friendly online shopping experience with AG. What about next? Just Hooray !!</p>
                        <h2>Amajon</h2>
                        <img src="assets/img/specific/homeCustProfile3.jpg" alt="">
                    </div>
                </div>
            </div>
        </div>
        <!-- End Our Customers -->


        <!-- To Accept Cookies -->
        <!--    <div class="cookie-banner" style="">
                <p>
                    We use cookie to improve your browsing experience on our site. By using our site, you agree to our 
                    <a href="#" class="privacy-link text-primary">cookie policy</a>.
                  </p>
                <button class="close">&times;</button>
            </div>-->
        <!-- End To Accept Cookies -->

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


        <!-- End jQuery Frameworks
        ============================================= -->
    </body>
</html>