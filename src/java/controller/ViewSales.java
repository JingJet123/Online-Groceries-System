/**
 *
 * @author New Yee Hao
 */
package controller;

import entity.Order;
import entity.OrderDetails;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ViewSales", urlPatterns = {"/ViewSales"})
public class ViewSales extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(request.getParameter("startDate"));
            Date endDate = sdf.parse(request.getParameter("endDate"));

            HttpSession session = request.getSession();

            if (!validateEndDate(startDate, endDate)) {
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "End date must be after start date");
                session.setAttribute("alertType", "error");
                response.sendRedirect("secureAdmin/ViewSales.jsp");
            }
            List<Order> orderList = (List<Order>) getServletContext().getAttribute("orderList");
            List<Product> tempProductList = new ArrayList<>();
            Collections.sort(orderList, (Order o1, Order o2) -> o1.getOrderDate().compareTo(o2.getOrderDate()));
            orderList.forEach((Order order) -> {
                Date orderDate = order.getOrderDate();
                try {

                    // after - order date is inclusive
                    // before - order date is exclusive
                    // compareTo - 0 means the two date is equal
                    if (orderDate.after(startDate)
                            && (orderDate.before(endDate) || sdf.parse(sdf.format(orderDate)).compareTo(endDate) == 0)) {
                        order.getOrderDetailsList().forEach((OrderDetails orderDetails) -> {
                            if (!orderDetails.getOrder().getOrderStatus().equals("Cancelled")) {
                                tempProductList.add(orderDetails.getProduct());
                            }
                        });
                    }

                } catch (ParseException ex) {
                    Logger.getLogger(ViewSales.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            List<Product> filteredProdList = new ArrayList<>(new HashSet<>(tempProductList));
            tempProductList.forEach((prod) -> {
                out.println(prod + String.valueOf(prod.getItemSold(startDate, endDate)) + "<br/>");
            });
            session.setAttribute("filteredProdList", filteredProdList);
            session.setAttribute("fStartDate", startDate);
            session.setAttribute("fEndDate", endDate);
            response.sendRedirect("secureAdmin/ViewSales.jsp");
        } catch (Exception ex) {
            out.println(ex.getMessage());
        }
    }

    private boolean validateEndDate(Date startDate, Date endDate) {
        int validEndDate = endDate.compareTo(startDate);
        // 0 = equal means endDate is valid
        // 1 = larger
        // -1 = smaller
        // endDate must >= startDate
        return validEndDate >= 0;
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
