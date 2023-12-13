/**
 *
 * @author New Yee Hao
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import java.util.*;
import service.*;
import entity.*;

public class RetrieveSubCategory extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Override
    public void init() throws ServletException {
        System.out.println("SubCategoryRetrieve Servlet init");
        SubCategoryService subCategService = new SubCategoryService(em);
        List<SubCategory> subCategList = subCategService.findAll();
        getServletContext().setAttribute("subCategList", subCategList);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            //Apply Session
            HttpSession session = request.getSession();

            String path2 = null;
            if ((String) session.getAttribute("path2") != null) {
                path2 = (String) session.getAttribute("path2");
            }

            Category categSelected = new Category();
            if ((Category) session.getAttribute("categSelected") != null) {
                categSelected = (Category) session.getAttribute("categSelected");
            }

            //Declare SubCategory Service and call the find method
            SubCategoryService subCategService = new SubCategoryService(em);
            List<SubCategory> subCategList = subCategService.findAll();
            getServletContext().setAttribute("subCategList", subCategList);

            if (request.getParameter("path") != null && request.getParameter("path").equals("secureStaff/MaintainPromotion.jsp")) {
                response.sendRedirect("secureStaff/MaintainPromotion.jsp");
            }
            //For Maintain SubCategory Use
            if (path2.equals("MaintainSubCategory")) {
                List<SubCategory> getSubCategList = new ArrayList<SubCategory>();
//                List<SubCategory> selectedSubCategList = (List<SubCategory>) session.getAttribute("getSubCategList");

                for (SubCategory allSubCateg : subCategList) {
                    if (allSubCateg.getCategoryId().getCategoryId().equals(categSelected.getCategoryId())) {
                        getSubCategList.add(allSubCateg);
                    }
                }

                session.setAttribute("getSubCategList", getSubCategList);
                response.sendRedirect("secureStaff/MaintainSubCategory.jsp");
            } else {
                response.sendRedirect("RetrieveOrder");
            }
        } catch (Exception ex) {
            out.println("<p>" + ex.getMessage() + "</p>");
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
