/**
 *
 * @author Ng Eason
 */

package controller;

import entity.Feedback;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import service.FeedbackService;

@WebServlet(name = "feedbackToDelete", urlPatterns = "/feedbackToDelete")
public class FeedbackDelete extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();

            boolean success = false;
            Long feedbackId = Long.parseLong(request.getParameter("feedbackID"));

            FeedbackService feedbackservice = new FeedbackService(em);
            Feedback feedback = feedbackservice.findFeedbackByFdbkId(feedbackId);
            /* process to delete feedback record*/
            if (feedback != null) {
                utx.begin();
                success = feedbackservice.deleteFeedback(feedbackId);
                utx.commit();
            }

            /* if the success variable is true then send back to feedback servlet */
            if (success) {
                out.println("Yes,success");
//                session.setAttribute("alert","on");
//                session.setAttribute("alertMsg",feedback.getFeedbackId()+"/"+feedback.getCategory()+"has been deleted");
//                session.setAttribute("alertMsg","success");
                session.setAttribute("success", success);
                response.sendRedirect("ToFeedback");
            } else {
                out.println("No,failed");
                response.sendRedirect("secureStaff/feedback.jsp");
            }
        } catch (Exception ex) {
            out.println("<p>" + ex.getMessage() + "<p>");
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
