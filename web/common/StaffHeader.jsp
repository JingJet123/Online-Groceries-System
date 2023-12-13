<%-- 
    Document : staffHeader 
    Created on : Mar 27, 2022, 9:44:14 PM 
    Author : Ng Eason 
--%>

<%@page import="java.util.List" %>
<%@page import="entity.Staff" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%  
    List<Staff> stfList = (List<Staff>) application.getAttribute("stfList");
    Staff curStaff = null;
    for (Staff stf : stfList) {
        if (stf.getUsername().equals(request.getRemoteUser())) {
            session.setAttribute("currentStaff", stf);
            curStaff = stf;
        }
    }
%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- ========== Favicon Icon ========== -->
        <link rel="shortcut icon" href="<%=request.getParameter("logoFlavicon")%>" type="image/x-icon">
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <title>AG Market</title>

    </head>

    <body>
        <input type="checkbox" checked id="nav-toggle">
        <div class="sidebar">
            <div class="sidebar-brand">
                <h2><span><img src="<%= request.getParameter("logoImg")%>" width="50px"
                               height="60px" alt=""></span> <span>AG Market</span></h2>
            </div>
            <!--side bar-->
            <div class="sidebar-menu">
                <ul>
                    <li>
                        <a href="<%= request.getParameter("staffIndex")%>" >
                            <span class="las la-home"></span><span>Dashboard</span>
                        </a>
                    </li>
                    <% if(curStaff.getStfRole() == 'A') { %>
                        <li>
                            <a href="<%= request.getParameter("maintainStaff")%>">
                                <span class="las la-user"></span><span>Maintain Staff</span>
                            </a>
                        </li>
                    <% } %>    
                    <li>
                        <div>
                            <div class="dropdown-list">
                                <a><span class="las la-box"></span><span>Product Information</span>
                                    <span class="fa fa-caret-down"></span></a>
                            </div>
                            <div class="dropdown-container">
                                <a href="<%= request.getParameter("maintainCateg")%>"><span class="las la-box"></span>
                                    <span>Maintain Product Category</span></a>
                                <a href="<%= request.getParameter("prodPagination")%>"><span class="las la-clipboard-list"></span>
                                    <span>Maintain Product</span></a>
                                <a href="<%= request.getParameter("maintainPromo")%>"><span class="las la-gift"></span>
                                    <span>Product Promotion Module</span></a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <a href="<%=request.getParameter("orderStatus")%>">
                            <span class="las la-shopping-cart"></span><span>Order Status</span>
                        </a>
                    </li>

                    <li>
                        <div>
                            <div class="dropdown-list">
                                <a><span class="las la-comment"></span><span>Comment &
                                        Feedback</span>
                                    <span class="fa fa-caret-down"></span></a>
                            </div>
                            <div class="dropdown-container">
                                <a href="<%= request.getParameter("comment")%>"><span
                                        class="las la-clipboard-list"></span>
                                    <span>View Comment & Reply</span></a>
                                <a href="<%= request.getParameter("feedback")%>"><span
                                        class="las la-comments"></span>
                                    <span>View Feedback & Reply</span></a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div>
                            <div class="dropdown-list">
                                <a><span class="las la-folder"></span><span>Record & Report</span>
                                    <span class="fa fa-caret-down"></span></a>
                            </div>
                            <div class="dropdown-container">
                                <a href="<%=request.getParameter("viewCust")%>"><span
                                        class="las la-clipboard-list"></span>
                                    <span>View Customer Records</span></a>
                                <a href="<%=request.getParameter("viewSales")%>"><span
                                        class="las la-clipboard-list"></span>
                                    <span>View Sales Records</span></a>
                                <a href="<%=request.getParameter("viewReports")%>"><span class="las la-folder-plus"></span>
                                    <span>Generate Report</span></a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <a href="../Logout">
                            <span class="las la-sign-out-alt"></span><span>Logout</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="main-content">
            <header>
                <label for="nav-toggle">
                    <span class="las la-bars"></span>
                </label>
                <div class="user-wrapper">
                    <a href="#">
                        <img src="data:image/jpg;base64,<%=curStaff.encodeImageToString()%>"
                             width="40px" height="40px" alt="">
                    </a>
                    <div>
                        <% if (curStaff.getStfRole() == 'A') { %>
                        <h4>Admin</h4>
                        <% } else { %>
                        <h4>Staff</h4>
                        <% }%>
                        <small>
                            <%=curStaff.getUsername()%>
                        </small>
                    </div>
                </div>
            </header>

            <script>
                /* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
                var dropdown = document.getElementsByClassName("dropdown-list");
                var i;

                for (i = 0; i < dropdown.length; i++) {
                    dropdown[i].addEventListener("click", function () {
                        this.classList.toggle("active");
                        var dropdownContent = this.nextElementSibling;
                        if (dropdownContent.style.display === "block") {
                            dropdownContent.style.display = "none";
                        } else {
                            dropdownContent.style.display = "block";
                        }
                    });
                }
            </script>


    </body>
</html>