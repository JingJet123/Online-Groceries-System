<%-- 
    Document   : header
    Created on : Mar 6, 2022, 11:53:54 PM
    Author     : Joey Kok Yen Ni
--%>
<%@page import="entity.Customer"%>
<%
    Customer customer = (Customer) session.getAttribute("customer");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <div class="l-navbar expander" id="navbar">
      <nav class="nav custnav">
        <div>
          <div class="nav__brand">
            <ion-icon
              name="menu-outline"
              class="nav__toggle"
              id="nav-toggle"
            ></ion-icon>
            <a href="#" class="nav__logo"><%= customer.getUsername() %></a>
          </div>
          <div class="nav__list">
            <a href="<%= request.getParameter("profilePath")%>" class="<%= request.getParameter("profileClass")%>">
              <ion-icon name="home-outline" class="nav__icon"></ion-icon>
              <span class="nav__name">My Profile</span>
            </a>
            <a href="<%= request.getParameter("orderPath")%>" class="<%= request.getParameter("orderClass")%>">
              <ion-icon name="pie-chart-outline" class="nav__icon"></ion-icon>
              <span class="nav__name">My Orders</span>
            </a>
            <a href="<%= request.getParameter("feedbackPath")%>" class="<%= request.getParameter("feedbackClass")%>">
                <ion-icon name="chatbubbles-outline" class="nav__icon"></ion-icon>
                <span class="nav__name">My Feedback</span>
              </a>
 
          </div>
        </div>

        <a href="Logout" class="nav__link">
          <ion-icon name="log-out-outline" class="nav__icon"></ion-icon>
          <span class="nav__name">Log Out</span>
        </a>
      </nav>
    </div>

