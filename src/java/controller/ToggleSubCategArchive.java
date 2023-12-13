package controller;

import entity.Category;
import entity.SubCategory;
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
import service.SubCategoryService;

public class ToggleSubCategArchive extends HttpServlet {

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
            SubCategoryService subCategoryService = new SubCategoryService(em);
            Long categIdSelected = Long.parseLong(request.getParameter("categIdSelected"));
            List<SubCategory> tempList = subCategoryService.findAll();
            List<SubCategory> getSubCategList = new ArrayList<SubCategory>();
            
            String toggle = request.getParameter("toggle2");
            out.println(toggle + "\t" + categIdSelected + "\t" + isArchiveShow[0]);

            
            switch (toggle) {
                case "subCategId":
                    if (isArchiveShow[0]) {
                        for(SubCategory subCateg: tempList) {
                            if(subCateg.getIsDeleted() && Objects.equals(subCateg.getCategoryId().getCategoryId(), categIdSelected)) {
                                getSubCategList.add(subCateg);
                                out.print("Ori \t" + subCateg.getSubCategoryName()+ "\t");
                            }
                        }  
                    } else {
                        for(SubCategory subCateg: tempList) {
                            if(!subCateg.getIsDeleted() && Objects.equals(subCateg.getCategoryId().getCategoryId(), categIdSelected)) {
                                getSubCategList.add(subCateg);
                                out.print("Archived" + subCateg.getSubCategoryName()+ "\t");
                            }
                        }
                    }
                    convert(isArchiveShow, 0);
                    break;
                default:
            }
//            
            //Set Session Attribute
            session.setAttribute("getSubCategList", getSubCategList);
            response.sendRedirect("secureStaff/MaintainSubCategory.jsp");
            
        } catch(Exception ex) {
            out.println("<p> in ToggleSubCategArchive " + ex.getMessage() + "</p>");
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
