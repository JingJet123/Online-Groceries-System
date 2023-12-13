/**
 *
 * @author New Yee Hao
 */
package controller;

import entity.Order;
import entity.OrderDetails;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "GenReport", urlPatterns = {"/GenReport"})
public class GenReport extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
        HttpSession session = request.getSession();

        try {
            if (request.getParameter("salesStartDate") != null && request.getParameter("salesEndDate") != null) {
                Date startDate = sdf.parse(request.getParameter("salesStartDate"));
                Date endDate = sdf.parse(request.getParameter("salesEndDate"));
                if (!validateEndDate(startDate, endDate)) {
                    session.setAttribute("alert", "on");
                    session.setAttribute("alertMsg", "End date must be after start date");
                    session.setAttribute("alertType", "error");
                    response.sendRedirect("secureAdmin/ViewReports.jsp");
                }
                session.setAttribute("salesStartDate", startDate);
                session.setAttribute("salesEndDate", endDate);
                response.sendRedirect("secureAdmin/SalesReport.jsp");
            } else if (request.getParameter("month") != null && request.getParameter("category") != null) {
                // Get the start date of the month
                Date startDate = sdf2.parse(request.getParameter("month"));
                // Convert Date to LocalDate in order to get the last date of the particular month
                LocalDate localEndDate = sdf2.parse(request.getParameter("month")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                // To get the last date of the particular month
                localEndDate = localEndDate.withDayOfMonth(localEndDate.getMonth().length(localEndDate.isLeapYear()));
                // Convert LocalDate back to Date
                // Date.from() - parameter accept Instant, return Date
                // atStartOfDay() - parameter accept ZoneId, return ZonedDateTime
                // toInstant() - return Instant -> Date.from() - method of ZonedDateTime
                Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                out.println(startDate + "<br/>");
                out.println(endDate);
                String category = request.getParameter("category");
                List<Product> tempProdList = new ArrayList<>();

                List<Order> orderList = (List<Order>) getServletContext().getAttribute("orderList");
                orderList.forEach((Order order) -> {
                    Date orderDate = order.getOrderDate();
                    try {
                        // after - order date is inclusive
                        // before - order date is exclusive
                        // compareTo - 0 means the two date is equal
                        if (orderDate.after(startDate)
                                && (orderDate.before(endDate) || sdf.parse(sdf.format(orderDate)).compareTo(endDate) == 0)) {
                            order.getOrderDetailsList().forEach((OrderDetails orderDetails) -> {
                                if (!orderDetails.getOrder().getOrderStatus().equals("Cancelled")) {
                                    tempProdList.add(orderDetails.getProduct());
                                }
                            });
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(ViewSales.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                out.println(category);
                List<Product> filteredProdList = new ArrayList<>(new HashSet<>(tempProdList));
                if (!category.equals("All")) {
                    Collections.sort(filteredProdList, (Product p1, Product p2)
                            -> p2.getItemSoldOfACateg(startDate, endDate, category)
                            - p1.getItemSoldOfACateg(startDate, endDate, category));
                } else {
                    Collections.sort(filteredProdList, (Product p1, Product p2)
                            -> p2.getItemSold(startDate, endDate) - p1.getItemSold(startDate, endDate));
                }
                List<Product> top5ProdList = new ArrayList<>();
                List<Integer> itemSoldList = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    top5ProdList.add(filteredProdList.get(i));
                    if (!category.equals("All")) {
                        itemSoldList.add(filteredProdList.get(i).getItemSoldOfACateg(startDate, endDate, category));
                    } else {
                        itemSoldList.add(filteredProdList.get(i).getItemSold(startDate, endDate));
                    }
                }

                for (int i = top5ProdList.size() - 1; i >= 0; i--) {
                    if (itemSoldList.get(i) == 0) {
                        top5ProdList.remove(i);
                    }
                }

                session.setAttribute("top5ProdList", top5ProdList);
                session.setAttribute("month", startDate);
                session.setAttribute("itemSoldList", itemSoldList);
                session.setAttribute("category", category);
                response.sendRedirect("secureAdmin/Top5SalesReport.jsp");
            } else {
                List<Order> orderList = (List<Order>) getServletContext().getAttribute("orderList");
                // Sort the Order List in Ascending Order
                Collections.sort(orderList, (Order o1, Order o2) -> o1.getOrderDate().compareTo(o2.getOrderDate()));

                List<Date> dateList = new ArrayList<>();
                List<Integer> occurrenceList = new ArrayList<>();
                // Get the date of the first order after sorting
                Calendar firstDate = Calendar.getInstance();
                firstDate.setTime(orderList.get(0).getOrderDate());
                // Get the date of the last order after sorting
                Calendar lastDate = Calendar.getInstance();
                lastDate.setTime(orderList.get(orderList.size() - 1).getOrderDate());
                // Add one more day into last date in order to include the order of last date in the loop
                // for counting occurrence purpose
                lastDate.add(Calendar.DATE, 1);

                try {
                    for (Calendar date = firstDate;
                            // if equals 0 - date are same and stop loop
                            sdf.parse(sdf.format(date.getTime())).compareTo(sdf.parse(sdf.format(lastDate.getTime()))) != 0;
                            date.add(Calendar.DATE, 1)) {
                        int occurrence = 0;
                        for (Order order : orderList) {
                            // Count the occurrence of the order date to look for most busy date
                            if (sdf.parse(sdf.format(order.getOrderDate())).compareTo(sdf.parse(sdf.format(date.getTime()))) == 0) {
                                occurrence++;
                            }
                        }

                        dateList.add(date.getTime());
                        occurrenceList.add(occurrence);
                    }
                } catch (ParseException ex) {
                    out.println(ex.getMessage());
                }

                // Get the most busy date - that have most orders
                Date busiestDate = dateList.get(occurrenceList.indexOf(Collections.max(occurrenceList)));
                session.setAttribute("busiestDate", busiestDate);
                response.sendRedirect("secureAdmin/OrderSummaryReport.jsp");
            }

        } catch (Exception ex) {
            out.println(ex.getMessage());
        }

    }

    private boolean validateEndDate(Date startDate, Date endDate) {
        int validEndDate = endDate.compareTo(startDate);
        // 0 = equal means endDate is valid
        // 1 = larger
        // -1 = smaller
        // endDate must >= startDate
        return validEndDate >= 0;
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
