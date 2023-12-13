/**
 *
 * @author Lee Jia Jie
 */
package controller;

import entity.Order;
import entity.ShippingDetails;
import entity.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import service.OrderService;
import service.ShippingDetailsService;

@WebServlet(name = "UpdateOrder", urlPatterns = {"/UpdateOrder"})
public class UpdateOrder extends HttpServlet {

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
            ShippingDetailsService sds = new ShippingDetailsService(em);
            OrderService ord = new OrderService(em);
            HttpSession session = request.getSession();

            String status = request.getParameter("updatestt");
            String order = request.getParameter("orderId");
            Staff stfId = (Staff) session.getAttribute("currentStaff");
            List<String> orderidList = Arrays.asList(order.split(","));
            List<Order> ordList = ord.findAll();
            ShippingDetails ship = new ShippingDetails();
            Date date = new Date();
            boolean success = true;

            ArrayList<String> selectedOrderStatus = new ArrayList();

            for (Order orders : ordList) {
                for (String ordid : orderidList) {
                    if (orders.getOrderId().equals(Long.parseLong(ordid))) {
                        out.print(orders.getOrderStatus());
                        selectedOrderStatus.add(orders.getOrderStatus());
                    }
                }
            }

            if (status.equals("Cancelled")) {
                out.print("Enter Cancelled");
                //Set Status to Cancelled
                for (Order orders : ordList) {
                    for (String ordid : orderidList) {
                        if (orders.getOrderId().equals(Long.parseLong(ordid))) {
                            orders.setOrderStatus(status);
                            orders.setStfId(stfId);
                            //out.print(orders);

                            utx.begin();
                            ord.updateOrder(orders);
                            utx.commit();
                        }
                    }
                }

            } else {
                // Check is there any different type ot f status selected
                for (int i = 0; i < selectedOrderStatus.size(); i++) {
                    for (int j = 0; j < selectedOrderStatus.size(); j++) {
                        if (!selectedOrderStatus.get(i).equals(selectedOrderStatus.get(j))) {
                            success = false;
                            session.setAttribute("alert", "on");
                            session.setAttribute("alertMsg", "Cannot select different status of order");
                            session.setAttribute("alertType", "error");
                            response.sendRedirect("secureStaff/UpdateOrder.jsp");
                        }
                    }
                }

                // Check if process is following processes
                for (int i = 0; i < selectedOrderStatus.size(); i++) {
                    if (selectedOrderStatus.get(i).equals("Pending")) {
                        if (!status.equals("Packaging")) {
                            success = false;
                            session.setAttribute("alert", "on");
                            session.setAttribute("alertMsg", "Cannot Skip Process");
                            session.setAttribute("alertType", "error");
                            response.sendRedirect("secureStaff/UpdateOrder.jsp");
                        }
                    }
                    if (selectedOrderStatus.get(i).equals("Packaging")) {
                        if (!status.equals("Shipping")) {
                            success = false;
                            session.setAttribute("alert", "on");
                            session.setAttribute("alertMsg", "Cannot Skip Process");
                            session.setAttribute("alertType", "error");
                            response.sendRedirect("secureStaff/UpdateOrder.jsp");
                        }
                    }
                    if (selectedOrderStatus.get(i).equals("Shipping")) {
                        if (!status.equals("Delivered")) {
                            success = false;
                            session.setAttribute("alert", "on");
                            session.setAttribute("alertMsg", "Cannot Skip Process");
                            if(status.equals("Packaging")){
                                session.setAttribute("alertMsg", "Cannot Backward Process");
                            }
                            session.setAttribute("alertType", "error");
                            response.sendRedirect("secureStaff/UpdateOrder.jsp");
                        }
                    }
                }
            }

            // Set Status
            for (Order orders : ordList) {
                for (String ordid : orderidList) {
                    if (orders.getOrderId().equals(Long.parseLong(ordid)) && success) {
                        if (status.equals("Shipping")) {
                            ship = sds.findShippingDetailsByOrderId(orders);
                            ship.setShippingDate(new Date());

                            utx.begin();
                            sds.updateShippingDetails(ship);
                            utx.commit();
                        }
                        orders.setOrderStatus(status);
                        orders.setStfId(stfId);
                        //out.print(orders);

                        utx.begin();
                        ord.updateOrder(orders);
                        utx.commit();
                    }
                }
            }

//            for (Order orders : ordList) {
//                for (String ordid : orderidList) {
//                    if (orders.getOrderId().equals(Long.parseLong(ordid))) {
//                        if (status.equals("Shipping")) {
//                            ship = sds.findShippingDetailsByOrderId(orders);
//                            ship.setShippingDate(new Date());
//
//                            utx.begin();
//                            sds.updateShippingDetails(ship);
//                            utx.commit();
//                        }
//                        orders.setOrderStatus(status);
//                        orders.setStfId(stfId);
//                        out.print(orders);
//
//                        utx.begin();
//                        ord.updateOrder(orders);
//                        utx.commit();
//                    }
//                }
//            }
            getServletContext().setAttribute("orderList", ordList);

            response.sendRedirect("secureStaff/UpdateOrder.jsp");
        } catch (Exception ex) {
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
