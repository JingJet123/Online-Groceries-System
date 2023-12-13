/**
 *
 * @author Lee Jia Jie
 */

package service;

import entity.Order;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.ShippingDetails;
import java.util.*;

public class ShippingDetailsService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public ShippingDetailsService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addShippingDetails(ShippingDetails shipDetails) {
        mgr.persist(shipDetails);
        return true;
    }

    public ShippingDetails findShippingDetailsById(Long id) {
        ShippingDetails shippingDetails = mgr.find(ShippingDetails.class, id);
        return shippingDetails;
    }
    
    public ShippingDetails findShippingDetailsByOrderId(Order order) {
        ShippingDetails shippingDetails = (ShippingDetails) mgr.createNamedQuery("ShippingDetails.findByOrderId").setParameter("orderId", order).getSingleResult();
        return shippingDetails;
    }

    public boolean deleteShipDetails(Long id) {
        ShippingDetails shipDetails = findShippingDetailsById(id);
        if (shipDetails != null) {
            mgr.remove(shipDetails);
            return true;
        }
        return false;
    }

    public List<ShippingDetails> findAll() {
        List shipDetailsList = mgr.createNamedQuery("ShippingDetails.findAll").getResultList();
        return shipDetailsList;
    }

    public boolean updateShippingDetails(ShippingDetails shipDetails) {
        ShippingDetails tempShipDetails = findShippingDetailsById(shipDetails.getShippingId());
        if (tempShipDetails != null) {
            tempShipDetails.setShippingDate(shipDetails.getShippingDate());
            tempShipDetails.setShippingAddress(shipDetails.getShippingAddress());
            return true;
        }
        return false;
    }

}
