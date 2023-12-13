/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Chuah Shee Yeap
 */
@WebServlet(name = "SortingStaff", urlPatterns = {"/SortingStaff"})
public class SortingStaff extends HttpServlet {

    boolean[] isAscending = {true, true, true, true, true, true};

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //Apply session
        HttpSession session = request.getSession();

        List<Staff> staffList = (List<Staff>) getServletContext().getAttribute("stfList");

        String sortOption = request.getParameter("sortingCriteria");
        switch (sortOption) {
            case "staffID":
                if (isAscending[0]) {
                    Collections.sort(staffList, (Staff s1, Staff s2) -> s1.getStfId().compareTo(s2.getStfId()));
                } else {
                    Collections.reverse(staffList);
                }
                convert(isAscending, 0);
                break;
            case "staffName":
                if (isAscending[1]) {
                    Collections.sort(staffList, (Staff s1, Staff s2) -> s1.getStfName().compareTo(s2.getStfName()));
                } else {
                    Collections.reverse(staffList);
                }
                convert(isAscending, 1);
                break;
            case "contact":
                if (isAscending[2]) {
                    Collections.sort(staffList, (Staff s1, Staff s2) -> s1.getStfContact().compareTo(s2.getStfContact()));
                } else {
                    Collections.reverse(staffList);
                }
                convert(isAscending, 2);
                break;
            case "email":
                if (isAscending[3]) {
                    Collections.sort(staffList, (Staff s1, Staff s2) -> s1.getStfEmail().compareTo(s2.getStfEmail()));
                } else {
                    Collections.reverse(staffList);
                }
                convert(isAscending, 3);
                break;
            case "salary":
                if (isAscending[4]) {
                    Collections.sort(staffList, (Staff s1, Staff s2) -> Double.compare(s1.getStfSalary(), s2.getStfSalary()));
                } else {
                    Collections.reverse(staffList);
                }
                convert(isAscending, 4);
                break;
            case "username":
                if (isAscending[5]) {
                    Collections.sort(staffList, (Staff s1, Staff s2) -> s1.getUsername().compareTo(s2.getUsername()));
                } else {
                    Collections.reverse(staffList);
                }
                convert(isAscending, 5);
                break;
            default:
                break;
        }

        session.setAttribute("stfList", staffList);
        response.sendRedirect("secureAdmin/MaintainStaff.jsp");

    }

    private void convert(boolean[] isAsc, int index) {
        for (int i = 0; i < isAsc.length; i++) {
            if (i == index) {
                isAsc[i] = !isAsc[i];
            } else {
                isAsc[i] = true;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
