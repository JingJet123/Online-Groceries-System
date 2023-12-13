/**
 *
 * @author New Yee Hao, Joey Kok Yen Ni
 */

package service;

import entity.Customer;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class CustomerService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public CustomerService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addCustomer(Customer customer) {
        mgr.persist(customer);
        return true;
    }

    public Customer findCustomerByCustId(Long id) {
        Customer customer = mgr.find(Customer.class, id);
        return customer;
    }

    public boolean deleteCustomer(Long id) {
        Customer customer = findCustomerByCustId(id);
        if (customer != null) {
            mgr.remove(customer);
            return true;
        }
        return false;
    }

    public List<Customer> findAll() {
        List customerList = mgr.createNamedQuery("Customer.findAll").getResultList();
        return customerList;
    }

    public boolean updateCustomer(Customer customer) {
        Customer tempCustomer = findCustomerByCustId(customer.getCustId());
        if (tempCustomer != null) {
            tempCustomer.setUsername(customer.getUsername());
            tempCustomer.setPassword(customer.getPassword());
            tempCustomer.setCustName(customer.getCustName());
            tempCustomer.setCustContact(customer.getCustContact());
            tempCustomer.setCustAddress(customer.getCustAddress());
            tempCustomer.setCustEmail(customer.getCustEmail());
            tempCustomer.setProfileImg(customer.getProfileImg());
            return true;
        }
        return false;
    }
}
