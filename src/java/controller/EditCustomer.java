/**
 *
 * @author Lee Jing Jet
 */

package controller;

import entity.Customer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import service.CustomerService;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@MultipartConfig
public class EditCustomer extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditCustomer</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditCustomer at " + request.getContextPath() + "</h1>");
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
        try {
            CustomerService custService = new CustomerService(em);
            HttpSession session = request.getSession();
            String name = request.getParameter("name");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String contact = request.getParameter("contact");
            String address = request.getParameter("address");
            boolean success = false;

            Customer cust = (Customer) session.getAttribute("customer");
            List<Customer> custList = custService.findAll();
            custList.remove(cust);
            for (Customer c : custList) {
                if (username.equals(c.getUsername()) || email.equals(c.getCustEmail())) {

                    session.setAttribute("alert", "on");
                    session.setAttribute("alertMsg", "Invalid Username or Email. Might taken by another user.");
                    session.setAttribute("alertType", "error");

                    response.sendRedirect("EditInformation.jsp");
                }
            }

            if (request.getPart("photo").getSize() != 0) {
                Part filePart = request.getPart("photo");
                InputStream inputStream = filePart.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                int nRead;
                byte[] data = new byte[16384];

                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                cust.setProfileImg(buffer.toByteArray());
            }

            cust.setCustName(name);
            cust.setCustAddress(address);
            cust.setUsername(username);
            cust.setCustEmail(email);
            cust.setCustContact(contact);

            utx.begin();
            em.merge(cust);
            success = custService.updateCustomer(cust);
            utx.commit();

            if (success) {

                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Edit Success");
                session.setAttribute("alertType", "success");
                response.sendRedirect("MyProfile.jsp");

            } else {
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", "Error Editing Profile");
                session.setAttribute("alertType", "error");
                response.sendRedirect("EditInformation.jsp");
            }
        } catch (Exception ex) {
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
