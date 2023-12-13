/**
 *
 * @author Chuah Shee Yeap
 */

package controller;

import java.io.*;
import java.util.*;
import entity.Product;
import service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "DeleteProduct", urlPatterns = {"/DeleteProduct"})
public class DeleteProduct extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            Product prodSelected = (Product) session.getAttribute("prodSelected");
            
            //Delete from database
            ProductService productService = new ProductService(em);
            utx.begin();
            boolean success = productService.chgDeleteStatus(prodSelected.getProdId());
            utx.commit();
            
            //Check Success Status
            if(success) {
                out.print("Yes Okay liao");
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", prodSelected.getProdName() + "'s Record Successfully Deleted.");
                session.setAttribute("alertType", "success");
            } else {
                out.print("Failed");
            }
            
            //Set Session Attribute
            response.sendRedirect("RetrieveProduct");
//            response.sendRedirect("secureStaff/MaintainProduct.jsp");
            
        } catch(Exception ex) {
            out.println("<p> in DeleteProduct " + ex.getMessage() + "</p>");
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
        PrintWriter out = response.getWriter();

        ProductService productService = new ProductService(em);

        // ======= This code is for insert all category into database ======
//        initProduct().forEach((product) -> {
//            try {
//                utx.begin();
//                productService.addProduct(product);
//                utx.commit();
//            } catch (Exception ex) {
//                out.println(ex.getMessage());
//            }
//        });
        // =================================================================
        // To add ONE category just use the code below
        // boolean isAdded = categoryService.addCategory(category);
        out.println("Success");
    }

    private List<Product> initProduct() {
        List<Product> productList = new ArrayList<>();

        return productList;
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
