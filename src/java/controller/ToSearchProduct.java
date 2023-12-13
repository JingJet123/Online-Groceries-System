/**
 *
 * @author Ng Eason
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import entity.Product;
import java.util.*;
import javax.servlet.http.HttpSession;

public class ToSearchProduct extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();
            
            //Prepare variable to store
//            int startpoint = (Integer) session.getAttribute("ProdStartPoint");
//            String allProd = request.getParameter("allProd");
            String searchProductName = request.getParameter("productSearch");
            
            //Find some name matched
            List<Product> searchProductList = new ArrayList<>();
            List<Product> productList = (List) getServletContext().getAttribute("productList");
            
            for (Product product : productList) {
                if (product.getProdName().toUpperCase().contains(searchProductName.toUpperCase()) 
                    || product.getSubCategoryId().getCategoryId().getCategoryName().toUpperCase().contains(searchProductName.toUpperCase())
                    || product.getSubCategoryId().getSubCategoryName().toUpperCase().contains(searchProductName.toUpperCase())){
                    searchProductList.add(product);
                    
                }
            }
            
//            session.setAttribute("allProd", allProd);
            session.setAttribute("searchProductList", searchProductList);
//            out.println(searchProductList);
            response.sendRedirect("SearchedProduct.jsp");

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
