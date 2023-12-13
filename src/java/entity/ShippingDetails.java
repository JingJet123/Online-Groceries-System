/**
 *
 * @author Lee Jia Jie
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "SHIPPINGDETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShippingDetails.findAll", query = "SELECT s FROM ShippingDetails s")
    , @NamedQuery(name = "ShippingDetails.findByShippingId", query = "SELECT s FROM ShippingDetails s WHERE s.shippingId = :shippingId")
    , @NamedQuery(name = "ShippingDetails.findByOrderId", query = "SELECT s FROM ShippingDetails s WHERE s.orderId = :orderId")
    , @NamedQuery(name = "ShippingDetails.findByShippingDate", query = "SELECT s FROM ShippingDetails s WHERE s.shippingDate = :shippingDate")
    , @NamedQuery(name = "ShippingDetails.findByShippingAddress", query = "SELECT s FROM ShippingDetails s WHERE s.shippingAddress = :shippingAddress")})
public class ShippingDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SHIPPINGID")
    private Long shippingId;
    @Basic(optional = false)
    @Column(name = "SHIPPINGDATE")
    @Temporal(TemporalType.DATE)
    private Date shippingDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 180)
    @Column(name = "SHIPPINGADDRESS")
    private String shippingAddress;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @OneToOne(optional = false)
    private Order orderId;

    public ShippingDetails() {
    }

    public ShippingDetails(Long shippingId) {
        this.shippingId = shippingId;
    }

    public ShippingDetails(Date shippingDate, String shippingAddress, Order orderId) {
        this.shippingDate = shippingDate;
        this.shippingAddress = shippingAddress;
        this.orderId = orderId;
    }
    
    public ShippingDetails(String shippingAddress, Order orderId) {
        this.shippingAddress = shippingAddress;
        this.orderId = orderId;
    }
    
    public ShippingDetails(Long shippingId, Date shippingDate, String shippingAddress) {
        this.shippingId = shippingId;
        this.shippingDate = shippingDate;
        this.shippingAddress = shippingAddress;
    }

    public ShippingDetails(Long shippingId, Date shippingDate, String shippingAddress, Order orderId) {
        this.shippingId = shippingId;
        this.shippingDate = shippingDate;
        this.shippingAddress = shippingAddress;
        this.orderId = orderId;
    }

    public Long getShippingId() {
        return shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shippingId != null ? shippingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShippingDetails)) {
            return false;
        }
        ShippingDetails other = (ShippingDetails) object;
        if ((this.shippingId == null && other.shippingId != null) || (this.shippingId != null && !this.shippingId.equals(other.shippingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Shippingdetails[ shippingid=" + shippingId + " ]";
    }
    
}
