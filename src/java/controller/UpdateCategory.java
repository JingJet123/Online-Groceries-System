/**
 *
 * @author Chuah Shee Yeap
 */

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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;


@MultipartConfig
@WebServlet(name = "UpdateCategory", urlPatterns = {"/UpdateCategory"})
public class UpdateCategory extends HttpServlet {

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
            String categName = request.getParameter("categName").trim();
            
            //Assign all request value to categSelected
            categSelected.setCategoryName(categName);
                      
            //Update to database
            CategoryService categoryService = new CategoryService(em);
            utx.begin();
            boolean success = categoryService.updateCategory(categSelected);
            utx.commit();
            
            //Check Success Status
            if(success) {
                List<Category> categList = categoryService.findAll();
                getServletContext().setAttribute("categList", categList);
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", categSelected.getCategoryName()+ " Successfully Updated.");
                session.setAttribute("alertType", "success");
            } else {
                out.print("Failed");
            }
            
            //Set Session Attribute
            response.sendRedirect("RetrieveCategory");
            
        } catch(Exception ex) {
            out.println("<p> in UpdateCategory " + ex.getMessage() + "</p>");
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

        CategoryService categuctService = new CategoryService(em);

        // ======= This code is for insert all category into database ======
//        initCategory().forEach((categuct) -> {
//            try {
//                utx.begin();
//                categuctService.addCategory(categuct);
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
        List<Category> categuctList = new ArrayList<>();

        return categuctList;
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
