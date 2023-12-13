<%-- 
    Document   : MaintainPromotion
    Created on : Mar 26, 2022
    Author     : Lee Jia Jie
--%>

<%@page import="entity.SubCategory"%>
<%@page import="entity.Category"%>
<%@page import="entity.Staff"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.PromotionItem"%>
<%@page import="entity.PromotionItemPK"%>
<%@page import="entity.Product"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <!-- ========== MaintainPromotion css ========= -->
        <link href="../assets/css/overlay.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/MaintainPromotion.css" rel="stylesheet" type="text/css" />

        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">


        <!-- ========== Page Title ========== -->
        <title>AG Market Maintain Promotion</title>

        <!-- ========== Favicon Icon ========== -->
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <!-- ========== Start Stylesheet ========== -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/StaffDashboard.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />

        <!-- ============ retrieve session object ========== -->   

        
        <%
            //Getting all Category records
            List<Category> categList = null;
            if ((List<Category>) application.getAttribute("categList") != null) {
                categList = (List<Category>) application.getAttribute("categList");
            } 

            //Getting all SubCategory records in respect to that category
            List<SubCategory> getSubCategList = null;
            if ((List<SubCategory>) application.getAttribute("subCategList") != null) {
                getSubCategList = (List<SubCategory>) application.getAttribute("subCategList");
            }
        
        %>
        
        <!--Script-->
        <% SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); %>
        <script>
            var categoryObject = {
               <% for(Category categ: categList) { %>
                    "<%=categ.getCategoryName()%>": {
                       <% for(SubCategory subCateg: getSubCategList) { 
                             if(subCateg.getCategoryId().getCategoryId().equals(categ.getCategoryId())) { %>    
                                "<%=subCateg.getSubCategoryName() %>": [],
                       <% } } %>
                    },    
                 <% } %>
            }; 

            window.onload = function () {
                var categorySel = document.getElementById("category");
                var subcategorySel = document.getElementById("subcategory");
                for (var x in categoryObject) {
                    categorySel.options[categorySel.options.length] = new Option(x, x);
                }

                categorySel.onchange = function () {
                        //empty Chapters- and Topics- dropdowns
                        subcategorySel.length = 1;
                    //display correct values
                    for (var y in categoryObject[this.value]) {
                        subcategorySel.options[subcategorySel.options.length] = new Option(y, y);
                    }
                }
            };
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


        <section>
            <div class="section3" style="margin-top:5%;">
                <button class="sort-btn" data-target="#sort-drop">Sort By</button>
                <div class="sort-menu sort-drop" style="width:auto;border: none;border-radius: 0;box-shadow: none;" id="sort-drop">
                    <a class="btn btn-outline-success" href="../PromotionFliter?status=1">Ongoing</a>
                    <a class="btn btn-outline-danger" href="../PromotionFliter?status=0">In-activate</a>
                </div>
            </div>
            <form name="form1" id="form1" class="form-inline" style="align-items:baseline;justify-content:space-between;" action="../PromotionFliter">
                <div class="configuration confisection category">
                    <select name="category" id="category">
                        <option selected="selected">Select Category</option>
                    </select>
                    <i class="las la-angle-double-right" style="width:100px;"></i>
                    <select name="subcategory" id="subcategory">
                        <option selected="selected">Select Subcategory</option>
                    </select>
                </div>

                <div>
                    <input type="text" class="form-control header_search" style="text-indent: 0;" name="searchitem" placeholder="Search..">
