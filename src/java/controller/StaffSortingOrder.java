/**
 *
 * @author Ng Eason
 */

package controller;

import entity.Order;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "StaffSortingOrder", urlPatterns = {"/StaffSortingOrder"})
public class StaffSortingOrder extends HttpServlet {

    boolean[] isAscending = {true, true, true, true};

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        List<Order> orderList = (List<Order>) getServletContext().getAttribute("orderList");

        String sortOption = request.getParameter("sortingCriteria");
        // sort table column based on few sorting option
        switch (sortOption) {
            case "orderID":
                if (isAscending[0]) {
                    Collections.sort(orderList, (Order o1, Order o2) -> o1.getOrderId().compareTo(o2.getOrderId()));
                } else {
                    Collections.reverse(orderList);
                }
                convert(isAscending, 0);
                break;
            case "orderStatus":
                if (isAscending[1]) {
                    Collections.sort(orderList, (Order o1, Order o2) -> o1.getOrderStatus().compareTo(o2.getOrderStatus()));
                } else {
                    Collections.reverse(orderList);
                }
                convert(isAscending, 1);
                break;
            case "Total":
                if (isAscending[2]) {
                    Collections.sort(orderList, (Order o1, Order o2) -> Double.compare(o1.getTotalAmount(), o2.getTotalAmount()));
                } else {
                    Collections.reverse(orderList);
                }
                convert(isAscending, 2);
                break;
            case "custName":
                if (isAscending[3]) {
                    Collections.sort(orderList, (Order o1, Order o2) -> o1.getCustId().getCustName().compareTo(o2.getCustId().getCustName()));
                } else {
                    Collections.reverse(orderList);
                }
                convert(isAscending, 3);
                break;
            default:
                break;
        }
        session.setAttribute("sortedOrderList", orderList);
        response.sendRedirect("secureStaff/UpdateOrder.jsp");
    }

    private void convert(boolean[] isAsc, int index) {
        for (int i = 0; i < isAsc.length; i++) {
            if (i == index) {
                isAsc[i] = !isAsc[i];
            } else {
                isAsc[i] = true;
            }
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
