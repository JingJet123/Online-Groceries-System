/**
 *
 * @author Lee Jing Jet
 */
package controller;

import entity.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.CustomerService;
import service.CartService;
import entity.Cart;

public class CustLogin extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        try {
            CustomerService custService = new CustomerService(em);
            //apply session
            HttpSession session = request.getSession();
            String userInfo = request.getParameter("userInfo");
            String password = request.getParameter("password");

            List<Customer> custList = custService.findAll();
            CartService cartService = new CartService(em);

            for (Customer customer : custList) {
                // User can login based on their email/username
                if ((customer.getCustEmail().equals(userInfo) || customer.getUsername().equals(userInfo))
                        && customer.getPassword().equals(password)) {
                    session.setAttribute("customer", customer);
                    for (Cart cart : cartService.findAll()) {
                        if (cart.getCustId().equals(customer)) {
                            session.setAttribute("cart", cart);
                        }
                    }
                    if (session.getAttribute("cart") != null) {
                        session.setAttribute("customer", customer);
                        session.setAttribute("path", "index.jsp");
                        response.sendRedirect("RetrieveCartItem");
                    }
                    response.sendRedirect("index.jsp");
                }

            }

            Customer cust = (Customer) session.getAttribute("customer");
            if (cust == null) {
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Incorrect Email, Username or Password. Please Try Again.");
                session.setAttribute("alertType", "error");
                response.sendRedirect("login.jsp");
            }

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
