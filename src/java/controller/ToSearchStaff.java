/**
 *
 * @author Ng Eason
 */

package controller;

import entity.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import javax.servlet.http.HttpSession;

public class ToSearchStaff extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();
            
            //Prepare variable to store
            String someName = request.getParameter("nameSearch").toLowerCase();
            List<Staff> stfList = (List<Staff>) session.getAttribute("staffList");
            List<Staff> getStfBySomeNameList = new ArrayList<Staff>();
            
            out.println(someName);
            for(Staff s: stfList) {
                out.println(s.getStfName() + "\t");
            }
            
            //Check if a substring inputted has matched with: 
            //Username, Staff Name, Contact No, Email Address, Salary
            for (Staff stf : stfList) {
                if (stf.getUsername().toUpperCase().contains(someName.toUpperCase()) 
                    || stf.getStfName().toUpperCase().contains(someName.toUpperCase()) 
                    || stf.getStfContact().contains(someName) 
                    || stf.getStfEmail().toUpperCase().contains(someName.toUpperCase())) {
                    getStfBySomeNameList.add(stf);
                }
            }
            
            for(Staff s: getStfBySomeNameList) {
                out.println(s.getStfName() + "\t");
            }
            
            session.setAttribute("staffList", getStfBySomeNameList);
            response.sendRedirect("secureAdmin/MaintainStaff.jsp");

        } catch (Exception ex) {
            out.println(ex.getMessage());
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
