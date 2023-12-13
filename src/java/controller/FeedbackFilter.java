/**
 *
 * @author Ng Eason
 */

package controller;

import entity.Feedback;
import javax.persistence.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.FeedbackService;


public class FeedbackFilter extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            /* TODO output your page here. You may use following sample code. */
            FeedbackService feedbackService = new FeedbackService(em);
            HttpSession session = request.getSession();
            // obtain value of category from feedback.jsp
            String category = request.getParameter("category");
            //obtain an empty list to store current particular comment information
            List<Feedback> feedbackList = new ArrayList<>();
            // if category is not equal to value "All"
            if (!category.equals("All")) {
                // the other information from other variable will be stored in list if category is equal to value obtained
                for (Feedback feedback : feedbackService.findAll()) {
                    if (feedback.getCategory().equals(category)) {
                        feedbackList.add(feedback);
                    }
                }
                // else it will display all information
            } else {
                feedbackList = feedbackService.findAll();
            }
            session.setAttribute("feedbackList", feedbackList);
            response.sendRedirect("secureStaff/feedback.jsp");
        } catch (Exception ex) {
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
