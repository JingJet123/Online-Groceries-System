package controller;

import entity.*;
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
public class CategPopupRetrieve extends HttpServlet {

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
            Long popCategId = Long.parseLong(request.getParameter("categId"));
            List<Category> categList = (List<Category>) session.getAttribute("categList");
            Category categSelected = new Category();

            out.println(popCategId);

            //Declare Service to find specific object
            for (Category categ : categList) {
                if (Objects.equals(categ.getCategoryId(), popCategId)) {
                    categSelected = categ;
                }
            }

            //Set Session Attribute
            session.setAttribute("categSelected", categSelected);
            if (action.equals("edit")) {
                response.sendRedirect("secureStaff/MaintainCategory.jsp#popup2");
            } else if (action.equals("delete")) {
                response.sendRedirect("secureStaff/MaintainCategory.jsp#popup3");
            }

        } catch (Exception ex) {
            out.println("<p> in CategPopupRetrieve " + ex.getMessage() + "</p>");
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
