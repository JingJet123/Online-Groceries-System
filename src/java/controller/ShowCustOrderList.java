/**
 *
 * @author Joey Kok Yen Ni
 */

package controller;

import entity.Customer;
import entity.Feedback;
import entity.Order;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
import service.FeedbackService;
import service.OrderService;

public class ShowCustOrderList extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession();
            Customer cust = (Customer) session.getAttribute("customer");

            String status = request.getParameter("custViewStatus") == null ? (String) session.getAttribute("custViewStatus") : request.getParameter("custViewStatus");
            if (status == null){
                status = "Pending";
            }
            
            out.println(cust.getCustName());
            out.println(status);

            OrderService os = new OrderService(em);
            List<Order> orderList = os.findOrderByOrderStatusForACust(cust, status);
            
            
            FeedbackService fbs = new FeedbackService(em);
            List<Feedback> allFeedbacks = fbs.findAll();
            for(Order o: orderList){
                List<Feedback> feedbacks = new ArrayList<Feedback>();
                for(Feedback fb: allFeedbacks){
                    if(fb.getOrderId().equals(o)){
                        feedbacks.add(fb);
                    }
                }
                o.setFeedbackList(feedbacks);
            }

            if(request.getParameter("sort")!=null){
                String sort = request.getParameter("sort");
                if(sort.equals("desc")){
                    Collections.reverse(orderList);
                }
            }
            
            if(request.getParameter("amount")!=null){
                String amount = request.getParameter("amount");
                if(amount.equals("lowest")){
                    Collections.sort(orderList, (Order o1, Order o2) -> Double.compare(o1.getTotalAmount(), o2.getTotalAmount()));
                }else{
                    Collections.sort(orderList, (Order o1, Order o2) -> Double.compare(o2.getTotalAmount(), o1.getTotalAmount()));
               
                }
            }
            
            session.setAttribute("custViewStatus", status);
            session.setAttribute("checkOrderList", orderList);
            
            out.println(session.getAttribute("checkOrderList"));
            response.sendRedirect("CustCheckOrder.jsp?status="+ status);
            
            
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
