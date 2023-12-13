/**
 *
 * @author New Yee Hao, Joey Kok Yen Ni
 */

package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "CUSTOMER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
    , @NamedQuery(name = "Customer.findByCustId", query = "SELECT c FROM Customer c WHERE c.custId = :custId")
    , @NamedQuery(name = "Customer.findByUsername", query = "SELECT c FROM Customer c WHERE c.username = :username")
    , @NamedQuery(name = "Customer.findByPassword", query = "SELECT c FROM Customer c WHERE c.password = :password")
    , @NamedQuery(name = "Customer.findByCustName", query = "SELECT c FROM Customer c WHERE c.custName = :custName")
    , @NamedQuery(name = "Customer.findByCustContact", query = "SELECT c FROM Customer c WHERE c.custContact = :custContact")
    , @NamedQuery(name = "Customer.findByCustEmail", query = "SELECT c FROM Customer c WHERE c.custEmail = :custEmail")
    , @NamedQuery(name = "Customer.findByCustAddress", query = "SELECT c FROM Customer c WHERE c.custAddress = :custAddress")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CUSTID")
    private Long custId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CUSTNAME")
    private String custName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CUSTCONTACT")
    private String custContact;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CUSTEMAIL")
    private String custEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "CUSTADDRESS")
    private String custAddress;
    @Basic(optional = false)
    @Lob
    @Column(name = "PROFILEIMG")
    private byte[] profileImg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "custId")
    private List<Comment> commentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "custId")
    private List<Order> orderList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "custId")
    private List<Feedback> feedbackList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "custId")
    private Cart cart;

    public Customer() {
    }

    public Customer(Long custId) {
        this.custId = custId;
    }

    public Customer(String username, String password, String custName, String custContact, String custEmail, String custAddress) {
        this.username = username;
        this.password = password;
        this.custName = custName;
        this.custContact = custContact;
        this.custEmail = custEmail;
        this.custAddress = custAddress;
    }

    public Customer(String username, String password, String custName, String custContact, String custEmail,
            String custAddress, byte[] profileImg) {
        this.username = username;
        this.password = password;
        this.custName = custName;
        this.custContact = custContact;
        this.custEmail = custEmail;
        this.custAddress = custAddress;
        this.profileImg = profileImg;
    }

    public Customer(Long custId, String username, String password, String custName, String custContact,
            String custEmail, String custAddress, byte[] profileImg) {
        this.custId = custId;
        this.username = username;
        this.password = password;
        this.custName = custName;
        this.custContact = custContact;
        this.custEmail = custEmail;
        this.custAddress = custAddress;
        this.profileImg = profileImg;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustContact() {
        return custContact;
    }

    public void setCustContact(String custContact) {
        this.custContact = custContact;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public String encodeImageToString() {
        return Base64.getEncoder().encodeToString(this.profileImg);
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    @XmlTransient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @XmlTransient
    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @XmlTransient
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getTotalOrderMade() {
        int ttlOrderMade = 0;
        ttlOrderMade = this.orderList.stream().filter((order) -> (!order.getOrderStatus().equals("Cancelled"))).map((_item) -> 1).reduce(ttlOrderMade, Integer::sum);
        return ttlOrderMade;
    }

    public boolean isCommentable(List<OrderDetails> allOrderDetails, Customer customer, Product viewingProduct, List<Comment> commentList) {
        boolean hasOrdered = false, commentable = true;
        //Get All Ordered Order Details for current customer
        List<OrderDetails> currentCustAllOrderDetails = new ArrayList<OrderDetails>();
        for (OrderDetails od : allOrderDetails) {
            if (od.getOrder().getCustId().equals(customer)) {
                currentCustAllOrderDetails.add(od);
            }
        }
        Date lastOrderDate = null;
        for (OrderDetails od : currentCustAllOrderDetails) { //Check if customer has ordered the product before
            if (od.getProduct().equals(viewingProduct) && od.getOrder().getOrderStatus().equals("Delivered")) {
                hasOrdered = true;
                lastOrderDate = od.getOrder().getOrderDate();
            }
        }
        //Check if customer already commented after the last order placed
        for (Comment c : commentList) {
            if (c.getCustId().equals(customer) && c.getCmtDate().after(lastOrderDate)) {
                commentable = false;
            }
        }

        return (hasOrdered && commentable);

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (custId != null ? custId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.custId == null && other.custId != null) || (this.custId != null && !this.custId.equals(other.custId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Customer{" + "custName=" + custName + '}';
    }

}
