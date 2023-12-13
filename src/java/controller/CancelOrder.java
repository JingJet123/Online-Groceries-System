/**
 *
 * @author Joey Kok Yen Ni
 */

package controller;

import entity.Order;
import entity.OrderDetails;
import entity.Product;
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
import service.ProductService;

public class CancelOrder extends HttpServlet {

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

            boolean success1 = false;
            boolean success2 = false;
            Long orderId = Long.parseLong(request.getParameter("orderId"));
//            out.print(orderId);
            OrderService os = new OrderService(em);
            Order order = os.findOrderByOrderId(orderId);
            order.setOrderStatus("Cancelled");
         
//            out.print(order.getOrderId());
//            out.print(order.getOrderStatus());
            
            if (order != null) {
                utx.begin();
                em.merge(order);
                success1 = os.updateOrder(order);
                utx.commit();
            }

            OrderDetailsService detailsService = new OrderDetailsService(em);
            List<OrderDetails> orderDetails = new ArrayList<>();
            orderDetails = detailsService.findOrderDetailsListByOrderId(orderId);

//            out.print(orderDetails);
            List<Product> products = new ArrayList<Product>();
            ProductService ps = new ProductService(em);
            for (OrderDetails od : orderDetails) {
                Product prod = ps.findProductByProdId(od.getProduct().getProdId());
                prod.setStock(prod.getStock() + od.getQuantity());
                products.add(prod);
            }

//            out.print(products);
            for (Product p : products) {
                utx.begin();
                em.merge(p);
                success2 = ps.updateProduct(p);
                utx.commit();
            }

            if (success1 && success2) {
                out.print("Yes, success");

                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg"," Your Order (ID:"+ order.getOrderId()+") Successfully Cancelled.");
                session.setAttribute("alertType", "success");
                // info 
                // error 
                // success
                // warning

                response.sendRedirect("ShowCustOrderList?custViewStatus=Cancelled");
            } else {
                out.print("No, falied");
                response.sendRedirect("cart.jsp");
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
