/**
 *
 * @author Lee Jia Jie
 */

package controller;

import entity.PromotionItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

@WebServlet(name = "PromotionFliter", urlPatterns = {"/PromotionFliter"})
public class PromotionFliter extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            PromotionItemService prom = new PromotionItemService(em);
            HttpSession session = request.getSession();
            List<PromotionItem> promoList = prom.findAll();
            List<PromotionItem> tempList = new ArrayList<PromotionItem>();;
            String status;

            if (request.getParameter("status") != null) {
                out.print("Enter stt");
                status = (String) request.getParameter("status");
                if (status.equals("1")) {
                    promoList = prom.findAllByValidDate();
                    out.print(status);
                }
                if (status.equals("0")) {
                    promoList.removeAll(prom.findAllByValidDate());
                    out.print(status);
                }
                session.setAttribute("promoFliter", promoList);
                response.sendRedirect("secureStaff/MaintainPromotion.jsp");
            }

            if (!request.getParameter("searchitem").equals("")) {
                out.print("Enter srh");
                String search = request.getParameter("searchitem");
                for (PromotionItem p : promoList) {
                    if (p.getProduct().getProdName().contains(search)) {
                        tempList.add(p);
                    }
                }
                promoList = new ArrayList<PromotionItem>(tempList);
            }

            tempList = new ArrayList<PromotionItem>();

            if (!request.getParameter("category").equals("Select Category")) {
                out.print("Enter cate");
                String cate = request.getParameter("category");
                for (PromotionItem p : promoList) {
                    if (p.getProduct().getSubCategoryId().getCategoryId().getCategoryName().equals(cate)) {
                        tempList.add(p);
                    }
                }
                promoList = new ArrayList<PromotionItem>(tempList);
            }

            tempList = new ArrayList<PromotionItem>();

            if (!request.getParameter("subcategory").equals("Select Subcategory")) {
                out.print("Enter subcate");
                String subcate = request.getParameter("subcategory");
                for (PromotionItem p : promoList) {
                    if (p.getProduct().getSubCategoryId().getSubCategoryName().equals(subcate)) {
                        tempList.add(p);
                    }
                }

                promoList = new ArrayList<PromotionItem>(tempList);
            }

            out.print("In Promotion Fliter</br>");

            for (PromotionItem p : promoList) {
                out.print(p.getProduct().getProdName());
                out.print("</br>");
                out.print(p.getProduct().getSubCategoryId().getCategoryId().getCategoryName());
                out.print("</br>");
                out.print(p.getProduct().getSubCategoryId().getSubCategoryName());
                out.print("</br>");
            }

            session.setAttribute("promoFliter", promoList);
            response.sendRedirect("secureStaff/MaintainPromotion.jsp");

        } catch (Exception ex) {
            out.print(ex.getMessage() + "<< Error");
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
