/**
 *
 * @author New Yee Hao, Joey Kok Yen Ni
 */

package service;

import entity.Order;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.Payment;
import java.util.*;

public class PaymentService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public PaymentService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addPayment(Payment payment) {
        mgr.persist(payment);
        return true;
    }

    public Payment findPaymentByPayId(Long id) {
        Payment payment = mgr.find(Payment.class, id);
        return payment;
    }
    
    public Payment findPaymentByOrderId(Order order){
        Payment payment = (Payment) mgr.createNamedQuery("Payment.findByOrderId").setParameter("orderId", order).getSingleResult();
        return payment;
    }

    public boolean deletePayment(Long id) {
        Payment payment = findPaymentByPayId(id);
        if (payment != null) {
            mgr.remove(payment);
            return true;
        }
        return false;
    }

    public List<Payment> findAll() {
        List paymentList = mgr.createNamedQuery("Payment.findAll").getResultList();
        return paymentList;
    }

    public boolean updatePayment(Payment payment) {
        Payment tempPayment = findPaymentByPayId(payment.getPayId());
        if (tempPayment != null) {
            tempPayment.setPayType(payment.getPayType());
            tempPayment.setPayDate(payment.getPayDate());
            tempPayment.setPayAmount(payment.getPayAmount());
            return true;
        }
        return false;
    }
}
