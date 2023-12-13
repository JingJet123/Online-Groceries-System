/**
 *
 * @author Ng Eason
 */

package controller;

import entity.Comment;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.CommentService;

public class CommentFilter extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            CommentService commentService = new CommentService(em);
            HttpSession session = request.getSession();
            // obtain value of rating from previous comment.jsp
            int rating = Integer.parseInt(request.getParameter("rating"));
            //obtain an empty list to store current particular comment information
            List<Comment> commentList = new ArrayList<>();
            
            // obtain all the information when user press 0 rating as it will display all information
            if(rating == 0){
                commentList = commentService.findAll();
            // obtain information based on particular rating
            }else{
                for(Comment comment : commentService.findAll()){
                    if(comment.getRating() == rating){
                        commentList.add(comment);
                    }
                }
            }
            session.setAttribute("commentList", commentList);
            response.sendRedirect("secureStaff/Comment.jsp");
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
