<%-- 
    Document   : Maintain Category
    Created on : Apr 9, 2022, 11:18:09 PM
    Author     : Chuah Shee Yeap
--%>


<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.*"%>
<%@page import="java.util.*"%>
<%
    //For switching Admin-Staff part's visibility
    List<Staff> stfList = (List<Staff>) application.getAttribute("stfList");
    Staff curStaff = null;
    for (Staff stf : stfList) {
        if (stf.getUsername().equals(request.getRemoteUser())) {
            session.setAttribute("currentStaff", stf);
            curStaff = stf;
        }
    }

    //Getting all Category records
    List<Category> categList = null;
    if ((List<Category>) session.getAttribute("categList") != null) {
        categList = (List<Category>) session.getAttribute("categList");
    } else {
        categList = (List<Category>) application.getAttribute("categList");
    }

    //Getting all SubCategory records
    List<SubCategory> subCategList = (List<SubCategory>) application.getAttribute("subCategList");
    

    //Set session
    session.setAttribute("categList", categList);
    session.setAttribute("subCategList", subCategList);
    session.setAttribute("path1", "MaintainCategory");
    session.setAttribute("path2", "MaintainCategory#SubCategory");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AG Market Maintain Category</title>
        <link rel="shortcut icon" href="../assets/img/AGLogo/favicon.png" type="image/x-icon">

        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="../assets/css/StaffDashboard.css" rel="stylesheet">
        <link href="../assets/css/StaffHeaderStyle.css" rel="stylesheet" />
        <link href="../assets/css/MaintainProduct.css" rel="stylesheet" />
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


        <div id="details" class="details-area default-padding">
            <div class="row">
                <div class="col-lg-10 offset-lg-1 details-items" id="tabs">
                    <!-- Tab Nav -->
                    <ul id="tabs" class="nav nav-tabs text-center">
                        <li class="nav-item"><a href="#Category" onclick="showContent('Category')" data-target="#tab1" data-toggle="tab" class="active nav-link" id="Category">Category Info</a></li>
                    </ul>
                    <!-- End Tab Nav -->

                    <!-- Start Tab Content -->
                    <div id="tabsContent" class="tab-content">

                        <!-- First Tab: Category -->
                        <div id="tab1" class="tab-pane fade active show">
                            <section class="categ-section">
                                <div class="row">
                                    <div class="col-xl-10 col-md-10 col-sm-10 col-lg-10 categ-wrapper">
                                        <div class="card mb-4">
                                            <div class="card-header pb-0">
                                                <!-- Breadcrumb at page top -->
                                                <div class="topPage-bc">
                                                    <nav aria-label="breadcrumb">
                                                        <ol class="breadcrumb">
                                                            <li class="breadcrumb-item"><a href="../ToStaffDashboard">Dashboard</a></li>
                                                            <li class="breadcrumb-item active" aria-current="page">Maintain Category</li>
                                                        </ol>
                                                    </nav>
                                                </div>
                                                <form class="form-inline form-search" action="../ToSearchCateg">
                                                    <input class="form-control categ-search" name="nameSearch" type="search" placeholder="Search Something..." aria-label="Search">
                                                    <button class="btn search_btn" type="submit">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                                        </svg>
                                                    </button>
                                                </form>   
                                                <div class="add-wrapper">             
                                                    <a href="../ToggleCategArchive?toggle=categId"><input class="archiveBtn" type="submit" value="Toggle Archive" /></a>
                                                    <a href="../RetrieveCategory"><input class="showAllBtn" type="submit" value="Show All" /></a>
                                                    <a href="#popup1"><input class="addBtn" type="submit" value="Add" /></a>
                                                </div>
                                            </div>

                                            <div class="card-body px-0 pt-0 pb-2">
                                                <div class="table-responsive p-0">
                                                    <table class="table table-hover align-items-center mb-0">
                                                        <thead>
                                                            <tr>
                                                                <th><a href="../SortCateg?criteria=categId" class="sorting text-center text-uppercase text-secondary font-weight-bolder opacity-85">ID</a></th>
                                                                <th><a href="../SortCateg?criteria=categName"  class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Category Name</a></th>
                                                                <th>
                                                                    <% if (curStaff.getStfRole() == 'A') { %>
                                                                        <a href="javascript:void(0)"  class="editIcon text-center text-uppercase text-secondary font-weight-bolder opacity-85">Edit</a>
                                                                    <% } else if (curStaff.getStfRole() == 'S') { %>
                                                                        <a href="javascript:void(0)"  class="editIcon text-center text-uppercase text-secondary font-weight-bolder opacity-85">View</a>
                                                                    <% }  %>
                                                                    
                                                                </th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <% for (Category categ : categList) {%>
                                                            <tr>
                                                                <td class="align-center">
                                                                    <a href="../GetCategSelected?categId=<%=categ.getCategoryId()%>" class="button button-small edit" title="View SubCategory">
                                                                        <i class="las la-boxes box-icon"></i>
                                                                    </a>
                                                                    <p class="text-xs  mb-0 categ-pText"><%=categ.getCategoryId()%></p>
                                                                </td>  
                                                                <td class="align-left">
                                                                    <p class="text-xs  mb-0 categ-pText2"><%=categ.getCategoryName()%></p>
                                                                    <p class="text-xs text-secondary mb-0 categ-pText2" style="font-size: 12px"><%=categ.getIsDeleted() ? "Archived" : "Show To Customer"%></p>     
                                                                </td>                                    
                                                                <td class="align-middle text-center">
                                                                    <% if (curStaff.getStfRole() == 'A' && !categ.getIsDeleted()) {%>
                                                                    <span class="text-secondary text-xs font-weight-bold">
                                                                        <a href="../CategPopupRetrieve?categId=<%=categ.getCategoryId()%>&action=edit" class="button button-small edit" title="Edit">
                                                                            <i class="fa fa-pencil"></i>
                                                                        </a> 
                                                                        <a href="../CategPopupRetrieve?categId=<%=categ.getCategoryId()%>&action=delete" class="button button-small delete" title="Achieve">
                                                                            <i class="fa fa-trash"></i>
                                                                        </a>
                                                                    </span>
                                                                    <% } else if (curStaff.getStfRole() == 'S') {%>
                                                                    <span class="text-secondary text-xs font-weight-bold">
                                                                        <a href="../CategPopupRetrieve?categId=<%=categ.getCategoryId()%>&action=delete" class="button button-small edit" title="View">
                                                                            <i class="fa fa-solid fa-eye"></i>
                                                                        </a> 
                                                                    </span>
                                                                    <% }  %>
                                                                </td>
                                                            </tr>
                                                            <% } %>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>                      
                                        </div>
                                    </div>
                                </div>          
                            </section>
                        </div>
                    </div>
                </div>
            </div>   
        </div>

        <!--  =======================
                Category Popup
             ======================= -->
        <!-- Add Category Popup -->
        <div id="popup1" class="popup-overlay">
            <div class="popup-add categ-add">
                <h2>Add Category</h2>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../AddCategory" class="form-popup categAdd-form" method="post">    
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="categName">Category Name</label>
                                <input type="text" class="form-control categAdd-input" id="categName" placeholder="Category Name..." name="categName" required style="padding-right: 20px !important">
                            </div>
                        </div>
                        <div class="btn-row">
                            <input type="reset" name="reset-btn" class="reset-btn" value="Reset"/>
                            <input type="submit" name="update-btn" class="update-btn" value="Add"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- End Add Category Popup -->

        <!-- Edit Category Popup -->    
        <!-- Here to retrieve selected category when edit/delete icon clicked -->
        <%
            Category categSelected = new Category();
            if ((Category) session.getAttribute("categSelected") != null) {
                categSelected = (Category) session.getAttribute("categSelected");
            }
            session.setAttribute("categSelected", categSelected);
        %>

        <div id="popup2" class="popup-overlay">
            <div class="popup-edit categ-edit">
                <h2>Edit Category</h2>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../UpdateCategory" class="form-popup" method="post">    
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label for="categList2">Current Category List</label>
                                <select class="custom-select" id="categList2" disabled multiple style="height: 315px;">
                                    <% for (Category categ : categList) {%>
                                    <option value="<%=categ.getCategoryId()%>"><%=categ.getCategoryId()%>. <%=categ.getCategoryName()%></option>
                                    <% }%>
                                </select>
                            </div>
                            <div class="form-group col-md-8">
                                <div class="form-group col-md-12">
                                    <label for="note2">Please Take Note: </label>
                                    <input type="text" disabled class="form-control" id="note2" value="Name to update must be different with category list beside..." name="note">
                                </div>
                                <div class="form-group col-md-12">
                                    <label for="categId2">Category ID</label>
                                    <input type="text" disabled class="form-control" id="categId2" value="<%=categSelected.getCategoryId()%>" name="categId">
                                </div>
                                <div class="form-group col-md-12">
                                    <label for="categName2">Category Name</label>
                                    <input type="text" class="form-control" id="categName2" value="<%=categSelected.getCategoryName()%>" name="categName" required>
                                </div>
                                <div class="form-group">
                                    <div class="form-check chkShow form-check-inline">
                                        <input class="form-check-input" type="radio" id="showCheck2" name="showCheck" value="false" checked>
                                        <label class="form-check-label" for="showCheck2">Show To Customer</label>
                                    </div>
                                </div>
                                <div class="btn-row">
                                    <input type="reset" name="reset-btn" class="reset-btn" value="Reset"/>
                                    <input type="submit" name="update-btn" class="update-btn" value="Update"/>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- End Edit Category Popup -->


        <!-- Delete Category Popup -->
        <div id="popup3" class="popup-overlay">
            <div class="popup-delete categ-delete">
                <% if (curStaff.getStfRole() == 'A') { %>
                <h2>Archive Category</h2>
                <% } else if (curStaff.getStfRole() == 'S') { %>
                <h2>View Category</h2>
                <% }  %>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../DeleteCategory" class="form-popup" method="post">    
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label for="categList3">Current Category List</label>
                                <select class="custom-select" id="categList3" disabled multiple style="height: 315px;">
                                    <% for (Category categ : categList) {%>
                                    <option value="<%=categ.getCategoryId()%>"><%=categ.getCategoryId()%>. <%=categ.getCategoryName()%></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="form-group col-md-8">
                                <div class="form-group col-md-12">
                                    <label for="textarea1">Please Take Note:</label>
                                    <% if (curStaff.getStfRole() == 'A') { %>
                                    <textarea disabled class="form-control" id="textarea1" rows="3" style="resize:none">Category Deletion will not directly remove this record from database. Instead, it will be stored as unavailable. (Means: Not Show To Customer, Still Exist)</textarea>
                                    <% } else { %>
                                    <textarea disabled class="form-control" id="textarea1" rows="3" style="resize:none">You're a staff role, thus not allowed to perform any edition or record removal.</textarea>
                                    <% }%>
                                </div>
                                <div class="form-group col-md-12">
                                    <label for="categId3">Category ID</label>
                                    <input type="text" disabled class="form-control" id="categId3" value="<%=categSelected.getCategoryId()%>" name="categId">
                                </div>
                                <div class="form-group col-md-12">
                                    <label for="categName3">Category Name</label>
                                    <input type="text" disabled class="form-control" id="categName3" value="<%=categSelected.getCategoryName()%>" name="categName" required>
                                </div>
                                <% if (curStaff.getStfRole() == 'A') { %>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" disabled id="showCheck3" name="showCheck" value="true" checked>
                                    <label class="form-check-label" for="showCheck3">Archive (Extract From Customer)</label>
                                </div>
                                <% } else { %>
                                <div class="form-check chkShow form-check-inline">
                                    <input class="form-check-input" type="radio" disabled id="showCheck4" name="showCheck" value="false" checked>
                                    <label class="form-check-label" for="showCheck4">Show To Customer</label>
                                </div>
                                <% } %>
                                <div class="btn-row">
                                    <% if (curStaff.getStfRole() == 'A') { %>
                                    <input type="submit" name="update-btn" class="delete-btn" value="Delete"/>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- End Delete Category Popup -->

        <jsp:include page="../common/alertPop.jsp">   
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType")%>" />
        </jsp:include>
        
        <script src="../assets/js/jquery-1.12.4.min.js"></script>
        <script src="../assets/js/bootstrap.min.js"></script>
        <script src="../assets/js/switchTab.js"></script> 
        <script src="../assets/js/adminMain.js"></script>   


    </body>
</html>