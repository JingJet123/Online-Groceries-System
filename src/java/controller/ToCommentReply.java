/**
 *
 * @author Ng Eason
 */

package controller;

import entity.Comment;
import entity.CommentReply;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
import service.CommentService;

public class ToCommentReply extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            /* TODO output your page here. You may use following sample code. */
            
            /* get method from comment and comment reply service*/
            CommentReplyService commentReplyService = new CommentReplyService(em);
            CommentService commentService = new CommentService(em);
            /* get value from comment jsp */
            Long commentId = Long.parseLong(request.getParameter("commentID"));
            HttpSession session = request.getSession();
            List<CommentReply> commentReplyList = new ArrayList<>();
            Comment currentComment = commentService.findCommentByCmtId(commentId);
            // to determine comment reply id based on comment id
            for(CommentReply commentReply : commentReplyService.findAll()){
                if(commentId.equals(commentReply.getCommentId().getCommentId())){
                    commentReplyList.add(commentReply);
                }
            }
            session.setAttribute("currentComment", currentComment);
            session.setAttribute("commentReplyList", commentReplyList);
            response.sendRedirect("secureStaff/CommentReply.jsp");
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
