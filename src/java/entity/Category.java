/**
 *
 * @author Chuah Shee Yeap
 */

package entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "CATEGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
    , @NamedQuery(name = "Category.findByCategoryId", query = "SELECT c FROM Category c WHERE c.categoryId = :categoryId")
    , @NamedQuery(name = "Category.findByCategoryName", query = "SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    , @NamedQuery(name = "Category.findByIsDeleted", query = "SELECT c FROM Category c WHERE c.isDeleted = :isDeleted")})
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CATEGORYID")
    private Long categoryId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CATEGORYNAME")
    private String categoryName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isDeleted = false;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryId")
    private List<SubCategory> subCategoryList;

    public Category() {
    }

    public Category(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(Long categoryid, String categoryname, Boolean isdeleted) {
        this.categoryId = categoryid;
        this.categoryName = categoryname;
        this.isDeleted = isdeleted;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @XmlTransient
    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public static Comparator<Category> CategIdComparator = (Category c1, Category c2) -> {
        return (int) c1.getCategoryId().compareTo(c2.getCategoryId());
    };

    public static Comparator<Category> CategNameComparator = (Category c1, Category c2) -> {
        String categName1 = c1.getCategoryName().toUpperCase();
        String categName2 = c2.getCategoryName().toUpperCase();
        return categName1.compareTo(categName2);
    };

    public double totalSalesOfACateg(Long categId, Date startDate, Date endDate) {
        double totalSales = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = sdf.parse(sdf.format(startDate));
            endDate = sdf.parse(sdf.format(endDate));
            for (SubCategory subCateg : subCategoryList) {
                if (subCateg.getCategoryId().getCategoryId().equals(categId)) {
                    for (Product product : subCateg.getProductList()) {
                        for (OrderDetails orderDetails : product.getOrderDetailsList()) {
                            Date orderDate = sdf.parse(sdf.format(orderDetails.getOrder().getOrderDate()));
                            if (!orderDetails.getOrder().getOrderStatus().equals("Cancelled")) {
                                if ((orderDate.after(startDate) || orderDate.compareTo(startDate) == 0)
                                        && (orderDate.before(endDate) || orderDate.compareTo(endDate) == 0)) {
                                    if (orderDetails.getProduct().getSubCategoryId().getCategoryId().getCategoryId().equals(categId)) {
                                        totalSales += orderDetails.getSubTotal();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return totalSales;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Category[ categoryid=" + categoryId + " ]";
    }

}
