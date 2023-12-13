/**
 *
 * @author Lee Jia Jie
 */
package controller;

import entity.PromotionItem;
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
import service.PromotionItemService;

public class RetrievePromotionItem extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Override
    public void init() throws ServletException {
        System.out.println("PromotionItemRetrieve Servlet init");
        PromotionItemService promoItemService = new PromotionItemService(em);
        List<PromotionItem> allPromoList = promoItemService.findAll();
        getServletContext().setAttribute("allPromotionItemList", allPromoList);
        List<PromotionItem> validPromoList = promoItemService.findAllByValidDate();
        getServletContext().setAttribute("validPromotionItemList", validPromoList);
//        System.out.println(getServletContext().getAttribute("validPromotionItemList"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            //Declare CartItem Service and call the find method
            PromotionItemService promoItemService = new PromotionItemService(em);
            List<PromotionItem> allPromoList = promoItemService.findAll();
            List<PromotionItem> validPromoList = promoItemService.findAllByValidDate();
//            session.setAttribute("allPromotionItemList", allPromoList);
//            session.setAttribute("validPromoList", validPromoList);
            getServletContext().setAttribute("validPromotionItemList", validPromoList);
            getServletContext().setAttribute("allPromotionItemList", allPromoList);

            if (request.getParameter("path") != null && request.getParameter("path").equals("secureStaff/MaintainPromotion.jsp")) {
                response.sendRedirect("RetrieveCategory?path=secureStaff/MaintainPromotion.jsp");
            }
            if (request.getParameter("path") != null && request.getParameter("path").equals("ViewPromotionItemPage.jsp")) {
                response.sendRedirect(request.getParameter("path"));
            }

            response.sendRedirect("RetrieveStaff");

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
