/**
 *
 * @author Lee Jing Jet
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.*;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

public class ProdGetByPriceRange extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            double range1 = Double.parseDouble(request.getParameter("priceRange1"));
            double range2 = Double.parseDouble(request.getParameter("priceRange2"));
            
            if(range1 > range2) {
                double temp = range1;
                range1 = range2;
                range2 = temp;
            } 
            
            List<Product> prodList = (List<Product>) session.getAttribute("prodList");
            List<Product> getProdByPriceRangeList = new ArrayList<Product>();
            
            for(Product p: prodList) {
                if(p.getUnitPrice() >= range1 && p.getUnitPrice() <= range2) {
                    getProdByPriceRangeList.add(p);
                }
            }
            
            //Set Session Attribute
            session.setAttribute("sortProdList", getProdByPriceRangeList);
            response.sendRedirect("ViewProduct.jsp");
            
        } catch(Exception ex) {
            out.println("<p> in ProdGetByPriceRange " + ex.getMessage() + "</p>");
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
