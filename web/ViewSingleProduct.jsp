<%-- 
    Document   : View Single Product
    Created on : Apr 9, 2022, 11:18:09 PM
    Author     : Joey Kok Yen Ni
--%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.*"%> <%@page errorPage="errorPage.jsp"
                                    contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean
    id="viewingProduct"
    class="entity.Product"
    scope="session"
    ></jsp:useBean>
<%
    Customer customer = null; //Get Current Customer
    if (session.getAttribute("customer") != null) {
        customer = (Customer) session.getAttribute("customer");
    }

    //Get All Comments for this product
//    List<Comment> commentList = null; 
//    if (session.getAttribute("viewingProdCommentList") != null) {
//        commentList = (List<Comment>) session.getAttribute("viewingProdCommentList");
//    }
    List<PromotionItem> validPiList = null; //Get All Promoting Products (To check if the current product is in)
    List<Product> prodInPromo = null;
    Product tempP = new Product();
    if (application.getAttribute("validPromotionItemList") != null) {
        validPiList = (List<PromotionItem>) application.getAttribute("validPromotionItemList");
        prodInPromo = tempP.findAllProductinPromo(validPiList);
    }

    List<OrderDetails> allOrderDetails = null; //Get All Order details
    if (application.getAttribute("allOrderDetailsList") != null) {
        allOrderDetails = (List<OrderDetails>) application.getAttribute("allOrderDetailsList");
    }

    //======================= Checking ============================
    boolean isCommentable = false;
    if (customer != null) {
        Customer tempCust = new Customer();
        isCommentable = tempCust.isCommentable(allOrderDetails, customer, viewingProduct, viewingProduct.getCommentList());
    }

    //=======================================================================
    List<Product> sortProdList = null; //Get all Related Products and Randomize the list
    if ((List<Product>) session.getAttribute("sortProdList") != null) {
        sortProdList = (List<Product>) session.getAttribute("sortProdList");
        Collections.shuffle(sortProdList);
        if (sortProdList.size() > 8) {
            sortProdList = sortProdList.subList(0, 8);
        } else if (sortProdList.size() > 4) {
            sortProdList = sortProdList.subList(0, 4);
        }
    }
