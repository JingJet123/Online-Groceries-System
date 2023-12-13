/**
 *
 * @author Chuah Shee Yeap
 */

package controller;

import entity.Category;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Chuah Shee Yeap
 */
public class SortCateg extends HttpServlet {

    boolean[] isAscending = {true, true};

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            List<Category> categList = (List<Category>) session.getAttribute("categList");

            String criteria = request.getParameter("criteria");
            switch (criteria) {
                case "categId":
                    if (isAscending[0]) {
                        Collections.reverse(categList);
                    } else {
                        Collections.sort(categList, Category.CategIdComparator);
                    }
                    convert(isAscending, 0);
                    break;
                case "categName":
                    if (isAscending[1]) {
                        Collections.reverse(categList);

                    } else {
                        Collections.sort(categList, Category.CategNameComparator);
                    }
                    convert(isAscending, 1);
                    break;
                default:
            }
            session.setAttribute("categList", categList);

            response.sendRedirect("secureStaff/MaintainCategory.jsp");

        } catch (Exception ex) {
            out.println("<p> in SortCateg " + ex.getMessage() + "</p>");
        }
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
