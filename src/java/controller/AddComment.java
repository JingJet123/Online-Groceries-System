/**
 *
 * @author Ng Eason
 */

package controller;

import entity.*;
import entity.Payment;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
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
import service.*;

/**
 *
 * @author Yenni
 */
public class AddComment extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

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
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();

            String rate = request.getParameter("rate");

            String message = request.getParameter("message");
            Long prodId = Long.parseLong(request.getParameter("prodId"));
            Long custId = Long.parseLong(request.getParameter("custId"));

            int rating = 0;
            if (rate.equals("rate-1")) {
                rating = 1;
            } else if (rate.equals("rate-2")) {
                rating = 2;
            } else if (rate.equals("rate-3")) {
                rating = 3;
            } else if (rate.equals("rate-4")) {
                rating = 4;
            } else if (rate.equals("rate-5")) {
                rating = 5;
            }
            
            CustomerService cs = new CustomerService(em);
            ProductService ps = new ProductService(em);
            Comment comment = new Comment(rating, message, Calendar.getInstance().getTime(), cs.findCustomerByCustId(custId), ps.findProductByProdId(prodId));

//            out.print(comment);
            boolean success = false;
            
            CommentService commentService = new CommentService(em);
            utx.begin();
            success = commentService.addComment(comment);
            utx.commit();
            
            if (success) {
                
                response.sendRedirect("GetSingleProduct?prodId="+prodId+"&addedComment=true");

            } else {

                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Error Posting Comment");
                session.setAttribute("alertType", "error");
                response.sendRedirect("cart.jsp");
            }
        } catch (Exception ex) {
            out.println("In add comment : " + ex.getMessage());
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
