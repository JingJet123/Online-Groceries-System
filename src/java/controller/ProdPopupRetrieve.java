package controller;

import entity.Product;
import service.ProductService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Chuah Shee Yeap
 */
public class ProdPopupRetrieve extends HttpServlet {

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
            Long popProdId = Long.parseLong(request.getParameter("prodId"));   
            Product prodSelected = new Product();

            out.println(popProdId);

            //Declare Service to find specific object
            ProductService productService = new ProductService(em);
            List<Product> prodList = productService.findAll();
            for (Product prod : prodList) {
                if (Objects.equals(prod.getProdId(), popProdId)) {
                    prodSelected = prod;
                }
            }

            //Set Session Attribute
            session.setAttribute("prodSelected", prodSelected);
            if (action.equals("edit")) {
                response.sendRedirect("secureStaff/MaintainProduct.jsp#popup2");
            } else if (action.equals("delete")) {
                response.sendRedirect("secureStaff/MaintainProduct.jsp#popup3");
            }
            
//            if (action.equals("edit")) {
//                response.sendRedirect("secureStaff/MaintainProduct.jsp#popup2");
//            } else if (action.equals("delete")) {
//                response.sendRedirect("secureStaff/MaintainProduct.jsp#popup3");
//            }

        } catch (Exception ex) {
            out.println("<p> in ProdPopupRetrieve " + ex.getMessage() + "</p>");
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
