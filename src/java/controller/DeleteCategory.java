/**
 *
 * @author Chuah Shee Yeap
 */

package controller;

import java.io.*;
import java.util.*;
import entity.*;
import service.*;
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

@WebServlet(name = "DeleteCategory", urlPatterns = {"/DeleteCategory"})
public class DeleteCategory extends HttpServlet {

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
            Category categSelected = (Category) session.getAttribute("categSelected");
            
            //Delete from database
            CategoryService categoryService = new CategoryService(em);
            utx.begin();
            boolean success = categoryService.chgDeleteStatus(categSelected.getCategoryId());
            utx.commit();
            
            //Check Success Status
            if(success) {
                boolean prodRemove = false;
                boolean subCategRemove = false;
                //SubCategory will be removed when category is removed
                SubCategoryService subCategoryService = new SubCategoryService(em);
                List<SubCategory> subCategList = subCategoryService.findAll();
                for(SubCategory subCateg: subCategList) {
                    if(Objects.equals(subCateg.getCategoryId().getCategoryId(), categSelected.getCategoryId())) {
                        subCateg.setIsDeleted(true);
                        utx.begin();
                        subCategRemove = subCategoryService.updateSubCategory(subCateg);
                        utx.commit();
                    }
                }
                
                //Product will be removed when category is removed
                ProductService productService = new ProductService(em);
                List<Product> prodList = productService.findAll();
                for(Product prod: prodList) {
                    if(Objects.equals(prod.getSubCategoryId().getCategoryId().getCategoryId(), categSelected.getCategoryId())) {
                        prod.setIsDeleted(true);
                        utx.begin();
                        prodRemove = productService.updateProduct(prod);
                        utx.commit();
                    }
                }
                
                if(prodRemove && subCategRemove) {
                    session.setAttribute("alert", "on");
                    session.setAttribute("alertMsg", categSelected.getCategoryName()+ "'s Record Successfully Archived.");
                    session.setAttribute("alertType", "success");
                }            
            } else {
                out.print("Failed");
            }
            
            //Set Session Attribute
            response.sendRedirect("RetrieveCategory");
            
        } catch(Exception ex) {
            out.println("<p> in DeleteCategory " + ex.getMessage() + "</p>");
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

        CategoryService productService = new CategoryService(em);

        // ======= This code is for insert all category into database ======
//        initCategory().forEach((product) -> {
//            try {
//                utx.begin();
//                productService.addCategory(product);
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

    private List<Category> initCategory() {
        List<Category> productList = new ArrayList<>();

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
