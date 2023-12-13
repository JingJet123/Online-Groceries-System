/**
 *
 * @author Ng Eason
 */

package controller;

import entity.Order;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ToSearchOrder extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            List<Order> orderList = (List<Order>) getServletContext().getAttribute("orderList");
            String searchOrderCriteria = request.getParameter("searchOrder");
            List<Order> searchOrderList = new ArrayList<>();
            HttpSession session = request.getSession();
            for (Order order : orderList) {
                if (order.getOrderStatus().toUpperCase().contains(searchOrderCriteria.toUpperCase())) {
                    searchOrderList.add(order);
                } else if (order.getCustId().getCustName().toUpperCase().contains(searchOrderCriteria.toUpperCase())) {
                    searchOrderList.add(order);
                } 
            }
            session.setAttribute("searchOrderList", searchOrderList);
            out.println(searchOrderList);
            response.sendRedirect("secureStaff/SearchedOrder.jsp");
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
