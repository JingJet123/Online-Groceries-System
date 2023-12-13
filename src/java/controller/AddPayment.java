/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.*;
import entity.Payment;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
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
public class AddPayment extends HttpServlet {

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

            String payType = request.getParameter("pay");
            String shippingAddress = (String) request.getParameter("ship");
            String orderId = request.getParameter("orderId");
            
            
            OrderService os = new OrderService(em);
            Order order = os.findOrderByOrderId(Long.parseLong(orderId));
            
//            out.print("in payment " + payType);
//            out.print(order);
//            out.print(shippingAddress);

            Payment payment =new Payment(payType, Calendar.getInstance().getTime() , order.getTotalAmount(), order);
            PaymentService paymentService = new PaymentService(em);
            
            boolean success = false;
            utx.begin();
            success = paymentService.addPayment(payment);
            utx.commit();
            success = true;

            if (success) {

                response.sendRedirect("AddShippingDetail?orderId=" + orderId + "&ship=" + shippingAddress);

            } else {

                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Error Placing Order");
                session.setAttribute("alertType", "error");
                response.sendRedirect("cart.jsp");
            }
            
        } catch (Exception ex) {
            out.println("In add to order: " + ex.getMessage());
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
