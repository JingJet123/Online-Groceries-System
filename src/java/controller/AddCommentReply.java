/**
 *
 * @author Ng Eason
 */

package controller;

import entity.Comment;
import entity.CommentReply;
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
import service.CommentReplyService;

public class AddCommentReply extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            
            HttpSession session = request.getSession();
            boolean success = false;
            String message = request.getParameter("message");
            CommentReplyService commentReplyService = new CommentReplyService(em);
            // get value of current comment
            Comment comment = (Comment)session.getAttribute("currentComment");
            //get value of current staff
            Staff staff = (Staff)session.getAttribute("currentStaff");
            // obtain value of feedback reply followed by reply message,date, feedback information and CURRENT staff
            CommentReply commentReply = new CommentReply(message,new Date(),comment,staff);
            utx.begin();
            success = commentReplyService.addCommentReply(commentReply);
            utx.commit();
            // if success add reply to particular feedback the information will store to database based on particular feedback id
            if(success){
                session.setAttribute("success",success);
                response.sendRedirect("ToCommentReply?commentID="+comment.getCommentId());
            }else{
                response.sendRedirect("secureStaff/CommentReply.jsp");
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
