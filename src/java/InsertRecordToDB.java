/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import entity.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import service.*;
import java.util.*;
import java.util.logging.*;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author hao11
 */
@WebServlet(name = "InsertRecordToDB", urlPatterns = "/InsertRecordToDB")
public class InsertRecordToDB extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        insertCategory(out);
        insertSubCategory(out);
        insertProduct(out);
        insertCustomer(out);
        insertCart(out);
        insertCartItem(out);
        insertStaff(out);
        insertOrder(out);
        insertOrderDetails(out);
        insertFeedback(out);
        insertFdbkReply(out);
        insertShippingDetails(out);
        insertPayment(out);
        insertPromoItem(out);
        insertComment(out);
        insertCmtReply(out);
        out.println("Success");
        
    }

    private List<Category> initCategory() {
        List<Category> categList = new ArrayList<>();
        categList.add(new Category("Beverages"));
        categList.add(new Category("Chilled & Frozen Products"));
        categList.add(new Category("Fresh Produce"));
        categList.add(new Category("Household"));
        categList.add(new Category("Packaged Food"));
        categList.add(new Category("Pantry & Ingredients"));
        categList.add(new Category("Personal Care"));
        return categList;
    }

    private List<SubCategory> initSubCategory() {
        CategoryService categoryService = new CategoryService(em);
        List<SubCategory> subCategories = new ArrayList<>();
        // Beverages
        subCategories.add(new SubCategory("Alcohol", categoryService.findCategByCategId(1L)));
        subCategories.add(new SubCategory("Coffee Tea", categoryService.findCategByCategId(1L)));
        subCategories.add(new SubCategory("Juice", categoryService.findCategByCategId(1L)));
        subCategories.add(new SubCategory("Soft Drinks", categoryService.findCategByCategId(1L)));
        subCategories.add(new SubCategory("Water", categoryService.findCategByCategId(1L)));
        // CAF (Chilled & Frozen) Products
        subCategories.add(new SubCategory("Dairy", categoryService.findCategByCategId(2L)));
        subCategories.add(new SubCategory("Frozen Food", categoryService.findCategByCategId(2L)));
        subCategories.add(new SubCategory("Ice Cream", categoryService.findCategByCategId(2L)));
        // Fresh Produce
        subCategories.add(new SubCategory("Eggs", categoryService.findCategByCategId(3L)));
        subCategories.add(new SubCategory("Fruits", categoryService.findCategByCategId(3L)));
        subCategories.add(new SubCategory("Meat", categoryService.findCategByCategId(3L)));
        subCategories.add(new SubCategory("Seafood", categoryService.findCategByCategId(3L)));
        subCategories.add(new SubCategory("Vegetables", categoryService.findCategByCategId(3L)));
        // Household
        subCategories.add(new SubCategory("Home Cleaning", categoryService.findCategByCategId(4L)));
        subCategories.add(new SubCategory("Kitchen Utensils", categoryService.findCategByCategId(4L)));
        subCategories.add(new SubCategory("Laundry", categoryService.findCategByCategId(4L)));
        subCategories.add(new SubCategory("Paper Product", categoryService.findCategByCategId(4L)));
        // Packaged Food
        subCategories.add(new SubCategory("Canned Goods", categoryService.findCategByCategId(5L)));
        subCategories.add(new SubCategory("Chips & Biscuits", categoryService.findCategByCategId(5L)));
        subCategories.add(new SubCategory("Chocolates & Candies", categoryService.findCategByCategId(5L)));
        subCategories.add(new SubCategory("Dried Nuts", categoryService.findCategByCategId(5L)));
        subCategories.add(new SubCategory("Instant Food", categoryService.findCategByCategId(5L)));
        // Pantry and Ingeredients
        subCategories.add(new SubCategory("Grains", categoryService.findCategByCategId(6L)));
        subCategories.add(new SubCategory("Noodles", categoryService.findCategByCategId(6L)));
        subCategories.add(new SubCategory("Oil", categoryService.findCategByCategId(6L)));
        subCategories.add(new SubCategory("Sauce Dressing", categoryService.findCategByCategId(6L)));
        subCategories.add(new SubCategory("Spice Seasoning", categoryService.findCategByCategId(6L)));
        // Personal Care
        subCategories.add(new SubCategory("Body Care", categoryService.findCategByCategId(7L)));
        subCategories.add(new SubCategory("Hair Care", categoryService.findCategByCategId(7L)));
        subCategories.add(new SubCategory("Oral Care", categoryService.findCategByCategId(7L)));
        subCategories.add(new SubCategory("Sanitary", categoryService.findCategByCategId(7L)));
        subCategories.add(new SubCategory("Skin Care", categoryService.findCategByCategId(7L)));

        return subCategories;
    }

    private List<Product> initProduct() {
        // Call Service to retrieve the Sub-Category
        SubCategoryService subCategService = new SubCategoryService(em);
        // List to store every product
        List<Product> productList = new ArrayList<>();

        productList.add(new Product("Cass Beer (6 x 355ml)", 62.99, 200, "Albert Wines", subCategService.findSubCategBySubId(1L)));
        productList.add(new Product("Edelweiss Beer (24 x 330ml)", 185.99, 100, "Css Liquor", subCategService.findSubCategBySubId(1L)));
        productList.add(new Product("Emidio Pepe Montelpulciano (1 x 750ml)", 592.99, 245, "Css Liquor", subCategService.findSubCategBySubId(1L)));
        productList.add(new Product("Italy Gaja Barbaresco (1 x 750ml)", 269.97, 35, "Magnum Wines", subCategService.findSubCategBySubId(1L)));
        productList.add(new Product("Heineken Beer Large Bottle (1 x 325ml)", 11.3, 70, "Albert Wines", subCategService.findSubCategBySubId(1L)));
        productList.add(new Product("France ChÃ¢teau Corton Grancey Grand Cru (1 x 750ml)", 508.0, 160, "Albert Wines", subCategService.findSubCategBySubId(1L)));
        productList.add(new Product("Prats & Symington (P+S) Prazo de Roriz Douro (1 x 750ml)", 66.0, 150, "Albert Wines", subCategService.findSubCategBySubId(1L)));
        productList.add(new Product("Singha Beer (24 x 320ml)", 348.0, 30, "Magnum Wines", subCategService.findSubCategBySubId(1L)));
        productList.add(new Product("Tiger Beer (24 x 320ml)", 209.99, 140, "Luen Heng F&B", subCategService.findSubCategBySubId(1L)));
        // Change this to delete after insert all record to db
        productList.add(new Product("Vega Sicilia Unico Ribera Del Duero (1 x 750ml)", 449.97, 25, "Luen Heng F&B", subCategService.findSubCategBySubId(1L)));
        productList.add(new Product("Boh Tea (500g)", 16.4, 15, "Coffeeland ", subCategService.findSubCategBySubId(2L)));
        productList.add(new Product("Yeo's Chrysanthemum Tea Drink (24 x 250ml)", 19.5, 30, "Yeo's Malaysia", subCategService.findSubCategBySubId(2L)));
        productList.add(new Product("Pokka Green Tea (6 x 250ml)", 13.59, 25, "Boncafe Malaysia", subCategService.findSubCategBySubId(2L)));
        productList.add(new Product("Seasons Ice Lemon Tea (24 x 250ml)", 18.9, 45, "Boncafe Malaysia", subCategService.findSubCategBySubId(2L)));
        productList.add(new Product("Lipton Tea (200g)", 13.5, 85, "Lipton ", subCategService.findSubCategBySubId(2L)));
        productList.add(new Product("Yeo's Lychee Drink (24 x 250ml)", 19.85, 48, "Yeo's Malaysia", subCategService.findSubCategBySubId(2L)));
        productList.add(new Product("Pokka Oolong Tea (1 bottle)", 2.9, 60, "Boncafe Malaysia", subCategService.findSubCategBySubId(2L)));
        productList.add(new Product("Boh Teh Tarik Halia (12 x 26g)", 14.95, 85, "Coffeeland ", subCategService.findSubCategBySubId(2L)));
        productList.add(new Product("Oldtown White Milk Tea (13 x 520g)", 15.6, 48, "Coffeeland ", subCategService.findSubCategBySubId(2L)));
        productList.add(new Product("Yeo's Winter Melon Tea (24 x 250ml)", 18.95, 89, "Yeo's Malaysia", subCategService.findSubCategBySubId(2L)));
        productList.add(new Product("MariGold Apple Juice", 3.5, 26, "The Cool Juice", subCategService.findSubCategBySubId(3L)));
        productList.add(new Product("YOU-C1000 Vitamin Lemon", 5.0, 89, "Hairi Juice Trading", subCategService.findSubCategBySubId(3L)));
        productList.add(new Product("Jus Limau (Tanpa Biji)", 4.0, 54, "Hairi Juice Trading", subCategService.findSubCategBySubId(3L)));
        productList.add(new Product("Aloe Vera Juice", 6.0, 15, "Hairi Juice Trading", subCategService.findSubCategBySubId(3L)));
        productList.add(new Product("Ceres Mango", 7.5, 24, "Fabulous Juice Solution", subCategService.findSubCategBySubId(3L)));
        productList.add(new Product("MariGold Orange Juice", 3.5, 32, "The Cool Juice", subCategService.findSubCategBySubId(3L)));
        productList.add(new Product("Ceres Passion Fruit", 7.5, 14, "The Cool Juice", subCategService.findSubCategBySubId(3L)));
        productList.add(new Product("MariGold Pear & Mixed Berries", 3.5, 74, "Fabulous Juice Solution", subCategService.findSubCategBySubId(3L)));
        productList.add(new Product("Pokka Pomegranate", 5.5, 4, "Fabulous Juice Solution", subCategService.findSubCategBySubId(3L)));
        productList.add(new Product("MariGold Purple Veggie Mixed Fruits", 3.5, 28, "Fabulous Juice Solution", subCategService.findSubCategBySubId(3L)));
        productList.add(new Product("7Up 1.5L", 3.8, 47, "Beveco", subCategService.findSubCategBySubId(4L)));
        productList.add(new Product("100Plus Original (6 Tin x 325ml) x 4", 25.69, 59, "Beveco", subCategService.findSubCategBySubId(4L)));
        productList.add(new Product("A&W 1.5L", 3.6, 89, "Beveco", subCategService.findSubCategBySubId(4L)));
        productList.add(new Product("Coca Cola No Sugar (4 Tin x 320ml) x 6", 28.99, 17, "Zar Food & Drinks", subCategService.findSubCategBySubId(4L)));
        productList.add(new Product("F&N Minuman Soda 325ml", 3.2, 58, "Beveco", subCategService.findSubCategBySubId(4L)));
        productList.add(new Product("Fresa Fresher 200ml", 4.7, 233, "Beveco", subCategService.findSubCategBySubId(4L)));
        productList.add(new Product("Pepsi (24 Tin x 325ml)", 30.2, 32, "Ren's Palate", subCategService.findSubCategBySubId(4L)));
        productList.add(new Product("F&N Sarsi 1.5L", 3.7, 19, "Ren's Palate", subCategService.findSubCategBySubId(4L)));
        productList.add(new Product("Schweppes Tonic Water 325ml", 4.6, 54, "Zar Food & Drinks", subCategService.findSubCategBySubId(4L)));
        productList.add(new Product("Sprite Lemon-Lime mini Can Pack (6 x 180ml)", 36.79, 139, "Zar Food & Drinks", subCategService.findSubCategBySubId(4L)));
        productList.add(new Product("Bleu Natural Mineral Water 500ml", 5.4, 18, "Sterling Pure", subCategService.findSubCategBySubId(5L)));
        productList.add(new Product("evian Natural Mineral Water 600ml", 7.6, 6, "Sterling Pure", subCategService.findSubCategBySubId(5L)));
        productList.add(new Product("Jaya Grocer Air Mineral Semula Jadi 500ml", 3.5, 170, "Sterling Pure", subCategService.findSubCategBySubId(5L)));
        productList.add(new Product("Perrier Mineral Water 350ml", 5.4, 85, "Sterling Pure", subCategService.findSubCategBySubId(5L)));
        productList.add(new Product("Perrier Sparkling Water 300ml", 6.5, 24, "Sterling Pure", subCategService.findSubCategBySubId(5L)));
        productList.add(new Product("Spritzer Mineral Water 600ml", 4.8, 98, "RegalWater", subCategService.findSubCategBySubId(5L)));
        productList.add(new Product("Spritzer Sparkling Water 450ml", 5.4, 104, "JuzWater", subCategService.findSubCategBySubId(5L)));
        productList.add(new Product("Summer Mineral Water 450ml", 3.6, 22, "RegalWater", subCategService.findSubCategBySubId(5L)));
        productList.add(new Product("Pure Creamery Butter", 18.7, 28, "Hargobind Dairy Milk", subCategService.findSubCategBySubId(6L)));
        productList.add(new Product("Farmhouse Fresh Milk", 6.8, 68, "Hybrid Allied Dairy", subCategService.findSubCategBySubId(6L)));
        productList.add(new Product("Anchor Butter", 14.6, 47, "A2 Milk ", subCategService.findSubCategBySubId(6L)));
        productList.add(new Product("Emborg Cooking Cream", 19.6, 58, "A2 Milk ", subCategService.findSubCategBySubId(6L)));
        productList.add(new Product("Emborg Mozzarella", 15.4, 32, "A2 Milk ", subCategService.findSubCategBySubId(6L)));
        productList.add(new Product("Emborg Butter", 21.3, 49, "A2 Milk ", subCategService.findSubCategBySubId(6L)));
        productList.add(new Product("Farmhouse Chocolate Milk", 7.6, 54, "Hybrid Allied Dairy", subCategService.findSubCategBySubId(6L)));
        productList.add(new Product("Farmfresh Milk", 7.8, 42, "Farm Fresh Kuala Lumpur", subCategService.findSubCategBySubId(6L)));
        productList.add(new Product("Farmfresh Milk (large size)", 20.5, 45, "Farm Fresh Kuala Lumpur", subCategService.findSubCategBySubId(6L)));
        productList.add(new Product("Farmfresh Yogurt", 25.2, 36, "Farm Fresh Kuala Lumpur", subCategService.findSubCategBySubId(6L)));
        productList.add(new Product("Ayamas Frank Furter Ayam", 4.9, 78, "Chongsway Frozen Mart", subCategService.findSubCategBySubId(7L)));
        productList.add(new Product("Ayamas Drumet and Kepak", 17.6, 75, "Chongsway Frozen Mart", subCategService.findSubCategBySubId(7L)));
        productList.add(new Product("PacificWest Fish Fillets", 13.4, 48, "LB Frozen Food", subCategService.findSubCategBySubId(7L)));
        productList.add(new Product("Ramly Minced Beef", 10.9, 25, "LB Frozen Food", subCategService.findSubCategBySubId(7L)));
        productList.add(new Product("Kawan Paratha", 13.5, 69, "LB Frozen Food", subCategService.findSubCategBySubId(7L)));
        productList.add(new Product("PacificWest Cod Fish Fingers", 15.8, 86, "Shifeng Frozen Food", subCategService.findSubCategBySubId(7L)));
        productList.add(new Product("Ayamas Cheese Chicken Frank Furters", 12.4, 47, "Shifeng Frozen Food", subCategService.findSubCategBySubId(7L)));
        productList.add(new Product("Figo Seafood Tofu", 13.7, 23, "Shifeng Frozen Food", subCategService.findSubCategBySubId(7L)));
        productList.add(new Product("Atlantic Keep Frozen", 6.9, 36, "Shifeng Frozen Food", subCategService.findSubCategBySubId(7L)));
        productList.add(new Product("Creapan Mini Pancake Bites", 16.9, 15, "Shifeng Frozen Food", subCategService.findSubCategBySubId(7L)));
        productList.add(new Product("King Potong RedBean", 7.9, 105, "Five Star Gourmet", subCategService.findSubCategBySubId(8L)));
        productList.add(new Product("HaagenDazs Vanilla", 25.5, 20, "ASEnterprise Freezing ", subCategService.findSubCategBySubId(8L)));
        productList.add(new Product("TupTop NeaPolitan", 8.9, 180, "ASEnterprise Freezing ", subCategService.findSubCategBySubId(8L)));
        productList.add(new Product("Walls Vanilla Flavour", 8.9, 26, "Katering D'Rico", subCategService.findSubCategBySubId(8L)));
        productList.add(new Product("SnickersIce Cream", 11.2, 67, "Katering D'Rico", subCategService.findSubCategBySubId(8L)));
        productList.add(new Product("Magnum Mini Almond", 14.7, 58, "Magnum Malaysia", subCategService.findSubCategBySubId(8L)));
        productList.add(new Product("Cornetto Mini", 6.9, 48, "Katering D'Rico", subCategService.findSubCategBySubId(8L)));
        productList.add(new Product("Walls Solero Lime and VanillaSplit", 6.7, 96, "Five Star Gourmet", subCategService.findSubCategBySubId(8L)));
        productList.add(new Product("Nestile Neapolitan", 9.8, 38, "Five Star Gourmet", subCategService.findSubCategBySubId(8L)));
        productList.add(new Product("Bulla Creamy Classics", 12.99, 78, "Katering D'Rico", subCategService.findSubCategBySubId(8L)));
        productList.add(new Product("Duck Eggs 10S", 7.9, 50, "New Xun Fatt Enterprise", subCategService.findSubCategBySubId(9L)));
        productList.add(new Product("Green Egg Antibiotic Free 15S", 9.5, 45, "Selasih Aman", subCategService.findSubCategBySubId(9L)));
        productList.add(new Product("LKH Chic Century Eggs 4S", 6.0, 62, "Selasih Aman", subCategService.findSubCategBySubId(9L)));
        productList.add(new Product("LKH Quail Eggs 15S", 12.3, 38, "Selasih Aman", subCategService.findSubCategBySubId(9L)));
        productList.add(new Product("LKH Salted Eggs 4S", 12.0, 21, "Selasih Aman", subCategService.findSubCategBySubId(9L)));
        productList.add(new Product("LKH Telur Kampung Segar 10S", 8.1, 15, "Selasih Aman", subCategService.findSubCategBySubId(9L)));
        productList.add(new Product("Nutriplus Organic Selenium (M)10 Eggs", 6.55, 78, "Ng Brothers Trading", subCategService.findSubCategBySubId(9L)));
        productList.add(new Product("QPLUS Farm Fresh Eggs 6S", 7.08, 69, "Ng Brothers Trading", subCategService.findSubCategBySubId(9L)));
        productList.add(new Product("SunnySide Omega 3 12S", 6.4, 85, "Lau Kon Hing Egg Dealer", subCategService.findSubCategBySubId(9L)));
        productList.add(new Product("SunnySide Omega 3 30S", 19.2, 26, "Lau Kon Hing Egg Dealer", subCategService.findSubCategBySubId(9L)));
        productList.add(new Product("Anggur Hitem Tanpe Biji", 15.5, 48, "Ever Fresh Cameron", subCategService.findSubCategBySubId(10L)));
        productList.add(new Product("Epal Fuji China XL - Biji (3990)", 4.0, 69, "Ever Fresh Cameron", subCategService.findSubCategBySubId(10L)));
        productList.add(new Product("Kelapa Top Off - Sebiji", 4.0, 98, "Ever Fresh Cameron", subCategService.findSubCategBySubId(10L)));
        productList.add(new Product("Kiwi Hijau Zespri 4S", 12.5, 75, "Ever Fresh Cameron", subCategService.findSubCategBySubId(10L)));
        productList.add(new Product("Labu Air", 4.3, 36, "Ever Fresh Cameron", subCategService.findSubCategBySubId(10L)));
        productList.add(new Product("Mangga Susu (2414)", 6.5, 32, "Ever Fresh Cameron", subCategService.findSubCategBySubId(10L)));
        productList.add(new Product("Oren Navel XL - Biji (4467)", 3.0, 24, "Ever Fresh Cameron", subCategService.findSubCategBySubId(10L)));
        productList.add(new Product("Pisang Cavendish - KG (4990)", 7.5, 21, "Tropical Fruit ", subCategService.findSubCategBySubId(10L)));
        productList.add(new Product("Tembikai Wangi Rock Tempatan - KG(4789)", 5.5, 18, "Tropical Fruit ", subCategService.findSubCategBySubId(10L)));
        productList.add(new Product("Thai Longan - KG (2405)", 13.5, 89, "Ever Fresh Cameron", subCategService.findSubCategBySubId(10L)));
        productList.add(new Product("Chicken Thigh", 5.5, 23, "Eurofresh ", subCategService.findSubCategBySubId(11L)));
        productList.add(new Product("Chicken Wing", 6.7, 34, "Eurofresh ", subCategService.findSubCategBySubId(11L)));
        productList.add(new Product("Frozen Black Chicken 500G", 35.6, 45, "Lucky Frozen", subCategService.findSubCategBySubId(11L)));
        productList.add(new Product("FRZN Chicken Boneless Breast", 17.9, 28, "Meaty Foods", subCategService.findSubCategBySubId(11L)));
        productList.add(new Product("Gourmessa Chicken Garlic", 22.6, 19, "Meaty Foods", subCategService.findSubCategBySubId(11L)));
        productList.add(new Product("Gourmet Chef Smoked Chicken Toast", 17.8, 87, "Meaty Foods", subCategService.findSubCategBySubId(11L)));
        productList.add(new Product("Natural Farm New Zealand Chilled Boneless Lamb Leg 2kg", 36.7, 56, "JTM Food Industry", subCategService.findSubCategBySubId(11L)));
        productList.add(new Product("New Zealand Frozen Lamb Leg Chop 500g", 46.9, 39, "Eurofresh ", subCategService.findSubCategBySubId(11L)));
        productList.add(new Product("Prepacked Satay Chicken", 24.3, 74, "Eurofresh ", subCategService.findSubCategBySubId(11L)));
        productList.add(new Product("Sweet Japanese Ayam Madu Drumstick", 10.0, 62, "Eurofresh ", subCategService.findSubCategBySubId(11L)));
        productList.add(new Product("Aus Banana Prawn - 100G", 22.5, 54, "Fishlicious Seafood", subCategService.findSubCategBySubId(12L)));
        productList.add(new Product("Aus Tiger Prawn - 100G", 26.5, 86, "Chip Loong Shark's", subCategService.findSubCategBySubId(12L)));
        productList.add(new Product("CB 25-30G Frozen Abalone", 50.99, 74, "Chip Loong Shark's", subCategService.findSubCategBySubId(12L)));
        productList.add(new Product("IQF Blue Mussel Meat 200GM", 78.69, 48, "Chip Loong Shark's", subCategService.findSubCategBySubId(12L)));
        productList.add(new Product("Isi Udang - 100G", 16.9, 23, "Chip Loong Shark's", subCategService.findSubCategBySubId(12L)));
        productList.add(new Product("Kerang - 100G", 14.99, 39, "Chip Loong Shark's", subCategService.findSubCategBySubId(12L)));
        productList.add(new Product("Ketam Bunga - Seekor", 25.99, 65, "Hai Kee Hung ", subCategService.findSubCategBySubId(12L)));
        productList.add(new Product("LW Smoked Salmon Bombay Style 100G", 89.99, 28, "Hai Kee Hung ", subCategService.findSubCategBySubId(12L)));
        productList.add(new Product("Patin Hitam - Seekor", 14.3, 18, "Fishlicious Seafood", subCategService.findSubCategBySubId(12L)));
        productList.add(new Product("Salmon Block - 2 Pieces", 36.79, 75, "YM SEAFOOD", subCategService.findSubCategBySubId(12L)));
        productList.add(new Product("Bawang Putih - 300G per packet", 7.5, 49, "Ever Fresh Cameron", subCategService.findSubCategBySubId(13L)));
        productList.add(new Product("CF Baby Swiss Brown - 200G", 12.6, 86, "Ever Fresh Cameron", subCategService.findSubCategBySubId(13L)));
        productList.add(new Product("Chine Carrot - 300G", 8.99, 57, "Kong Kee Vegetable", subCategService.findSubCategBySubId(13L)));
        productList.add(new Product("CON-GG Mixed Salad Four Season 125G", 12.6, 84, "FK Supply", subCategService.findSubCategBySubId(13L)));
        productList.add(new Product("Halia Tua - 1 Piece", 5.5, 24, "FK Supply", subCategService.findSubCategBySubId(13L)));
        productList.add(new Product("Kobis Panjang China - 1 Packet", 4.7, 17, "FK Supply", subCategService.findSubCategBySubId(13L)));
        productList.add(new Product("Sawi Jepun - 1 Packet", 4.7, 89, "FK Supply", subCategService.findSubCategBySubId(13L)));
        productList.add(new Product("Super Sweetcorn - 2 Pieces", 12.89, 63, "Fresh Select Sdn Bhd", subCategService.findSubCategBySubId(13L)));
        productList.add(new Product("Sweet Peas 130G", 3.5, 21, "Ever Fresh Cameron", subCategService.findSubCategBySubId(13L)));
        productList.add(new Product("Terung Panjang - 1 Piece", 3.7, 25, "Kong Kee Vegetable", subCategService.findSubCategBySubId(13L)));
        productList.add(new Product("CIF Lemon 660G", 5.7, 39, "Efficient Hygeine", subCategService.findSubCategBySubId(14L)));
        productList.add(new Product("Clorox Disinfec Wipes Lemon Scent 35S", 6.9, 96, "Jacleen Sdn Bhd", subCategService.findSubCategBySubId(14L)));
        productList.add(new Product("Clorox Toilet Bowl Cleaner Clinging Bleach Gel 709ml", 9.99, 87, "Jacleen Sdn Bhd", subCategService.findSubCategBySubId(14L)));
        productList.add(new Product("Clorox Tru Blu Toilet Bowl Cleaner 50g x 6", 16.46, 75, "Wise Household", subCategService.findSubCategBySubId(14L)));
        productList.add(new Product("Dettol Disinfectant Spray Morning Dew 450ml", 21.9, 48, "Wise Household", subCategService.findSubCategBySubId(14L)));
        productList.add(new Product("Dettol Healthy Clean Bathroom Surface Spray Disinfectant 500ml", 16.1, 69, "Cleanequip Resources", subCategService.findSubCategBySubId(14L)));
        productList.add(new Product("Dettol Multi Surface Cleaner Lavender 1.5L", 22.9, 36, "Cleanequip Resources", subCategService.findSubCategBySubId(14L)));
        productList.add(new Product("FamilyGuard Spray Fragance FREE 280ML", 24.8, 86, "Cleanequip Resources", subCategService.findSubCategBySubId(14L)));
        productList.add(new Product("GM Oven Cleaner 400ML", 15.99, 57, "Cleanequip Resources", subCategService.findSubCategBySubId(14L)));
        productList.add(new Product("Green Shield Microwave & Fridge Wipes 50sheets", 16.99, 28, "Jacleen Sdn Bhd", subCategService.findSubCategBySubId(14L)));
        productList.add(new Product("Local Stock Profine Stainless Steel Knife Set (3 Pcs) COD", 89.8, 47, "GoChef Kitchen Equipment", subCategService.findSubCategBySubId(15L)));
        productList.add(new Product("Equip Marble+ 5 Piece Cookware Set Marble", 180.0, 89, "T H Equipment", subCategService.findSubCategBySubId(15L)));
        productList.add(new Product("Wiltshire Baguette 40 Piece Cutlery Set Stainless Steel", 161.0, 86, "T H Equipment", subCategService.findSubCategBySubId(15L)));
        productList.add(new Product("Chopping board Chopping board Bamboo solid wood household ", 31.9, 75, "CKE Holdings", subCategService.findSubCategBySubId(15L)));
        productList.add(new Product("Enamel Kitchen Drain Basket Ala IKEA GEMAK Colander SIZE S", 42.7, 48, "CKE Holdings", subCategService.findSubCategBySubId(15L)));
        productList.add(new Product("Stainless Steel Aquarium Tank Aquatic Plant Tweezers Tongs Aquascaping Tools Curved Fish Tank", 3.98, 36, "GoChef Kitchen Equipment", subCategService.findSubCategBySubId(15L)));
        productList.add(new Product("Xiaomi Huohou Kitchen Garlic Presser Manual Garlic Crusher Kitchen Tool", 19.9, 29, "GoChef Kitchen Equipment", subCategService.findSubCategBySubId(15L)));
        productList.add(new Product("Victorinox Stainless Scissors 16", 80.0, 18, "GoChef Kitchen Equipment", subCategService.findSubCategBySubId(15L)));
        productList.add(new Product("4Inch stainless steel cream cake spatula", 3.0, 58, "GoChef Kitchen Equipment", subCategService.findSubCategBySubId(15L)));
        productList.add(new Product("Durable Stainless Steel Food Bag Clips Clamp Kitchen Home Storage Sealer Clips", 20.3, 67, "SB Kitchen Equipment", subCategService.findSubCategBySubId(15L)));
        productList.add(new Product("Breeze Detergent Luxury Perfume 3.6kg", 29.9, 79, "Agies Laundry ", subCategService.findSubCategBySubId(16L)));
        productList.add(new Product("Clorox Bleach Original 1L x 2packs", 7.4, 86, "CF Costerfill", subCategService.findSubCategBySubId(16L)));
        productList.add(new Product("Downy Antibacterial Concentrate Fabric Softener Refill 1.5L", 18.5, 48, "CF Costerfill", subCategService.findSubCategBySubId(16L)));
        productList.add(new Product("Downy Parfum Collection Passion Concentrate Fabric Conditioner Refill 580ml", 11.99, 78, "CF Costerfill", subCategService.findSubCategBySubId(16L)));
        productList.add(new Product("Dynamo Power Gel Regular Liquid Detergent 3L", 48.99, 65, "CF Costerfill", subCategService.findSubCategBySubId(16L)));
        productList.add(new Product("Kao Bleach Colour Liquid 2L", 22.9, 35, "QuickDrop Solution", subCategService.findSubCategBySubId(16L)));
        productList.add(new Product("Persil Fibre Intelligent Detergent 5kg", 87.2, 32, "QuickDrop Solution", subCategService.findSubCategBySubId(16L)));
        productList.add(new Product("TOP Colour Protect Liquid Detergent 4kg", 18.7, 29, "QuickDrop Solution", subCategService.findSubCategBySubId(16L)));
        productList.add(new Product("Vanish Power O2 Fabric Stain Remover 900g", 28.86, 89, "Agies Laundry ", subCategService.findSubCategBySubId(16L)));
        productList.add(new Product("ZIP Multi-Purpose Scouring Powder Floral 750g", 7.6, 63, "DA Laundry ", subCategService.findSubCategBySubId(16L)));
        productList.add(new Product("Clorox Disinfecting Wipes Fresh Scent 253g", 23.99, 47, "Associated Paper", subCategService.findSubCategBySubId(17L)));
        productList.add(new Product("Cutie Compact Bathroom Tissue 30 rolls", 35.89, 45, "Associated Paper", subCategService.findSubCategBySubId(17L)));
        productList.add(new Product("Dettol Anti-Bacterial Wet Wipes 50 sheets", 16.7, 28, "Associated Paper", subCategService.findSubCategBySubId(17L)));
        productList.add(new Product("KCA Kitchen Towel 70 sheets x 6 rolls", 26.88, 38, "Associated Paper", subCategService.findSubCategBySubId(17L)));
        productList.add(new Product("Premier Facial Tissue Paper 200 pulls x 4 boxes", 15.7, 48, "Associated Paper", subCategService.findSubCategBySubId(17L)));
        productList.add(new Product("Premier Sanitizing Wipes Tissue 50 sheets x 2", 13.2, 65, "Associated Paper", subCategService.findSubCategBySubId(17L)));
        productList.add(new Product("Royal Gold Luxurious Interleaf Facial Tissue 80 pulls x 4 boxes", 19.99, 63, "Associated Paper", subCategService.findSubCategBySubId(17L)));
        productList.add(new Product("Scott Kitchen Towels 60 sheets x 6 rolls", 23.79, 24, "Associated Paper", subCategService.findSubCategBySubId(17L)));
        productList.add(new Product("Vinda Deluxe Kitchen Towel 80pcs x 6", 30.6, 54, "Nation Paper Corporation", subCategService.findSubCategBySubId(17L)));
        productList.add(new Product("Vinda Deluxe Wet Kitchen Towel 40pcs x 2", 12.3, 78, "Nation Paper Corporation", subCategService.findSubCategBySubId(17L)));
        productList.add(new Product("Ayam Brand Baked Beans 425g", 4.15, 55, "Makmur Malaya", subCategService.findSubCategBySubId(18L)));
        productList.add(new Product("Kraft macaroni & cheese 250g", 13.89, 26, "Sangla Foods", subCategService.findSubCategBySubId(18L)));
        productList.add(new Product("Ayam Brand Whole Kernel Corn 425g", 3.75, 69, "Sangla Foods", subCategService.findSubCategBySubId(18L)));
        productList.add(new Product("Hosen Logan 400g", 7.31, 35, "Sangla Foods", subCategService.findSubCategBySubId(18L)));
        productList.add(new Product("Hosen Lychee 400g", 6.5, 68, "Sangla Foods", subCategService.findSubCategBySubId(18L)));
        productList.add(new Product("Hosen Mushroom 400g", 3.95, 47, "Sangla Foods", subCategService.findSubCategBySubId(18L)));
        productList.add(new Product("Ayam Brand Sardines 425g", 8.95, 56, "Sangla Foods", subCategService.findSubCategBySubId(18L)));
        productList.add(new Product("Ciao Italian Peeled Tomatoes 400g", 4.75, 74, "Care Food Industries", subCategService.findSubCategBySubId(18L)));
        productList.add(new Product("DuChef Tomate Puree 430g", 4.75, 78, "Care Food Industries", subCategService.findSubCategBySubId(18L)));
        productList.add(new Product("Ayam Brand Tuna 160g", 5.8, 25, "Makmur Malaya", subCategService.findSubCategBySubId(18L)));
        productList.add(new Product("BinBin Rice Crackers", 6.8, 78, "NCM Food", subCategService.findSubCategBySubId(19L)));
        productList.add(new Product("ChipsMore Double Choc 163.2g", 4.8, 75, "Way Huat Trading", subCategService.findSubCategBySubId(19L)));
        productList.add(new Product("Goldfish Baked Snack Crackers 187g", 6.6, 81, "Way Huat Trading", subCategService.findSubCategBySubId(19L)));
        productList.add(new Product("Julie's Peanut Butter (12 x 80g)", 10.99, 45, "Way Huat Trading", subCategService.findSubCategBySubId(19L)));
        productList.add(new Product("Lexus Chocolate Cream (4 pieces per sachet) - 12 Sachets", 12.3, 43, "Way Huat Trading", subCategService.findSubCategBySubId(19L)));
        productList.add(new Product("Lotus Biscoff 250g", 23.5, 96, "Way Huat Trading", subCategService.findSubCategBySubId(19L)));
        productList.add(new Product("Munchy's Cream Cracker 300g", 6.9, 63, "Way Huat Trading", subCategService.findSubCategBySubId(19L)));
        productList.add(new Product("Oreo Vanilla (9 packs x 3 sandwich)", 12.39, 32, "Way Huat Trading", subCategService.findSubCategBySubId(19L)));
        productList.add(new Product("Hup Seng Cream Crackers", 8.99, 25, "Way Huat Trading", subCategService.findSubCategBySubId(19L)));
        productList.add(new Product("Pocky Family Pack Strawberry Flavour", 25.6, 14, "Datebars Biscuit", subCategService.findSubCategBySubId(19L)));
        productList.add(new Product("Ritter Sport Dark Whole Hazelnuts 100g", 13.6, 19, "Fruity Chocolate ", subCategService.findSubCategBySubId(20L)));
        productList.add(new Product("Tomorion Choco-Pie 360g", 21.3, 87, "Fruity Chocolate ", subCategService.findSubCategBySubId(20L)));
        productList.add(new Product("Nestle KitKat Dark Mint 9 Bars", 18.5, 45, "Fruity Chocolate ", subCategService.findSubCategBySubId(20L)));
        productList.add(new Product("Kinder JOY (Boys)", 3.5, 56, "Fruity Chocolate ", subCategService.findSubCategBySubId(20L)));
        productList.add(new Product("Kinder JOY (Girls)", 3.5, 25, "Fruity Chocolate ", subCategService.findSubCategBySubId(20L)));
        productList.add(new Product("Hershey's Kisses Milk Chocolate (32 pieces)", 32.1, 65, "Fruity Chocolate ", subCategService.findSubCategBySubId(20L)));
        productList.add(new Product("Nestle KitKat Original 9 Bars", 15.5, 48, "The Unicorn Chocolate", subCategService.findSubCategBySubId(20L)));
        productList.add(new Product("Coco Pudding (Mango & Lychee & Strawberry & Passion Fruit)", 10.99, 35, "The Unicorn Chocolate", subCategService.findSubCategBySubId(20L)));
        productList.add(new Product("Snickers Fun Size (12 x 20g)", 18.99, 96, "Quantum Supplies", subCategService.findSubCategBySubId(20L)));
        productList.add(new Product("Ritter Sport Cocoa (74% Intense from Peru)", 26.8, 25, "Quantum Supplies", subCategService.findSubCategBySubId(20L)));
        productList.add(new Product("Pagoda Almond Nuts", 11.79, 24, "Hwa Heng Lee Sdn Bhd", subCategService.findSubCategBySubId(21L)));
        productList.add(new Product("Cashew Nut 150g", 17.0, 69, "Lun Heng Sdn Bhd", subCategService.findSubCategBySubId(21L)));
        productList.add(new Product("Tai Sun Cocktail Nuts 180g", 9.9, 86, "Lun Heng Sdn Bhd", subCategService.findSubCategBySubId(21L)));
        productList.add(new Product("Emco Hazelnuts 375g", 9.7, 58, "Lun Heng Sdn Bhd", subCategService.findSubCategBySubId(21L)));
        productList.add(new Product("Mixed Dried Fruits & Nuts 160g", 12.0, 47, "Lun Heng Sdn Bhd", subCategService.findSubCategBySubId(21L)));
        productList.add(new Product("Sante Granola Nuts 500g", 18.9, 36, "Top East Asia ", subCategService.findSubCategBySubId(21L)));
        productList.add(new Product("Mixed Nuts 160g", 14.62, 24, "Top East Asia ", subCategService.findSubCategBySubId(21L)));
        productList.add(new Product("Yogood Fruit & Nuts (6 x 23g Bars)", 14.53, 14, "Top East Asia ", subCategService.findSubCategBySubId(21L)));
        productList.add(new Product("Natural Pine Nuts 140g", 39.9, 98, "Mahnaz Food", subCategService.findSubCategBySubId(21L)));
        productList.add(new Product("Yogood Nuts 370g", 13.49, 75, "Hwa Heng Lee Sdn Bhd", subCategService.findSubCategBySubId(21L)));
        productList.add(new Product("Curry Chicken Rice ", 12.85, 15, "Tiao Yoke Food Sdn Bhd", subCategService.findSubCategBySubId(22L)));
        productList.add(new Product("Nissin Demae Ramen Tonkutsu 100g", 5.42, 13, "Tiao Yoke Food Sdn Bhd", subCategService.findSubCategBySubId(22L)));
        productList.add(new Product("IndoMie Mi Goreng 35g x 5", 5.8, 105, "Tiao Yoke Food Sdn Bhd", subCategService.findSubCategBySubId(22L)));
        productList.add(new Product("Shin Black Noodle 30g x 5", 24.75, 47, "Tiao Yoke Food Sdn Bhd", subCategService.findSubCategBySubId(22L)));
        productList.add(new Product("WingsFood Mi Sedaap Perisa Asli (5 x 40g)", 4.25, 59, "Care Food Industries", subCategService.findSubCategBySubId(22L)));
        productList.add(new Product("Minimie Instant Noodles Slurry Chicken Flavor ", 7.59, 46, "Care Food Industries", subCategService.findSubCategBySubId(22L)));
        productList.add(new Product("Nissin Raoh Miso Ramen", 8.0, 35, "Soon Fatt Foods", subCategService.findSubCategBySubId(22L)));
        productList.add(new Product("Pringles Ramen ", 6.0, 14, "Soon Fatt Foods", subCategService.findSubCategBySubId(22L)));
        productList.add(new Product("SamYang Hot Spicy Chicken Ramen", 20.38, 96, "Soon Fatt Foods", subCategService.findSubCategBySubId(22L)));
        productList.add(new Product("Nissin Top Ramen 35g", 8.35, 26, "Soon Fatt Foods", subCategService.findSubCategBySubId(22L)));
        productList.add(new Product("Black Glutinous Rice 500g", 8.5, 19, "Royal Black Gold Grains", subCategService.findSubCategBySubId(23L)));
        productList.add(new Product("Ecobrown's Original Unpolished Brown Rice 5kg", 23.29, 24, "Royal Black Gold Grains", subCategService.findSubCategBySubId(23L)));
        productList.add(new Product("FaizaKohinoor Super Extra Long Basmathi Rice 2kg", 25.79, 35, "Royal Black Gold Grains", subCategService.findSubCategBySubId(23L)));
        productList.add(new Product("Faiza Super Special Monghul Basmathi Rice 1kg", 14.9, 48, "Royal Black Gold Grains", subCategService.findSubCategBySubId(23L)));
        productList.add(new Product("Floral PremiumI Mported Taiwan Rice 5kg", 40.35, 57, "Royal Black Gold Grains", subCategService.findSubCategBySubId(23L)));
        productList.add(new Product("Jasmine Pusa Cream Basmathi Sella Parboiled Rice 5kg", 41.89, 61, "Ets Venture Sdn Bhd", subCategService.findSubCategBySubId(23L)));
        productList.add(new Product("Jasmine Super Special Tempatan Rice 5kg", 16.5, 79, "Ets Venture Sdn Bhd", subCategService.findSubCategBySubId(23L)));
        productList.add(new Product("Riso Scotti Arborio Rice 1kg", 39.7, 84, "Ets Venture Sdn Bhd", subCategService.findSubCategBySubId(23L)));
        productList.add(new Product("Sumo Calrose Rice 1kg", 10.49, 95, "Ets Venture Sdn Bhd", subCategService.findSubCategBySubId(23L)));
        productList.add(new Product("Sunflower Pure Basmathi Premium Pusa 1121(Cream) 2kg", 25.0, 62, "Ets Venture Sdn Bhd", subCategService.findSubCategBySubId(23L)));
        productList.add(new Product("Akagi Shokuhin Joshu Akagi Soba 270g", 5.9, 24, "Soon Fatt Foods", subCategService.findSubCategBySubId(24L)));
        productList.add(new Product("Jasmine Bihun Rice Vermicell i400g", 2.65, 58, "Soon Fatt Foods", subCategService.findSubCategBySubId(24L)));
        productList.add(new Product("Kong Moon Bihun Rice Vermicelli 454g", 4.39, 69, "Soon Fatt Foods", subCategService.findSubCategBySubId(24L)));
        productList.add(new Product("Megah Clay Pot Yee Mee 375g", 3.79, 84, "Soon Fatt Foods", subCategService.findSubCategBySubId(24L)));
        productList.add(new Product("Miyatake Sanuki Hogure Yakisoba 650g", 17.83, 51, "Soon Fatt Foods", subCategService.findSubCategBySubId(24L)));
        productList.add(new Product("Mr.Korea Vermicelli Noodle 500g", 11.5, 23, "Soon Fatt Foods", subCategService.findSubCategBySubId(24L)));
        productList.add(new Product("Samlip Fresh Udon 200g x3", 8.5, 21, "Tiao Yoke Food Sdn Bhd", subCategService.findSubCategBySubId(24L)));
        productList.add(new Product("San Remo Spirals 500g", 4.99, 56, "Tiao Yoke Food Sdn Bhd", subCategService.findSubCategBySubId(24L)));
        productList.add(new Product("San Remo Vermicelli 500g", 4.09, 57, "Tiao Yoke Food Sdn Bhd", subCategService.findSubCategBySubId(24L)));
        productList.add(new Product("Sl Dried Vegetarian Noodle(s) 500g", 8.7, 55, "Tiao Yoke Food Sdn Bhd", subCategService.findSubCategBySubId(24L)));
        productList.add(new Product("Borges Olive Oil 2L", 83.5, 88, "Bizz infinity globe", subCategService.findSubCategBySubId(25L)));
        productList.add(new Product("Carotino Cooking Oil 1kg", 12.99, 47, "Hydrastep Malaysia", subCategService.findSubCategBySubId(25L)));
        productList.add(new Product("Daisy Corn Oil 2kg", 33.9, 65, "Hydrastep Malaysia", subCategService.findSubCategBySubId(25L)));
        productList.add(new Product("Filippo Berio Olive Oil 500ml", 23.4, 28, "Hydrastep Malaysia", subCategService.findSubCategBySubId(25L)));
        productList.add(new Product("Knife Cooking Oil 1kg", 9.39, 44, "Hydrastep Malaysia", subCategService.findSubCategBySubId(25L)));
        productList.add(new Product("Mazola Canola Oil 1kg", 10.49, 63, "Hydrastep Malaysia", subCategService.findSubCategBySubId(25L)));
        productList.add(new Product("Mazola Sunflower Oil 1kg", 12.69, 34, "Hydrastep Malaysia", subCategService.findSubCategBySubId(25L)));
        productList.add(new Product("Medella Coconut Cooking Oil 1.9l", 39.5, 15, "Bizz infinity globe", subCategService.findSubCategBySubId(25L)));
        productList.add(new Product("Ottogi Sesame Oil 80ml", 20.34, 27, "Bizz infinity globe", subCategService.findSubCategBySubId(25L)));
        productList.add(new Product("TemggiriIn Oil 280g", 20.5, 48, "Bizz infinity globe", subCategService.findSubCategBySubId(25L)));
        productList.add(new Product("ABC SAUS MANIS 320ml", 5.8, 22, "Sauce Empire ", subCategService.findSubCategBySubId(26L)));
        productList.add(new Product("Adabi Perencah Tom Yam 200g", 8.99, 14, "Sauce Empire ", subCategService.findSubCategBySubId(26L)));
        productList.add(new Product("Kewpie Chilli Mayo 310ml", 9.99, 36, "Sauce Empire ", subCategService.findSubCategBySubId(26L)));
        productList.add(new Product("Lee Kum Kee Dark Soy Sauce 500ml", 8.67, 79, "Sauce Empire ", subCategService.findSubCategBySubId(26L)));
        productList.add(new Product("Mahsuri Sos Tiga Rasa 530g", 6.4, 58, "Manja Food Supply", subCategService.findSubCategBySubId(26L)));
        productList.add(new Product("Puteri Sos Ikan 750ml", 3.72, 94, "Longson Food Production", subCategService.findSubCategBySubId(26L)));
        productList.add(new Product("Tabasco Gar Pepp 60ml", 9.29, 57, "Longson Food Production", subCategService.findSubCategBySubId(26L)));
        productList.add(new Product("Teans Gourmet Crisp Yanchovy Chilly 320g", 12.45, 25, "Longson Food Production", subCategService.findSubCategBySubId(26L)));
        productList.add(new Product("Value Chilli Sauce 5kg", 14.99, 33, "Manja Food Supply", subCategService.findSubCategBySubId(26L)));
        productList.add(new Product("Value Tomato Sauce 5kg", 14.99, 48, "Manja Food Supply", subCategService.findSubCategBySubId(26L)));
        productList.add(new Product("Ajinomoto 300G", 5.7, 56, "Hock Xeng Sdn Bhd", subCategService.findSubCategBySubId(27L)));
        productList.add(new Product("Aynuf Pes Sambal Tumis 100g", 5.9, 11, "Hock Xeng Sdn Bhd", subCategService.findSubCategBySubId(27L)));
        productList.add(new Product("Baba Turmeric Power 125g", 2.38, 24, "Hock Xeng Sdn Bhd", subCategService.findSubCategBySubId(27L)));
        productList.add(new Product("Brahims Kuah Kurma 180g", 7.5, 58, "Hock Xeng Sdn Bhd", subCategService.findSubCategBySubId(27L)));
        productList.add(new Product("Hexa Ginger Powder 40g", 3.8, 35, "Hock Xeng Sdn Bhd", subCategService.findSubCategBySubId(27L)));
        productList.add(new Product("Mak Siti Lemongrass Flakes 30g", 9.69, 26, "Hock Xeng Sdn Bhd", subCategService.findSubCategBySubId(27L)));
        productList.add(new Product("Mak Siti Serbuk Kunyit 250g", 5.6, 82, "Hock Xeng Sdn Bhd", subCategService.findSubCategBySubId(27L)));
        productList.add(new Product("Masfood Lemon Paste 150g", 5.85, 72, "Jiawan Spice Marketing Sdn Bhd", subCategService.findSubCategBySubId(27L)));
        productList.add(new Product("Mccormick Mixed Herbs 10g", 9.5, 42, "Jiawan Spice Marketing Sdn Bhd", subCategService.findSubCategBySubId(27L)));
        productList.add(new Product("MccorMick Paprika Hungarian 30g", 10.4, 3, "Jiawan Spice Marketing Sdn Bhd", subCategService.findSubCategBySubId(27L)));
        productList.add(new Product("Aiken Cracked Heel Cream 50G", 27.45, 63, "Kivae Bodython", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("Badlab BW Body Sculpting Gel 400ML", 14.6, 66, "Kivae Bodython", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("Dettol Barsoap Lasting Fresh 100G B3F1", 10.65, 35, "Kivae Bodython", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("Dove Shower Gel Beauty Nourishing 1L ", 24.9, 24, "Fendset", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("DR.P Basic Adult Diapers XL8", 14.88, 15, "Fendset", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("Method Gel Handwash French Lavender 354ml", 19.52, 85, "Fendset", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("Nivea Express Hydration Sea Minerals Body Lotion 400ml", 23.9, 75, "Fendset", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("Optrex Eye Lotion 300ML", 33.99, 44, "Fendset", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("Tena Adult Diaper Pants M Size 9pcs", 29.0, 8, "Kivae Bodython", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("Value Wipes 30S x 2", 7.0, 49, "Kivae Bodython", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("Vaseline Aloe Pure Jelly 50ML", 5.9, 25, "Kivae Bodython", subCategService.findSubCategBySubId(28L)));
        productList.add(new Product("Clairol Herbal Essence Conditioner Moroccan My Shine 300ml", 19.9, 22, "Skinare", subCategService.findSubCategBySubId(29L)));
        productList.add(new Product("Clairol Herbal Essence Shampoo Ardan Oil 400ml", 17.99, 46, "Skinare", subCategService.findSubCategBySubId(29L)));
        productList.add(new Product("Gatsby Moving Rubber-Grunce Mat 80G", 60.0, 58, "Galvanic", subCategService.findSubCategBySubId(29L)));
        productList.add(new Product("Gatsby Water Gloss Hard Wet Look Hair Gel 170g", 8.0, 75, "Caresy", subCategService.findSubCategBySubId(29L)));
        productList.add(new Product("Head & Shoulder Cool Menthol Anti-Dandruff Shampoo 330ml", 16.6, 49, "Caresy", subCategService.findSubCategBySubId(29L)));
        productList.add(new Product("Head & Shoulder Shampoo Men Cool Menthol 315ml", 16.6, 23, "Galvanic", subCategService.findSubCategBySubId(29L)));
        productList.add(new Product("Pantene Hair Fall Control Shampoo 340ml", 10.9, 36, "Caresy", subCategService.findSubCategBySubId(29L)));
        productList.add(new Product("Rejoice conditioner 320ml perfume smooth", 15.9, 56, "Caresy", subCategService.findSubCategBySubId(29L)));
        productList.add(new Product("Sunsilk Smooth and Manageable Shampoo 320ml", 10.9, 75, "Galvanic", subCategService.findSubCategBySubId(29L)));
        productList.add(new Product("Tresemme' Scalp Care Hair Shampoo 340ml", 20.5, 61, "Galvanic ", subCategService.findSubCategBySubId(29L)));
        productList.add(new Product("Colgate Fresh Mint 250g", 19.99, 44, "Careoryx", subCategService.findSubCategBySubId(30L)));
        productList.add(new Product("Colgate PLUS Fruity Fresh 250ML", 23.99, 85, "Careoryx", subCategService.findSubCategBySubId(30L)));
        productList.add(new Product("Colgate TP GRF 250G", 21.99, 74, "Careoryx", subCategService.findSubCategBySubId(30L)));
        productList.add(new Product("Darlie Double Action Toothpaste 250g", 18.9, 51, "Careoryx", subCategService.findSubCategBySubId(30L)));
        productList.add(new Product("Dentiste Plus White Premium and Natural White Intensive Whitening Treatment Toothpaste 100g", 13.9, 36, "Sharp Gentle Care", subCategService.findSubCategBySubId(30L)));
        productList.add(new Product("Listerine Healthy White 750ml", 28.3, 26, "Sharp Gentle Care", subCategService.findSubCategBySubId(30L)));
        productList.add(new Product("Listerine Original 750ml", 22.4, 15, "Careopolis", subCategService.findSubCategBySubId(30L)));
        productList.add(new Product("Oral B Cross Action GreenTea 1S", 11.9, 49, "Careoryx", subCategService.findSubCategBySubId(30L)));
        productList.add(new Product("Parodontax Active Fluoride 90g", 13.3, 88, "Careoryx", subCategService.findSubCategBySubId(30L)));
        productList.add(new Product("Sensodyne TP Sensitivity Gum 100G", 18.4, 52, "Sharp Gentle Care", subCategService.findSubCategBySubId(30L)));
        productList.add(new Product("Aliv Beauty Wipes 10SX3 Cherry Blossom", 2.5, 75, "Aliv Careers", subCategService.findSubCategBySubId(31L)));
        productList.add(new Product("Aliv Beauty Wipes 10SX3 Mandarin Lily", 2.5, 64, "Aliv Careers", subCategService.findSubCategBySubId(31L)));
        productList.add(new Product("Aliv Beauty Wipes 10SX3 Sweet Pea", 2.5, 32, "Aliv Careers", subCategService.findSubCategBySubId(31L)));
        productList.add(new Product("Antabax Antibac Cleansing Wipe 10SX3", 4.67, 33, "Antabax", subCategService.findSubCategBySubId(31L)));
        productList.add(new Product("Antabax Hand Sanitizer Spray 50ML", 4.5, 25, "Antabax", subCategService.findSubCategBySubId(31L)));
        productList.add(new Product("Dettol Antibacteria Wipes 50S", 2.99, 18, "Reckitt Benckiser", subCategService.findSubCategBySubId(31L)));
        productList.add(new Product("Dettol Hand Sanitizer Soothe 50ML", 6.29, 54, "Reckitt Benckiser", subCategService.findSubCategBySubId(31L)));
        productList.add(new Product("SaferCare PLUS Kids Sanitizer 35ML", 6.5, 99, "Hana Medic", subCategService.findSubCategBySubId(31L)));
        productList.add(new Product("Value Wipes 80S Refill", 13.9, 46, "Hana Medic", subCategService.findSubCategBySubId(31L)));
        productList.add(new Product("Value Wipes 100S CAN", 7.9, 28, "Hana Medic", subCategService.findSubCategBySubId(31L)));
        productList.add(new Product("BIOESS B-TRSURE Jeju Hyd Cleanser 2X100G", 33.9, 88, "Blaze Skin", subCategService.findSubCategBySubId(32L)));
        productList.add(new Product("CLN+CLR Foaming Wash 50ML", 18.68, 53, "Sibery Lon Care", subCategService.findSubCategBySubId(32L)));
        productList.add(new Product("Garnier Light Night Yogurt Cream 50ML", 29.9, 27, "Sibery Lon Care", subCategService.findSubCategBySubId(32L)));
        productList.add(new Product("Hadalabo Aha Bha Acnecontrol 130g", 35.9, 65, "Sibery Lon Care", subCategService.findSubCategBySubId(32L)));
        productList.add(new Product("Happy Vegan Mask Made Cassoside 27ml", 8.9, 49, "Blaze Skin", subCategService.findSubCategBySubId(32L)));
        productList.add(new Product("Men's Biore Deep Clean Extra Cool Double Scrub Facial Foam 100g", 15.4, 18, "Blaze Skin", subCategService.findSubCategBySubId(32L)));
        productList.add(new Product("Nutten Blackheads Away Gel 30ML", 12.9, 39, "Blaze Skin", subCategService.findSubCategBySubId(32L)));
        productList.add(new Product("Olay T.EFF Anti Aging+Fairness Cream 40G", 43.9, 75, "Sibery Lon Care", subCategService.findSubCategBySubId(32L)));
        productList.add(new Product("Oxy Oil Control Charcoal Wash 100G", 15.8, 56, "Sibery Lon Care", subCategService.findSubCategBySubId(32L)));
        productList.add(new Product("Sari Rania Gold Krim Pelembab Malam 40G", 29.3, 22, "Sibery Lon Care", subCategService.findSubCategBySubId(32L)));

        return productList;
    }

    private List<Customer> initCustomer() {
        List<Customer> custList = new ArrayList<>();

        // String username, String password, String custName, String custContact, String custEmail, String custAddress
        custList.add(new Customer("lamFung123", "lamFung!123", "Lam Fung", "012-7723489", "lamfung123@gmail.com", "119, Jalan Ah Lam 1/3, Taman Sri Fung 64230 K.L."));
        custList.add(new Customer("kennethMa20", "kennethMa@123", "Ma Guo Ming", "017-7783489", "kennethMa123@gmail.com", "120, Jalan Ah Lam 1/3, Taman Sri Fung 64230 K.L."));
        custList.add(new Customer("andyLau60", "andyLau!456", "Liu De Hua", "011-77234894", "andyLau333@gmail.com", "118, Jalan Ah Lam 1/4, Taman Sri Fung 64230 K.L."));
        custList.add(new Customer("lindaChung12", "lindaCh#333", "Zhong Kar Yan", "012-6199333", "lindaYan11@outlook.com", "117, Jalan Tan Lok Cheng 1, Damansara Lok 38020 Selangor"));
        custList.add(new Customer("boscoWong9", "boscoWong!123", "Wong Chung Chak", "016-1233445", "boscoWong33@yahoo.com", "116, Jalan Tan Lok Cheng 1, Damansara Lok 38020 Selangor"));
        custList.add(new Customer("jechoTee03", "jeCho%123", "Jecho Teeney", "019-5628455", "jteeney0@opera.com", "443, Jalan Abulah Im 4, Taman Abdul Im 43200 Selangor"));
        custList.add(new Customer("rhody321", "rhoDy#123", "Rhody Lockier", "017-5821692", "rlockier1@mozilla.org", "33, Jalan Sultan Ismail, Bukit Bintang 43200 K.L."));
        custList.add(new Customer("nerta1Wed", "wedLake!1N", "Nerta Wedlake", "013-9815913", "nwedlake2@yahoo.com", "21, Jalan Barat, Petaling Jaya, 46200 Selangor"));
        custList.add(new Customer("vinten123", "philipaV!9", "Philipa Vinten", "011-7173361", "pvinten3@nifty.com", "17, Jalan Perdana 6/8, Taman Pandan Perdana, 55300 K.L."));
        custList.add(new Customer("kembley333", "ryun3$Kem", "Ryun Kembley", "010-4903659", "rkembley2@outlook.com", "21, Jalan 2/57D, Segambut, 51200 K.L."));

        return custList;
    }

    private List<Cart> initCart() {
        List<Cart> cartList = new ArrayList<>();
        CustomerService custService = new CustomerService(em);
        cartList.add(new Cart(custService.findCustomerByCustId(1L)));
        cartList.add(new Cart(custService.findCustomerByCustId(2L)));
        cartList.add(new Cart(custService.findCustomerByCustId(3L)));
        cartList.add(new Cart(custService.findCustomerByCustId(4L)));
        cartList.add(new Cart(custService.findCustomerByCustId(5L)));
        cartList.add(new Cart(custService.findCustomerByCustId(6L)));
        cartList.add(new Cart(custService.findCustomerByCustId(7L)));
        cartList.add(new Cart(custService.findCustomerByCustId(8L)));
        cartList.add(new Cart(custService.findCustomerByCustId(9L)));
        cartList.add(new Cart(custService.findCustomerByCustId(10L)));

        return cartList;
    }

    private List<CartItem> initCartItem() {
        List<CartItem> cartItemList = new ArrayList<>();

        // CartItemPK(long cartId, long prodId)
        // CartItem(CartItemPK cartItemPK, int quantity)
        // Customer 1 (Lam Fung)
        cartItemList.add(new CartItem(new CartItemPK(1L, 1L), 3));
        cartItemList.add(new CartItem(new CartItemPK(1L, 54L), 2));
        cartItemList.add(new CartItem(new CartItemPK(1L, 105L), 4));
        cartItemList.add(new CartItem(new CartItemPK(1L, 202L), 1));
        // Customer 2 (Ma Guo Ming)
        cartItemList.add(new CartItem(new CartItemPK(2L, 319L), 1));
        cartItemList.add(new CartItem(new CartItemPK(2L, 83L), 3));
        cartItemList.add(new CartItem(new CartItemPK(2L, 221L), 5));
        // Customer 3 (Liu De Hua)
        cartItemList.add(new CartItem(new CartItemPK(3L, 290L), 4));
        cartItemList.add(new CartItem(new CartItemPK(3L, 246L), 1));
        cartItemList.add(new CartItem(new CartItemPK(3L, 204L), 6));
        cartItemList.add(new CartItem(new CartItemPK(3L, 156L), 1));
        // Customer 4 (Zhong Kar Yan)
        cartItemList.add(new CartItem(new CartItemPK(4L, 127L), 4));
        cartItemList.add(new CartItem(new CartItemPK(4L, 119L), 2));
        cartItemList.add(new CartItem(new CartItemPK(4L, 123L), 4));
        // Customer 5 (Wong Chung Chak)
        cartItemList.add(new CartItem(new CartItemPK(5L, 98L), 8));
        cartItemList.add(new CartItem(new CartItemPK(5L, 73L), 2));
        // Customer 6 (Jecho Teeney)
        cartItemList.add(new CartItem(new CartItemPK(6L, 75L), 4));
        cartItemList.add(new CartItem(new CartItemPK(6L, 110L), 2));
        cartItemList.add(new CartItem(new CartItemPK(6L, 184L), 9));
        // Customer 7 (Rhody Lockier)
        cartItemList.add(new CartItem(new CartItemPK(7L, 186L), 5));
        cartItemList.add(new CartItem(new CartItemPK(7L, 9L), 5));

        return cartItemList;
    }

    private List<Staff> initStaff() {
        List<Staff> staffList = new ArrayList<>();

        // String username, String password, String stfName, String stfContact, String stfEmail, double stfSalary, Character stfRole
        // Admin
        staffList.add(new Staff("jackieChan", "jackie@345", "Chan Kong-sang", "011-12342331", "jackieCHAN@gmail.com", 3500.00, 'A'));
        // Staff
        staffList.add(new Staff("tomHolland", "spider@Tom1", "Tom Holland", "017-61342331", "hollandSpi@gmail.com", 2000.00, 'S'));
        staffList.add(new Staff("zendaya123", "zendaya@345", "Zendaya Michelle", "019-90342331", "zendaya@gmail.com", 2500.00, 'S'));
        staffList.add(new Staff("andrew019", "andrewGar!123", "Andrew Garfield", "018-3364823", "andrew234@gmail.com", 2500.00, 'S'));

        return staffList;
    }

    private List<Order> initOrder() {
        List<Order> orderList = new ArrayList<>();
        CustomerService custService = new CustomerService(em);
        StaffService stfService = new StaffService(em);
        ProductService prodService = new ProductService(em);
        Calendar cal = Calendar.getInstance();

        /* 
        Order Status:
        - Delivered
        - Shipping
        - Packaging
        - Cancelled
         */
        // cal.set(int year, int month, int date, int hour, int minutes, int seconds)
        // NOTE: month is start from 0 - 11, 0 = January, 1 = February, 11 = December
        // Date orderDate, String orderStatus, double totalAmount, Customer custId, Staff stfId
        // =============================== JANUARY =============================================
        // Order 1 (Customer 1 - Lam Fung || Staff 1 - Jackie Chan[Admin])
        double totalAmt = prodService.findProductByProdId(54L).getUnitPrice() * 3
                + prodService.findProductByProdId(55L).getUnitPrice() * 5
                + prodService.findProductByProdId(81L).getUnitPrice() * 2
                + prodService.findProductByProdId(82L).getUnitPrice() * 3
                + prodService.findProductByProdId(137L).getUnitPrice() * 1
                + prodService.findProductByProdId(179L).getUnitPrice() * 7
                + prodService.findProductByProdId(206L).getUnitPrice() * 4;
        cal.set(2022, 0, 1, 10, 30, 43);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(1L), stfService.findStaffByStfId(1L)));
        // Order 2 (Customer 1 - Lam Fung || Staff 1 - Chan Kong-sang[Admin])
        totalAmt = prodService.findProductByProdId(194L).getUnitPrice() * 10
                + prodService.findProductByProdId(153L).getUnitPrice() * 2
                + prodService.findProductByProdId(89L).getUnitPrice() * 6;
        cal.set(2022, 0, 4, 12, 44, 58);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(1L), stfService.findStaffByStfId(1L)));
        // Order 3 (Customer 2 - Ma Guo Ming || Staff 2 - Tom Holland[Staff])
        totalAmt = prodService.findProductByProdId(13L).getUnitPrice() * 4
                + prodService.findProductByProdId(47L).getUnitPrice() * 6;
        cal.set(2022, 0, 17, 9, 12, 31);
        orderList.add(new Order(cal.getTime(), "Cancelled", totalAmt, custService.findCustomerByCustId(2L), stfService.findStaffByStfId(2L)));
        // Order 4 (Customer 3 - Liu De Hua || Staff 1 - Chan Kong-sang[Admin])
        totalAmt = prodService.findProductByProdId(88L).getUnitPrice() * 2
                + prodService.findProductByProdId(312L).getUnitPrice() * 3
                + prodService.findProductByProdId(284L).getUnitPrice() * 1
                + prodService.findProductByProdId(266L).getUnitPrice() * 1
                + prodService.findProductByProdId(113L).getUnitPrice() * 6;
        cal.set(2022, 0, 23, 20, 8, 0);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(3L), stfService.findStaffByStfId(1L)));
        // Order 5 (Customer 4 - Zhong Kar Yan || Staff 3 - Zendaya Michelle[Staff])
        totalAmt = prodService.findProductByProdId(10L).getUnitPrice() * 6
                + prodService.findProductByProdId(15L).getUnitPrice() * 6
                + prodService.findProductByProdId(211L).getUnitPrice() * 10
                + prodService.findProductByProdId(216L).getUnitPrice() * 2;
        cal.set(2022, 0, 30, 16, 13, 23);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(4L), stfService.findStaffByStfId(3L)));
        // =====================================================================================
        // =============================== FEBRUARY =============================================
        // Order 6 (Customer 5 - Wong Chung Chak || Staff 2 - Tom Holland[Staff])
        totalAmt = prodService.findProductByProdId(172L).getUnitPrice() * 2
                + prodService.findProductByProdId(173L).getUnitPrice() * 2;
        cal.set(2022, 1, 3, 13, 20, 51);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(5L), stfService.findStaffByStfId(2L)));
        // Order 7 (Customer 6 - Jecho Teeney || Staff 3 - Zendaya Michelle[Staff])
        totalAmt = prodService.findProductByProdId(74L).getUnitPrice() * 3
                + prodService.findProductByProdId(79L).getUnitPrice() * 5
                + prodService.findProductByProdId(137L).getUnitPrice() * 1;
        cal.set(2022, 1, 14, 11, 22, 59);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(6L), stfService.findStaffByStfId(3L)));
        // Order 8 (Customer 2 - Ma Guo Ming || Staff 2 - Tom Holland[Staff])
        totalAmt = prodService.findProductByProdId(5L).getUnitPrice() * 24;
        cal.set(2022, 1, 17, 14, 15, 15);
        orderList.add(new Order(cal.getTime(), "Cancelled", totalAmt, custService.findCustomerByCustId(2L), stfService.findStaffByStfId(2L)));
        // Order 9 (Customer 7 - Rhody Lockier || Staff 1 - Chan Kong-sang[Admin])
        totalAmt = prodService.findProductByProdId(31L).getUnitPrice() * 4
                + prodService.findProductByProdId(32L).getUnitPrice() * 3
                + prodService.findProductByProdId(58L).getUnitPrice() * 5
                + prodService.findProductByProdId(91L).getUnitPrice() * 1
                + prodService.findProductByProdId(111L).getUnitPrice() * 2;
        cal.set(2022, 1, 22, 18, 20, 0);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(7L), stfService.findStaffByStfId(1L)));
        // Order 10 (Customer 3 - Liu De Hua || Staff 3 - Zendaya Michelle[Staff)
        totalAmt = prodService.findProductByProdId(166L).getUnitPrice() * 2
                + prodService.findProductByProdId(192L).getUnitPrice() * 5;
        cal.set(2022, 1, 28, 18, 30, 32);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(3L), stfService.findStaffByStfId(3L)));
        // ===================================================================================
        // =============================== MARCH =============================================
        // Order 11 (Customer 8 - Nerta Wedlake || Staff 3 - Zendaya Michelle[Staff])
        totalAmt = prodService.findProductByProdId(258L).getUnitPrice() * 2
                + prodService.findProductByProdId(259L).getUnitPrice() * 5
                + prodService.findProductByProdId(186L).getUnitPrice() * 8;
        cal.set(2022, 2, 5, 21, 21, 21);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(8L), stfService.findStaffByStfId(3L)));
        // Order 12 (Customer 9 - Philipa Vinten || Staff 2 - Tom Holland[Staff])
        totalAmt = prodService.findProductByProdId(192L).getUnitPrice() * 5
                + prodService.findProductByProdId(55L).getUnitPrice() * 5
                + prodService.findProductByProdId(81L).getUnitPrice() * 2
                + prodService.findProductByProdId(82L).getUnitPrice() * 3;
        cal.set(2022, 2, 9, 20, 36, 41);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(9L), stfService.findStaffByStfId(2L)));
        // Order 13 (Customer 10 - Ryun Kembley || Staff 1 - Chan Kong-sang[Admin])
        totalAmt = prodService.findProductByProdId(176L).getUnitPrice() * 3
                + prodService.findProductByProdId(187L).getUnitPrice() * 3
                + prodService.findProductByProdId(115L).getUnitPrice() * 5
                + prodService.findProductByProdId(254L).getUnitPrice() * 1
                + prodService.findProductByProdId(113L).getUnitPrice() * 6
                + prodService.findProductByProdId(194L).getUnitPrice() * 10
                + prodService.findProductByProdId(276L).getUnitPrice() * 2
                + prodService.findProductByProdId(89L).getUnitPrice() * 6;
        cal.set(2022, 2, 14, 13, 15, 15);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(10L), stfService.findStaffByStfId(1L)));
        // Order 14 (Customer 4 - Zhong Kar Yan || Staff 2 - Tom Holland[Staff])
        totalAmt = prodService.findProductByProdId(293L).getUnitPrice() * 4
                + prodService.findProductByProdId(310L).getUnitPrice() * 2
                + prodService.findProductByProdId(319L).getUnitPrice() * 1;
        cal.set(2022, 2, 14, 15, 16, 55);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(4L), stfService.findStaffByStfId(2L)));
        // Order 15 (Customer 5 - Wong Chung CHak || Staff 2 - Tom Holland[Staff])
        totalAmt = prodService.findProductByProdId(188L).getUnitPrice() * 7;
        cal.set(2022, 2, 16, 18, 20, 25);
        orderList.add(new Order(cal.getTime(), "Cancelled", totalAmt, custService.findCustomerByCustId(5L), stfService.findStaffByStfId(2L)));
        // Order 16 (Customer 1 - Lam Fung || Staff 4 - Andrew Garfield[Staff])
        totalAmt = prodService.findProductByProdId(319L).getUnitPrice() * 1
                + prodService.findProductByProdId(74L).getUnitPrice() * 3
                + prodService.findProductByProdId(79L).getUnitPrice() * 5
                + prodService.findProductByProdId(137L).getUnitPrice() * 1;
        cal.set(2022, 2, 16, 18, 16, 22);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(1L), stfService.findStaffByStfId(4L)));
        // Order 17 (Customer 6 - Jecho Teeney || Staff 4 - Andrew Garfield[Staff])
        totalAmt = prodService.findProductByProdId(126L).getUnitPrice() * 3
                + prodService.findProductByProdId(107L).getUnitPrice() * 5;
        cal.set(2022, 2, 28, 10, 20, 25);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(6L), stfService.findStaffByStfId(4L)));
        // Order 18 (Customer 2 - Ma Guo Ming || Staff 3 - Zendaya Michelle[Staff])
        totalAmt = prodService.findProductByProdId(118L).getUnitPrice() * 2;
        cal.set(2022, 2, 30, 15, 17, 37);
        orderList.add(new Order(cal.getTime(), "Delivered", totalAmt, custService.findCustomerByCustId(2L), stfService.findStaffByStfId(3L)));
        // ===================================================================================
        // =============================== APRIL =============================================
        // Order 19 (Customer 9 - Philipa Vinten || Staff 2 - Tom Holland[Staff])
        totalAmt = prodService.findProductByProdId(54L).getUnitPrice() * 3
                + prodService.findProductByProdId(55L).getUnitPrice() * 5
                + prodService.findProductByProdId(81L).getUnitPrice() * 2
                + prodService.findProductByProdId(82L).getUnitPrice() * 3
                + prodService.findProductByProdId(137L).getUnitPrice() * 1
                + prodService.findProductByProdId(179L).getUnitPrice() * 7
                + prodService.findProductByProdId(206L).getUnitPrice() * 4;
        cal.set(2022, 3, 1, 18, 20, 33);
        orderList.add(new Order(cal.getTime(), "Shipping", totalAmt, custService.findCustomerByCustId(9L), stfService.findStaffByStfId(2L)));
        // Order 20 (Customer 8 - Nerta Wedlake || Staff 1 - Chan Kong-sang[Admin])
        totalAmt = prodService.findProductByProdId(108L).getUnitPrice() * 3
                + prodService.findProductByProdId(140L).getUnitPrice() * 1
                + prodService.findProductByProdId(206L).getUnitPrice() * 4;
        cal.set(2022, 3, 2, 20, 31, 38);
        orderList.add(new Order(cal.getTime(), "Packaging", totalAmt, custService.findCustomerByCustId(8L), stfService.findStaffByStfId(1L)));

        return orderList;
    }

    private List<OrderDetails> initOrderDetails() {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        ProductService prodService = new ProductService(em);

        // OrderDetailsPK(long orderId, long prodId)
        // OrderDetails(OrderDetailsPK orderDetailsPK, int quantity, double subTotal)
        // Order 1
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(1L, 54L), 3, prodService.findProductByProdId(54L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(1L, 55L), 5, prodService.findProductByProdId(55L).getUnitPrice() * 5));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(1L, 81L), 2, prodService.findProductByProdId(81L).getUnitPrice() * 2));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(1L, 82L), 3, prodService.findProductByProdId(82L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(1L, 137L), 1, prodService.findProductByProdId(137L).getUnitPrice() * 1));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(1L, 179L), 7, prodService.findProductByProdId(179L).getUnitPrice() * 7));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(1L, 206L), 4, prodService.findProductByProdId(206L).getUnitPrice() * 4));
        // Order 2
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(2L, 194L), 10, prodService.findProductByProdId(194L).getUnitPrice() * 10));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(2L, 153L), 2, prodService.findProductByProdId(153L).getUnitPrice() * 2));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(2L, 89L), 6, prodService.findProductByProdId(89L).getUnitPrice() * 6));
        // Order 3
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(3L, 13L), 4, prodService.findProductByProdId(13L).getUnitPrice() * 4));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(3L, 47L), 6, prodService.findProductByProdId(47L).getUnitPrice() * 6));
        // Order 4
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(4L, 88L), 2, prodService.findProductByProdId(88L).getUnitPrice() * 2));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(4L, 312L), 3, prodService.findProductByProdId(312L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(4L, 284L), 1, prodService.findProductByProdId(284L).getUnitPrice() * 1));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(4L, 266L), 1, prodService.findProductByProdId(266L).getUnitPrice() * 1));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(4L, 113L), 6, prodService.findProductByProdId(113L).getUnitPrice() * 6));
        // Order 5
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(5L, 10L), 6, prodService.findProductByProdId(10L).getUnitPrice() * 6));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(5L, 15L), 6, prodService.findProductByProdId(15L).getUnitPrice() * 6));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(5L, 211L), 10, prodService.findProductByProdId(211L).getUnitPrice() * 10));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(5L, 216L), 2, prodService.findProductByProdId(216L).getUnitPrice() * 2));
        // Order 6
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(6L, 172L), 2, prodService.findProductByProdId(172L).getUnitPrice() * 2));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(6L, 173L), 2, prodService.findProductByProdId(173L).getUnitPrice() * 2));
        // Order 7
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(7L, 74L), 3, prodService.findProductByProdId(74L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(7L, 79L), 5, prodService.findProductByProdId(79L).getUnitPrice() * 5));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(7L, 137L), 1, prodService.findProductByProdId(137L).getUnitPrice() * 1));
        // Order 8
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(8L, 5L), 24, prodService.findProductByProdId(5L).getUnitPrice() * 24));
        // Order 9
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(9L, 31L), 4, prodService.findProductByProdId(31L).getUnitPrice() * 4));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(9L, 32L), 3, prodService.findProductByProdId(32L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(9L, 58L), 5, prodService.findProductByProdId(58L).getUnitPrice() * 5));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(9L, 91L), 1, prodService.findProductByProdId(91L).getUnitPrice() * 1));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(9L, 111L), 2, prodService.findProductByProdId(111L).getUnitPrice() * 2));
        // Order 10
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(10L, 166L), 2, prodService.findProductByProdId(166L).getUnitPrice() * 2));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(10L, 192L), 5, prodService.findProductByProdId(192L).getUnitPrice() * 5));
        // Order 11
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(11L, 258L), 2, prodService.findProductByProdId(258L).getUnitPrice() * 2));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(11L, 259L), 5, prodService.findProductByProdId(259L).getUnitPrice() * 5));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(11L, 186L), 8, prodService.findProductByProdId(186L).getUnitPrice() * 8));
        // Order 12
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(12L, 192L), 5, prodService.findProductByProdId(192L).getUnitPrice() * 5));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(12L, 55L), 5, prodService.findProductByProdId(55L).getUnitPrice() * 5));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(12L, 81L), 2, prodService.findProductByProdId(81L).getUnitPrice() * 2));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(12L, 82L), 3, prodService.findProductByProdId(82L).getUnitPrice() * 3));
        // Order 13
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(13L, 176L), 3, prodService.findProductByProdId(176L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(13L, 187L), 3, prodService.findProductByProdId(187L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(13L, 115L), 5, prodService.findProductByProdId(115L).getUnitPrice() * 5));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(13L, 254L), 1, prodService.findProductByProdId(254L).getUnitPrice() * 1));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(13L, 113L), 6, prodService.findProductByProdId(113L).getUnitPrice() * 6));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(13L, 194L), 10, prodService.findProductByProdId(194L).getUnitPrice() * 10));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(13L, 276L), 2, prodService.findProductByProdId(276L).getUnitPrice() * 2));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(13L, 89L), 6, prodService.findProductByProdId(89L).getUnitPrice() * 6));
        // Order 14
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(14L, 293L), 4, prodService.findProductByProdId(293L).getUnitPrice() * 4));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(14L, 310L), 2, prodService.findProductByProdId(310L).getUnitPrice() * 2));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(14L, 319L), 1, prodService.findProductByProdId(319L).getUnitPrice() * 1));
        // Order 15
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(15L, 188L), 7, prodService.findProductByProdId(188L).getUnitPrice() * 7));
        // Order 16
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(16L, 319L), 1, prodService.findProductByProdId(319L).getUnitPrice() * 1));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(16L, 74L), 3, prodService.findProductByProdId(74L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(16L, 79L), 5, prodService.findProductByProdId(79L).getUnitPrice() * 5));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(16L, 137L), 1, prodService.findProductByProdId(137L).getUnitPrice() * 1));
        // Order 17
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(17L, 126L), 3, prodService.findProductByProdId(126L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(17L, 107L), 5, prodService.findProductByProdId(107L).getUnitPrice() * 5));
        // Order 18
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(18L, 118L), 2, prodService.findProductByProdId(118L).getUnitPrice() * 2));
        // Order 19
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(19L, 54L), 3, prodService.findProductByProdId(54L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(19L, 55L), 5, prodService.findProductByProdId(55L).getUnitPrice() * 5));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(19L, 81L), 2, prodService.findProductByProdId(81L).getUnitPrice() * 2));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(19L, 82L), 3, prodService.findProductByProdId(82L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(19L, 137L), 1, prodService.findProductByProdId(137L).getUnitPrice() * 1));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(19L, 179L), 7, prodService.findProductByProdId(179L).getUnitPrice() * 7));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(19L, 206L), 4, prodService.findProductByProdId(206L).getUnitPrice() * 4));
        // Order 20
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(20L, 108L), 3, prodService.findProductByProdId(108L).getUnitPrice() * 3));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(20L, 140L), 1, prodService.findProductByProdId(140L).getUnitPrice() * 1));
        orderDetailsList.add(new OrderDetails(new OrderDetailsPK(20L, 206L), 4, prodService.findProductByProdId(206L).getUnitPrice() * 4));

        return orderDetailsList;
    }

    private List<ShippingDetails> initShippingDetails() {
        List<ShippingDetails> shippingDetailsList = new ArrayList<>();
        CustomerService custService = new CustomerService(em);
        OrderService orderService = new OrderService(em);
        Calendar cal = Calendar.getInstance();

        // ShippingDetails(Date shippingDate, String shippingAddress, Order orderId)
        // cal.set(int year, int month, int date)
        // NOTE: month is start from 0 - 11, 0 = January, 1 = February, 11 = December
        // Order 1 | Customer 1 - Lam Fung
        cal.set(2022, 0, 2);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(1L).getCustAddress(), orderService.findOrderByOrderId(1L)));
        // Ordr 2 | Customer 1 - Lam Fung
        cal.set(2022, 0, 5);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(1L).getCustAddress(), orderService.findOrderByOrderId(2L)));
        // Order 4 | Customer 3 - Liu De Hua
        cal.set(2022, 0, 24);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(3L).getCustAddress(), orderService.findOrderByOrderId(4L)));
        // Order 5 | Customer 4 - Zhong Kar Yan 
        cal.set(2022, 0, 31);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(4L).getCustAddress(), orderService.findOrderByOrderId(5L)));
        // Order 6 | Customer 5 - Wong Chung Chak
        cal.set(2022, 1, 4);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(5L).getCustAddress(), orderService.findOrderByOrderId(6L)));
        // Order 7 | Customer 6 - Jecho Teeney
        cal.set(2022, 1, 14);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(6L).getCustAddress(), orderService.findOrderByOrderId(7L)));
        // Order 9 | Customer 7 - Rhody Lockier
        cal.set(2022, 1, 23);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(7L).getCustAddress(), orderService.findOrderByOrderId(9L)));
        // Order 10 | Customer 3 - Liu De Hua
        cal.set(2022, 2, 1);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(3L).getCustAddress(), orderService.findOrderByOrderId(10L)));
        // Order 11 | Customer 8 - Nerta Wedlake
        cal.set(2022, 2, 7);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(8L).getCustAddress(), orderService.findOrderByOrderId(11L)));
        // Order 12 | Customer 9 - Phiipa Vinten
        cal.set(2022, 2, 11);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(9L).getCustAddress(), orderService.findOrderByOrderId(12L)));
        // Order 13 | Customer 10 - Ryun Kembley
        cal.set(2022, 2, 15);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(10L).getCustAddress(), orderService.findOrderByOrderId(13L)));
        // Order 14 | Customer 4 - Zhong Kar Yan
        cal.set(2022, 2, 15);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(4L).getCustAddress(), orderService.findOrderByOrderId(14L)));
        // Order 16 | Customer 1 - Lam Fung
        cal.set(2022, 2, 17);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(1L).getCustAddress(), orderService.findOrderByOrderId(16L)));
        // Order 17 | Customer 6 - Jecho Teeney
        cal.set(2022, 2, 29);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(6L).getCustAddress(), orderService.findOrderByOrderId(17L)));
        // Order 18 | Customer 2 - Ma Guo Ming
        cal.set(2022, 2, 31);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(2L).getCustAddress(), orderService.findOrderByOrderId(18L)));
        // Order 19 | Customer 9 - Philipa Vinten
        cal.set(2022, 3, 2);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(9L).getCustAddress(), orderService.findOrderByOrderId(19L)));
        // Order 20 | Customer 8 - Nerta Wedlake
        cal.set(2022, 3, 4);
        shippingDetailsList.add(new ShippingDetails(cal.getTime(),
                custService.findCustomerByCustId(8L).getCustAddress(), orderService.findOrderByOrderId(20L)));

        return shippingDetailsList;
    }

    private List<Feedback> initFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        CustomerService custService = new CustomerService(em);
        OrderService orderService = new OrderService(em);
        Calendar cal = Calendar.getInstance();
        /* 
        Category:
        - Service
        - Delivery
        - Product
        - Refund
         */
        // cal.set(int year, int month, int date)
        // NOTE: month is start from 0 - 11, 0 = January, 1 = February, 11 = December
        // Feedback(String category, String message, Date fdbkDate, Customer custId, Order orderId)
        // ====================== Service ====================================
        // Feedback 1
        // Order 3 | Customer 2 - Ma Guo Ming | Order Date - 2022, 1, 17 - 09:12:31
        cal.set(2022, 0, 17, 9, 30, 55);
        feedbackList.add(new Feedback("Service", "Does not have enough information about the products",
                cal.getTime(), custService.findCustomerByCustId(2L), orderService.findOrderByOrderId(3L)));
        // ====================================================================
        // ====================== Delivery ====================================
        // Feedback 2
        // Order 4 | Customer 3 - Liu De Hua | Order Date - 2022, 1, 23 - 20:08:00
        cal.set(2022, 0, 26, 9, 0, 31);
        feedbackList.add(new Feedback("Delivery", "Delivery service is slow and has exceeded the estimated delivery date",
                cal.getTime(), custService.findCustomerByCustId(3L), orderService.findOrderByOrderId(4L)));
        // Feedback 3
        // Order 9 | Customer 7 - Rhody Lockier | Order Date - 2022, 2, 22 - 18:20:00
        cal.set(2022, 1, 28, 13, 8, 52);
        feedbackList.add(new Feedback("Delivery", "The attitude of deliveryman is rude and bad",
                cal.getTime(), custService.findCustomerByCustId(7L), orderService.findOrderByOrderId(9L)));
        // ====================================================================
        // ====================== Product ====================================
        // Feedback 4
        // Order 2 | Customer 1 - Lam Fung | Order Date - 2022, 1, 4 - 12:44:58
        cal.set(2022, 0, 6, 13, 58, 19);
        feedbackList.add(new Feedback("Product", "Product is broken when received and not in good condition",
                cal.getTime(), custService.findCustomerByCustId(1L), orderService.findOrderByOrderId(2L)));
        // Feedback 5
        // Order 5 | Customer 4 - Zhong Kar Yan | Order Date - 2022, 1, 30 - 16:13:23
        cal.set(2022, 1, 2, 18, 30, 45);
        feedbackList.add(new Feedback("Product", "Product is expired and some of products is not well packed",
                cal.getTime(), custService.findCustomerByCustId(4L), orderService.findOrderByOrderId(5L)));
        // ====================================================================
        // ======================== Refund ====================================
        // Feedback 6
        // Order 8 | Customer 2 - Ma Guo Ming | Order Date - 2022, 2, 17 - 14:15:15
        cal.set(2022, 1, 20, 16, 55, 53);
        feedbackList.add(new Feedback("Refund", "The refunded amount is incorrect with the amount I paid when I dropped an order",
                cal.getTime(), custService.findCustomerByCustId(2L), orderService.findOrderByOrderId(8L)));
        // Feedback 7
        // Order 15 | Customer 5 - Wong Chung Chak | Order Date - 2022, 3, 16 - 18:20:25
        cal.set(2022, 2, 20, 9, 30, 0);
        feedbackList.add(new Feedback("Refund", "I have not received the refund amount yet",
                cal.getTime(), custService.findCustomerByCustId(5L), orderService.findOrderByOrderId(15L)));

        return feedbackList;
    }

    private List<FeedbackReply> initFdbkReply() {
        List<FeedbackReply> fdbkReplyList = new ArrayList<>();
        FeedbackService fdbkService = new FeedbackService(em);
        StaffService stfService = new StaffService(em);
        Calendar cal = Calendar.getInstance();

        // cal.set(int year, int month, int date)
        // NOTE: month is start from 0 - 11, 0 = January, 1 = February, 11 = December
        // FeedbackReply(String message, Date replyDate, Feedback feedbackId, Staff stfId)
        // Feedback 1 - Service Problem | Feedback Date - 2022, 1, 17 - 09:30:55
        // Customer 2 - Ma Guo Ming 
        // Message - Does not have enough information about the products
        // Staff 1 - Chan Kong-sang (Admin)
        cal.set(2022, 0, 17, 11, 20, 11);
        fdbkReplyList.add(new FeedbackReply("Sorry for the inconvenience caused, we will update this function to our IT team and enhance it. Thank you",
                cal.getTime(), fdbkService.findFeedbackByFdbkId(1L), stfService.findStaffByStfId(1L)));
        // Feedback 2 - Delivery Problem | Feedback Date - 2022, 1, 26 - 09:00:31
        // Customer 3 - Liu De Hua
        // Message - Delivery service is slow and has exceeded the estimated delivery date
        // Staff 2 - Tom Holland (Staff)
        cal.set(2022, 0, 26, 9, 30, 0);
        fdbkReplyList.add(new FeedbackReply("Dear customer, sorry for the inconvience, we will update this to our delivery team and improve in the future.",
                cal.getTime(), fdbkService.findFeedbackByFdbkId(2L), stfService.findStaffByStfId(2L)));
        // Feedback 3 - Delivery Problem | Feedback Date - 2022, 2, 28 - 13:08:52
        // Customer 7 - Rhody Lockier
        // Message - The attitude of deliveryman is rude and bad
        // Staff 1 - Chan Kong-sang (Admin)
        cal.set(2022, 1, 28, 13, 15, 10);
        fdbkReplyList.add(new FeedbackReply("I'm really sorry for your bad experience, we will warn the deliveryman and punish him.",
                cal.getTime(), fdbkService.findFeedbackByFdbkId(3L), stfService.findStaffByStfId(1L)));
        // Feedback 4 - Product Problem | Feedback Date - 2022, 1, 6 - 13:58:19
        // Customer 1 - Lam Fung
        // Message - Product is broken when received and not in good condition
        // Staff 2 - Tom Holland (Staff)
        cal.set(2022, 0, 7, 15, 45, 27);
        fdbkReplyList.add(new FeedbackReply("Dear customer, sorry for this issue and your bad experience with our service, we will refund you the price of the product",
                cal.getTime(), fdbkService.findFeedbackByFdbkId(4L), stfService.findStaffByStfId(2L)));
        // Feedback 5 - Product Problem | Feedback Date - 2022, 2, 2 - 18:30:45
        // Customer 4 - Zhong Kar Yan
        // Message - Product is expired and some of products is not well packed
        // Staff 3 - Zendaya Michelle (Staff)
        cal.set(2022, 1, 2, 19, 0, 25);
        fdbkReplyList.add(new FeedbackReply("Hi, sorry for your unhappy experience with our products, we will compensate a new one for you.",
                cal.getTime(), fdbkService.findFeedbackByFdbkId(5L), stfService.findStaffByStfId(3L)));
        // Feedback 6 - Refund Problem | Feedback Date - 2022, 2, 20 - 16:55:53
        // Customer 2 - Ma Guo Ming 
        // Message - The refunded amount is incorrect with the amount I paid when I dropped an order
        // Staff 3 - Zendaya Michelle (Staff)
        cal.set(2022, 1, 21, 9, 2, 18);
        fdbkReplyList.add(new FeedbackReply("Hello, we are sorry for the inconvience caused, and we will pay back to you shortly.",
                cal.getTime(), fdbkService.findFeedbackByFdbkId(6L), stfService.findStaffByStfId(3L)));
        // Feedback 7 - Refund Problem | Feedback Date - 2022, 3, 20 - 09:30:00
        // Customer 5 - Wong Chung Chak
        // Message - I have not received the refund amount yet
        // Staff 4 - Andrew Garfield (Staff)
        cal.set(2022, 2, 20, 11, 26, 38);
        fdbkReplyList.add(new FeedbackReply("Dear Wong, we are really sorry for your unhappy experience, we require 5-7 working days to process your refund request",
                cal.getTime(), fdbkService.findFeedbackByFdbkId(7L), stfService.findStaffByStfId(4L)));

        return fdbkReplyList;
    }

    private List<Payment> initPayment() {
        List<Payment> paymentList = new ArrayList<>();
        OrderService orderService = new OrderService(em);
        Calendar cal = Calendar.getInstance();
        /* 
        Payment Type:
        - Credit/Debit Card
        - PayPal
        Payment Status:
        - Refunded
        - Completed
        - Processing
         */
        // Payment(String payType, Date payDate, double payAmount, Order orderId)
        // =============================== JANUARY =============================================
        // Order 1 (Customer 1 - Lam Fung || Staff 1 - Jackie Chan[Admin])
        cal.set(2022, 0, 1, 10, 35, 33);
        Order order = orderService.findOrderByOrderId(1L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 2 (Customer 1 - Lam Fung || Staff 1 - Chan Kong-sang[Admin])
        cal.set(2022, 0, 4, 12, 46, 18);
        order = orderService.findOrderByOrderId(2L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 3 (Customer 2 - Ma Guo Ming || Staff 2 - Tom Holland[Staff])
        cal.set(2022, 0, 17, 9, 18, 0);
        order = orderService.findOrderByOrderId(3L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 4 (Customer 3 - Liu De Hua || Staff 1 - Chan Kong-sang[Admin])
        cal.set(2022, 0, 23, 20, 9, 48);
        order = orderService.findOrderByOrderId(4L);
        paymentList.add(new Payment("PayPal", cal.getTime(), order.getTotalAmount(), order));
        // Order 5 (Customer 4 - Zhong Kar Yan || Staff 3 - Zendaya Michelle[Staff])
        cal.set(2022, 0, 30, 16, 14, 53);
        order = orderService.findOrderByOrderId(5L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // =====================================================================================
        // =============================== FEBRUARY =============================================
        // Order 6 (Customer 5 - Wong Chung Chak || Staff 2 - Tom Holland[Staff])
        cal.set(2022, 1, 3, 13, 22, 11);
        order = orderService.findOrderByOrderId(6L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 7 (Customer 6 - Jecho Teeney || Staff 3 - Zendaya Michelle[Staff])
        cal.set(2022, 1, 14, 11, 23, 59);
        order = orderService.findOrderByOrderId(7L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 8 (Customer 2 - Ma Guo Ming || Staff 2 - Tom Holland[Staff])
        cal.set(2022, 1, 17, 14, 17, 15);
        order = orderService.findOrderByOrderId(8L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 9 (Customer 7 - Rhody Lockier || Staff 1 - Chan Kong-sang[Admin])
        cal.set(2022, 1, 22, 18, 21, 13);
        order = orderService.findOrderByOrderId(9L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 10 (Customer 3 - Liu De Hua || Staff 3 - Zendaya Michelle[Staff)
        cal.set(2022, 1, 28, 18, 32, 9);
        order = orderService.findOrderByOrderId(10L);
        paymentList.add(new Payment("PayPal", cal.getTime(), order.getTotalAmount(), order));
        // ===================================================================================
        // =============================== MARCH =============================================
        // Order 11 (Customer 8 - Nerta Wedlake || Staff 3 - Zendaya Michelle[Staff])
        cal.set(2022, 2, 5, 21, 22, 21);
        order = orderService.findOrderByOrderId(11L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 12 (Customer 9 - Philipa Vinten || Staff 2 - Tom Holland[Staff])
        cal.set(2022, 2, 9, 20, 38, 1);
        order = orderService.findOrderByOrderId(12L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 13 (Customer 10 - Ryun Kembley || Staff 1 - Chan Kong-sang[Admin])
        cal.set(2022, 2, 14, 13, 16, 35);
        order = orderService.findOrderByOrderId(13L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 14 (Customer 4 - Zhong Kar Yan || Staff 2 - Tom Holland[Staff])
        cal.set(2022, 2, 14, 15, 18, 55);
        order = orderService.findOrderByOrderId(14L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 15 (Customer 5 - Wong Chung Chak || Staff 2 - Tom Holland[Staff])
        cal.set(2022, 2, 16, 18, 22, 55);
        order = orderService.findOrderByOrderId(15L);
        paymentList.add(new Payment("PayPal", cal.getTime(), order.getTotalAmount(), order));
        // Order 16 (Customer 1 - Lam Fung || Staff 4 - Andrew Garfield[Staff])
        cal.set(2022, 2, 16, 18, 18, 1);
        order = orderService.findOrderByOrderId(16L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 17 (Customer 6 - Jecho Teeney || Staff 4 - Andrew Garfield[Staff])
        cal.set(2022, 2, 28, 10, 21, 32);
        order = orderService.findOrderByOrderId(17L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 18 (Customer 2 - Ma Guo Ming || Staff 3 - Zendaya Michelle[Staff])
        cal.set(2022, 2, 30, 15, 18, 47);
        order = orderService.findOrderByOrderId(18L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // ===================================================================================
        // =============================== APRIL =============================================
        // Order 19 (Customer 9 - Philipa Vinten || Staff 2 - Tom Holland[Staff])
        cal.set(2022, 3, 1, 18, 22, 33);
        order = orderService.findOrderByOrderId(19L);
        paymentList.add(new Payment("Credit/Debit Card", cal.getTime(), order.getTotalAmount(), order));
        // Order 20 (Customer 8 - Nerta Wedlake || Staff 1 - Chan Kong-sang[Admin])
        cal.set(2022, 3, 2, 20, 33, 8);
        order = orderService.findOrderByOrderId(20L);
        paymentList.add(new Payment("PayPal", cal.getTime(), order.getTotalAmount(), order));
        return paymentList;
    }

    private List<PromotionItem> initPromoItem() {
        List<PromotionItem> promoItemList = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        // PromotionItemPK(long stfId, long prodId, Date startDate, Date endDate)
        // PromotionItem(PromotionItemPK promotionItemPK, double promoRate)
        // Promotion Item 1
        // Staff 1 - Chan Kong-sang (Admin)
        cal1.set(2022, 0, 7); // Start Date
        cal2.set(2022, 0, 25); // End Date
        promoItemList.add(new PromotionItem(new PromotionItemPK(1L, 118L, cal1.getTime(), cal2.getTime()), 0.15));
        // Promotion Item 2
        // Staff 1 - Chan Kong-sang (Admin)
        cal1.set(2022, 0, 31); // Start Date
        cal2.set(2022, 1, 16); // End Date
        promoItemList.add(new PromotionItem(new PromotionItemPK(1L, 179L, cal1.getTime(), cal2.getTime()), 0.2));
        // Promotion Item 3
        // Staff 2 - Tom Holland (Staff)
        cal1.set(2022, 1, 14); // Start Date
        cal2.set(2022, 2, 1); // End Date
        promoItemList.add(new PromotionItem(new PromotionItemPK(2L, 230L, cal1.getTime(), cal2.getTime()), 0.1));
        // Promotion Item 4
        // Staff 3 - Zendaya Michelle (Staff)
        cal1.set(2022, 1, 25); // Start Date
        cal2.set(2022, 2, 15); // End Date
        promoItemList.add(new PromotionItem(new PromotionItemPK(3L, 4L, cal1.getTime(), cal2.getTime()), 0.05));
        // Promotion Item 5
        // Staff 4 - Andrew Garfield
        cal1.set(2022, 2, 14); // Start Date
        cal2.set(2022, 2, 28); // End Date
        promoItemList.add(new PromotionItem(new PromotionItemPK(4L, 134L, cal1.getTime(), cal2.getTime()), 0.3));
        // Promotion Item 6 
        // Staff 2 - Tom Holland (Staff)
        cal1.set(2022, 2, 31);
        cal2.set(2022, 3, 15);
        promoItemList.add(new PromotionItem(new PromotionItemPK(2L, 125L, cal1.getTime(), cal2.getTime()), 0.25));
        // Promotion Item 7
        // Staff 3 - Zendaya Michelle (Staff)
        cal1.set(2022, 3, 1); // Start Date
        cal2.set(2022, 3, 25); // End Date
        promoItemList.add(new PromotionItem(new PromotionItemPK(3L, 197L, cal1.getTime(), cal2.getTime()), 0.15));

        return promoItemList;
    }

    private List<Comment> initComment() {
        List<Comment> commentList = new ArrayList<>();
        ProductService prodService = new ProductService(em);
        CustomerService custService = new CustomerService(em);
        Calendar cal = Calendar.getInstance();

        // Comment(int rating, String message, Date cmtDate, Customer custId, Product prodId)
        // Comment 1
        // Product 55 - Farmhouse Chocolate Milk
        // Customer 1 - Lam Fung
        cal.set(2022, 0, 2, 18, 15, 22);
        commentList.add(new Comment(5, "The milk is in good condition and well packed", cal.getTime(),
                custService.findCustomerByCustId(1L), prodService.findProductByProdId(55L)));
        // Comment 2 
        // Product 179 - BinBin Rice Crackers
        // Customer 1 - Lam Fung
        cal.set(2022, 0, 2, 18, 25, 30);
        commentList.add(new Comment(2, "The rice crackers is soggy and not crispy anymore", cal.getTime(),
                custService.findCustomerByCustId(1L), prodService.findProductByProdId(179L)));
        // Comment 3 
        // Product 88 - SunnySide Omega 3 30S
        // Customer 3 - Liu De Hua
        cal.set(2022, 0, 25, 20, 32, 15);
        commentList.add(new Comment(4, "The egg is fresh", cal.getTime(),
                custService.findCustomerByCustId(3L), prodService.findProductByProdId(88L)));
        // Comment 4
        // Product 107 - Prepacked Satay Chicken
        // Customer 6 - Jecho Teeney
        cal.set(2022, 2, 30, 19, 25, 58);
        commentList.add(new Comment(4, "The satay is tasty and fresh", cal.getTime(),
                custService.findCustomerByCustId(6L), prodService.findProductByProdId(107L)));
        // Comment 5
        // Product 81 - LKH Chic Century Eggs 4S
        // Customer 9 - Philipa Vinten
        cal.set(2022, 3, 3, 11, 31, 38);
        commentList.add(new Comment(5, "The egg is well packed and fresh", cal.getTime(),
                custService.findCustomerByCustId(9L), prodService.findProductByProdId(81L)));

        return commentList;
    }

    private List<CommentReply> initCmtReply() {
        List<CommentReply> cmtReplyList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        CommentService cmtService = new CommentService(em);
        StaffService stfService = new StaffService(em);

        // CommentReply(String message, Date replyDate, Comment commentId, Staff stfId)
        // Comment 2 
        // Product 179 - BinBin Rice Crackers
        // Customer 1 - Lam Fung
        // Staff 1 - Chan Kong-sang (Admin)
        cal.set(2022, 0, 3, 11, 28, 11);
        cmtReplyList.add(new CommentReply("Sorry for your bad experience, we will avoid this issue happening in the future",
                cal.getTime(), cmtService.findCommentByCmtId(2L), stfService.findStaffByStfId(1L)));
        return cmtReplyList;
    }

    private void insertCategory(PrintWriter out) {
        CategoryService categoryService = new CategoryService(em);

        // ======= This code is for insert all category into database ======
        initCategory().forEach((category) -> {
            try {
                utx.begin();
                categoryService.addCategory(category);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertSubCategory(PrintWriter out) {
        // ======= This code is for insert all sub-category into database ======
        SubCategoryService subCategoryService = new SubCategoryService(em);
        initSubCategory().forEach((subCategory) -> {
            try {
                utx.begin();
                subCategoryService.addSubCategory(subCategory);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertProduct(PrintWriter out) {
        String currentPath;
        List<String> imgPathList = new ArrayList<>();

        try {
            // Get the location of the class file of this java
            currentPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            List<String> path = new ArrayList<>(Arrays.asList(currentPath.split("/")));

            /* 
            E.g. Full Path:
            /C:/Users/Owner/Desktop/GroceriesSystem/build/web/WEB-INF/classes/InsertRecordToDB.class
             */
            for (int i = 1; i <= 5; i++) {
                // remove the last 5 pathway from the currentPath
                path.remove(path.size() - 1);
            }
            // Change the directory to the product image folder
            File prodImgFolder = new File(String.join(File.separator, path) + "/web/assets/img/ProductImg");
            listOfFiles(prodImgFolder, imgPathList);
        } catch (Exception ex) {
            Logger.getLogger(InsertRecordToDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ProductService productService = new ProductService(em);
            // Initialize all products
            List<Product> productList = initProduct();
            for (int i = 0; i < productList.size(); i++) {
                // Create an instance of File by using full path of image
                File imgFile = new File(imgPathList.get(i));

                FileInputStream fileInputStream = new FileInputStream(imgFile);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                int nRead;
                byte[] data = new byte[16384];

                while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                productList.get(i).setProdImg(buffer.toByteArray());

            }
            for (Product product : productList) {
                utx.begin();
                productService.addProduct(product);
                utx.commit();
            }
        } catch (Exception ex) {
            out.println(ex.getMessage());
        }
    }

    private void insertCustomer(PrintWriter out) {
        CustomerService custService = new CustomerService(em);
        String currentPath;
        List<String> imgPathList = new ArrayList<>();

        try {
            // Get the location of the class file of this java
            currentPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            List<String> path = new ArrayList<>(Arrays.asList(currentPath.split("/")));

            /* 
            E.g. Full Path:
            /C:/Users/Owner/Desktop/GroceriesSystem/build/web/WEB-INF/classes/InsertRecordToDB.class
             */
            for (int i = 1; i <= 5; i++) {
                // remove the last 5 pathway from the currentPath
                path.remove(path.size() - 1);
            }
            // Change the directory to the customer profile image folder
            File profileImgFolder = new File(String.join(File.separator, path) + "/web/assets/img/CustProfile");
            listOfFiles(profileImgFolder, imgPathList);
        } catch (Exception ex) {
            Logger.getLogger(InsertRecordToDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            List<Customer> customerList = initCustomer();
            for (int i = 0; i < customerList.size(); i++) {
                // Create an instance of File by using full path of image
                File imgFile = new File(imgPathList.get(i));

                FileInputStream fileInputStream = new FileInputStream(imgFile);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                int nRead;
                byte[] data = new byte[16384];

                while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                customerList.get(i).setProfileImg(buffer.toByteArray());

            }
            for (Customer customer : customerList) {
                utx.begin();
                custService.addCustomer(customer);
                utx.commit();
            }
        } catch (Exception ex) {
            out.println(ex.getMessage());
        }
    }

    private void insertCart(PrintWriter out) {
        CartService cartService = new CartService(em);

        initCart().forEach((cart) -> {
            try {
                utx.begin();
                cartService.addCart(cart);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertCartItem(PrintWriter out) {
        CartItemService cartItemService = new CartItemService(em);

        initCartItem().forEach((cartItem) -> {
            try {
                utx.begin();
                cartItemService.addCartItem(cartItem);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertStaff(PrintWriter out) {
        StaffService stfService = new StaffService(em);
        String currentPath;
        List<String> imgPathList = new ArrayList<>();

        try {
            // Get the location of the class file of this java
            currentPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            List<String> path = new ArrayList<>(Arrays.asList(currentPath.split("/")));

            /* 
            E.g. Full Path:
            /C:/Users/Owner/Desktop/GroceriesSystem/build/web/WEB-INF/classes/InsertRecordToDB.class
             */
            for (int i = 1; i <= 5; i++) {
                // remove the last 5 pathway from the currentPath
                path.remove(path.size() - 1);
            }
            // Change the directory to the staff profile image folder
            File profileImgFolder = new File(String.join(File.separator, path) + "/web/assets/img/StfProfile");
            listOfFiles(profileImgFolder, imgPathList);
        } catch (Exception ex) {
            Logger.getLogger(InsertRecordToDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            List<Staff> staffList = initStaff();
            for (int i = 0; i < staffList.size(); i++) {
                // Create an instance of File by using full path of image
                File imgFile = new File(imgPathList.get(i));

                FileInputStream fileInputStream = new FileInputStream(imgFile);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                int nRead;
                byte[] data = new byte[16384];

                while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                staffList.get(i).setProfileImg(buffer.toByteArray());

            }
            for (Staff staff : staffList) {
                utx.begin();
                stfService.addStaff(staff);
                utx.commit();
            }
        } catch (Exception ex) {
            out.println(ex.getMessage());
        }

    }

    private void insertOrder(PrintWriter out) {
        OrderService orderService = new OrderService(em);
        initOrder().forEach((order) -> {
            try {
                utx.begin();
                orderService.addOrder(order);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertOrderDetails(PrintWriter out) {
        OrderDetailsService orDetailsService = new OrderDetailsService(em);
        initOrderDetails().forEach((orderDetails) -> {
            try {
                utx.begin();
                orDetailsService.addOrderDetails(orderDetails);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertShippingDetails(PrintWriter out) {
        ShippingDetailsService shipDetailsService = new ShippingDetailsService(em);
        initShippingDetails().forEach((shippingDetails) -> {
            try {
                utx.begin();
                shipDetailsService.addShippingDetails(shippingDetails);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertFeedback(PrintWriter out) {
        FeedbackService fdbkService = new FeedbackService(em);

        initFeedback().forEach((feedback) -> {
            try {
                utx.begin();
                fdbkService.addFeedback(feedback);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertFdbkReply(PrintWriter out) {
        FeedbackReplyService fdbkReplyService = new FeedbackReplyService(em);
        initFdbkReply().forEach((reply) -> {
            try {
                utx.begin();
                fdbkReplyService.addFeedbackReply(reply);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertPayment(PrintWriter out) {
        PaymentService paymentService = new PaymentService(em);
        initPayment().forEach((payment) -> {
            try {
                utx.begin();
                paymentService.addPayment(payment);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertPromoItem(PrintWriter out) {
        PromotionItemService promoItemService = new PromotionItemService(em);
        initPromoItem().forEach((promoItem) -> {
            try {
                utx.begin();
                promoItemService.addPromotionItem(promoItem);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertComment(PrintWriter out) {
        CommentService cmtService = new CommentService(em);

        initComment().forEach((comment) -> {
            try {
                utx.begin();
                cmtService.addComment(comment);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    private void insertCmtReply(PrintWriter out) {
        CommentReplyService cmtReplyService = new CommentReplyService(em);

        initCmtReply().forEach((reply) -> {
            try {
                utx.begin();
                cmtReplyService.addCommentReply(reply);
                utx.commit();
            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
        });
    }

    // This method is to list all files in a directory recursively
    public void listOfFiles(File dirPath, List<String> imgPathList) throws IOException {
//        PrintWriter out = response.getWriter();
        File filesList[] = dirPath.listFiles();

        for (File file : filesList) {
            if (file.isDirectory()) {
                // If is directory then will invoke itself again
                listOfFiles(file, imgPathList);
            } else {
                // Add the full path of image into the list
                imgPathList.add(file.getPath());

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