<%-- 
    Document   : MaintainPromotion
    Created on : Mar 26, 2022
    Author     : Lee Jia Jie


--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.Staff"%>
<%@page import="entity.Category"%>
<%@page import="entity.PromotionItem"%>
<%@page import="entity.PromotionItemPK"%>
<%@page import="entity.Product"%>
<%@page import="java.util.*"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <!-- ========== Custom css ========= -->
        <link href="../assets/css/overlay.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/MaintainPromotion.css" rel="stylesheet" type="text/css" />
        <link href="../layout.css" rel="stylesheet" />   
        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- ========== Page Title ========== -->
        <title>AG Market Add Promotion</title>

        <!-- ========== Favicon Icon ========== -->
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <!-- ========== Start Stylesheet ========== -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/StaffDashboard.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />

        <!-- ============ Custom css  ========== -->  
        <style>
            span{
                padding:0 !important;
            }

            .popup-check + label {
                margin-top: 5px !important;
            }

            span:before{
                top: -0.1em !important;
                left: -0.4em !important;
            }

        </style>

        <script>
            function setEndDate() {
                console.log(document.getElementById("addsdate").value);
                document.getElementById("addedate").setAttribute("min", document.getElementById("addsdate").value);
            }
        </script>
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

        <p class="adjustion"></p>


        <%
            int prodShowCount = 0;
            Product product = new Product();
            List<Product> prodList = null;
            List<Product> sortProdList = null;
            if (application.getAttribute("productList") != null) {
                prodList = (List<Product>) application.getAttribute("productList");
                session.setAttribute("prodList", prodList);
            }

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

            session.setAttribute("sortProdList", sortProdList);
            session.setAttribute("path", "secureStaff/AddPromotion.jsp");

            List<String> subCategNameList = new ArrayList<String>();
            if (!sortProdList.isEmpty()) {
                for (Product p : sortProdList) {
                    subCategNameList.add(p.getSubCategoryId().getSubCategoryName());
                }
                session.setAttribute("subCategNameList", subCategNameList);
            }
            
            List<Category> categList = null;
            if (application.getAttribute("categList") != null) {
                categList = (List<Category>) application.getAttribute("categList");
            }

            int pIndex = 0;
            Staff stf = (Staff) session.getAttribute("currentStaff");
        %>  

        <section>
            <div class="row">
                <div class="col-xl-12 col-lg-12 col-md-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header pb-0">
                            <div class="topPage-bc">
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="StaffDashboard.jsp">Dashboard</a></li>
                                        <li class="breadcrumb-item"><a href="../RetrievePromotionItem?path=secureStaff/MaintainPromotion.jsp">Maintain Promotion</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Add Promotion</li>
                                        <li>
                                            <a href="#popup1">
                                                <div id="searchbtn" style="margin-left:430%;">
                                                    <button style="border-radius:24px;width:100px;height:30px;">Filter<i class="las la-filter"></i></button>
                                                </div>
                                            </a>
                                        </li>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                    </div>
                    <div class="card-body px-0 pt-0 pb-2">
                        <div class="table-responsive p-0">
                            <table class="table align-items-center mb-0">
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%   if (!sortProdList.isEmpty()) {
                                            for (Product prod : sortProdList) {
                                    %> 
                                    <tr>
                                        <td style="width:70%;"><%= prod.getProdName()%></td>
                                        <td>
                                            <form class="btnform" method="get">
                                                <input type="hidden" id="prodname<%= pIndex%>" value="<%= prod.getProdName()%>">

                                                <input type="hidden" id="stfid<%= pIndex%>" value="<%= stf.getStfId()%>"/> 
                                                <input type="hidden" id="prdid<%= pIndex%>" value="<%= prod.getProdId()%>"/>

                                                <button type="button" class="btn btn-outline-secondary" value="<%= pIndex%>" onclick="openUpdate(this.value)">Add</button>
                                            </form>
                                        </td>
                                    </tr>
                                    <%pIndex++;
                                            }
                                        }%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <div class="overlay" id="update">
        <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
        <div class="overlay-content">
            <form action="../AddPromotion" action="get">
                <input style="display:none;" type="text" id="addstfid" name="addstfid">
                <input style="display:none;" type="text" id="addprodid" name="addprodid">
                <table class="table" style="background:white;">
                    <thead class="thead-dark">
                        <tr>
                            <th>Promotion ID</th>
                            <th>Rate</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                        </tr>
                    </thead>
                    <tr>
                        <% 
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date sttDate = new Date();
                        %>
                        <td id="updateName"></td>
                        <td><input type="number" name="rate" step="0.01" min="0" max="0.99" required></td>
                        <td><input type="date" id="addsdate" name="addsdate" onchange="setEndDate()" min="<%= sdf.format(sttDate) %>" required></td>
                        <td><input type="date" id="addedate" name="addedate" required></td>
                    </tr>
                    <tr>
                        <td colspan="4"><input class="btn btn-success" id="applybtn" type="submit" value="Add"></td>
                    </tr>
                </table>
            </form>
        </div>

        <!-- FLITER -->
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
                            <div class="article ac-content" style="height:75vh !important;">
                                <form action="../ProdGetByCateg">
                                    <section class="check-wrapper" style="margin-top:0!important;padding:0!important;">
                                        <% 
                                             int k = 0;
                                             for(Category categ: categList) { 
                                                 if(!categ.getIsDeleted()) { %>
                                        <input id='categ<%=k%>' type='checkbox' name="chkCateg" class="popup-check" value="<%=categ.getCategoryId() %>" />
                                        <label for='categ<%=k%>'><span></span><%=categ.getCategoryName() %><ins><i><%=categ.getCategoryName() %></i></ins></label>
                                                <%  ++k; }  
                                                } %>

                                        <input type="submit" name="chkCateg-btn" class="chkCateg-btn" value="Go On!"/>
                                    </section>
                                </form>
                                <br>
                            </div>
                        </div>
                    </div>
                    <!-- Filter by Name -->
                    <div>
                        <input class="ac-input" id="ac-3" name="accordion-1" type="checkbox" checked/>
                        <label class="ac-label" for="ac-3">Filter Name<i class="accordian-i"></i></label>
                        <div class="article ac-content3">
                            <form action="../ProdGetBySomeName">
                                <input class="form-control filter_search" style="margin-bottom:3em;" name="nameSearch" style="text-indent: 10px !important;" type="search" placeholder="Product Name..." aria-label="Search">
                                <input type="submit" name="chkCateg-btn3" class="chkCateg-btn3" value="Go On!"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script>
                // When the user clicks on button, open the popup
                function openSearch() {
                    document.getElementById("popup1").style.height = "100%";
                }

                function openUpdate(value) {
                    document.getElementById("update").style.height = "100%";

                    document.getElementById("updateName").innerHTML = document.getElementById("prodname" + value).value;
                    document.getElementById("addstfid").value = document.getElementById("stfid" + value).value;
                    document.getElementById("addprodid").value = document.getElementById("prdid" + value).value;
                }

                function closeNav() {
                    document.getElementById("update").style.height = "0%";
                    document.getElementById("popup1").style.height = "0%";
                }

        </script>
        <script src="../assets/js/catgDrop.js"></script>
        <script src="../assets/js/prodFilter.js"></script>
</body>
</html>
