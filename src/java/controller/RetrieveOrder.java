/**
 *
 * @author Lee Jia Jie
 */

package controller;

import entity.Order;
import entity.OrderDetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.OrderDetailsService;
import service.OrderService;

public class RetrieveOrder extends HttpServlet {
    
    @PersistenceContext
    EntityManager em;
    
    @Override
    public void init() throws ServletException {
        System.out.println("OrdersRetrieve Servlet init");
        OrderService orderService = new OrderService(em);
        List<Order> orderList = orderService.findAll();
        getServletContext().setAttribute("orderList", orderList);
        
        System.out.println(" - OrderDetails Retrieve");
        OrderDetailsService ods = new OrderDetailsService(em);
        List<OrderDetails> orderDetails = ods.findAll();
        getServletContext().setAttribute("allOrderDetailsList", orderDetails);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            OrderService orderService = new OrderService(em);
            List<Order> orderList = orderService.findAll();
            
            getServletContext().setAttribute("orderList", orderList);
            String path = request.getParameter("path");
            if (path != null && path.equals("secureStaff/StaffDashboard.jsp")) {
                response.sendRedirect("RetrieveProduct?path=" + path);
            } else if (path != null && path.equals("secureStaff/UpdateOrder.jsp")) {
                response.sendRedirect(path);
            } else if (path != null && path.equals("secureAdmin/ViewSales.jsp")) {
                response.sendRedirect(path);
            } else if (path != null && path.equals("GenReport")) {
                response.sendRedirect(path);
            }
            
        } catch (Exception ex) {
            out.println("<p>" + ex.getMessage() + "</p>");
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
