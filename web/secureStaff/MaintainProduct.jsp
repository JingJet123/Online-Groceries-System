<<<<<<< Updated upstream
<%-- 
    Document   : Maintain Product
    Created on : Apr 9, 2022, 11:18:09 PM
    Author     : Chuah Shee Yeap
--%>

=======
/**
 *
 * @author Chuah Shee Yeap
 */0
>>>>>>> Stashed changes

<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.*"%>
<%@page import="java.util.*"%>
<%
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
    if ((List<Category>) application.getAttribute("categList") != null) {
        categList = (List<Category>) application.getAttribute("categList");
    } 

    //Getting all SubCategory records in respect to that category
    List<SubCategory> getSubCategList = null;
    if ((List<SubCategory>) application.getAttribute("subCategList") != null) {
        getSubCategList = (List<SubCategory>) application.getAttribute("subCategList");
    }

    List<Product> prodList = (List<Product>) application.getAttribute("productList");
    if ((List<Product>) session.getAttribute("prodList") != null) {
        prodList = (List<Product>) session.getAttribute("prodList");
        session.setAttribute("prodList", prodList);
    }

    List<Product> prodListPaginated = new ArrayList<Product>(prodList);
    if ((List<Product>) session.getAttribute("prodListPaginated") != null) {
        prodListPaginated = (List<Product>) session.getAttribute("prodListPaginated");
    } 
    
    prodListPaginated = (List<Product>) session.getAttribute("prodListPaginated");
   
    session.setAttribute("prodListPaginated", prodListPaginated);
    session.setAttribute("path1", "MaintainProduct");
    
   
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AG Market Maintain Product</title>
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

        <section class="prod-section">
            <div class="row">
                <div class="col-xl-10 col-md-10 col-sm-10 col-lg-10 prod-wrapper">
                    <div class="card mb-4">
                        <div class="card-header pb-0">
                            <!-- Breadcrumb at page top -->
                            <div class="topPage-bc">
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="../ToStaffDashboard">Dashboard</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Maintain Product</li>
                                    </ol>
                                </nav>
                            </div>
                            <form class="form-inline form-search" action="../ProdGetBySomeName">
                                <input class="form-control prod-search" name="nameSearch" type="search" placeholder="Search Something..." aria-label="Search">
                                <button class="btn search_btn" type="submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                    <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                    </svg>
                                </button>
                            </form>   
                            <div class="add-wrapper"> 
                                <a href="../ToggleProdArchive?toggle=prodId"><input class="archiveBtn" type="submit" value="Toggle Archive" /></a>
                                <a href="../RetrieveProduct"><input class="showAllBtn" type="submit" value="Show All" /></a>
                                <a href="#popup1"><input class="addBtn" type="submit" value="Add" /></a>
                            </div>
                        </div>

                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="table-responsive p-0">
                                <table class="table table-hover align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th>
                                                <a href="../SortProduct?criteria=ProdName" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Product Details</a>
                                            </th>
                                            <th>
                                                <a href="../SortProduct?criteria=UnitPrice" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Price</a>
                                            </th>
                                            <th>
                                                <a href="../SortProduct?criteria=Supplier" 
                                                   class="sorting text-center text-uppercase text-secondary font-weight-bolder opacity-85">Supplier</a>
                                            </th>
                                            <th>
                                                <% if (curStaff.getStfRole() == 'A') { %>
                                                <a href="javascript:void(0)" 
                                                   class="editIcon text-center text-uppercase text-secondary font-weight-bolder opacity-85">Edit</a>
                                                <% } else if (curStaff.getStfRole() == 'S') { %>
                                                <a href="javascript:void(0)" 
                                                   class="editIcon text-center text-uppercase text-secondary font-weight-bolder opacity-85">View</a>
                                                <% }  %>

                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Product prod : prodListPaginated) { %>
                                        <tr>
                                            <td>
                                                <div class="d-flex px-2 py-1">
                                                    <div>
                                                        <img src="data:image/jpg;base64,<%=prod.encodeImageToString()%>" class="avatar avatar-sm me-3" alt="user1">
                                                    </div>
                                                    <div class="d-flex flex-column justify-content-center">
                                                        <h6 class="mb-0 font-weight-bold text-sm"><%=prod.getProdName()%></h6>
                                                        <p class="text-xs text-secondary mb-0 p-details"><%=prod.getSubCategoryId().getSubCategoryName()%> (<%=prod.getSubCategoryId().getCategoryId().getCategoryName()%>)</p>     
                                                        <p class="text-xs text-secondary mb-0 p-details">Use: <%=prod.getIsDeleted() ? "Discarded" : "Show To Customer"%></p>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="align-left">
                                                <p class="text-xs  mb-0 prod-pText"><%=String.format("%.2f", prod.getUnitPrice())%></p>
                                                <p class="text-xs text-secondary mb-0">Stock: <%=prod.getStock()%></p>
                                            </td>
                                            <td class="align-left text-center text-sm">
                                                <p class="text-xs mb-0 prod-pText"><%=prod.getSupplier()%></p>
                                            </td>                                     
                                            <td class="align-middle text-center">
                                                <% if (curStaff.getStfRole() == 'A' && !prod.getIsDeleted()) {%>
                                                <span class="text-secondary text-xs font-weight-bold">
                                                    <a href="../ProdPopupRetrieve?prodId=<%=prod.getProdId()%>&action=edit" class="button button-small edit" title="Edit">
                                                        <i class="fa fa-pencil"></i>
                                                    </a> 
                                                    <a href="../ProdPopupRetrieve?prodId=<%=prod.getProdId()%>&action=delete" class="button button-small delete" title="Delete">
                                                        <i class="fa fa-trash"></i>
                                                    </a>
                                                </span>
                                                <% } else if (curStaff.getStfRole() == 'S') {%>
                                                <span class="text-secondary text-xs font-weight-bold">
                                                    <a href="../ProdPopupRetrieve?prodId=<%=prod.getProdId()%>&action=delete" class="button button-small edit" title="View">
                                                        <i class="fa fa-solid fa-eye"></i>
                                                    </a> 
                                                </span>
                                                <% }  %>
                                            </td>
                                        </tr>
                                        <% 
                                            } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>                      
                    </div>
                </div>
            </div>        
            <%

                int totalRecords = (Integer) session.getAttribute("totalRecords");
                int totalPage = (Integer) session.getAttribute("totalPage");
                int curPage = (Integer) session.getAttribute("curPage");
            %>

            <!-- Pagination -->
            <div class="list-page">
                <span><%=totalRecords%> Records Found, <%=curPage%>/<%=totalPage%> Page<br></span>
                <a href="../ProductPagination?actionPage=1">First</a>
                <a href="../ProductPagination?actionPage=<%=curPage - 1 < 0 ? 1 : curPage - 1%>">Back</a>
                <a href="../ProductPagination?actionPage=<%=curPage + 1 > totalPage ? totalPage : curPage + 1%>">Next</a>
                <a href="../ProductPagination?actionPage=<%=totalPage%>">Last</a>

                <form action="../ProductPagination" class="searchPage">
                    <input class="form-control search-input" name="actionPage" type="search" placeholder="<%=curPage%>" aria-label="Search">
                    <input type="submit" name="searchNow" class="searchNow" value="Go"/>
                </form>
            </div>  
            <!-- End Pagination -->
        </section>

        <!-- Add Product Popup -->
        <div id="popup1" class="popup-overlay">
            <div class="popup-add">
                <h2>Add Product</h2>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../AddProduct" class="form-popup" method="post" enctype="multipart/form-data">    
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="prodName">Product Name</label>
                                <input type="text" class="form-control" id="prodName" placeholder="Product Name..." name="prodName" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="supplierName">Supplier Name</label>
                                <input type="text" class="form-control" id="supplierName" placeholder="Supplier Name..." name="supplierName" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <div class="drop-zone">
                                    <span class="drop-zone_prompt">Drag-and-Drop or Click file to upload</span>
                                    <input type="file" name="photo" class="drop-zone_text" required>
                                </div>
                                <div class="form-check chkShow form-check-inline">
                                    <input class="form-check-input" type="radio" id="showCheck" name="showCheck" value="false" checked>
                                    <label class="form-check-label" for="showCheck">Show To Customer</label>
                                </div>
                            </div>
                            <div class="form-group col-md-8">
                                <div class="form-group col-md-12">
                                    <label for="categSelect">Category</label> 
                                    <select id="categSelect" name="categName" class="form-control" required>
                                        <option selected>-- Please Select Category --</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-12">
                                    <label for="subCategSelect">SubCategory</label>
                                    <select id="subCategSelect" name="subCategName" class="form-control" required>
                                        <option selected="selected">-- Please Select SubCategory --</option>
                                    </select>
                                </div>
                                <div class="form-row col-md-12">
                                    <div class="col-md-6">
                                        <label for="unitPrice">Unit Price (RM)</label>
                                        <input type="number" class="form-control" id="unitPrice" value="0" min="0" step="0.05" name="unitPrice" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="stock">Stock (Unit)</label>
                                        <input type="number" class="form-control" id="stock" min="0" step="1" value="0" name="stock" required>
                                    </div>           
                                </div>
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
        <!-- End Add Product Popup -->

        <!-- Edit Product Popup -->    
        <!-- Here to retrieve selected product when edit/delete icon clicked -->
        <%
            Product prodSelected = new Product();
            if ((Product) session.getAttribute("prodSelected") != null) {
                prodSelected = (Product) session.getAttribute("prodSelected");
            }
            session.setAttribute("prodSelected", prodSelected);
        %>
        
        <%-- if(prodSelected.getSubCategoryId() != null) { %>
            <p><%=prodSelected.getSubCategoryId().getSubCategoryName() %></p>
        <% } --%>
        

        <div id="popup2" class="popup-overlay">
            <div class="popup-edit">
                <h2>Edit Product</h2>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../UpdateProduct" class="form-popup" method="post" enctype="multipart/form-data">    
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="prodName2">Product Name</label>
                                <input type="text" class="form-control" id="prodName2" placeholder="<%=prodSelected.getProdName()%>" value="<%=prodSelected.getProdName()%>" name="prodName" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="supplierName2">Supplier Name</label>
                                <input type="text" class="form-control" id="supplierName2" placeholder="<%=prodSelected.getSupplier()%>" value="<%=prodSelected.getSupplier()%>"  name="supplierName" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <div class="drop-zone">
                                    <span class="drop-zone_prompt">Drag-and-Drop or Click file to upload</span>
                                    <input type="file" name="photo" class="drop-zone_text" required >
                                </div>
                                <% if(prodSelected.getProdImg() != null) { %>
                                    <div class="oriImage-wrapper">
                                        <span tooltip="RMB: Drag Original Image To Above Area!" flow="down">
                                            <label for="oriProdImgToDrag">Original Image</label>
                                            <img src="data:image/jpg;base64,<%=prodSelected.encodeImageToString()%>" id="oriProdImgToDrag" class="avatar avatar-sm me-3" alt="prodEdit">
                                        </span>
                                    </div>
                               <% } %>  
                            </div>
                            <div class="form-group col-md-8">
                                <div class="form-group col-md-12">
                                    <label for="categSelect2">Category</label>
                                    <select id="categSelect2" name="categName" class="form-control" required>
                                        <option selected>-- Please Select Category --</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-12">
                                    <label for="subCategSelect2">SubCategory</label>
                                    <select id="subCategSelect2" name="subCategName" class="form-control" required>
                                        <option selected>-- Please Select SubCategory --</option>
                                    </select>
                                </div>
                                <div class="form-row col-md-12">
                                    <div class="col-md-6">
                                        <label for="unitPrice2">Unit Price (RM)</label>
                                        <input type="number" class="form-control" id="unitPrice2" value="<%=prodSelected.getUnitPrice()%>" min="0" step="0.01" name="unitPrice" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="stock2">Stock (Unit)</label>
                                        <input type="number" class="form-control" id="stock2" min="0" step="1" value="<%=prodSelected.getStock()%>" name="stock" required>
                                    </div>           
                                </div>
                                <div class="form-check chkShow form-check-inline">
                                    <input class="form-check-input" type="radio" id="showCheck2" name="showCheck" value="false" checked>
                                    <label class="form-check-label" for="showCheck2">Show To Customer</label>
                                </div>
                            </div>
                        </div>
                        <div class="btn-row">
                            <input type="reset" name="reset-btn" class="reset-btn" value="Reset"/>
                            <input type="submit" name="update-btn" class="update-btn" value="Update"/>
                        </div>
                    </form>
                </div>
            </div>
        </div> 
        <!-- End Edit Product Popup -->


        <!-- Delete Product Popup -->
        <div id="popup3" class="popup-overlay">
            <div class="popup-delete">
                <% if (curStaff.getStfRole() == 'A') { %>
                <h2>Delete Product</h2>
                <% } else if (curStaff.getStfRole() == 'S') { %>
                <h2>View Product</h2>
                <% }%>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../DeleteProduct" class="form-popup" method="post" enctype="multipart/form-data">    
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <% if(prodSelected.getProdImg() != null) { %>
                                    <div class="drop-zone">
                                        <span class="drop-zone_prompt">
                                                <img src="data:image/jpg;base64,<%=prodSelected.encodeImageToString()%>" class="avatar2 avatar-md" id="oriProdImg3" alt="user1">                                 
                                        </span>
                                    </div>
                                <% } %>
                            </div>
                            <div class="form-group col-md-8">
                                <% if(prodSelected.getSubCategoryId() != null) { %>
                                    <div class="form-group col-md-12">
                                        <label for="categSelect3">Category</label>
                                        <input type="text" disabled class="form-control" id="categSelect3" value="<%=prodSelected.getSubCategoryId().getCategoryId().getCategoryName()%>" >
                                    </div>
                                    <div class="form-group col-md-12">
                                        <label for="subCategSelect3">SubCategory</label>
                                        <input type="text" disabled class="form-control" id="subCategSelect3" value="<%=prodSelected.getSubCategoryId().getSubCategoryName() %>" >
                                    </div>
                                <% } %>          
                                <% if (curStaff.getStfRole() == 'A') { %>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" disabled type="radio" id="showCheck3" name="showCheck" value="true" checked>
                                    <label class="form-check-label" for="showCheck3">Archive (Extract From Customer)</label>
                                </div>
                                <% } else { %>
                                <div class="form-check chkShow form-check-inline">
                                    <input class="form-check-input" disabled type="radio" id="showCheck4" name="showCheck" value="false" checked>
                                    <label class="form-check-label" for="showCheck4">Show To Customer</label>
                                </div>
                                <% }%>  
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="prodName3">Product Name</label>
                                    <input type="text" disabled class="form-control" id="prodName3" placeholder="<%=prodSelected.getProdName()%>" value="<%=prodSelected.getProdName()%>" name="prodName" >
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="supplierName3">Supplier Name</label>
                                    <input type="text" disabled class="form-control" id="supplierName3" placeholder="<%=prodSelected.getSupplier()%>" value="<%=prodSelected.getSupplier()%>"  name="supplierName" >
                                </div>
                                <div class="form-group col-md-3">
                                    <label for="unitPric3e">Unit Price (RM)</label>
                                    <input type="number" disabled class="form-control" id="unitPrice3" value="<%=prodSelected.getUnitPrice()%>" min="0" step="0.01" name="unitPrice" required>
                                </div>  
                                <div class="form-group col-md-3">
                                    <label for="stock3">Stock (Unit)</label>
                                    <input type="number" disabled class="form-control" id="stock3" min="0" step="1" value="<%=prodSelected.getStock()%>" name="stock" required>
                                </div>
                            </div>
                        </div>
                        <div class="btn-row">
                            <% if (curStaff.getStfRole() == 'A') { %>
                            <input type="submit" name="update-btn" class="delete-btn" value="Delete"/>
                            <% }%>
                        </div>
                    </form>
                </div>
            </div>
        </div> 
        <!-- End Delete Product Popup -->   



        <jsp:include page="../common/alertPop.jsp">   
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType")%>" />
        </jsp:include>
       

        <script src="../assets/js/uploadImage.js"></script>       
        <!--<script src="../assets/js/adminMain.js"></script>-->  

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
                let categorySel = document.getElementById("categSelect");
                let subcategorySel = document.getElementById("subCategSelect");
                for (let x in categoryObject) {
                    categorySel.options[categorySel.options.length] = new Option(x, x);
                }

                categorySel.onchange = function () {
                    //empty Chapters- and Topics- dropdowns
                    subcategorySel.length = 1;
                    //display correct values
                    for (let y in categoryObject[this.value]) {
                        subcategorySel.options[subcategorySel.options.length] = new Option(y, y);
                    }
                }

                let categorySel2 = document.getElementById("categSelect2");
                let subcategorySel2 = document.getElementById("subCategSelect2");
                for (let x in categoryObject) {
                    categorySel2.options[categorySel2.options.length] = new Option(x, x);
                }

                categorySel2.onchange = function () {
                    //empty Chapters- and Topics- dropdowns
                    subcategorySel2.length = 1;
                    //display correct values
                    for (let y in categoryObject[this.value]) {
                        subcategorySel2.options[subcategorySel2.options.length] = new Option(y, y);
                    }
                }
            };
            
            //==========================================
            //  For opening and closing popup for CRUD
            //==========================================
            const popupAdd = document.querySelector('.addBtn');
            const sidebar = document.querySelector('.sidebar');

            popupAdd.addEventListener('click', () => {
                //Temporary make all content to back
                sidebar.style.zIndex = -1;

                //After popup closed then must add back z-index, else not functionable
                document.querySelector('.close-popup').addEventListener('click', () => {
                    sidebar.style.zIndex = 99;
                });
            });
        </script>
        
    </body>
</html>

