package controller;

import entity.Product;
import service.ProductService;
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

public class ToggleProdArchive extends HttpServlet {

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
            ProductService productService = new ProductService(em);
            List<Product> tempList = productService.findAll();
            List<Product> prodListPaginated = new ArrayList<Product>();
            
            String toggle = "";
            if(request.getParameter("toggle") != null) {
                toggle = request.getParameter("toggle");
            }
            
            switch (toggle) {
                case "prodId":
                    if (isArchiveShow[0]) {
                        for(Product prod: tempList) {
                            if(prod.getIsDeleted()) {
                                prodListPaginated.add(prod);
                                out.print("Archived \t" + prod.getProdName()+ "\t");
                            }
                        }  
                    } else {
                        for(Product prod: tempList) {
                            if(!prod.getIsDeleted()) {
                                prodListPaginated.add(prod);
//                                out.print("Ori" + prod.getProdName()+ "\t");
                            }
                        }
                    }
                    convert(isArchiveShow, 0);
                    break;
                default:
            }
            
            if(!prodListPaginated.isEmpty()) {
                out.println("no prob");
            } else {
                out.print("big prob");
            }
           
            //Set Session Attribute
            session.setAttribute("prodListPaginated", prodListPaginated);
            response.sendRedirect("secureStaff/MaintainProduct.jsp");
            
        } catch(Exception ex) {
            out.println("<p> in ToggleProdArchive " + ex.getMessage() + "</p>");
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
