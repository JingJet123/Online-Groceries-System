/**
 *
 * @author Chuah Shee Yeap
 */

package controller;

import java.io.*;
import java.util.*;
import entity.Staff;
import service.StaffService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "DeleteStaff", urlPatterns = {"/DeleteStaff"})
public class DeleteStaff extends HttpServlet {

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
            Staff stfSelected = (Staff) session.getAttribute("stfSelected");
            
            //Delete from database
            StaffService staffService = new StaffService(em);
            utx.begin();
            boolean success = staffService.chgDeleteStatus(stfSelected.getStfId());
            utx.commit();
            
            //Check Success Status
            if(success) {
                //Set Session Attribute
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", stfSelected.getUsername() + "'s Record Successfully Deleted.");
                session.setAttribute("alertType", "success");
            } else {
                out.print("Failed");
            }
            
            //Set Session Attribute
            response.sendRedirect("RetrieveStaff");
            
        } catch(Exception ex) {
            out.println("<p> in DeleteStaff " + ex.getMessage() + "</p>");
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
        PrintWriter out = response.getWriter();

        StaffService productService = new StaffService(em);

        // ======= This code is for insert all category into database ======
//        initStaff().forEach((product) -> {
//            try {
//                utx.begin();
//                productService.addStaff(product);
//                utx.commit();
//            } catch (Exception ex) {
//                out.println(ex.getMessage());
//            }
//        });
        // =================================================================
        // To add ONE category just use the code below
        // boolean isAdded = categoryService.addCategory(category);
        out.println("Success");
    }

    private List<Staff> initStaff() {
        List<Staff> productList = new ArrayList<>();

        return productList;
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
