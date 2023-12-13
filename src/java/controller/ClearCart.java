/**
 *
 * @author Joey Kok Yen Ni
 */

package controller;

import entity.Cart;
import entity.CartItem;
import java.io.IOException;
import java.io.PrintWriter;
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
import service.CartItemService;

public class ClearCart extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            boolean success = false;
            Cart cart = (Cart) session.getAttribute("cart");
//            out.print(cart);
            CartItemService cartItemService = new CartItemService(em);
            List<CartItem> itemList = cartItemService.findAllCartItemListByCartId(cart.getCartId());

            if (!itemList.isEmpty()) {
                for (CartItem ci : itemList) {
                    utx.begin();
                    success = cartItemService.deleteCartItem(ci);
                    utx.commit();
                }
            }

            if (success) {
                out.print("Yes, success");

                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Order Placed and the cart is cleared.");
                session.setAttribute("alertType", "success");

                //Set Session Attribute
                session.setAttribute("path", "ShowCustOrderList?sort=desc");
                session.setAttribute("custViewStatus", "Pending");
                response.sendRedirect("RetrieveCartItem");
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
