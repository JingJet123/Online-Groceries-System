/**
 *
 * @author Joey Kok Yen Ni
 */
package controller;

import entity.Order;
import entity.OrderDetails;
import entity.Payment;
import entity.ShippingDetails;
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
import service.OrderDetailsService;
import service.OrderService;
import service.PaymentService;
import service.ShippingDetailsService;

public class GetOrderDetails extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            HttpSession session = request.getSession();
            Long orderId = Long.parseLong(request.getParameter("orderId"));
            OrderDetailsService detailsService = new OrderDetailsService(em);

            List<OrderDetails> orderDetails = new ArrayList<>();

            orderDetails = detailsService.findOrderDetailsListByOrderId(orderId);

            out.println(orderDetails);

            OrderService os = new OrderService(em);

            PaymentService paymentService = new PaymentService(em);
            Payment payment = paymentService.findPaymentByOrderId(os.findOrderByOrderId(orderId));

            out.println(payment);

            ShippingDetailsService sds = new ShippingDetailsService(em);
            ShippingDetails sd = sds.findShippingDetailsByOrderId(os.findOrderByOrderId(orderId));

            out.println(sd);
            Order order = os.findOrderByOrderId(orderId);
            order.setShippingDetails(sd);
            

            session.setAttribute("custHisOrderDetails", orderDetails);
            session.setAttribute("custHisPayment", payment);
            session.setAttribute("custHisShip", sd);
            session.setAttribute("custHisOrder", order);
            out.println(os.findOrderByOrderId(orderId));

            if (request.getParameter("requestTo") != null) {
                if (request.getParameter("requestTo").equals("cancel")) {
                    response.sendRedirect("CancelConfirmation.jsp");
                }
            }

            response.sendRedirect("ViewOrderDetails.jsp");
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
