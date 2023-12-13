/**
 *
 * @author Joey Kok Yen Ni
 */

package controller;


import entity.Order;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import service.OrderService;

public class AddToOrder extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();

            Order orderFromSession = (Order) session.getAttribute("order");
            String shipAdd = request.getParameter("address");
            String shipPostCode = request.getParameter("postcode");
            String shipCity = request.getParameter("city");
            String payType = request.getParameter("pay");
            String shipAddress = String.format("%s, %s, %s", shipAdd, shipPostCode, shipCity);
            request.setAttribute("shippingAddress", shipAddress);

            boolean success = false;
            OrderService os = new OrderService(em);
            Order order = new Order(Calendar.getInstance().getTime(), "Pending", orderFromSession.getTotalAmount(), orderFromSession.getCustId());

//            out.print(order);
//            out.print(shipAddress);
//            out.print(shipPostCode);
//            out.print(shipCity);
//            out.print(payType);
            utx.begin();
            success = os.addOrder(order);
            utx.commit();
            success = true;

            if (success) {

                RequestDispatcher rd = request.getRequestDispatcher("AddOrderDetails");
                rd.forward(request, response);

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
