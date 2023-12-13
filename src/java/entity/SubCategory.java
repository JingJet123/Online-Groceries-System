/**
 *
 * @author Chuah Shee Yeap
 */

package entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "SUBCATEGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubCategory.findAll", query = "SELECT s FROM SubCategory s")
    , @NamedQuery(name = "SubCategory.findBySubCategoryId", query = "SELECT s FROM SubCategory s WHERE s.subCategoryId = :subCategoryId")
    , @NamedQuery(name = "SubCategory.findBySubCategoryName", query = "SELECT s FROM SubCategory s WHERE s.subCategoryName = :subCategoryName")
    , @NamedQuery(name = "SubCategory.findByIsDeleted", query = "SELECT s FROM SubCategory s WHERE s.isDeleted = :isDeleted")})
public class SubCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUBCATEGORYID")
    private Long subCategoryId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SUBCATEGORYNAME")
    private String subCategoryName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isDeleted = false;
    @JoinColumn(name = "CATEGORYID", referencedColumnName = "CATEGORYID")
    @ManyToOne(optional = false)
    private Category categoryId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subCategoryId")
    private List<Product> productList;

    public SubCategory() {
    }

    public SubCategory(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public SubCategory(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public SubCategory(String subCategoryName, Category categoryId) {
        this.subCategoryName = subCategoryName;
        this.categoryId = categoryId;
    }

    public SubCategory(Long subCategoryId, String subCategoryName, Boolean isDeleted) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.isDeleted = isDeleted;
    }

    public SubCategory(Long subCategoryId, String subCategoryName, Boolean isDeleted, Category categoryId) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.isDeleted = isDeleted;
        this.categoryId = categoryId;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    @XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    
    public static Comparator<SubCategory> SubCategIdComparator = (SubCategory sc1, SubCategory sc2) -> {
        return (int) sc1.getSubCategoryId().compareTo(sc2.getSubCategoryId());
    };
    
    public static Comparator<SubCategory> SubCategNameComparator = (SubCategory sc1, SubCategory sc2) -> {
        String subCategName1 = sc1.getSubCategoryName().toUpperCase();
        String subCategName2 = sc2.getSubCategoryName().toUpperCase();
        return subCategName1.compareTo(subCategName2);
    };
    
    public static Comparator<SubCategory> CategNameComparator = (SubCategory sc1, SubCategory sc2) -> {
        String categName1 = sc1.getCategoryId().getCategoryName().toUpperCase();
        String categName2 = sc2.getCategoryId().getCategoryName().toUpperCase();
        return categName1.compareTo(categName2);
    };

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subCategoryId != null ? subCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubCategory)) {
            return false;
        }
        SubCategory other = (SubCategory) object;
        if ((this.subCategoryId == null && other.subCategoryId != null) || (this.subCategoryId != null && !this.subCategoryId.equals(other.subCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(subCategoryId);
    }

}
