/**
 *
 * @author Chuah Shee Yeap
 */

package controller;

import entity.Product;
import java.io.*;
import java.util.*;
import entity.SubCategory;
import service.SubCategoryService;
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
import service.ProductService;

@WebServlet(name = "DeleteSubCategory", urlPatterns = {"/DeleteSubCategory"})
public class DeleteSubCategory extends HttpServlet {

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
            SubCategory subCategSelected = (SubCategory) session.getAttribute("subCategSelected");

            //Delete from database
            SubCategoryService subCategoryService = new SubCategoryService(em);
            utx.begin();
            boolean success = subCategoryService.chgDeleteStatus(subCategSelected.getSubCategoryId());
            utx.commit();

            //Check Success Status
            if(success) {
                boolean prodRemove  = false;
                
                //Product will be removed when subCatgeory is removed
                ProductService productService = new ProductService(em);
                List<Product> prodList = productService.findAll();
                for(Product prod: prodList) {
                    if(Objects.equals(prod.getSubCategoryId().getSubCategoryId(), subCategSelected.getSubCategoryId())) {
                        prod.setIsDeleted(true);
                        utx.begin();
                        prodRemove = productService.updateProduct(prod);
                        utx.commit();
                    }
                }
                
                if(prodRemove) {
                    session.setAttribute("alert", "on");
                    session.setAttribute("alertMsg", subCategSelected.getSubCategoryName()+ "'s Record Successfully Archieved.");
                    session.setAttribute("alertType", "success");
                }                
            } else {
                out.print("Failed");
            }

            //Set Session Attribute
            response.sendRedirect("RetrieveSubCategory");

        } catch (Exception ex) {
            out.println("<p> in DeleteSubCategory " + ex.getMessage() + "</p>");
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
