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

    //Getting all SubCategory records in respect to that category
    List<SubCategory> getSubCategList = null;
    List<SubCategory> subCategList = null;
    if ((List<SubCategory>) session.getAttribute("getSubCategList") != null) {
        getSubCategList = (List<SubCategory>) session.getAttribute("getSubCategList");
    } else {
        getSubCategList = (List<SubCategory>) application.getAttribute("subCategList");
    }
    
    
    Category categSelected = (Category) session.getAttribute("categSelected");
    
    //Set session
    session.setAttribute("getSubCategList", getSubCategList);
    session.setAttribute("categSelected", categSelected);
    session.setAttribute("path2", "MaintainSubCategory");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AG Market Maintain SubCategory</title>
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
                    <ul id="tabs" class="nav nav-tabs text-center">
                        <li class="nav-item"><a href="javascript:void(0)" onclick="showContent('SubCategory')" data-target="#tab2" data-toggle="tab" class="active nav-link" id="SubCategory">SubCategory Info</a></li>
                    </ul>
                    <section class="categ-section">
                        <div class="row">
                            <div class="col-xl-11 col-md-11 col-sm-11 col-lg-11 subCateg-wrapper">
                                <div class="card mb-4">
                                    <div class="card-header pb-0">
                                        <!-- Breadcrumb at page top -->
                                        <div class="topPage-bc">
                                            <nav aria-label="breadcrumb">
                                                <ol class="breadcrumb">
                                                    <li class="breadcrumb-item"><a href="../ToStaffDashboard">Dashboard</a></li>
                                                    <li class="breadcrumb-item"><a href="MaintainCategory.jsp">Maintain Category</a></li>
                                                    <li class="breadcrumb-item active" aria-current="page">Maintain SubCategory: <%=categSelected.getCategoryName() %></li>
                                                </ol>
                                            </nav>
                                        </div>
                                        <form class="form-inline form-search" action="../ToSearchSubCateg">
                                            <input class="form-control categ-search" name="nameSearch" type="search" placeholder="Search Something..." aria-label="Search">
                                            <button class="btn search_btn" type="submit">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                                </svg>
                                            </button>
                                        </form>   
                                        <div class="add-wrapper">             
                                            <a href="../ToggleSubCategArchive?categIdSelected=<%=categSelected.getCategoryId() %>&toggle2=subCategId"><input class="archiveBtn" type="submit" value="Toggle Archive" /></a>
                                            <a href="../RetrieveSubCategory"><input class="showAllBtn" type="submit" value="Show All" /></a>
                                            <a href="#popup4"><input class="addBtn" type="submit" value="Add" /></a>
                                        </div>
                                    </div>

                                    <div class="card-body px-0 pt-0 pb-2">
                                        <div class="table-responsive p-0">
                                            <table class="table table-hover align-items-center mb-0">
                                                <thead>
                                                    <tr>
                                                        <th><a href="../SortSubCateg?criteria2=subCategId" class="sorting text-center text-uppercase text-secondary font-weight-bolder opacity-85">ID</a></th>
                                                        <th><a href="../SortSubCateg?criteria2=subCategName"  class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">SubCategory Name</a></th>
                                                        <th><a href="../SortSubCateg?criteria2=categName"  class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Belongs (Category)</a></th>
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
                                                    <% for (SubCategory subCateg : getSubCategList) {%>
                                                    <tr>
                                                        <td class="align-center">
                                                            <p class="text-xs  mb-0 categ-pText"><%=subCateg.getSubCategoryId()%></p>
                                                        </td>  
                                                        <td class="align-left">
                                                            <p class="text-xs  mb-0 categ-pText2"><%=subCateg.getSubCategoryName()%></p>
                                                            <p class="text-xs text-secondary mb-0 categ-pText2" style="font-size: 12px"><%=subCateg.getIsDeleted() ? "Archived" : "Show To Customer"%></p>     
                                                        </td>
                                                        <td class="align-center">
                                                            <p class="text-xs  mb-0 categ-pText"><%=subCateg.getCategoryId().getCategoryName()%></p>
                                                        </td>
                                                        <td class="align-middle text-center">
                                                            <% if (curStaff.getStfRole() == 'A' && !subCateg.getIsDeleted()) {%>
                                                            <span class="text-secondary text-xs font-weight-bold">
                                                                <a href="../SubCategPopupRetrieve?subCategId=<%=subCateg.getSubCategoryId()%>&action=edit" class="button button-small edit" title="Edit">
                                                                    <i class="fa fa-pencil"></i>
                                                                </a> 
                                                                <a href="../SubCategPopupRetrieve?subCategId=<%=subCateg.getSubCategoryId()%>&action=delete" class="button button-small delete" title="Delete">
                                                                    <i class="fa fa-trash"></i>
                                                                </a>
                                                            </span>
                                                            <% } else if (curStaff.getStfRole() == 'S') {%>
                                                            <span class="text-secondary text-xs font-weight-bold">
                                                                <a href="../SubCategPopupRetrieve?subCategId=<%=subCateg.getSubCategoryId()%>&action=delete" class="button button-small edit" title="View">
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
        

        <!--  =======================
                 SubCategory Popup
              ======================= -->
        <!-- Add SubCategory Popup -->
        <div id="popup4" class="popup-overlay">
            <div class="popup-add subCateg-add">
                <h2>Add SubCategory</h2>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../AddSubCategory" class="form-popup categAdd-form" method="post">    
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="categList4">Available Category</label>
                                <select id="categList4" name="categId" class="form-control">
                                    <option value="<%=categSelected.getCategoryId()%>"><%=categSelected.getCategoryName()%></option>
                                </select>
                            </div>
                            <div class="form-group col-md-12">
                                <label for="subCategName4">SubCategory Name</label>
                                <input type="text" class="form-control categAdd-input" id="categName4" placeholder="SubCategory Name..." name="subCategName" required style="padding-right: 20px !important">
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
        <!-- End Add SubCategory Popup -->


        <!-- Edit SubCategory Popup -->    
        <!-- Here to retrieve selected subCategory when edit/delete icon clicked -->
        <%
            SubCategory subCategSelected = new SubCategory();
            if ((SubCategory) session.getAttribute("subCategSelected") != null) {
                subCategSelected = (SubCategory) session.getAttribute("subCategSelected");
            }
            session.setAttribute("subCategSelected", subCategSelected);
        %>

        <div id="popup5" class="popup-overlay">
            <div class="popup-edit categ-edit">
                <h2>Edit SubCategory</h2>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../UpdateSubCategory" class="form-popup" method="post">    
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label for="subCategList5">Current SubCategory List</label>
                                <select class="custom-select" id="subCategList5" disabled multiple style="height: 315px;">
                                    <% for (SubCategory subCateg : getSubCategList) {%>
                                    <option value="<%=subCateg.getSubCategoryId()%>"><%=subCateg.getSubCategoryId()%>. <%=subCateg.getSubCategoryName()%></option>
                                    <% }%>
                                </select>
                            </div>
                            <div class="form-group col-md-8">
                                <div class="form-group col-md-12">
                                    <label for="note5">Please Take Note For Below Update </label>
                                    <input type="text" disabled class="form-control" id="note5" value="Name to update must be different with SubCategory list beside..." name="note">
                                </div>
                                <div class="form-group col-md-12">
                                    <label for="subCategId5">SubCategory ID</label>
                                    <input type="text" disabled class="form-control" id="subCategId5" value="<%=subCategSelected.getSubCategoryId()%>" name="subCategId">
                                </div>
                                <div class="form-row col-md-12">
                                    <div class="col-md-6">
                                        <label for="subCategName5">SubCategory Name</label>
                                        <input type="text" class="form-control" id="subCategName5" value="<%=subCategSelected.getSubCategoryName()%>" name="subCategName" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="categList5">Available Category</label>
                                        <select id="categList5" name="categId" class="form-control">
                                            <% for (Category categ : categList) {
                                                    if (!categ.getIsDeleted()) {%>
                                                    <option value="<%=categ.getCategoryId()%>"><%=categ.getCategoryName()%></option>
                                            <% } } %>
                                        </select>
                                    </div>           
                                </div>
                                <div class="form-group">
                                    <div class="form-check chkShow form-check-inline">
                                        <input class="form-check-input" type="radio" id="showCheck" name="showCheck6" value="false" checked>
                                        <label class="form-check-label" for="showCheck6">Show To Customer</label>
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
        <!-- End Edit SubCategory Popup -->


        <!-- Delete SubCategory Popup -->
        <div id="popup6" class="popup-overlay">
            <div class="popup-delete subCateg-delete">
                <% if (curStaff.getStfRole() == 'A') { %>
                <h2>Delete SubCategory</h2>
                <% } else if (curStaff.getStfRole() == 'S') { %>
                <h2>View SubCategory</h2>
                <% }  %>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../DeleteSubCategory" class="form-popup" method="post">    
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label for="subCategList6">Current SubCategory List</label>     
                                    <select class="custom-select" id="subCategList6" disabled multiple style="height: 315px;">
                                       <%  for (SubCategory subCateg : getSubCategList) { %>
                                           <% if(subCateg.getSubCategoryId() != null) { %>
                                               <option value="<%=subCateg.getSubCategoryId()%>"><%=subCateg.getSubCategoryId()%>. <%=subCateg.getSubCategoryName()%></option>
                                        <% } } %>
                                    </select>
                            </div>
                            <div class="form-group col-md-8">
                                <div class="form-group col-md-12">
                                    <label for="textarea2">Please Take Note For Below Removal:</label>
                                    <% if (curStaff.getStfRole() == 'A') { %>
                                        <textarea disabled class="form-control" id="textarea2" rows="3" style="resize:none">SubCategory Deletion will not directly remove this record from database. Instead, it will be stored as unavailable. (Means: Not Show To Customer, Still Exist)</textarea>
                                    <% } else { %>
                                        <textarea disabled class="form-control" id="textarea2" rows="3" style="resize:none">You're a staff role, thus not allowed to perform any edition or record removal.</textarea>
                                    <% }%>
                                </div>
                                <div class="form-group col-md-12">
                                    <label for="subCategId6">SubCategory ID</label>
                                    <input type="text" disabled class="form-control" id="subCategId6" value="<%=subCategSelected.getSubCategoryId()%>" name="subCategId">
                                </div>
                                <div class="form-row col-md-12">
                                    <div class="col-md-6">
                                        <label for="subCategName6">SubCategory Name</label>
                                        <input type="text" disabled class="form-control" id="subCategName6" value="<%=subCategSelected.getSubCategoryName()%>" name="subCategName" required>
                                    </div>
                                    <div class="col-md-6">
                                        <% if(categSelected.getCategoryId() != null) { %>
                                            <label for="categList6">Category Belongs</label>
                                            <select id="categList6" disabled name="categId" class="form-control">
                                                <option selected><%=categSelected.getCategoryName()%></option>
                                            </select>
                                        <% } %>
                                    </div>           
                                </div>
                                <div class="form-group">
                                    <% if (curStaff.getStfRole() == 'A') { %>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" disabled id="showCheck7" name="showCheck" value="true" checked>
                                        <label class="form-check-label" for="showCheck7">Archive (Extract From Customer)</label>
                                    </div>
                                    <% } else { %>
                                    <div class="form-check chkShow form-check-inline">
                                        <input class="form-check-input" type="radio" disabled id="showCheck8" name="showCheck" value="false" checked>
                                        <label class="form-check-label" for="showCheck8">Show To Customer</label>
                                    </div>
                                    <% } %>
                                </div>
                                <div class="btn-row">
                                    <% if (curStaff.getStfRole() == 'A') { %>
                                    <input type="submit" name="update-btn" class="delete-btn" value="Delete"/>
                                    <% }%>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- End Delete SubCategory Popup -->


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