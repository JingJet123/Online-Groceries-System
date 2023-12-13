/**
 *
 * @author Joey Kok Yen Ni
 */

package controller;

import entity.CartItem;
import entity.Customer;
import entity.Order;
import entity.OrderDetails;
import entity.OrderDetailsPK;
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

public class AddOrderDetails extends HttpServlet {

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

            OrderService os = new OrderService(em);
            Order latestOrder = os.getLatestOrderRecord();

            ProductService productService = new ProductService(em);

            //============Order Detail List
            List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cartItemList");
            List<OrderDetails> odList = new ArrayList<OrderDetails>();
            for (CartItem ci : cartItemList) {
                OrderDetails od = new OrderDetails();
                od.setOrderDetailsPK(new OrderDetailsPK(latestOrder.getOrderId(), ci.getProduct().getProdId()));
                od.setProduct(ci.getProduct());
                od.setQuantity(ci.getQuantity());
                od.setSubTotal(od.calcSubTotal());
                odList.add(od);
            }

            String payType = request.getParameter("pay");
            String shippingAddress = (String) request.getAttribute("shippingAddress");

            out.print(payType);
            out.print(shippingAddress);

            boolean success = false;
            OrderDetailsService ods = new OrderDetailsService(em);
               
            //Deduct Purchased Quantity From Stock and Update the Product Stock
            for (OrderDetails orderDetails : odList) {
                Product product = productService.findProductByProdId(orderDetails.getProduct().getProdId());
                int quantity = product.getStock();
                product.setStock(quantity - orderDetails.getQuantity());
                utx.begin();
                em.merge(product);
                success = productService.updateProduct(product);
                utx.commit();
            }

            //Create Order Details Record
            for (OrderDetails orderDetails : odList) {

                utx.begin();
                success = ods.addOrderDetails(orderDetails);
                utx.commit();
            }

            if (success) {

                request.setAttribute("order", latestOrder);
                response.sendRedirect("AddPayment?pay=" + payType + "&orderId=" + latestOrder.getOrderId() + "&ship=" + shippingAddress);

            } else {

                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Error Placing Order");
                session.setAttribute("alertType", "error");
                response.sendRedirect("cart.jsp");
            }

        } catch (Exception ex) {
            out.println("In add order details : " + ex.getMessage());
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
