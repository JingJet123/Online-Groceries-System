/**
 *
 * @author Lee Jia Jie
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PROMOTIONITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PromotionItem.findAll", query = "SELECT p FROM PromotionItem p")
    , @NamedQuery(name = "PromotionItem.findByStfId", query = "SELECT p FROM PromotionItem p WHERE p.promotionItemPK.stfId = :stfId")
    , @NamedQuery(name = "PromotionItem.findByProdId", query = "SELECT p FROM PromotionItem p WHERE p.promotionItemPK.prodId = :prodId")
    , @NamedQuery(name = "PromotionItem.findByStartDate", query = "SELECT p FROM PromotionItem p WHERE p.promotionItemPK.startDate = :startDate")
    , @NamedQuery(name = "PromotionItem.findByValidDate", query = "SELECT p FROM PromotionItem p WHERE p.promotionItemPK.endDate > :todayDate AND p.promotionItemPK.startDate <= :todayDate")
    , @NamedQuery(name = "PromotionItem.findByEndDate", query = "SELECT p FROM PromotionItem p WHERE p.promotionItemPK.endDate = :endDate")
    , @NamedQuery(name = "PromotionItem.findByPromoRate", query = "SELECT p FROM PromotionItem p WHERE p.promoRate = :promoRate")
    , @NamedQuery(name = "PromotionItem.findByCompsId", query = "SELECT p FROM PromotionItem p WHERE p.promotionItemPK.stfId = :stfId AND p.promotionItemPK.prodId = :prodId AND p.promotionItemPK.startDate = :startDate AND p.promotionItemPK.endDate = :endDate")})
public class PromotionItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PromotionItemPK promotionItemPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROMORATE")
    private double promoRate;
    @JoinColumn(name = "PRODID", referencedColumnName = "PRODID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "STFID", referencedColumnName = "STFID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Staff staff;

    public PromotionItem() {
    }

    public PromotionItem(PromotionItemPK promotionItemPK) {
        this.promotionItemPK = promotionItemPK;
    }

    public PromotionItem(PromotionItemPK promotionItemPK, double promoRate) {
        this.promotionItemPK = promotionItemPK;
        this.promoRate = promoRate;
    }

    public PromotionItem(long stfId, long prodId, Date startDate, Date endDate) {
        this.promotionItemPK = new PromotionItemPK(stfId, prodId, startDate, endDate);
    }

    public PromotionItemPK getPromotionItemPK() {
        return promotionItemPK;
    }

    public void setPromotionItemPK(PromotionItemPK promotionItemPK) {
        this.promotionItemPK = promotionItemPK;
    }

    public double getPromoRate() {
        return promoRate;
    }

    public void setPromoRate(double promoRate) {
        this.promoRate = promoRate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    
    public double getPriceAfterDiscount(){
        return this.product.getUnitPrice() - calcDiscountAmt();
    }
    
    public double calcDiscountAmt(){
        return this.product.getUnitPrice() * this.promoRate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotionItemPK != null ? promotionItemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromotionItem)) {
            return false;
        }
        PromotionItem other = (PromotionItem) object;
        if ((this.promotionItemPK == null && other.promotionItemPK != null) || (this.promotionItemPK != null && !this.promotionItemPK.equals(other.promotionItemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Promotionitem[ promotionitemPK=" + promotionItemPK + " ]";
    }

}
