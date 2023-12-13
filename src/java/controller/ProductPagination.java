/**
 *
 * @author Chuah Shee Yeap
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.*;
import service.*;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

public class ProductPagination extends HttpServlet {
    
    @PersistenceContext
    EntityManager em;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            int curPage = 1;
            int count = 30;
            String actionPage = null;
            if(request.getParameter("actionPage") != null) {
                actionPage = request.getParameter("actionPage");
            } else {
                actionPage = "1";
            }
            
            ProductService productService = new ProductService(em);
                        
            if(actionPage != null) {
                curPage = Integer.parseInt(actionPage);
            } 
            
            //To know total records in a table and the page should be (based on count)
            int totalRecordnPage[] = productService.totalPage(count);
            
            //Everytime get into this servlet will find the newest page range 
            List<Product> prodListPaginated = productService.findAllByPaginationList(curPage, count);
            
            //Set Session Attribute
            session.setAttribute("totalRecords", totalRecordnPage[0]);
            session.setAttribute("totalPage", totalRecordnPage[1]);
            session.setAttribute("curPage", curPage);
            session.setAttribute("prodListPaginated", prodListPaginated);
            response.sendRedirect("secureStaff/MaintainProduct.jsp");
            
        } catch(Exception ex) {
            out.println("<p> in ProductPagination " + ex.getMessage() + "</p>");
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
