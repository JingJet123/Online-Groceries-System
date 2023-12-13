/**
 *
 * @author Joey Kok Yen Ni
 */

package service;

import entity.OrderDetails;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class OrderDetailsService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public OrderDetailsService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addOrderDetails(OrderDetails orderDetails) {
        mgr.persist(orderDetails);
        return true;
    }

    public List<OrderDetails> findOrderDetailsListByOrderId(Long id) {
        List orderDetailsList = mgr.createNamedQuery("OrderDetails.findByOrderId").setParameter("orderId", id).getResultList();
        return orderDetailsList;
    }

//    public OrderDetails findOrderDetailsByCompsId(Long orderId, Long prodId) {
//        OrderDetails orderDetails = new OrderDetails();
//        for (OrderDetails orderDetail : findAll()) {
//            if (orderDetail.getOrder().getOrderId().equals(orderId) && orderDetail.getProduct().getProdId().equals(prodId)) {
//                orderDetails = orderDetail;
//            }
//        }
//        return orderDetails;
//    }
    // This method not yet tested
    public OrderDetails findOrderDetailsByCompsId(Long orderId, Long prodId) {
        OrderDetails orderDetails = (OrderDetails) mgr.createNamedQuery("OrderDetails.findByCompsId").setParameter("orderId", orderId)
                .setParameter("prodId", prodId).getSingleResult();
        return orderDetails;
    }

//    public boolean deleteOrderDetails(Long orderId, Long prodId) {
//        OrderDetails orderDetails = findOrderDetailsByCompsId(orderId, prodId);
//        if (orderDetails != null) {
//            mgr.remove(orderDetails);
//            return true;
//        }
//        return false;
//    }

    public boolean deleteOrderDetails(OrderDetails orderDetailsToDlt) {
        OrderDetails od = new OrderDetails();
        if (orderDetailsToDlt != null) {
            if (!mgr.contains(orderDetailsToDlt)) {
                od = mgr.merge(orderDetailsToDlt);
            }
            mgr.remove(od);
            return true;
        }
        return false;
    }

    public List<OrderDetails> findAll() {
        List orderDetailsList = mgr.createNamedQuery("OrderDetails.findAll").getResultList();
        return orderDetailsList;
    }

    public boolean updateOrderDetails(OrderDetails orderDetails) {
        OrderDetails tempOrderDetails = findOrderDetailsByCompsId(
                orderDetails.getOrder().getOrderId(), orderDetails.getProduct().getProdId()
        );
        if (tempOrderDetails != null) {
            tempOrderDetails.setQuantity(orderDetails.getQuantity());
            tempOrderDetails.setSubTotal(orderDetails.getSubTotal());
            return true;
        }
        return false;
    }
}
