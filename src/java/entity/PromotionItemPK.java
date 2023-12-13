/**
 *
 * @author Lee Jia Jie
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Embeddable
public class PromotionItemPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "STFID")
    private long stfId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODID")
    private long prodId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public PromotionItemPK() {
    }

    public PromotionItemPK(long prodId) {
        this.prodId = prodId;
    }

    public PromotionItemPK(long stfId, long prodId, Date startDate, Date endDate) {
        this.stfId = stfId;
        this.prodId = prodId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getStfId() {
        return stfId;
    }

    public void setStfId(long stfId) {
        this.stfId = stfId;
    }

    public long getProdId() {
        return prodId;
    }

    public void setProdId(long prodId) {
        this.prodId = prodId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) stfId;
        hash += (int) prodId;
        hash += (startDate != null ? startDate.hashCode() : 0);
        hash += (endDate != null ? endDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromotionItemPK)) {
            return false;
        }
        PromotionItemPK other = (PromotionItemPK) object;
        if (this.stfId != other.stfId) {
            return false;
        }
        if (this.prodId != other.prodId) {
            return false;
        }
        if ((this.startDate == null && other.startDate != null) || (this.startDate != null && !this.startDate.equals(other.startDate))) {
            return false;
        }
        if ((this.endDate == null && other.endDate != null) || (this.endDate != null && !this.endDate.equals(other.endDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PromotionitemPK[ stfid=" + stfId + ", prodid=" + prodId + ", startdate=" + startDate + ", enddate=" + endDate + " ]";
    }

}
