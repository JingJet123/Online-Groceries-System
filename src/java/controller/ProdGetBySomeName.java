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

public class ProdGetBySomeName extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            String from = "";
            String path = "";
            
            if((String) session.getAttribute("path") != null) {
                from = (String) session.getAttribute("path");
            }
            
            if((String) session.getAttribute("path1") != null) {
                path = (String) session.getAttribute("path1");
            }
            
            String someName = request.getParameter("nameSearch").toLowerCase();
            List<Product> prodList = (List<Product>) session.getAttribute("prodList");
            List<Product> getProdBySomeNameList = new ArrayList<Product>();


            if(from.equals("ViewProduct.jsp")) {
                //Check if a substring inputted has matched with: 
                //Product Name, Category Name, SubCategory Name
                for (Product p : prodList) {
                    if (p.getProdName().toLowerCase().contains(someName.toLowerCase())
                            || p.getSubCategoryId().getSubCategoryName().toLowerCase().contains(someName.toLowerCase())
                            || p.getSubCategoryId().getCategoryId().getCategoryName().toLowerCase().contains(someName.toLowerCase())) {
                        getProdBySomeNameList.add(p);
                        out.print(p.getProdName() + "\t");
                    }
                }
            }
            
            if (path.equals("MaintainProduct")) {
                //Check if a substring inputted has matched with: 
                //Product Name, Supplier Name, Category Name, SubCategory Name
                for (Product p : prodList) {
                    if (p.getProdName().toLowerCase().contains(someName.toLowerCase())
                            || p.getSupplier().toLowerCase().contains(someName.toLowerCase())
                            || p.getSubCategoryId().getSubCategoryName().toLowerCase().contains(someName.toLowerCase())
                            || p.getSubCategoryId().getCategoryId().getCategoryName().toLowerCase().contains(someName.toLowerCase())) {
                        getProdBySomeNameList.add(p);
                    }
                }
            }
            

            //Set Session Attribute
            session.setAttribute("sortProdList", getProdBySomeNameList);
            if (from.equals("ViewProduct.jsp")) {
                response.sendRedirect("ViewProduct.jsp");
            } else if (from.equals("secureStaff/AddPromotion.jsp")) {
                response.sendRedirect("secureStaff/AddPromotion.jsp");
            }
            
            if (path.equals("MaintainProduct")) {
                session.setAttribute("prodListPaginated", getProdBySomeNameList);
                response.sendRedirect("secureStaff/MaintainProduct.jsp");
            }

        } catch (Exception ex) {
            out.println("<p> in ProdGetBySomeName " + ex.getMessage() + "</p>");
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
