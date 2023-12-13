/**
 *
 * @author Joey Kok Yen Ni
 */


package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.*;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import service.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

@WebServlet(name = "ProdQtyIncDec", urlPatterns = "/ProdQtyIncDec")
public class ProdQtyIncDec extends HttpServlet {

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
            String action = request.getParameter("action");
            int qty = Integer.parseInt(request.getParameter("qty"));
            Long cartId = Long.parseLong(request.getParameter("cartId"));
            Long prodId = Long.parseLong(request.getParameter("prodId"));
            String path = request.getParameter("path");
            List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cartItemList");

            //Declare CartItem Service
            CartItemService cartItemService = new CartItemService(em);

            if (action != null && cartId != null) {
                if (action.equals("inc")) {
                    for (CartItem ci : cartItemList) {
                        if (ci.getCart().getCartId().equals(cartId) && ci.getProduct().getProdId().equals(prodId)) {

                            if ((ci.getQuantity() + 1) < ci.getProduct().getStock()) {

                                success = true;
                                ci.setQuantity(++qty);
                                //Update to database
                                utx.begin();
                                em.merge(ci);
                                success = cartItemService.updateCartItem(ci);
                                utx.commit();
                            } else {
                                success = false;
                            }
                        }
                    }
                } else if (action.equals("dec")) {
                    for (CartItem ci : cartItemList) {
                        if (ci.getCart().getCartId().equals(cartId) && ci.getProduct().getProdId().equals(prodId) && ci.getQuantity() > 1) {
                            success = true;
                            ci.setQuantity(--qty);

                            //Update to database
                            utx.begin();
                            em.merge(ci);
                            success = cartItemService.updateCartItem(ci);
                            utx.commit();

                        }
                    }
                }
            }

            if (success) {
                request.setAttribute("path", path);
                RequestDispatcher rd = request.getRequestDispatcher("RetrieveCartItem");
                rd.forward(request, response);
            } else {
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
