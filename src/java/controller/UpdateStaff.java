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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;

@MultipartConfig
@WebServlet(name = "UpdateStaff", urlPatterns = {"/UpdateStaff"})
public class UpdateStaff extends HttpServlet {

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
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password").trim();
            String stfName = request.getParameter("stfName").trim();
            String contact  = request.getParameter("contact").trim();
            String email  = request.getParameter("email").trim();
            double salary = Double.parseDouble(request.getParameter("salary"));
            char role = 'S';
                       
            //Image
            Part filePart = request.getPart("photo");
            InputStream inputStream = filePart.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            
            Staff stfSelected = (Staff) session.getAttribute("stfSelected");
            stfSelected.setUsername(username);
            stfSelected.setStfName(stfName);
            stfSelected.setPassword(password);
            stfSelected.setStfContact(contact);
            stfSelected.setStfEmail(email);
            stfSelected.setStfSalary(salary);
            stfSelected.setStfRole(role);
            stfSelected.setProfileImg(buffer.toByteArray());
            stfSelected.setIsDeleted(false);
            
            //Add to database
            StaffService stfService = new StaffService(em);
            utx.begin();
            boolean success = stfService.updateStaff(stfSelected);
            utx.commit();
            
            if(success) {
                //Set Session Attribute
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", stfSelected.getUsername() + " Successfully Updated.");
                session.setAttribute("alertType", "success");
            } else {
                out.print("Failed");
            }
            
            //Set Session Attribute
            response.sendRedirect("RetrieveStaff");
            
        } catch(Exception ex) {
            out.println("<p> in AddStaff " + ex.getMessage() + "</p>");
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

        StaffService stfService = new StaffService(em);

        // ======= This code is for insert all category into database ======
//        initStaff().forEach((stf) -> {
//            try {
//                utx.begin();
//                stfService.addStaff(stf);
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
        List<Staff> stfList = new ArrayList<>();

        return stfList;
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
