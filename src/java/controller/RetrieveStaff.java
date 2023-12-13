/**
 *
 * @author New Yee Hao
 */

package controller;

import entity.Staff;
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
import service.StaffService;

public class RetrieveStaff extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Override
    public void init() throws ServletException {
        System.out.println("StaffRetrieve Servlet init");
        StaffService stfService = new StaffService(em);
        List<Staff> stfList = stfService.findAll();
        getServletContext().setAttribute("stfList", stfList);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply Session
            HttpSession session = request.getSession();

            String path = null;
            if ((String) session.getAttribute("path1") != null) {
                path = (String) session.getAttribute("path1");
            }

            //Declare CartItem Service and call the find method
            StaffService stfService = new StaffService(em);
            List<Staff> stfList = stfService.findAll();
            getServletContext().setAttribute("stfList", stfList);

            //For Maintain Product Use
            if (path != null && path.equals("MaintainStaff")) {
                session.setAttribute("staffList", stfList);
                response.sendRedirect("secureAdmin/MaintainStaff.jsp");
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
