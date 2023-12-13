/**
 *
 * @author New Yee Hao, Joey Kok Yen Ni
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
@Table(name = "PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
    , @NamedQuery(name = "Payment.findByPayId", query = "SELECT p FROM Payment p WHERE p.payId = :payId")
    , @NamedQuery(name = "Payment.findByOrderId", query = "SELECT p FROM Payment p WHERE p.orderId = :orderId")
    , @NamedQuery(name = "Payment.findByPayType", query = "SELECT p FROM Payment p WHERE p.payType = :payType")
    , @NamedQuery(name = "Payment.findByPayDate", query = "SELECT p FROM Payment p WHERE p.payDate = :payDate")
    , @NamedQuery(name = "Payment.findByPayAmount", query = "SELECT p FROM Payment p WHERE p.payAmount = :payAmount")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PAYID")
    private Long payId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PAYTYPE")
    private String payType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYAMOUNT")
    private double payAmount;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @OneToOne(optional = false)
    private Order orderId;

    public Payment() {
    }

    public Payment(Long payId) {
        this.payId = payId;
    }

    public Payment(Long payId, String payType, Date payDate, double payAmount) {
        this.payId = payId;
        this.payType = payType;
        this.payDate = payDate;
        this.payAmount = payAmount;
    }

    public Payment(String payType, Date payDate, double payAmount, Order orderId) {
        this.payType = payType;
        this.payDate = payDate;
        this.payAmount = payAmount;
        this.orderId = orderId;
    }

    public Payment(Long payId, String payType, Date payDate, double payAmount, Order orderId) {
        this.payId = payId;
        this.payType = payType;
        this.payDate = payDate;
        this.payAmount = payAmount;
        this.orderId = orderId;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
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
        hash += (payId != null ? payId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.payId == null && other.payId != null) || (this.payId != null && !this.payId.equals(other.payId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Payment{" + "payId=" + payId + '}';
    }

    
    
    
    

    
    


}