<!--                    <button class="btn search_btn" type="submit">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                        </svg>
                    </button> -->
                </div>

                <div>
                    <input class="btn btn-success srhbtn" type="submit" value="Search" id="submitbtn"> 
                </div>
            </form>

            <div class="row">
                <div class="col-xl-12 col-lg-12 col-md-6 col-sm-12 col-12">
                    <div class="card mb-4">
                        <div class="card-header pb-0">
                            <div class="topPage-bc">
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="../ToStaffDashboard">Dashboard</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Maintain Promotion</li>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="table-responsive p-0">
                                <table class="table align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th colspan="2" style="text-align:center;">Promotion List</th>
                                        </tr>
                                        <tr>
                                            <th>Promotion Name</th>
                                            <th style="text-align:center;">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            //List<PromotionItem> promoItemList = (List<PromotionItem>) application.getAttribute("allPromotionItemList");
                                            List<PromotionItem> promoItemList = null;
                                            Staff stf = (Staff) session.getAttribute("currentStaff");
                                            if (session.getAttribute("promoFliter") != null) {
                                                promoItemList = (List<PromotionItem>) session.getAttribute("promoFliter");
                                            } else {
                                                promoItemList = (List<PromotionItem>) application.getAttribute("allPromotionItemList");
                                            }
                                            int pIndex = 0;
                                        %>
                                        <% for (PromotionItem promo : promoItemList) {%> 
                                        <tr>
                                            <% if(promo.getProduct().getProdName() != null){%>
                                            <td style="width:60%;"><%= promo.getProduct().getProdName()%>
                                                </br><p class="text-secondary"><%= sdf.format(promo.getPromotionItemPK().getStartDate())%> ~ 
                                                    <%= sdf.format(promo.getPromotionItemPK().getEndDate()) %></p></td>
                                            <td>
                                                <form class="btnform" method="get" >
                                                    <input type="hidden" id="name<%= pIndex%>" value="<%= promo.getProduct().getProdName()%>"/>
                                                    <input type="hidden" id="rate<%= pIndex%>" value="<%= promo.getPromoRate()%>"/>
                                                    <input type="hidden" id="price<%= pIndex%>" value="<%= promo.getProduct().getUnitPrice()%>"/>
                                                    <input type="hidden" id="price2<%= pIndex%>" value="<%= promo.getPriceAfterDiscount()%>"/>

                                                    <input type="hidden" id="stfid<%= pIndex%>" value="<%= promo.getStaff().getStfId()%>"/>
                                                    <input type="hidden" id="prdid<%= pIndex%>" value="<%= promo.getProduct().getProdId()%>"/>
                                                    <input type="hidden" id="sttdate<%= pIndex%>" value="<%= sdf.format(promo.getPromotionItemPK().getStartDate())%>"/>
                                                    <input type="hidden" id="enddate<%= pIndex%>" value="<%= sdf.format(promo.getPromotionItemPK().getEndDate())%>"/>

                                                    <button type="button" class="btn btn-outline-secondary" value="<%= pIndex %>" onclick="openView(this.value)">View</button>
                                                    <%if (stf.getStfRole() == 'A') {%>
                                                    <button type="button" class="btn btn-outline-secondary" value="<%= pIndex %>" onclick="openUpdate(this.value)">Update</button>
                                                    <button type="button" class="btn btn-outline-secondary" value="<%= pIndex %>" onclick="openDelete(this.value)">Delete</button>
                                                    <%}}%>
                                                </form>
                                            </td>
                                        </tr>
                                        <%pIndex++;
                                            }%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="overlay" id="view">

                            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
                            <div class="overlay-content" style="background:white;">
                                <table class="table">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th>Item Name</th>
                                            <th>Promotion Rate</th>
                                            <th>Before Discount</th>
                                            <th>After Discount</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <tr>
                                            <td id="viewName"></td>
                                            <td id="viewRate"></td>
                                            <td id="viewPrePrice"></td>
                                            <td id="viewAftPrice"></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="overlay" id="update">
                            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
                            <div class="overlay-content">
                                <form action="../UpdatePromotion" action="get">
                                    <input style="display:none;" type="text" id="updatestfid" name="updatestfid">
                                    <input style="display:none;" type="text" id="updateprodid" name="updateprodid">
                                    <input style="display:none;" type="text" id="updatesdate" name="updatesdate">
                                    <input style="display:none;" type="text" id="updateedate" name="updateedate">

                                    <table class="table" style="background:white;">
                                        <thead class="thead-dark">
                                            <tr>
                                                <th scope="col">Promotion ID</th>
                                                <th scope="col">Rate</th>
                                            </tr>
                                        </thead>
                                        <tr>
                                            <td scope="row" id="updateName"></td>
                                            <td><input type="number" name="rate" step="0.01" min="0.01" max="0.99" required></td>
                                        </tr>
                                        <tr>

                                            <td colspan="4"><input class="btn btn-outline-success" id="applybtn" type="submit" value="Apply"></td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                        </div>

                        <div class="overlay" id="delete">
                            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()"><span>&times;</span></a>
                            <div class="overlay-content">
                                <form action="../DeletePromotion" method="get">
                                    <input style="display:none;" type="text" id="staffId" name="staffId">
                                    <input style="display:none;" type="text" id="prodId" name="prodId">
                                    <input style="display:none;" type="text" id="startDate" name="dltstartDate">
                                    <input style="display:none;" type="text" id="endDate" name="dltendDate">

                                    <table class="table" style="background:white;">
                                        <thead class="thead-dark">
                                            <tr>
                                                <th>Staff ID</th>
                                                <th>Product ID</th>
                                                <th>Start Date</th>
                                                <th>End Date</th>
                                            </tr>
                                        </thead>
                                        <tr>
                                            <td id="dltstfid"></td>
                                            <td id="dltprdid"></td>
                                            <td id="dltsdate"></td>
                                            <td id="dltedate"></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2"><input class="btn btn-outline-secondary" type="button" onclick="closeNav()" value="Cancel"></td>
                                            <td colspan="2"><input class="btn btn-outline-danger" type="submit" value="Delete"></td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <a href="AddPromotion.jsp" class="btn btn-primary btn-lg btn-info" style="float:right;margin-bottom: 3em;"><i class="las la-plus-circle"></i>ADD MORE</a>
        </section>

        <script>
            // When the user clicks on button, open the popup
            function openView(value) {
                document.getElementById("view").style.height = "100%";

                //Get specific Product Item
                document.getElementById("viewName").innerHTML = document.getElementById("name" + value).value;
                document.getElementById("viewRate").innerHTML = document.getElementById("rate" + value).value;
                var prePrice = parseFloat(document.getElementById("price" + value).value);
                document.getElementById("viewPrePrice").innerHTML = prePrice.toFixed(2);
                var aftPrice = parseFloat(document.getElementById("price2" + value).value);
                document.getElementById("viewAftPrice").innerHTML = aftPrice.toFixed(2);
            }

            function openUpdate(value) {
                document.getElementById("update").style.height = "100%";


                document.getElementById("updateName").innerHTML = document.getElementById("name" + value).value;
                document.getElementById("updatestfid").value = document.getElementById("stfid" + value).value;
                document.getElementById("updateprodid").value = document.getElementById("prdid" + value).value;
                document.getElementById("updatesdate").value = document.getElementById("sttdate" + value).value;
                document.getElementById("updateedate").value = document.getElementById("enddate" + value).value;

            }

            function openDelete(value) {
                document.getElementById("delete").style.height = "100%";

                document.getElementById("dltstfid").innerHTML = document.getElementById("stfid" + value).value;
                document.getElementById("dltprdid").innerHTML = document.getElementById("prdid" + value).value;
                document.getElementById("dltsdate").innerHTML = document.getElementById("sttdate" + value).value;
                document.getElementById("dltedate").innerHTML = document.getElementById("enddate" + value).value;

                document.getElementById("staffId").value = document.getElementById("stfid" + value).value;
                document.getElementById("prodId").value = document.getElementById("prdid" + value).value;
                sdate = document.getElementById("sttdate" + value).value;
                document.getElementById("startDate").value = sdate;
                edate = document.getElementById("enddate" + value).value;
                document.getElementById("endDate").value = edate;
            }

            function closeNav() {
                document.getElementById("view").style.height = "0%";
                document.getElementById("update").style.height = "0%";
                document.getElementById("delete").style.height = "0%";
            }

        </script>
        <script src="../assets/js/catgDrop.js"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
        <script src="../assets/js/viewProdDropdown.js"></script>
        <script src="assets/js/pagination.js"></script>
    </body>
</html>
