/**
 *
 * @author Lee Jia Jie
 */

package controller;

import entity.PromotionItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.sql.Date;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import service.PromotionItemService;

@WebServlet(name = "UpdatePromotion", urlPatterns = {"/UpdatePromotion"})
public class UpdatePromotion extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            
            Long stfId = Long.parseLong(request.getParameter("updatestfid"));
            Long prdId = Long.parseLong(request.getParameter("updateprodid"));
            Double rate = Double.parseDouble(request.getParameter("rate"));
            String i = request.getParameter("updatesdate");
            String j = request.getParameter("updateedate");
            SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
            Date sDate = Date.valueOf(i);
            Date eDate = Date.valueOf(j);
            PromotionItemService prom = new PromotionItemService(em);
            out.print("Update Promotion</br>");
            //out.print(sDate);
            //out.print(eDate);
            PromotionItem promo = prom.findPromoItemByCompsId(stfId, prdId, sDate, eDate);
            promo.setPromoRate(rate);
            utx.begin();
            boolean rs = prom.updatePromoItem(promo);
            utx.commit();
            out.print("</br></br>" + rs);
            response.sendRedirect("RetrievePromotionItem?path=secureStaff/MaintainPromotion.jsp");
        }catch(Exception ex){
            out.print("Error >>" + ex.getMessage());
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
