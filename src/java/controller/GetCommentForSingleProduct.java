/**
 *
 * @author Lee Jing Jet
 */

package controller;

import entity.Comment;
import entity.CommentReply;
import entity.Product;
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

public class GetCommentForSingleProduct extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            HttpSession session = request.getSession();
            Product product = (Product) session.getAttribute("viewingProduct");
            CommentService commentService = new CommentService(em);
            List<Comment> commentList = new ArrayList<>();
            commentList = commentService.findAll();
            List<Comment> prodCommentList = new ArrayList<>();

            CommentReplyService commentReplyService = new CommentReplyService(em);
            List<CommentReply> allCommentReply = commentReplyService.findAll();

            for (Comment c : commentList) {
                if (c.getProdId().equals(product)) {
                    prodCommentList.add(c);
                }
            }

            for (Comment cm : prodCommentList) {
                List<CommentReply> commRply = new ArrayList<CommentReply>();
                for (CommentReply cr : allCommentReply) {
                    if (cr.getCommentId().equals(cm)) {
                        commRply.add(cr);
                    }
                }
                cm.setCommentReplyList(commRply);
            }

            product.setCommentList(prodCommentList);

//            session.setAttribute("viewingProdCommentList", prodCommentList);

            //Get Related Products (By Subcategory)
            Long prodSubCat = product.getSubCategoryId().getSubCategoryId();

            if (request.getParameter("addedComment") != null) {
                if (request.getParameter("addedComment").equals("true")) {
                    response.sendRedirect("ProdGetBySubCateg?viewPath=ViewSingleProduct.jsp&subCategId=" + prodSubCat + "&addedComment=true");
                }
            }

            response.sendRedirect("ProdGetBySubCateg?viewPath=ViewSingleProduct.jsp&subCategId=" + prodSubCat);

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