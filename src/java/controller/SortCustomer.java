/**
 *
 * @author New Yee Hao
 */

package controller;

import entity.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SortCustomer", urlPatterns = {"/SortCustomer"})
public class SortCustomer extends HttpServlet {

    boolean[] isAscending = {true, true, true, true};

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        List<Customer> custList = (List<Customer>) session.getAttribute("custList");
        if (session.getAttribute("sortedCustList") != null) {
            custList = (List<Customer>) session.getAttribute("sortedCustList");
        }

        String criteria = request.getParameter("criteria");
        switch (criteria) {
            case "CustName":
                if (isAscending[0]) {
                    Collections.sort(custList, (Customer c1, Customer c2) -> c1.getCustName().compareTo(c2.getCustName()));
                } else {
                    Collections.reverse(custList);
                }
                convert(isAscending, 0);
                break;
            case "Contact":
                if (isAscending[1]) {
                    Collections.sort(custList, (Customer c1, Customer c2) -> c1.getCustContact().compareTo(c2.getCustContact()));
                } else {
                    Collections.reverse(custList);
                }
                convert(isAscending, 1);
                break;
            case "Address":
                if (isAscending[2]) {
                    Collections.sort(custList, (Customer c1, Customer c2) -> c1.getCustAddress().compareTo(c2.getCustAddress()));
                } else {
                    Collections.reverse(custList);
                }
                convert(isAscending, 2);
                break;
            case "TotalOrder":
                if (isAscending[3]) {
                    Collections.sort(custList, (Customer c1, Customer c2) -> c1.getTotalOrderMade() - c2.getTotalOrderMade());
                } else {
                    Collections.sort(custList, (Customer c1, Customer c2) -> c2.getTotalOrderMade() - c1.getTotalOrderMade());
                }
                convert(isAscending, 3);
                break;
            default:
        }
        session.setAttribute("sortedCustList", custList);
        response.sendRedirect("secureAdmin/ViewCustomer.jsp");
        
    }

    private void convert(boolean[] isAsc, int index) {
        for (int i = 0; i < isAsc.length; i++) {
            if (i == index) {
                isAsc[i] = !isAsc[i];
            } else {
                isAsc[i] = true;
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
