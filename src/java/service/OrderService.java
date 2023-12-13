/**
 *
 * @author Joey Kok Yen Ni
 */

package service;

import entity.*;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.Order;
import java.util.*;

public class OrderService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;
    
    public OrderService() {
    }

    public OrderService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addOrder(Order order) {
        mgr.persist(order);
        return true;
    }

    public Order findOrderByOrderId(Long id) {
        Order order = mgr.find(Order.class, id);
        return order;
    }
    
    public List<Order> findOrderByCustId(Long id) {
        List order = mgr.createNamedQuery("Orders.findByCustId").setParameter("custId", id).getResultList();
        return order;
    }
    
    public List<Order> findOrderByOrderStatusForACust(Customer cust, String status) {
        List order = mgr.createNamedQuery("Orders.findByOrderStatusForACust").setParameter("orderStatus", status).setParameter("custId", cust).getResultList();
        return order;
    }

    public boolean deleteOrder(Long id) {
        Order order = findOrderByOrderId(id);
        if (order != null) {
            mgr.remove(order);
            return true;
        }
        return false;
    }

    public List<Order> findAll() {
        List orderList = mgr.createNamedQuery("Orders.findAll").getResultList();
        return orderList;
    }

    public boolean updateOrder(Order order) {
        Order tempOrder = findOrderByOrderId(order.getOrderId());
        if (tempOrder != null) {
            tempOrder.setOrderDate(order.getOrderDate());
            tempOrder.setOrderStatus(order.getOrderStatus());
            tempOrder.setTotalAmount(order.getTotalAmount());
            tempOrder.setStfId(order.getStfId());
            return true;
        }
        return false;
    }
    
    public Order getLatestOrderRecord(){
        List<Order> allOrder = this.findAll();
        Order lastOrder = allOrder.get(allOrder.size()-1);
        return lastOrder;
    }
}