%>

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
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
            />

        <!-- ========== Start Stylesheet ========== -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />
        <link href="style.css" rel="stylesheet" />
        <link href="layout.css" rel="stylesheet" />

        <!-- app css -->
        <link rel="stylesheet" href="assets/css/viewSingleProd.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
        <!-- ========== End Stylesheet ========== -->
        <style>
            .review-container{
                position: relative;
                /*width: 400px;*/
                background: #006666;
                padding: 20px 30px;
                /*border: 1px solid #444;*/
                border-radius: 5px;
                display: flex;
                /*align-items: center;*/
                justify-content: center;
                flex-direction: column;
            }
            .review-container .post{
                display: none;
            }
            .review-container .text{
                font-size: 25px;
                color: #666;
                font-weight: 500;
            }
            .review-container .edit{
                position: absolute;
                right: 10px;
                top: 5px;
                font-size: 16px;
                color: #666;
                font-weight: 500;
                cursor: pointer;
            }
            .review-container .edit:hover{
                text-decoration: underline;
            }
            .review-container .star-widget input{
                display: none;
            }
            .review-container .star-widget label{
                font-size: 40px;
                /*color: #444;*/
                padding: 10px;
                float: right;
                transition: all 0.2s ease;
            }
            .review-container input:not(:checked) ~ label:hover,
            .review-container input:not(:checked) ~ label:hover ~ label{
                color: #fd4;
            }
            .review-container input:checked ~ label{
                color: #fd4;
            }
            .review-container input#rate-5:checked ~ label{
                color: #fe7;
                text-shadow: 0 0 20px #952;
            }
            .review-container form{
                display: none;
            }
            .review-container input:checked ~ form{
                display: block;
            }
            .review-container .textarea{
                height: 100px;
                width: 100%;
                overflow: hidden;
            }
            .review-container .textarea textarea{
                border-radius: 5px;
                height: 100%;
                width: 100%;
                outline: none;
                color: #003333;
                /*border: 1px solid #333;*/
                background: #ccccff;
                padding: 10px;
                font-size: 17px;
                resize: none;
            }
            .review-container .textarea textarea:focus{
                border-color: #444;
            }
            .review-container .btn{
                height: 45px;
                width: 100%;
                margin: 15px 0;
            }
            .review-container .btn button{
                border-radius: 5px;
                height: 100%;
                width: 100%;
                /*border: 1px solid #444;*/
                outline: none;
                background: #ccccff;
                color: #003333;
                font-size: 17px;
                font-weight: 500;
                text-transform: uppercase;
                cursor: pointer;
                transition: all 0.3s ease;
            }
            .review-container .btn button:hover{
                background: #ccffcc;
            }
            .button {
                appearance: none;
                background: none;
                border: none;
                outline: none;

                padding: 15px 30px;
                border-radius: 8px;
                color: #212121;
                font-size: 22px;
                font-weight: 600;
                cursor: pointer;
                transition: 0.4s;
            }
            .button-4 {
                color: #FFF;
                background-color: #68A0DE;
                box-shadow:inset 0 -8px 0 0 rgba(0,0,0,0.17);
                transition: 0.1s;
                text-shadow: 0px 3px rgba(0,0,0,0.17);
            }

            .button-4:hover {
                box-shadow:inset 0 -6px 0 0 rgba(0,0,0,0.17);
                text-shadow: 0px 2px rgba(0,0,0,0.17);
            }

            .button-4:active {
                box-shadow:inset 0 0px 0 0 rgba(0,0,0,0.17);
                text-shadow: 0px 0px rgba(0,0,0,0.17);
            }
            .qty-wrapper{
                height: 40px;
                max-width: 380px;
            }
            .qty-wrapper input{
                width: 50%;
                text-align: center;
                font-size: 20px;
                font-weight: 600;
                cursor: pointer;
                user-select: none;
                border-radius: 12px;
                box-shadow: 0 5px 10px rgba(0,0,0,0.2);
            }
            .qty-wrapper input{
                font-size: 20px;
                border-right: 2px solid rgba(0,0,0,0.2);
                border-left: 2px solid rgba(0,0,0,0.2);
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

        <!-- Breadcrumb at page top -->
        <div class="topPage-bc" style="margin-top: 2.5%">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="index.jsp">Home</a></li>
                    <li class="breadcrumb-item">
                        <a href="ProdGetBySubCateg?subCategId=<%=viewingProduct.getSubCategoryId().getSubCategoryId()%>"><%=viewingProduct.getSubCategoryId().getSubCategoryName()%></a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">
                        <%=viewingProduct.getProdName()%>
                    </li>
                </ol>
            </nav>
        </div>

        <!-- Any new contents place here -->

        <!-- product-detail content -->
        <div class="bg-main">
            <div class="container">
                <!--============================== Product Card (Details) =================================-->
                <div class="row product-row">
                    <div class="col-5 mt-5">
                        <div class="product-img" id="product-img">
                            <img style="height: 350px; max-width: 400px; width: auto;" id="main_img" src="data:image/jpg;base64,<%=viewingProduct.encodeImageToString()%>"alt=""
                                 />
                        </div>
                    </div>
                    <div class="col-7">
                        <div class="product-info">
                            <h1><%=viewingProduct.getProdName()%></h1>
                            <div class="product-info-detail">
                                <span class="product-info-detail-title">Supplier:</span>
                                <a href="#"><%=viewingProduct.getSupplier()%></a>
                            </div>
                            <div class="product-info-detail">
                                <span class="product-info-detail-title">Stock:</span>
                                <a href="#"><%=viewingProduct.getStock()%></a>
                            </div>
                            <div class="product-info-detail">
                                <span class="product-info-detail-title">Rated:</span>

                                <span class="rating">
                                    <% if (viewingProduct.getCommentList().size() > 0) {
                                            Comment com = new Comment();
                                            for (int i = 0; i
                                                    < com.getAverageRating(viewingProduct.getCommentList()); i++) {%>
                                    <i class="bx bxs-star"></i>
                                    <% }
                                    } else {%>
                                    No Rating
                                    <% }%>
                                </span>
                            </div>

                            <% if (prodInPromo.indexOf(viewingProduct) != -1) {
                                    PromotionItem promoIm = validPiList.get(prodInPromo.indexOf(viewingProduct));
                            %>   
                            <div class="product-info-price">
                                <h3> 
                                    <span style="text-decoration: line-through">RM <%=String.format("%.2f", viewingProduct.getUnitPrice())%> /unit </span>
                                    <span style="background-color: #ff6666" >&nbsp;&nbsp;-<%=String.format("%.0f", promoIm.getPromoRate() * 100)%>%</span>
                                    &nbsp;RM <%= String.format("%.2f", viewingProduct.getOfferPrice(promoIm.getPromoRate()))%>
                                </h3>
                            </div>

                            <% } else {%>
                            <div class="product-info-price">RM <%=String.format("%.2f", viewingProduct.getUnitPrice())%> /unit</div>
                            <% } %>

                            <% if (viewingProduct.getStock() > 0) {%>
                            <div class="row qty-wrapper ">
                                <form action="AddToCart" class="row" style="width:400px">
                                    <input name="prodId" value="<%=viewingProduct.getProdId()%>" hidden/>
                                    <input name="path" value="GetSingleProduct?prodId=<%=viewingProduct.getProdId()%>" hidden/>
                                    <%if (customer != null && customer.getCart() != null) {
                                    %>
                                    <input name="cartId" value="<%=customer.getCart().getCartId()%>" hidden/>
                                    <% } else { %>
                                    <input name="cartId" value="empty" hidden/>
                                    <% }%>
                                    <input type="number" name="quantity" value="1" min="1" max="<%=viewingProduct.getStock() - 1%>" />
                                    <% if (viewingProduct.getIsDeleted()) {  %>
                                    <div>
                                        <button disabled style="margin-left: 10px" class="button button-4">Unavailable</button>
                                    </div>
                                    <% } else { %>
                                    <div>
                                        <button style="margin-left: 10px" class="button button-4">Add to cart</button>
                                    </div>
                                    <% } %>
                                </form>
                            </div>
                            <% } else {%>
                            <div class="row qty-wrapper ">
                                <form class="row" style="width:400px">
                                    
                                    <div>
                                        <button disabled style="margin-left: 10px" class="button button-4">Out Of Stock</button>
                                    </div>
                                    
                                </form>
                            </div>
                            <% } %>

                        </div>
                    </div>
                </div>

                <!--============= Customer Add Comment (Only applicable for those purchased the product) -> Each order detail one comment ====================-->
                <div class="box  mt-5 review-section">
                    <div class="box-header">review</div>
                    <% if (isCommentable) {%>
                    <form action="AddComment" method="post">
                        <div class="review-container mb-5">
                            <div class="star-widget">

                                <input type="radio" name="rate" id="rate-5" value="rate-5">
                                <label for="rate-5" class="fas fa-star"></label>
                                <input type="radio" name="rate" id="rate-4" value="rate-4">
                                <label for="rate-4" class="fas fa-star"></label>
                                <input type="radio" name="rate" id="rate-3" value="rate-3">
                                <label for="rate-3" class="fas fa-star"></label>
                                <input type="radio" name="rate" id="rate-2" value="rate-2">
                                <label for="rate-2" class="fas fa-star"></label>
                                <input type="radio" name="rate" id="rate-1" value="rate-1">
                                <label for="rate-1" class="fas fa-star"></label>
                                <input name="prodId" value="<%=viewingProduct.getProdId()%>" hidden />
                                <input name="custId" value="<%=customer.getCustId()%>" hidden />                                 
                                <div class="textarea">
                                    <textarea cols="30" name="message" placeholder="Leave some comment on the product..."></textarea>
                                </div>
                                <div class="btn">
                                    <button type="submit"><h4>Post</h4></button>
                                </div>

                            </div>

                        </div>
                    </form>
                    <%} %>

                    <!--===========================Display Comment Section====================-->
                    <div>
                        <% if (viewingProduct.getCommentList().size() > 0) {
                                for (Comment com : viewingProduct.getCommentList()) {%>
                        <div class="user-rate">
                            <div class="user-info">
                                <div class="user-avt">
                                    <img src="data:image/jpg;base64,<%=com.getCustId().encodeImageToString()%>" alt="" />
                                </div>
                                <div class="user-name">
                                    <span class="name"><%=com.getCustId().getCustName()%></span>
                                    <span class="rating">
                                        <%=com.getCmtDate()%>
                                        <%for (int i = 0; i < com.getRating(); i++) { %>
                                        <i class="bx bxs-star"></i>
                                        <% }%>
                                    </span>
                                </div>
                            </div>
                            <div class="user-rate-content">
                                <%=com.getMessage()%>
                            </div>

                            <% if (!com.getCommentReplyList().isEmpty()) {
                                    for (CommentReply cr : com.getCommentReplyList()) {
                            %>
                            <div class="row">
                                <div class="arrow-avt col col-lg-2">
                                    <img src="assets/img/arrow1.png" alt="" />
                                </div>        
                                <div class="staff-rate col-auto">
                                    <div class="staff-info">
                                        <div class="staff-avt">
                                            <img src="data:image/jpg;base64,<%=cr.getStfId().encodeImageToString()%>" alt="" />
                                        </div>
                                        <div class="staff-name">
                                            <span class="name"><%=cr.getStfId().getStfName()%></span>
                                            <span class="rating">
                                                <%=cr.getReplyDate()%>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="staff-rate-content">
                                        <%=cr.getMessage()%>
                                    </div>
                                </div>
                            </div>
                            <% }
                                }%>
                        </div>
                        <% }
                        } else { %>
                        <div class="row justify-content-center default-padding">
                            <h3>No Comments For This Product.</h3>
                        </div>
                        <%}%>
                    </div>
                </div>

                <!--===========================Related Products====================-->
                <div class="box">
                    <div class="box-header">related products</div>
                    <div class="row">
                        <%                        if (!sortProdList.isEmpty()) {
                                for (Product prod : sortProdList) {
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
                                </a>
                            </div>
                        </div>
                        <%   }

                        } else { %>
                        <div class="noProdListed shadow dark2">
                            Emm...It looks like no product listed.<br>Please try other option instead.
                        </div>   
                        <% }%>
                    </div>
                </div>
            </div>
        </div>
        <!-- end product-detail content -->

        <jsp:include page="common/alertPop.jsp">
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>"
            /> <jsp:param name="alertType" value="<%=session.getAttribute("alertType")%>" />
        </jsp:include>

        <jsp:include page="common/footer.jsp">
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png" />
        </jsp:include>
    </body>

    <script>
        const btn = document.querySelector("button");
        const post = document.querySelector(".post");
        const widget = document.querySelector(".star-widget");
        const editBtn = document.querySelector(".edit");
        btn.onclick = () => {
            widget.style.display = "none";
            post.style.display = "block";
            editBtn.onclick = () => {
                widget.style.display = "block";
                post.style.display = "none";
            }
            return false;
        }
    </script>


    <!-- jQuery Frameworks
          ============================================= -->
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <!-- Custom bootstrap -->
    <script src="assets/js/jquery-1.12.4.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/bootsnav.js"></script>
    <script src="assets/js/main.js"></script>

    <!-- Pre-loading -->
    <script src="assets/js/preloader.js"></script>

    <!--View Cart-->
    <script src="assets/js/cartOpen.js"></script>

    <!-- End jQuery Frameworks
          ============================================= -->
</html>


<script src="assets/js/zoomsl.js" type="text/javascript"></script>
<script>
        $(document).ready(function () {
            $("#main_img").imagezoomsl({
                zoomrange: [3, 3]
            });
        });
</script>