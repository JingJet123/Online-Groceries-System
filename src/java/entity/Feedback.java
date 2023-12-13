/**
 *
 * @author Ng Eason, Lee Jing Jet
 */


package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "FEEDBACK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feedback.findAll", query = "SELECT f FROM Feedback f")
    , @NamedQuery(name = "Feedback.findByFeedbackId", query = "SELECT f FROM Feedback f WHERE f.feedbackId = :feedbackId")
    , @NamedQuery(name = "Feedback.findByCategory", query = "SELECT f FROM Feedback f WHERE f.category = :category")
    , @NamedQuery(name = "Feedback.findByMessage", query = "SELECT f FROM Feedback f WHERE f.message = :message")
    , @NamedQuery(name = "Feedback.findByFdbkDate", query = "SELECT f FROM Feedback f WHERE f.fdbkDate = :fdbkDate")})
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FEEDBACKID")
    private Long feedbackId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CATEGORY")
    private String category;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "MESSAGE")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FDBKDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fdbkDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedbackId")
    private List<FeedbackReply> feedbackReplyList;
    @JoinColumn(name = "CUSTID", referencedColumnName = "CUSTID")
    @ManyToOne(optional = false)
    private Customer custId;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @ManyToOne(optional = false)
    private Order orderId;

    public Feedback() {
    }

    public Feedback(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Feedback(Long feedbackId, String category, String message, Date fdbkDate) {
        this.feedbackId = feedbackId;
        this.category = category;
        this.message = message;
        this.fdbkDate = fdbkDate;
    }

    public Feedback(String category, String message, Date fdbkDate, Customer custId, Order orderId) {
        this.category = category;
        this.message = message;
        this.fdbkDate = fdbkDate;
        this.custId = custId;
        this.orderId = orderId;
    }

    public Feedback(Long feedbackId, String category, String message, Date fdbkDate, Customer custId, Order orderId) {
        this.feedbackId = feedbackId;
        this.category = category;
        this.message = message;
        this.fdbkDate = fdbkDate;
        this.custId = custId;
        this.orderId = orderId;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getFdbkDate() {
        return fdbkDate;
    }

    public void setFdbkDate(Date fdbkDate) {
        this.fdbkDate = fdbkDate;
    }

    @XmlTransient
    public List<FeedbackReply> getFeedbackReplyList() {
        return feedbackReplyList;
    }

    public void setFeedbackReplyList(List<FeedbackReply> feedbackReplyList) {
        this.feedbackReplyList = feedbackReplyList;
    }

    public Customer getCustId() {
        return custId;
    }

    public void setCustId(Customer custId) {
        this.custId = custId;
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
        hash += (feedbackId != null ? feedbackId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feedback)) {
            return false;
        }
        Feedback other = (Feedback) object;
        if ((this.feedbackId == null && other.feedbackId != null) || (this.feedbackId != null && !this.feedbackId.equals(other.feedbackId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Feedback[ feedbackid=" + feedbackId + " ]";
    }

    public boolean addFeedback(Feedback feedback) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
