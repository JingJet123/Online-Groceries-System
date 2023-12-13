/**
 *
 * @author Joey Kok Yen Ni
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import service.*;
import entity.*;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;

@WebServlet(name = "AddToCart", urlPatterns = "/AddToCart")
public class AddToCart extends HttpServlet {

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
            Long prodId = Long.parseLong(request.getParameter("prodId"));
            String path = request.getParameter("path");

            if (request.getParameter("cartId").equals("empty")) {
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Please Log in before Add item(s) to cart");
                session.setAttribute("alertType", "info");
                response.sendRedirect(path);
            }
            
            int quantity = 1;
            if (request.getParameter("quantity") != null) {
                quantity = Integer.parseInt(request.getParameter("quantity"));
            }

            Long cartId = Long.parseLong(request.getParameter("cartId"));
            CartService cartService = new CartService(em);
            ProductService productService = new ProductService(em);
            Cart cart = cartService.findCartById(cartId);
            Product prod = productService.findProductByProdId(prodId);
     
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(prod);
            cartItem.setQuantity(quantity);
            cartItem.setCartItemPK(new CartItemPK(cartId, prodId));
            CartItemService cartItemService = new CartItemService(em);

            if (prod.getStock() > quantity) {

                utx.begin();
                success = cartItemService.addCartItem(cartItem);
                utx.commit();
            } else {
                success = false;
            }

            List<CartItem> originalCartItemList = (List<CartItem>) session.getAttribute("cartItemList");
            List<CartItem> updatedCartItemList = originalCartItemList;

            if (success) {

                updatedCartItemList.add(cartItem);
                session.setAttribute("cartItemList", updatedCartItemList);
                
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", prod.getProdName() + " x " + quantity + " has been added to your cart.");
                session.setAttribute("alertType", "success");
                session.setAttribute("path", path);
                response.sendRedirect("RetrieveCartItem");
//                response.sendRedirect(path);
            } else {
                //Set Session Attribute
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", prod.getProdName() + ": Failed to add to cart.");
                session.setAttribute("alertType", "error");
                response.sendRedirect(path);
            }

        } catch (Exception ex) {
            out.println("<p>" + ex.getMessage() + "</p>");

            if (ex.getMessage().contains("rollback")) {
                HttpSession session = request.getSession();
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "The item is already in your cart.");
                session.setAttribute("alertType", "warning");

                response.sendRedirect(request.getParameter("path"));
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
