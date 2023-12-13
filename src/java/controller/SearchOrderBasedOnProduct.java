/**
 *
 * @author Joey Kok Yen Ni
 */

package controller;

import entity.*;
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

public class SearchOrderBasedOnProduct extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.print(request.getParameter("prodName"));
            String productKeyword = request.getParameter("prodName");
            
            HttpSession session = request.getSession();
            List<OrderDetails> allOrderDetails = (List<OrderDetails>) getServletContext().getAttribute("allOrderDetailsList");
            Customer cust = (Customer) session.getAttribute("customer");

            //Get All Ordered Order Details for current customer
            List<OrderDetails> currentCustAllOrderDetails = new ArrayList<OrderDetails>();
            for (OrderDetails od : allOrderDetails) {
                if (od.getOrder().getCustId().equals(cust)) {
                    currentCustAllOrderDetails.add(od);
                }
            }

            List<OrderDetails> matchedOrderDetails = new ArrayList<OrderDetails>();
            for (OrderDetails od: currentCustAllOrderDetails) {
                if(od.getProduct().getProdName().toUpperCase().contains(productKeyword.toUpperCase())){
                    matchedOrderDetails.add(od);
                }
            }
            
//            out.print(currentCustAllOrderDetails);
//            out.println("Hello");
//            out.print(matchedOrderDetails);

            session.setAttribute("matchedProductOrderDetails", matchedOrderDetails);
            response.sendRedirect("ViewMyOrdersByProduct.jsp?keyword="+productKeyword);
            

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
