/**
 *
 * @author Ng Eason
 */

package controller;

import entity.Feedback;
import entity.FeedbackReply;
import entity.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import service.FeedbackReplyService;

/**
 *
 * @author User
 */
public class AddFeedbackReply extends HttpServlet {

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
            String message = request.getParameter("message");
            FeedbackReplyService feedbackReplyService = new FeedbackReplyService(em);
            // get value of current feedback
            Feedback feedback = (Feedback) session.getAttribute("currentFeedback");
            //get value of current staff
            Staff staff  = (Staff)session.getAttribute("currentStaff");
            // obtain value of feedback reply followed by reply message,date, feedback information and CURRENT staff
            FeedbackReply feedbackReply = new FeedbackReply(message,new Date(),feedback,staff);
            utx.begin();
            success = feedbackReplyService.addFeedbackReply(feedbackReply);
            utx.commit();
            
            // if success add reply to particular feedback the information will store to database based on particular feedback id
            if(success){
                session.setAttribute("success",success);
                response.sendRedirect("ToFeedbackReply?feedbackID="+feedback.getFeedbackId());
            }else{
                response.sendRedirect("secureStaff/FeedbackReply.jsp");
            }
        }catch(Exception ex){
            out.println(ex.getMessage());
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
