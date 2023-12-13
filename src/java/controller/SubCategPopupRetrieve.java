package controller;

import entity.*;
import service.SubCategoryService;
import java.io.IOException;
import java.io.PrintWriter;
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
public class SubCategPopupRetrieve extends HttpServlet {

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
            Long popSubCategId = Long.parseLong(request.getParameter("subCategId"));

            out.println(popSubCategId);

            //Declare Service to find specific object
            SubCategoryService subCategoryServiceService = new SubCategoryService(em);
            SubCategory subCategSelected = subCategoryServiceService.findSubCategBySubId(popSubCategId);

            //Set Session Attribute
            session.setAttribute("subCategSelected", subCategSelected);
            if (action.equals("edit")) {
                response.sendRedirect("secureStaff/MaintainSubCategory.jsp#popup5");
            } else if (action.equals("delete")) {
                response.sendRedirect("secureStaff/MaintainSubCategory.jsp#popup6");
            }

        } catch (Exception ex) {
            out.println("<p> in SubCategPopupRetrieve " + ex.getMessage() + "</p>");
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
