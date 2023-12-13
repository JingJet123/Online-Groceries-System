package controller;

import entity.Staff;
import service.StaffService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ToggleStaffArchive extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    
    boolean[] isArchiveShow = { true };

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            StaffService staffService = new StaffService(em);
            List<Staff> tempList = staffService.findAll();
            List<Staff> staffList = new ArrayList<Staff>();
            
            String toggle = "";
            if(request.getParameter("toggle") != null) {
                toggle = request.getParameter("toggle");
            }
            
            switch (toggle) {
                case "stfId":
                    if (isArchiveShow[0]) {
                        for(Staff stf: tempList) {
                            if(stf.getIsDeleted()) {
                                staffList.add(stf);
                                out.print("Archived \t" + stf.getStfName()+ "\t");
                            }
                        }  
                    } else {
                        for(Staff stf: tempList) {
                            if(!stf.getIsDeleted()) {
                                staffList.add(stf);
                                out.print("Ori \t" + stf.getStfName()+ "\t");
                            }
                        }
                    }
                    convert(isArchiveShow, 0);
                    break;
                default:
            }
           
            //Set Session Attribute
            session.setAttribute("staffList", staffList);
            response.sendRedirect("secureAdmin/MaintainStaff.jsp");
            
        } catch(Exception ex) {
            out.println("<p> in ToggleStaffArchive " + ex.getMessage() + "</p>");
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
