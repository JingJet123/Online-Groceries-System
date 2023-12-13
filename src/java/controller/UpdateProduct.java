package controller;

import java.io.*;
import java.util.*;
import entity.Product;
import entity.SubCategory;
import service.ProductService;
import service.SubCategoryService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Chuah Shee Yeap
 */
@MultipartConfig
@WebServlet(name = "UpdateProduct", urlPatterns = {"/UpdateProduct"})
public class UpdateProduct extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Apply session
            HttpSession session = request.getSession();

            //Prepare variable to store
            List<Product> prodListPaginated = (List<Product>) session.getAttribute("prodListPaginated");
            Product prodSelected = (Product) session.getAttribute("prodSelected");

            String prodName = request.getParameter("prodName").trim();
            String supplierName = request.getParameter("supplierName").trim();
            String showCheck = request.getParameter("showCheck");
            String subCategName = request.getParameter("subCategName").trim();
            double unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            //Verify whether to show customer
            Boolean isDeleted = false;
            if (showCheck.equals("true")) {
                isDeleted = true;
            } else {
                isDeleted = false;
            }

            //SubCategory
            SubCategory subCategory = new SubCategory();
            SubCategoryService subCategoryService = new SubCategoryService(em);
            List<SubCategory> subCategList = subCategoryService.findAll();
            for (SubCategory subCateg : subCategList) {
                if (subCateg.getSubCategoryName().equals(subCategName)) {
                    subCategory = subCateg;
                    break;
                }
            }

            //Image
            Part filePart = request.getPart("photo");
            InputStream inputStream = filePart.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            //Assign all request value to prodSelected
            prodSelected.setProdName(prodName);
            prodSelected.setSupplier(supplierName);
            prodSelected.setSubCategoryId(subCategory);
            prodSelected.setUnitPrice(unitPrice);
            prodSelected.setStock(stock);
            prodSelected.setProdImg(buffer.toByteArray());
            prodSelected.setIsDeleted(isDeleted);

            //Update to database
            ProductService productService = new ProductService(em);
            utx.begin();
            boolean success = productService.updateProduct(prodSelected);
            utx.commit();

            //Check Success Status
            if (success) {
                List<Product> productList = productService.findAll();
                getServletContext().setAttribute("productList", productList);
                session.setAttribute("alert", "on");
                session.setAttribute("alertMsg", prodSelected.getProdName() + " Successfully Updated.");
                session.setAttribute("alertType", "success");
            } else {
                out.print("Failed");
            }

            //Set Session Attribute
            session.setAttribute("prodListPaginated", prodListPaginated);
            response.sendRedirect("RetrieveProduct");

        } catch (Exception ex) {
            out.println("<p> in UpdateProduct " + ex.getMessage() + "</p>");
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
        PrintWriter out = response.getWriter();

        ProductService productService = new ProductService(em);

        // ======= This code is for insert all category into database ======
//        initProduct().forEach((product) -> {
//            try {
//                utx.begin();
//                productService.addProduct(product);
//                utx.commit();
//            } catch (Exception ex) {
//                out.println(ex.getMessage());
//            }
//        });
        // =================================================================
        // To add ONE category just use the code below
        // boolean isAdded = categoryService.addCategory(category);
        out.println("Success");
    }

    private List<Product> initProduct() {
        List<Product> productList = new ArrayList<>();

        return productList;
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
