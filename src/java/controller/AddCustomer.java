/**
 *
 * @author Joey Kok Yen Ni
 */
package controller;

import entity.Cart;
import entity.Customer;
import java.io.IOException;
import java.io.PrintWriter;
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
import service.CartService;
import service.CustomerService;

public class AddCustomer extends HttpServlet {
    
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddCustomer</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCustomer at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try {
            
            String name = request.getParameter("name");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String contact = request.getParameter("contact");
            String address = request.getParameter("address");
            String password = request.getParameter("password");
            boolean success = false;
            
            Customer cust = new Customer();
            cust.setUsername(username);
            cust.setPassword(password);
            cust.setCustName(name);
            cust.setCustContact(contact);
            cust.setCustEmail(email);
            cust.setCustAddress(address);
            CustomerService cs = new CustomerService(em);
            utx.begin();
            success = cs.addCustomer(cust);
            utx.commit();
            
            Cart cart = new Cart();
            cart.setCustId(cust);
            CartService cartService = new CartService(em);
            utx.begin();
            success = cartService.addCart(cart);
            utx.commit();
            
            if (success) {
                
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Registration Success");
                session.setAttribute("alertType", "success");
                
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            } else {
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Registration Failed");
                session.setAttribute("alertType", "error");
                response.sendRedirect("register.jsp");
            }
//            response.sendRedirect("login.jsp");
        } catch (Exception ex) {
            if (ex.getMessage().contains("rollback")) {
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Registration Failed due to duplicated username/email");
                session.setAttribute("alertType", "error");
                response.sendRedirect("register.jsp");
            }
            out.println(ex.getMessage());
        }
        
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
