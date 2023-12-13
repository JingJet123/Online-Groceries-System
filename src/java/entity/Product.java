/**
 *
 * @author Chuah Shee Yeap
 */

package entity;

import java.io.Serializable;
import java.util.*;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import static java.lang.Double.compare;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import service.PromotionItemService;

@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findByProdId", query = "SELECT p FROM Product p WHERE p.prodId = :prodId")
    , @NamedQuery(name = "Product.findByProdName", query = "SELECT p FROM Product p WHERE p.prodName = :prodName")
    , @NamedQuery(name = "Product.findByUnitPrice", query = "SELECT p FROM Product p WHERE p.unitPrice = :unitPrice")
    , @NamedQuery(name = "Product.findByStock", query = "SELECT p FROM Product p WHERE p.stock = :stock")
    , @NamedQuery(name = "Product.findBySupplier", query = "SELECT p FROM Product p WHERE p.supplier = :supplier")
    , @NamedQuery(name = "Product.findByIsDeleted", query = "SELECT p FROM Product p WHERE p.isDeleted = :isDeleted")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRODID")
    private Long prodId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 180)
    @Column(name = "PRODNAME")
    private String prodName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UNITPRICE")
    private double unitPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STOCK")
    private int stock;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SUPPLIER")
    private String supplier;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "PRODIMG")
    private byte[] prodImg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isDeleted = false;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<CartItem> cartItemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodId")
    private List<Comment> commentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<PromotionItem> promotionItemList;
    @JoinColumn(name = "SUBCATEGORYID", referencedColumnName = "SUBCATEGORYID")
    @ManyToOne(optional = false)
    private SubCategory subCategoryId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<OrderDetails> orderDetailsList;

    public Product() {
    }

    public Product(Long prodId) {
        this.prodId = prodId;
    }

    public Product(String prodName, double unitPrice, int stock, String supplier, SubCategory subCategoryId) {
        this.prodName = prodName;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.supplier = supplier;
        this.subCategoryId = subCategoryId;
    }

    public Product(String prodName, double unitPrice, int stock, String supplier, byte[] prodImg) {
        this.prodName = prodName;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.supplier = supplier;
        this.prodImg = prodImg;
    }

    public Product(Long prodId, String prodName, double unitPrice, int stock, String supplier, byte[] prodImg, Boolean isDeleted) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.supplier = supplier;
        this.prodImg = prodImg;
        this.isDeleted = isDeleted;
    }

    public Product(String prodName, double unitPrice, int stock, String supplier, byte[] prodImg, SubCategory subCategoryId) {
        this.prodName = prodName;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.supplier = supplier;
        this.prodImg = prodImg;
        this.subCategoryId = subCategoryId;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public byte[] getProdImg() {
        return prodImg;
    }

    public void setProdImg(byte[] prodImg) {
        this.prodImg = prodImg;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @XmlTransient
    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @XmlTransient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @XmlTransient
    public List<PromotionItem> getPromotionItemList() {
        return promotionItemList;
    }

    public void setPromotionItemList(List<PromotionItem> promotionItemList) {
        this.promotionItemList = promotionItemList;
    }

    public SubCategory getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(SubCategory subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    @XmlTransient
    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public String encodeImageToString() {
        return Base64.getEncoder().encodeToString(this.prodImg);
    }

    public List<Product> randomProducts(List<Product> prodList) {
        int min = 0, max = prodList.size();
        List<Product> randProdList = new ArrayList<Product>();
        if (prodList.size() >= 10) {
            for (int i = 0; i < 10; i++) {
                if(prodList.get(i).getIsDeleted()) {
                    --i;
                } else {
                    int rand = (int) (Math.random() * (max - min + 1) + min);
                    randProdList.add(prodList.get(rand));

                    if (rand < prodList.size()) {
                        randProdList.add(prodList.get(rand + 1));
                    }
                }
            }
        }
        return randProdList;
    }

    public List<Product> findAllProductinPromo(List<PromotionItem> piList) {
        Date today = Calendar.getInstance().getTime();
        List<Product> prodsInPromo = new ArrayList<Product>();
        for (PromotionItem pi : piList) {
            if ((pi.getPromotionItemPK().getStartDate().compareTo(today) == 0 || pi.getPromotionItemPK().getStartDate().before(today))
                    && pi.getPromotionItemPK().getEndDate().after(today)) {
                prodsInPromo.add(pi.getProduct());
            }
        }
        return prodsInPromo;
    }

    public double getOfferPrice(double offerRate) {
        return this.unitPrice - (this.unitPrice * offerRate);
    }

    public int getItemSold(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int itemSold = 0;
        try {
            startDate = sdf.parse(sdf.format(startDate));
            endDate = sdf.parse(sdf.format(endDate));
            for (OrderDetails orderDetails : this.orderDetailsList) {
                if (!orderDetails.getOrder().getOrderStatus().equals("Cancelled")) {
                    Date orderDate = sdf.parse(sdf.format(orderDetails.getOrder().getOrderDate()));
                    if ((orderDate.after(startDate) || orderDate.compareTo(startDate) == 0)
                            && (orderDate.before(endDate) || orderDate.compareTo(endDate) == 0)) {
                        if (orderDetails.getProduct().getProdId().equals(this.prodId)) {
                            itemSold += orderDetails.getQuantity();
                        }
                    }
                }

            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return itemSold;
    }

    public int getItemSoldOfACateg(Date startDate, Date endDate, String categName) {
        int itemSold = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = sdf.parse(sdf.format(startDate));
            endDate = sdf.parse(sdf.format(endDate));
            for (OrderDetails orderDetails : this.orderDetailsList) {
                if (!orderDetails.getOrder().getOrderStatus().equals("Cancelled")) {
                    Date orderDate = sdf.parse(sdf.format(orderDetails.getOrder().getOrderDate()));
                    if ((orderDate.after(startDate) || orderDate.compareTo(startDate) == 0)
                            && (orderDate.before(endDate) || orderDate.compareTo(endDate) == 0)) {
                        if (orderDetails.getProduct().getProdId().equals(this.prodId)
                                && orderDetails.getProduct().getSubCategoryId().getCategoryId().getCategoryName().equals(categName)) {
                            itemSold += orderDetails.getQuantity();
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return itemSold;
    }

    public static Comparator<Product> ProdNameComparator = (Product p1, Product p2) -> {
        String prodName1 = p1.getProdName().toUpperCase();
        String prodName2 = p2.getProdName().toUpperCase();
        return prodName1.compareTo(prodName2);
    };

    public static Comparator<Product> ProdSupplierComparator = (Product p1, Product p2) -> {
        String supplierName1 = p1.getSupplier().toUpperCase();
        String supplierName2 = p2.getSupplier().toUpperCase();
        return supplierName1.compareTo(supplierName2);
    };

    public static Comparator<Product> ProdPriceComparator = (Product p1, Product p2) -> {
        double preciseCheck = p1.getUnitPrice() - p2.getUnitPrice();
        //If direct compare and the subtraction will be cast to int (round by), then the result incorrect
        if (preciseCheck > 0.00001) {
            return 1;
        }
        if (preciseCheck < -0.00001) {
            return -1;
        }
        return 0;
    };

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodId != null ? prodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.prodId == null && other.prodId != null) || (this.prodId != null && !this.prodId.equals(other.prodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "prodId=" + prodId + ", prodName=" + prodName + ", unitPrice=" + unitPrice + ", stock=" + stock + ", supplier=" + supplier + ", isDeleted=" + isDeleted + '}';
    }

}
