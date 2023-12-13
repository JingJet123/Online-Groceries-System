/**
 *
 * @author Joey Kok Yen Ni
 */

package entity;

import java.io.Serializable;
import java.util.*;
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
@Table(name = "CARTITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CartItem.findAll", query = "SELECT c FROM CartItem c")
    , @NamedQuery(name = "CartItem.findByCartId", query = "SELECT c FROM CartItem c WHERE c.cartItemPK.cartId = :cartId")
    , @NamedQuery(name = "CartItem.findByProdId", query = "SELECT c FROM CartItem c WHERE c.cartItemPK.prodId = :prodId")
    , @NamedQuery(name = "CartItem.findByQuantity", query = "SELECT c FROM CartItem c WHERE c.quantity = :quantity")
    , @NamedQuery(name = "CartItem.findByCompsId", query = "SELECT c FROM CartItem c WHERE c.cartItemPK.cartId = :cartId AND c.cartItemPK.prodId = :prodId")})
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CartItemPK cartItemPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @JoinColumn(name = "CARTID", referencedColumnName = "CARTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cart cart;
    @JoinColumn(name = "PRODID", referencedColumnName = "PRODID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public CartItem() {
    }

    public CartItem(CartItemPK cartItemPK) {
        this.cartItemPK = cartItemPK;
    }

    public CartItem(CartItemPK cartItemPK, int quantity) {
        this.cartItemPK = cartItemPK;
        this.quantity = quantity;
    }

    public CartItem(long cartId, long prodId) {
        this.cartItemPK = new CartItemPK(cartId, prodId);
    }

    public CartItemPK getCartItemPK() {
        return cartItemPK;
    }

    public void setCartItemPK(CartItemPK cartItemPK) {
        this.cartItemPK = cartItemPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getTotalCartItemPrice(List<CartItem> cartItemForCust) {
        double sum = 0;

        for (CartItem ci : cartItemForCust) {
            sum += ci.getProduct().getUnitPrice() * ci.getQuantity();
        }

        return sum;
    }

    public double getSubTotal() {
        return this.quantity * this.product.getUnitPrice();
    }

    public double calcSubTotalAfterDiscount(double rate) {
        return getSubTotal() - (getSubTotal() * rate);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartItemPK != null ? cartItemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CartItem)) {
            return false;
        }
        CartItem other = (CartItem) object;
        if ((this.cartItemPK == null && other.cartItemPK != null) || (this.cartItemPK != null && !this.cartItemPK.equals(other.cartItemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cartitem[ cartitemPK=" + cartItemPK + " ]";
    }

}
