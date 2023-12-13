/**
 *
 * @author Lee Jing Jet
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.*;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ProdGetByCateg", urlPatterns = {"/ProdGetByCateg"})
public class ProdGetByCateg extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            String from = "";
            if((String) session.getAttribute("path") != null) {
                from = (String) session.getAttribute("path");
            }
            
            List<Product> prodList = (List<Product>) session.getAttribute("prodList");
            List<Product> getProdByCategNameList = new ArrayList<Product>();

            if (from.equals("ViewProduct.jsp") || from.equals("secureStaff/AddPromotion.jsp")) {
                String[] tempChk = request.getParameterValues("chkCateg");
                String[] categName = tempChk;

                if (tempChk == null || tempChk.length == 0) {
                    getProdByCategNameList = new ArrayList<Product>(prodList);
                } else {
                    for (Product p : prodList) {
                        for (String name : categName) {
                            //Match CategoryId
                            Long categId = Long.parseLong(name);
                            if (Objects.equals(p.getSubCategoryId().getCategoryId().getCategoryId(), categId)) {
                                getProdByCategNameList.add(p);
                                out.print(p.getProdName() + "\t");
                            }
                        }
                    } 
                }                         
            } else {
                Long categId = null;
                if(request.getParameter("categId") != null) {
                    categId = Long.parseLong(request.getParameter("categId"));
                }
                
                for (Product p : prodList) {
                    //Match CategoryId
                    if (Objects.equals(p.getSubCategoryId().getCategoryId().getCategoryId(), categId)) {
                        getProdByCategNameList.add(p);
                        out.print(p.getProdName() + "\t");
                    }
                }
            }

            out.print(from);
            
            //Set Session Attribute
            session.setAttribute("sortProdList", getProdByCategNameList);
             
            if(from.equals("secureStaff/AddPromotion.jsp")){
                response.sendRedirect("secureStaff/AddPromotion.jsp");
            }
            response.sendRedirect("ViewProduct.jsp");
            
        } catch(Exception ex) {
            out.println("<p> in ProdGetByCateg " + ex.getMessage() + "</p>");
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
