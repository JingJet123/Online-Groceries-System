package controller;

import java.io.*;
import java.util.*;
import entity.Category;
import entity.SubCategory;
import service.CategoryService;
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

/**
 *
 * @author Chuah Shee Yeap
 */
public class AddSubCategory extends HttpServlet {

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
            String subCategName = request.getParameter("subCategName").trim();
            Long categId = Long.parseLong(request.getParameter("categId"));
//            List<SubCategory> selectedSubCategList = (List<SubCategory>) session.getAttribute("getSubCategList");
            out.print(subCategName + "\t" + categId);

            //Find Category
            CategoryService categoryService = new CategoryService(em);
            Category categ = categoryService.findCategByCategId(categId);

            SubCategory subCateg = new SubCategory(subCategName, categ);

            //Add to database
            SubCategoryService subCategoryService = new SubCategoryService(em);
            utx.begin();
            boolean success = subCategoryService.addSubCategory(subCateg);
            utx.commit();

            if (success) {
                List<SubCategory> subCategList = subCategoryService.findAll();
                getServletContext().setAttribute("subCategList", subCategList);
                
//                selectedSubCategList.add(subCateg);
//                session.setAttribute("getSubCategList", selectedSubCategList);
                
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", subCateg.getSubCategoryName() + " Successfully Added.");
                session.setAttribute("alertType", "success");
            } else {
                out.print("Failed");
            }

            //Set Session Attribute
            response.sendRedirect("RetrieveSubCategory");

        } catch (Exception ex) {
            out.println("<p> in AddSubCategory " + ex.getMessage() + "</p>");
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
