/**
 *
 * @author Chuah
 */

package controller;

import entity.Category;
import java.util.*;
import service.SubCategoryService;
import entity.SubCategory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.CategoryService;

public class GetCategSelected extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            Long selectedCategId = Long.parseLong(request.getParameter("categId"));
            Category categSelected = new Category();
            List<SubCategory> getSubCategList = new ArrayList<SubCategory>();

            //Declare Service to find specific object     
            CategoryService categoryService = new CategoryService(em);
            List<Category> allCategList = categoryService.findAll();
            for (Category categ : allCategList) {
                if (Objects.equals(categ.getCategoryId(), selectedCategId)) {
                    categSelected = categ;                
                    break;
                }
            }
            
            SubCategoryService subCategoryService = new SubCategoryService(em);
            List<SubCategory> allSubCategList = subCategoryService.findAll();
                    
            for (SubCategory subCateg : allSubCategList) {
                if (Objects.equals(subCateg.getCategoryId().getCategoryId(), selectedCategId)) {
                    getSubCategList.add(subCateg);
                    out.print(subCateg.getSubCategoryName());
                }
            }  

            //Set Session Attribute
            session.setAttribute("categSelected", categSelected);
            session.setAttribute("getSubCategList", getSubCategList);
            response.sendRedirect("secureStaff/MaintainSubCategory.jsp");
                
        } catch (Exception ex) {
            out.println("<p> in GetCategSelected " + ex.getMessage() + "</p>");
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
