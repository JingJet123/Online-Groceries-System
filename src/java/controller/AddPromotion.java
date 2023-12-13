/**
 *
 * @author Lee Jia Jie
 */
package controller;

import entity.PromotionItem;
import entity.PromotionItemPK;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import service.ProductService;
import service.PromotionItemService;
import service.StaffService;

@WebServlet(name = "AddPromotion", urlPatterns = {"/AddPromotion"})
public class AddPromotion extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            ProductService productService = new ProductService(em);
            StaffService staffService = new StaffService(em);
            Long stfId = Long.parseLong(request.getParameter("addstfid"));
            Long prdId = Long.parseLong(request.getParameter("addprodid"));
            Double rate = Double.parseDouble(request.getParameter("rate"));
            String i = request.getParameter("addsdate");
            String j = request.getParameter("addedate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date sDate = sdf.parse(i);
            Date eDate = sdf.parse(j);
            PromotionItemService prom = new PromotionItemService(em);
            out.print("Add Promotion</br>");
            PromotionItem promo = new PromotionItem();
            promo.setProduct(productService.findProductByProdId(prdId));
            promo.setPromotionItemPK(new PromotionItemPK(stfId, prdId, sDate, eDate));
            promo.setStaff(staffService.findStaffByStfId(stfId));
            promo.setPromoRate(rate);
            utx.begin();
            boolean rs = prom.addPromotionItem(promo);
            utx.commit();
            out.print("</br></br>" + rs);
            response.sendRedirect("secureStaff/AddPromotion.jsp");
        } catch (Exception ex) {
            out.print("Error>> " + ex.getMessage());
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
