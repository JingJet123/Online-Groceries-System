/**
 *
 * @author Joey Kok Yen Ni
 */
package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name = "Orders")
@Table(name = "ORDERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o")
    , @NamedQuery(name = "Orders.findByOrderId", query = "SELECT o FROM Orders o WHERE o.orderId = :orderId")
    , @NamedQuery(name = "Orders.findByOrderDate", query = "SELECT o FROM Orders o WHERE o.orderDate = :orderDate")
    , @NamedQuery(name = "Orders.findByOrderStatus", query = "SELECT o FROM Orders o WHERE o.orderStatus = :orderStatus")
    , @NamedQuery(name = "Orders.findByTotalAmount", query = "SELECT o FROM Orders o WHERE o.totalAmount = :totalAmount")
    , @NamedQuery(name = "Orders.findByOrderStatusForACust", query = "SELECT o FROM Orders o WHERE o.orderStatus = :orderStatus AND o.custId = :custId")
    , @NamedQuery(name = "Orders.findByCustId", query = "SELECT o FROM Orders o WHERE o.custId = :custId")})
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORDERID")
    private Long orderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ORDERSTATUS")
    private String orderStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTALAMOUNT")
    private double totalAmount;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Payment payment;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orderId")
    private ShippingDetails shippingDetails;
    @JoinColumn(name = "CUSTID", referencedColumnName = "CUSTID")
    @ManyToOne(optional = false)
    private Customer custId;
    @JoinColumn(name = "STFID", referencedColumnName = "STFID")
    @ManyToOne(optional = false)
    private Staff stfId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetails> orderDetailsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<Feedback> feedbackList;

    public Order() {
    }

    public Order(Long orderId) {
        this.orderId = orderId;
    }

    public Order(Date orderDate, String orderStatus, double totalAmount) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
    }

    public Order(Long orderId, Date orderDate, String orderStatus, double totalAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
    }

    public Order(Date orderDate, String orderStatus, double totalAmount, Customer custId) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.custId = custId;
        this.stfId = stfId;
    }

    public Order(Date orderDate, String orderStatus, double totalAmount, Customer custId, Staff stfId) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.custId = custId;
        this.stfId = stfId;
    }

    public Order(Long orderId, Date orderDate, String orderStatus, double totalAmount, Customer custId, Staff stfId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.custId = custId;
        this.stfId = stfId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetails shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public Customer getCustId() {
        return custId;
    }

    public void setCustId(Customer custId) {
        this.custId = custId;
    }

    public Staff getStfId() {
        return stfId;
    }

    public void setStfId(Staff stfId) {
        this.stfId = stfId;
    }

    @XmlTransient
    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public Calendar[] getEstimatedDeliveryDates() {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        String strDate = formatter.format(orderDate);

        if (orderDate != null) {

            Calendar cal[] = {Calendar.getInstance(), Calendar.getInstance()};
            cal[0].setTime(orderDate);
            cal[0].add(Calendar.DATE, 2);
            cal[1].setTime(orderDate);
            cal[1].add(Calendar.DATE, 4);
            return cal;
        } else {
            Calendar cal[] = {Calendar.getInstance(), Calendar.getInstance()};
            cal[0].add(Calendar.DATE, 2);
            cal[1].add(Calendar.DATE, 4);
            return cal;
        }
    }

    public String displayDate(Calendar c) {
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        return String.format("%d/%d/%d", day, month, year);
    }

    @XmlTransient
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + '}';
    }

}
