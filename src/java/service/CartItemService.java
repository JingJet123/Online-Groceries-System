/**
 *
 * @author Joey Kok Yen Ni
 */

package service;

import entity.*;
import java.util.*;
import javax.annotation.Resource;
import javax.persistence.*;

public class CartItemService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public CartItemService() {
    }

    public CartItemService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addCartItem(CartItem cartItem) {
        mgr.persist(cartItem);
        return true;
    }

    public List<CartItem> findCartItemListByCartId(Long id) {
        
        List<CartItem> cartItemList = mgr.createNamedQuery("CartItem.findByCartId").setParameter("cartId", id).getResultList();
        return cartItemList;
    }
    
    public List<CartItem> findAllCartItemListByCartId(Long id) {
        
        List<CartItem> cartItemList = new ArrayList<CartItem>();
        List<CartItem> allCartItem = findAll();
        for(CartItem ci: allCartItem){
            if(ci.getCartItemPK().getCartId() == id){
                cartItemList.add(ci);
            }
        }
        return cartItemList;
    }
    
    
    public CartItem findCartItemByCompsId(Long cartId, Long prodId) {
//        CartItem cartItem = new CartItem();
//        List<CartItem> cartItemList = findAll();
//        for(CartItem ci: cartItemList) {
//            if(ci.getCart().getCartId().equals(cartId) && ci.getProduct().getProdId().equals(prodId)) {
//                cartItem = ci;
//            }
//        }
//        return cartItem;
        CartItem cartItem = (CartItem) mgr.createNamedQuery("CartItem.findByCompsId")
                .setParameter("cartId", cartId).setParameter("prodId", prodId).getSingleResult();
        return cartItem;
    }

    public boolean deleteCartItem(Long cartId, Long prodId) {
        CartItem ci = findCartItemByCompsId(cartId, prodId);
        if (ci != null) {
            mgr.remove(ci);
            return true;
        }
        return false;
    }

    public boolean deleteCartItem(CartItem cartItemToDelete) {
        CartItem ci = new CartItem();
        if (cartItemToDelete != null) {
            if (!mgr.contains(cartItemToDelete)) {
                ci = mgr.merge(cartItemToDelete);
            }
            mgr.remove(ci);
            return true;
        }
        return false;
    }

    public List<CartItem> findAll() {
        List cartItemList = mgr.createNamedQuery("CartItem.findAll").getResultList();
        return cartItemList;
    }

    public boolean updateCartItem(CartItem cartItem) {
        CartItem tempCartItem = findCartItemByCompsId(cartItem.getCart().getCartId(), cartItem.getProduct().getProdId());
        if (tempCartItem != null) {
            tempCartItem.setQuantity(cartItem.getQuantity());
            return true;
        }
        return false;
    }
}
//package service;
//
//import entity.*;
//import java.util.*;
//import javax.annotation.Resource;
//import javax.persistence.*;
//
//public class CartItemService {
//
//    @PersistenceContext
//    EntityManager mgr;
//    @Resource
//    Query query;
//
//    public CartItemService(EntityManager mgr) {
//        this.mgr = mgr;
//    }
//
//    public boolean addCartItem(CartItem cartItem) {
//        mgr.persist(cartItem);
//        return true;
//    }
//
//    public CartItem findCartItemById(Long id) {
//        CartItem cartItem = mgr.find(CartItem.class, id);
//        return cartItem;
//    }
//
//    public boolean deleteCartItem(Long id) {
//        CartItem cartItem = findCartItemById(id);
//        if (cartItem != null) {
//            mgr.remove(cartItem);
//            return true;
//        }
//        return false;
//    }
//
//    public List<CartItem> findAll() {
//        List cartItemList = mgr.createNamedQuery("CartItem.findAll").getResultList();
//        return cartItemList;
//    }
//
//    public boolean updateCartItem(CartItem cartItem) {
//        CartItem tempCartItem = findCartItemById(cartItem.getCart().getCartId());
//        CustService custService = new CustService();
//        if (tempCartItem != null) {
//            tempCartItem.setQuantity(cartItem.getQuantity());
//            return true;
//        }
//        return false;
//    }
//
//    public double getTotalCartItemPrice(List<CartItem> cartItemForCust) {
//        double sum = 0;
//        ProductService prodService = new ProductService();
//        CartItemService ci = new CartItemService();
//        try {
//            if (cartItemForCust.size() > 0) {
//                for (CartItem cartItem : cartItemForCust) {
//                    Product prod = prodService.findProductById(cartItem.getProduct().getProdId());
//                    sum += prod.getUnitPrice() * cartItem.getQuantity();
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println(ex.getMessage());
//        }
//        return sum;
//    }
//
//}
