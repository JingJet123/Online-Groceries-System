/**
 *
 * @author Joey Kok Yen Ni
 */

package entity;

import java.io.Serializable;
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
@Table(name = "ORDERDETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderDetails.findAll", query = "SELECT o FROM OrderDetails o")
    , @NamedQuery(name = "OrderDetails.findByOrderId", query = "SELECT o FROM OrderDetails o WHERE o.orderDetailsPK.orderId = :orderId")
    , @NamedQuery(name = "OrderDetails.findByProdId", query = "SELECT o FROM OrderDetails o WHERE o.orderDetailsPK.prodId = :prodId")
    , @NamedQuery(name = "OrderDetails.findByQuantity", query = "SELECT o FROM OrderDetails o WHERE o.quantity = :quantity")
    , @NamedQuery(name = "OrderDetails.findBySubTotal", query = "SELECT o FROM OrderDetails o WHERE o.subTotal = :subTotal")
    , @NamedQuery(name = "OrderDetails.findByCompsId", query = "SELECT o FROM OrderDetails o WHERE o.orderDetailsPK.orderId = :orderId AND o.orderDetailsPK.prodId = :prodId")})
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderDetailsPK orderDetailsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUBTOTAL")
    private double subTotal;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Order order;
    @JoinColumn(name = "PRODID", referencedColumnName = "PRODID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    

    public OrderDetails() {
    }

    public OrderDetails(OrderDetailsPK orderDetailsPK) {
        this.orderDetailsPK = orderDetailsPK;
    }

    public OrderDetails(OrderDetailsPK orderDetailsPK, int quantity, double subTotal) {
        this.orderDetailsPK = orderDetailsPK;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public OrderDetails(long orderId, long prodId) {
        this.orderDetailsPK = new OrderDetailsPK(orderId, prodId);
    }

    public OrderDetailsPK getOrderDetailsPK() {
        return orderDetailsPK;
    }

    public void setOrderDetailsPK(OrderDetailsPK orderDetailsPK) {
        this.orderDetailsPK = orderDetailsPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
    
    public double calcSubTotal(){
        return this.product.getUnitPrice() * this.quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderDetailsPK != null ? orderDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDetails)) {
            return false;
        }
        OrderDetails other = (OrderDetails) object;
        if ((this.orderDetailsPK == null && other.orderDetailsPK != null) || (this.orderDetailsPK != null && !this.orderDetailsPK.equals(other.orderDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderDetails{" + "orderDetailsPK=" + orderDetailsPK + ", quantity=" + quantity + ", subTotal=" + subTotal + ", order=" + order + ", product=" + product + '}';
    }

    

}
