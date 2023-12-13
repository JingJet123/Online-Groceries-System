/**
 *
 * @author Chuah Shee Yeap
 */

package controller;

import entity.Category;
import service.CategoryService;
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

public class ToggleCategArchive extends HttpServlet {

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
            CategoryService categoryService = new CategoryService(em);
            List<Category> tempList = categoryService.findAll();
            List<Category> categList = new ArrayList<Category>();
            
            String toggle = request.getParameter("toggle");
            out.println(toggle);
            switch (toggle) {
                case "categId":
                    if (isArchiveShow[0]) {
                        for(Category categ: tempList) {
                            if(categ.getIsDeleted()) {
                                categList.add(categ);
                                out.print("Ori" + categ.getIsDeleted() + "\t");
                            }
                        }  
                    } else {
                        for(Category categ: tempList) {
                            if(!categ.getIsDeleted()) {
                                categList.add(categ);
                                out.print("Archived" + categ.getIsDeleted() + "\t");
                            }
                        }
                    }
                    convert(isArchiveShow, 0);
                    break;
                default:
            }
            
            //Set Session Attribute
            session.setAttribute("categList", categList);
            response.sendRedirect("secureStaff/MaintainCategory.jsp#Category");
            
        } catch(Exception ex) {
            out.println("<p> in ToggleCategArchive " + ex.getMessage() + "</p>");
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
