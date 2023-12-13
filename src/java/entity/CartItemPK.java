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
public class CartItemPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "CARTID")
    private long cartId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODID")
    private long prodId;

    public CartItemPK() {
    }

    public CartItemPK(long cartId, long prodId) {
        this.cartId = cartId;
        this.prodId = prodId;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
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
        hash += (int) cartId;
        hash += (int) prodId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CartItemPK)) {
            return false;
        }
        CartItemPK other = (CartItemPK) object;
        if (this.cartId != other.cartId) {
            return false;
        }
        if (this.prodId != other.prodId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CartitemPK[ cartid=" + cartId + ", prodid=" + prodId + " ]";
    }

}
