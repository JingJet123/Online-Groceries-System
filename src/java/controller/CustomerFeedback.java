/**
 *
 * @author Lee Jing Jet
 */

package controller;

import entity.Feedback;
import entity.Customer;
import entity.Order;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import service.FeedbackService;

public class CustomerFeedback extends HttpServlet {

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
            String category = request.getParameter("category");
            Customer customer = (Customer) session.getAttribute("customer");
            Date date = new Date();
            List<Order> orderList = (List<Order>) getServletContext().getAttribute("orderList");
            Long orderId = Long.parseLong(request.getParameter("orderId"));
            Order curOrder = null;
            for (Order order : orderList) {
                if (order.getOrderId().equals(orderId)) {
                    curOrder = order;
                }
            }
            FeedbackService feedbackService = new FeedbackService(em);
            // Feedback(String category, String message, Date fdbkDate, Customer custId, Order orderId)
            Feedback feedback = new Feedback(category, message, date, customer, curOrder);

            utx.begin();
            success = feedbackService.addFeedback(feedback);
            utx.commit();

            if (success) {
                session.setAttribute("success", success);
                response.sendRedirect("ToFeedback?path=MyFeedback.jsp");
            } else {
                response.sendRedirect("CustCheckOrder.jsp");
            }
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
