/**
 *
 * @author Category
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import java.util.*;
import service.*;
import entity.*;

public class RetrieveCategory extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Override
    public void init() throws ServletException {
        System.out.println("CategoryRetrieve Servlet init");
        CategoryService categService = new CategoryService(em);
        List<Category> categList = categService.findAll();
        getServletContext().setAttribute("categList", categList);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            //Apply Session
            HttpSession session = request.getSession();

            String path = null;
            if ((String) session.getAttribute("path1") != null) {
                path = (String) session.getAttribute("path1");
            }

            //Declare Category Service and call the find method
            CategoryService categService = new CategoryService(em);
            List<Category> categList = categService.findAll();
            getServletContext().setAttribute("categList", categList);

            if (request.getParameter("path") != null && request.getParameter("path").equals("secureStaff/MaintainPromotion.jsp")) {
                response.sendRedirect("RetrieveSubCategory?path=secureStaff/MaintainPromotion.jsp");
            }

            //For Maintain Category Use
            if (path != null && path.equals("MaintainCategory")) {
                session.setAttribute("categList", categList);
                response.sendRedirect("secureStaff/MaintainCategory.jsp#Category");
            } else {
                response.sendRedirect("RetrieveSubCategory");
            }
        } catch (Exception ex) {
            out.println("<p>" + ex.getMessage() + "</p>");
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
