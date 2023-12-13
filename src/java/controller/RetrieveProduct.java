/**
 *
 * @author New Yee Hao
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

import java.util.*;
import service.*;
import entity.*;
import javax.servlet.http.HttpSession;

public class RetrieveProduct extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Override
    public void init() throws ServletException {
        System.out.println("ProductRetrieve Servlet init");
        ProductService productService = new ProductService(em);
        List<Product> prodList = productService.findProductExcludeDeleted();
        getServletContext().setAttribute("productList", prodList);
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

            //Declare CartItem Service and call the find method
            ProductService productService = new ProductService(em);
            List<Product> prodList = productService.findAll();

            getServletContext().setAttribute("productList", prodList);

            if (request.getParameter("path") != null) {
                path = request.getParameter("path");
            }
            if (path != null && path.equals("secureStaff/StaffDashboard.jsp")) {
                response.sendRedirect(path);
            }
            
            //For Maintain Product Use
            if (path != null && path.equals("MaintainProduct")) {
                session.setAttribute("prodListPaginated", prodList);
                response.sendRedirect("ProductPagination");
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
