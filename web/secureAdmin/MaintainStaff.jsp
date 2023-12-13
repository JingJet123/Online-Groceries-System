<%-- 
    Document   : Maintain Staff
    Created on : Apr 9, 2022, 11:18:09 PM
    Author     : Chuah Shee Yeap
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.*"%>
<%@page import="java.util.*"%>
<%
    //Getting all staff records
    List<Staff> staffList = (List<Staff>) application.getAttribute("stfList");
    if ((List<Staff>) session.getAttribute("staffList") != null) {
        staffList = (List<Staff>) session.getAttribute("staffList");
    }

    session.setAttribute("staffList", staffList);
    session.setAttribute("path1", "MaintainStaff");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AG Market Maintain Staff</title>
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
            <jsp:param name="maintainStaff" value="MaintainStaff.jsp" />
            <jsp:param name="prodPagination" value="../ProductPagination" />
            <jsp:param name="maintainCateg" value="../secureStaff/MaintainCategory.jsp#Category" />
            <jsp:param name="maintainSubCateg" value="../secureStaff/MaintainCategory.jsp#SubCategory" />
            <jsp:param name="maintainPromo" value="../RetrievePromotionItem?path=secureStaff/MaintainPromotion.jsp" />
            <jsp:param name="orderStatus" value="../SearchOrder"/> 
            <jsp:param name="comment" value="../ToComment"/>
            <jsp:param name="feedback" value="../ToFeedback"/> 
            <jsp:param name="viewCust" value="../RetrieveCustomer?path=secureAdmin/ViewCustomer.jsp" />
            <jsp:param name="viewSales" value="ViewSales.jsp" />
            <jsp:param name="viewReports" value="ViewReports.jsp" />
        </jsp:include>

        <section class="staff-section">
            <div class="row">
                <div class="col-xl-12 col-md-6 col-sm-12 col-lg-12 staff-wrapper">
                    <div class="card mb-4">
                        <div class="card-header pb-0">
                            <!-- Breadcrumb at page top -->
                            <div class="topPage-bc">
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="../ToStaffDashboard">Dashboard</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Maintain Staff</li>
                                    </ol>
                                </nav>
                            </div>
                            <form class="form-inline form-search" action="../ToSearchStaff">
                                <input class="form-control prod-search" name="nameSearch" type="search" placeholder="Search Something..." aria-label="Search">
                                <button class="btn search_btn" type="submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                    <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                    </svg>
                                </button>
                            </form>   
                            <div class="add-wrapper">   
                                <a href="../ToggleStaffArchive?toggle=stfId"><input class="archiveBtn" type="submit" value="Toggle Archive" /></a>
                                <a href="../RetrieveStaff"><input class="showAllBtn" type="submit" value="Show All" /></a>
                                <a href="#popup1"><input class="addBtn" type="submit" value="Add" /></a>
                            </div>
                        </div>

                        <div class="card-body px-0 pt-0 pb-2">
                            <div class="table-responsive p-0">
                                <table class="table table-hover align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th>
                                                <a href="../SortingStaff?sortingCriteria=username" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Staff Details</a>
                                            </th>
                                            <th>
                                                <a href="../SortingStaff?sortingCriteria=staffName" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Full Name</a>
                                            </th>
                                            <th>
                                                <a href="../SortingStaff?sortingCriteria=contact" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Contact No</a>
                                            </th>
                                            <th>
                                                <a href="../SortingStaff?sortingCriteria=email" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Email</a>
                                            </th>
                                            <th>
                                                <a href="../SortingStaff?sortingCriteria=salary" 
                                                   class="sorting text-uppercase text-secondary font-weight-bolder opacity-85">Salary</a>
                                            </th>
                                            <th>
                                                <a href="javascript:void(0)" class="editIcon text-center text-uppercase text-secondary font-weight-bolder opacity-85">Edit</a>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Staff stf : staffList) {
                                                if (stf.getStfRole() == 'S') {
                                        %>
                                        <tr>
                                            <td>
                                                <div class="d-flex px-2 py-1">
                                                    <div>
                                                        <img src="data:image/jpg;base64,<%=stf.encodeImageToString()%>" class="avatar avatar-sm me-3" alt="user1">
                                                    </div>
                                                    <div class="d-flex flex-column justify-content-center">
                                                        <h6 class="mb-0 font-weight-bold text-sm"><%=stf.getUsername()%></h6>
                                                        <p class="text-xs text-secondary mb-0 p-details">Password: <%=stf.getPassword()%></p>     
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="align-left">
                                                <p class="text-xs  mb-0 prod-pText"><%=stf.getStfName()%></p>
                                                <p class="text-xs text-secondary mb-0 p-details">Status: <%=stf.getIsDeleted() ? "Archived" : "Active"%></p>
                                            </td>
                                            <td class="align-left text-center text-sm">
                                                <p class="text-xs mb-0 prod-pText"><%=stf.getStfContact()%></p>
                                            </td>      
                                            <td class="align-left text-center text-sm">
                                                <p class="text-xs mb-0 prod-pText"><%=stf.getStfEmail()%></p>
                                            </td> 
                                            <td class="align-left text-center text-sm">
                                                <p class="text-xs mb-0 prod-pText">RM <%=String.format("%.2f", stf.getStfSalary())%></p>
                                            </td>  
                                            <td class="align-middle text-center">
                                                <span class="text-secondary text-xs font-weight-bold">
                                                    <% if(!stf.getIsDeleted()) { %>
                                                        <a href="../StaffPopupRetrieve?stfId=<%=stf.getStfId()%>&action=edit" class="button button-small edit" title="Edit">
                                                            <i class="fa fa-pencil"></i>
                                                        </a> 
                                                        <a href="../StaffPopupRetrieve?stfId=<%=stf.getStfId()%>&action=delete" class="button button-small delete" title="Delete">
                                                            <i class="fa fa-trash"></i>
                                                        </a>
                                                    <% } %>
                                                </span>
                                            </td>
                                        </tr>
                                        <% }
                                            } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>                      
                    </div>
                </div>
            </div>        
        </section>


        <!-- Add Staff Popup -->
        <div id="popup1" class="popup-overlay">
            <div class="popup-add staff-add">
                <h2>Add Staff</h2>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../AddStaff" class="form-popup" method="post" enctype="multipart/form-data">    
                        <div class="form-row">
                            <div class="form-group col-md-5">
                                <label for="username">Username</label>
                                <input type="text" class="form-control" id="username" placeholder="Username..." name="username" required
                                       pattern="[A-Za-z\d]{5,}"  
                                       title="Username must be at least 5 characters.">
                            </div>
                            <div class="form-group col-md-4">
                                <label for="password">Password</label>
                                <input type="password" class="form-control" id="password" placeholder="Password..." name="password" required
                                       title="Password must contain at least one lower-upper-case letter, one digit and one special character with a length of 8 to 16 characters."
                                       pattern="(?=.*[~!@#$%^&*()_+`\-=;':,./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\S{8,16}">
                                <span><i id="toggler1"class="fa fa-fw fa-eye-slash"></i></span>
                            </div>    
                            <div class="col-md-3">
                                <label for="salary">Monthly Salary</label>
                                <input type="number" class="form-control" id="salary" value="0" min="0" step="0.01" name="salary" required>
                            </div> 
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <div class="drop-zone">
                                    <span class="drop-zone_prompt">Drag-and-Drop or Click file to upload</span>
                                    <input type="file" name="photo" class="drop-zone_text" required>
                                </div>
                            </div>
                            <div class="form-group col-md-8">
                                <div class="form-group col-md-12">
                                    <div class="form-row" style="padding-top: 15px;">
                                        <div class="col-md-8">
                                            <label for="stfName">Staff Name</label>
                                            <input type="text" class="form-control" id="stfName" placeholder="Staff Name..." name="stfName" required
                                                   pattern="[A-Za-z\s]+[.]?[/]?[A-Za-z\s]*" title="Name should have letters only (except some special characters).">
                                        </div>
                                        <div class="col-md-4">
                                            <label for="role">Your Role</label>
                                            <input type="text" disabled class="form-control" id="role" value="Staff" name="role">
                                        </div>
                                    </div>
                                    <div class="form-row" style="padding-top: 25px;">
                                        <div class="col-md-4">
                                            <label for="contact">Contact No</label>
                                            <input type="text" class="form-control" id="contact" placeholder="Contact No..." name="contact" required
                                                   pattern="\d{3}[-]\d{7,8}" title="Phone Number should be 3 digits followed by 1 dashed('-') and 7-8 digits.">
                                        </div>
                                        <div class="col-md-8">
                                            <label for="email">Email Address</label>
                                            <input type="email" class="form-control" id="email" placeholder="Email Address..." name="email" required>
                                        </div> 
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
        <!-- End Add Staff Popup -->

        <!-- Edit Staff Popup -->    
        <!-- Here to retrieve selected staff when edit/delete icon clicked -->
        <%
            Staff stfSelected = new Staff();
            if ((Staff) session.getAttribute("stfSelected") != null) {
                stfSelected = (Staff) session.getAttribute("stfSelected");
            }
            session.setAttribute("stfSelected", stfSelected);
        %>


        <div id="popup2" class="popup-overlay">
            <div class="popup-edit">
                <h2>Edit Staff</h2>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../UpdateStaff" class="form-popup" method="post" enctype="multipart/form-data">    
                        <div class="form-row">
                            <div class="form-group col-md-5">
                                <label for="username2">Username</label>
                                <input type="text" class="form-control" id="username2" value="<%=stfSelected.getUsername()%>" name="username" required
                                       pattern="[A-Za-z\d]{5,}" title="Username must be at least 5 characters.">
                            </div>
                            <div class="form-group col-md-4">
                                <label for="password2">Password</label>
                                <input type="password" class="form-control" id="password2" value="<%=stfSelected.getPassword()%>" name="password" required
                                       pattern="(?=.*[~!@#$%^&*()_+`\-=;':,./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\S{8,16}"
                                       title="Password must contain at least one lower-upper-case letter, one digit and one special character with a length of 8 to 16 characters.">
                                <span><i id="toggler2"class="fa fa-fw fa-eye-slash"></i></span>
                                、                        </div>
                            <div class="col-md-3">
                                <label for="salary2">Monthly Salary</label>
                                <input type="number" class="form-control" id="salary2" value="0" min="0" step="0.01" name="salary" required>
                            </div> 
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <div class="drop-zone">
                                    <span class="drop-zone_prompt">Drag-and-Drop or Click file to upload</span>
                                    <input type="file" name="photo" class="drop-zone_text" required>
                                </div>
                                <% if (stfSelected.getProfileImg() != null) {%>
                                <div class="oriImage-wrapper">
                                    <span tooltip="RMB: Drag Original Image To Above Area!" flow="down">
                                        <label for="oriStfImg2">Original Image</label>
                                        <img src="data:image/jpg;base64,<%=stfSelected.encodeImageToString()%>" class="avatar avatar-sm me-3" id="oriStfImg2" alt="user1">
                                    </span>
                                </div> 
                                <% }%>
                            </div>
                            <div class="form-group col-md-8">
                                <div class="form-group col-md-12">
                                    <div class="form-row" style="padding-top: 15px;">
                                        <div class="col-md-8">
                                            <label for="stfName2">Staff Name</label>
                                            <input type="text" class="form-control" id="stfName2" value="<%=stfSelected.getStfName()%>" name="stfName" required
                                                   pattern="[A-Za-z\s]+[.]?[/]?[A-Za-z\s]*" title="Name should have letters only (except some special characters).">
                                        </div>
                                        <div class="col-md-4">
                                            <label for="role2">Your Role</label>
                                            <input type="text" disabled class="form-control" id="role2" value="Staff" name="role">
                                        </div>
                                    </div>
                                    <div class="form-row" style="padding-top: 25px;">
                                        <div class="col-md-4">
                                            <label for="contact2">Contact No</label>
                                            <input type="text" class="form-control" id="contact2" value="<%=stfSelected.getStfContact()%>" name="contact" required
                                                   pattern="\d{3}[-]\d{7,8}" title="Phone Number should be 3 digits followed by 1 dashed('-') and 7-8 digits.">
                                        </div>
                                        <div class="col-md-8">
                                            <label for="email2">Email Address</label>
                                            <input type="email" class="form-control" id="email2" value="<%=stfSelected.getStfEmail()%>" name="email" required>
                                        </div> 
                                    </div>
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
        <!-- End Edit Staff Popup -->

        <!-- Delete Staff Popup -->
        <div id="popup3" class="popup-overlay">
            <div class="popup-delete">
                <h2>Delete Staff</h2>
                <a class="close-popup" href="#">&times;</a>
                <div class="popup-content">
                    <form action="../DeleteStaff" class="form-popup" method="post" enctype="multipart/form-data">
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <% if (stfSelected.getProfileImg() != null) {%>
                                <div class="drop-zone">
                                    <span class="drop-zone_prompt">
                                        <img src="data:image/jpg;base64,<%=stfSelected.encodeImageToString()%>" class="avatar2 avatar-md" id="oriProdImg" alt="user1">                             
                                    </span>
                                </div>
                                <% }%>
                            </div>
                            <div class="form-group col-md-8">
                                <div class="form-group col-md-12">
                                    <label for="username3">Username</label>
                                    <input type="text" disabled class="form-control" id="username3" value="<%=stfSelected.getUsername()%>" name="username" required>
                                </div>
                                <div class="form-group col-md-12">
                                    <label for="password3">Password</label>
                                    <input type="password" disabled class="form-control" id="password3" value="<%=stfSelected.getPassword()%>" name="password" required>
                                </div>    
                            </div>  
                            <div class="form-row">
                                <div class="form-group col-md-5">
                                    <label for="stfName3">Staff Name</label>
                                    <input type="text" disabled class="form-control" id="stfName3" value="<%=stfSelected.getStfName()%>"  name="stfName" required>
                                </div>  
                                <div class="form-group col-md-4">
                                    <label for="salary3">Monthly Salary</label>
                                    <input type="number" disabled class="form-control" id="salary3" value="<%=String.format("%.2f", stfSelected.getStfSalary())%>" min="0" step="0.01" name="salary" required>
                                </div>
                                <div class="form-group col-md-3">
                                    <label for="role3">Your Role</label>
                                    <input type="text" disabled class="form-control" id="role3" value="Staff" name="role">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="contact3">Contact No</label>
                                    <input type="text" disabled class="form-control" id="contact3" value="<%=stfSelected.getStfContact()%>" name="contact" required>
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="email3">Email Address</label>
                                    <input type="text" disabled class="form-control" id="email3" value="<%=stfSelected.getStfEmail()%>" name="email" required>
                                </div>
                            </div> 
                        </div>
                        <div class="btn-row">
                            <input type="submit" name="update-btn" class="delete-btn" value="Delete"/>
                        </div>   
                    </form>
                </div>
            </div>
        </div>
        <!-- End Delete Staff Popup -->   


        <jsp:include page="../common/alertPop.jsp">   
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType")%>" />
        </jsp:include>

<!--        <script>
            function isPasswordValid() {
                var pass = document.getElementById('password').value;
                var confPass = document.getElementById('password2').value;
                var submitBtn = document.getElementById('submitBtn');

                if (pass !== confPass) {
                    alert("Password does not match !");
                    password.value = "";
                    confPass.value = "";
                    submitBtn.disabled = true;
                } else {
                    submitBtn.disabled = false;
                }
            }

        </script>-->

        <script src="../assets/js/uploadImage.js"></script>       
        <script src="../assets/js/adminMain.js"></script> 
        <script src="../assets/js/togglePass.js"></script>

    </body>
</html>