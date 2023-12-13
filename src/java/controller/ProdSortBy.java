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

public class ProdSortBy extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            String action = request.getParameter("action");
            List<String> subCategNameList = (List<String>) session.getAttribute("subCategNameList");
            List<Product> prodList = (List<Product>) session.getAttribute("prodList");
//            List<Product> sortProdList = new ArrayList<Product>(prodList);
            List<Product> sortProdList = new ArrayList<Product>();
            
            for(Product p: prodList) {
                for(String name: subCategNameList) {
                    if(p.getSubCategoryId().getSubCategoryName().equals(name)) {
                        sortProdList.add(p);
                        break;
                    }
                }
            }
              
            //Able to sort filter also (CategName, SubCategName, Name Searched)
            switch (action) {
                case "oriSet": 
                    sortProdList = new ArrayList<Product>(prodList);
                    break;
                case "ascName":
                    Collections.sort(sortProdList, Product.ProdNameComparator);
                    break;
                case "desName":
                    Collections.sort(sortProdList, Product.ProdNameComparator);
                    Collections.reverse(sortProdList);
                    break;
                case "ascPrice":         
                    Collections.sort(sortProdList, Product.ProdPriceComparator);
                    Collections.reverse(sortProdList);
                    break;
                case "desPrice":
                    Collections.sort(sortProdList, Product.ProdPriceComparator);
                    break;
                default:
                    sortProdList = new ArrayList<Product>(prodList);
                    break;
            }
            
            //Set Session Attribute
            session.setAttribute("sortProdList", sortProdList);
            response.sendRedirect("ViewProduct.jsp");
            
        } catch(Exception ex) {
            out.println("<p> in ProdSortBy " + ex.getMessage() + "</p>");
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
