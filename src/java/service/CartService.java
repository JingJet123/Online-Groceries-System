/**
 *
 * @author Joey Kok Yen Ni
 */

package service;

import entity.Cart;
import entity.CartItem;
import entity.Customer;
import java.util.*;
import javax.annotation.Resource;
import javax.persistence.*;

public class CartService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public CartService() {
    }

    public CartService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addCart(Cart cart) {
        mgr.persist(cart);
        return true;
    }

    public Cart findCartById(Long id) {
        Cart cart = mgr.find(Cart.class, id);
        return cart;
    }

    public boolean deleteCart(Long id) {
        Cart cart = findCartById(id);
        if (cart != null) {
            mgr.remove(cart);
            return true;
        }
        return false;
    }

    public List<Cart> findAll() {
        List cartList = mgr.createNamedQuery("Cart.findAll").getResultList();
        return cartList;
    }
    
    public Cart findCartByCustId(Customer cust){
        Cart cart = (Cart) mgr.createNamedQuery("Cart.findByCustId").setParameter("custId", cust).getSingleResult();
        return cart;
    }

    public boolean updateCart(Cart cart) {
        Cart tempCart = findCartById(cart.getCartId());
        CustomerService custService = new CustomerService(mgr);
        if (tempCart != null) {
            tempCart.setCustId(custService.findCustomerByCustId(cart.getCustId().getCustId()));
            return true;
        }
        return false;
    }

    public double getTotalCartPrice(List<CartItem> cartItemForCust) {
        double sum = 0;

        if (cartItemForCust.size() > 0) {
            for (CartItem cartItem : cartItemForCust) {

                sum += cartItem.getProduct().getUnitPrice() * cartItem.getQuantity();
            }
        }
        return sum;

    }
}
