/**
 *
 * @author New Yee Hao
 */

package controller;

import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SortSales", urlPatterns = {"/SortSales"})
public class SortSales extends HttpServlet {

    boolean[] isAscending = {true, true, true, true};

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        List<Product> productList = (List<Product>) session.getAttribute("filteredProdList");
        Date startDate = (Date) session.getAttribute("fStartDate");
        Date endDate = (Date) session.getAttribute("fEndDate");

        String criteria = request.getParameter("criteria");
        switch (criteria) {
            case "ProdName":
                if (isAscending[0]) {
                    Collections.sort(productList, (Product p1, Product p2) -> p1.getProdName().compareTo(p2.getProdName()));
                } else {
                    Collections.reverse(productList);
                }
                convert(isAscending, 0);
                break;
            case "UnitPrice":
                if (isAscending[1]) {
                    Collections.sort(productList, (Product p1, Product p2) -> Double.compare(p1.getUnitPrice(), p2.getUnitPrice()));
                } else {
                    Collections.reverse(productList);
                }
                convert(isAscending, 1);
                break;
            case "ItemSold":
                if (isAscending[2]) {
                    Collections.sort(productList, (Product p1, Product p2) -> p1.getItemSold(startDate, endDate) - p2.getItemSold(startDate, endDate));
                } else {
                    Collections.reverse(productList);
                }
                convert(isAscending, 2);
                break;
            case "TotalProfit":
                if (isAscending[3]) {
                    Collections.sort(productList, (Product p1, Product p2)
                            -> Double.compare(p1.getUnitPrice() * p1.getItemSold(startDate, endDate),
                                    p2.getUnitPrice() * p2.getItemSold(startDate, endDate)));
                } else {
                    Collections.reverse(productList);
                }
                convert(isAscending, 3);
                break;
            default:
        }
        session.setAttribute("filteredProdList", productList);
        response.sendRedirect("secureAdmin/ViewSales.jsp");

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
