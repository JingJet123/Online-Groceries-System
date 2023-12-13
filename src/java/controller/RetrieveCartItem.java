/**
 *
 * @author Joey Kok Yen Ni
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.*;
import service.*;
import java.util.*;

public class RetrieveCartItem extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();
            List<PromotionItem> validPiList = null;
            if (getServletContext().getAttribute("validPromotionItemList") != null) {
                validPiList = (List<PromotionItem>) getServletContext().getAttribute("validPromotionItemList");
            } 
            Product tempP = new Product();
            List<Product> prodInPromo = tempP.findAllProductinPromo(validPiList);

            String path = null;
            if((String) session.getAttribute("path") == null) {
                path = "index.jsp";
            } else {
                path = (String) session.getAttribute("path");
            }

            Cart cart = (Cart) session.getAttribute("cart");            
            
            out.print(cart.getCartId() + "\t" + path);
            
            //Declare CartItem Service and call the find method
            CartItemService cartItemService = new CartItemService(em);
            List<CartItem> cartItemList = cartItemService.findCartItemListByCartId(cart.getCartId());
 
            //Prepare variable to store
            int numOfCartItem = cartItemList.size();
            double totalPriceForCart = 0;
            double subTotal = 0;
            for(CartItem ci : cartItemList){
                subTotal = ci.getSubTotal();
                if(prodInPromo.indexOf(ci.getProduct())!= -1){
                    PromotionItem promoIm = validPiList.get(prodInPromo.indexOf(ci.getProduct()));
                    subTotal = ci.calcSubTotalAfterDiscount(promoIm.getPromoRate());
                }
                totalPriceForCart+=subTotal;
            }

            //session setAttribute
            session.setAttribute("numOfCartItem", numOfCartItem);
            session.setAttribute("cartItemList", cartItemList);
            session.setAttribute("totalPriceForCart", totalPriceForCart);
            
            response.sendRedirect(path);

        } catch (Exception ex) {
            out.println("<p> in RetrieveCartItem " + ex.getMessage() + "</p>");
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
    }

}
