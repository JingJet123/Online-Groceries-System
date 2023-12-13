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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import entity.FeedbackReply;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import service.FeedbackReplyService;
import service.FeedbackService;

public class ToFeedbackReply extends HttpServlet {

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
            FeedbackReplyService feedbackReplyService = new FeedbackReplyService(em);
            FeedbackService feedbackService = new FeedbackService(em);
            Long feedbackId = Long.parseLong(request.getParameter("feedbackID"));
            HttpSession session = request.getSession();
            List<FeedbackReply> feedbackReplyList = new ArrayList<>();
            Feedback currentFeedback = feedbackService.findFeedbackByFdbkId(feedbackId);
            for (FeedbackReply feedbackReply : feedbackReplyService.findAll()) {
                if (feedbackId.equals(feedbackReply.getFeedbackId().getFeedbackId())) {
                    feedbackReplyList.add(feedbackReply);
                }
            }
            session.setAttribute("currentFeedback", currentFeedback);
            session.setAttribute("feedbackReplyList", feedbackReplyList);
            String path = request.getParameter("path");
            if (path != null && path.equals("FeedbackChat.jsp"))  {
                response.sendRedirect(path);
            }
            response.sendRedirect("secureStaff/FeedbackReply.jsp");
//            response.sendRedirect("feedbackReply.jsp");
        } catch (Exception ex) {
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
