/**
 *
 * @author Joey Kok Yen Ni
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class OrderDetailsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERID")
    private long orderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODID")
    private long prodId;

    public OrderDetailsPK() {
    }

    public OrderDetailsPK(long orderId, long prodId) {
        this.orderId = orderId;
        this.prodId = prodId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProdId() {
        return prodId;
    }

    public void setProdId(long prodId) {
        this.prodId = prodId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderId;
        hash += (int) prodId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDetailsPK)) {
            return false;
        }
        OrderDetailsPK other = (OrderDetailsPK) object;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (this.prodId != other.prodId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderdetailsPK[ orderid=" + orderId + ", prodid=" + prodId + " ]";
    }

}
