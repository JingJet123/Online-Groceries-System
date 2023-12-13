/**
 *
 * @author Chuah Shee Yeap
 */

package service;

import entity.Product;
import entity.PromotionItem;
import java.util.*;
import javax.annotation.Resource;
import javax.persistence.*;

public class ProductService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public ProductService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addProduct(Product product) {
        mgr.persist(product);
        return true;
    }

    public Product findProductByProdId(Long id) {
        Product product = mgr.find(Product.class, id);
        return product;
    }

    public List<Product> findProductExcludeDeleted() {
         List productList = mgr.createNamedQuery("Product.findByIsDeleted").setParameter("isDeleted", false).getResultList();
        return productList;
    }

    public boolean deleteProduct(Long id) {
        Product product = findProductByProdId(id);
        if (product != null) {
            mgr.remove(product);
            return true;
        }
        return false;
    }
    
    public boolean chgDeleteStatus(Long id) {
        Product product = findProductByProdId(id);
        if (product != null) {
            product.setIsDeleted(true);
        }
        return updateProduct(product);
    }

    public List<Product> findAll() {
        List productList = mgr.createNamedQuery("Product.findAll").getResultList();
        return productList;
    }

    public int[] totalPage(int count) {
        int totalRecordnPage[] = {0, 1};
        List<Product> productList = mgr.createNamedQuery("Product.findAll").getResultList();
        try {
            int totalRecord = 0;
            for(Product p: productList) {
                if(!p.getIsDeleted()) {
                    ++totalRecord;
                }
            }
            totalRecordnPage[0] = totalRecord;
            if(totalRecordnPage[0] % count == 0) {
                totalRecordnPage[1] = totalRecordnPage[0] / count;
            } else {
                totalRecordnPage[1] = totalRecordnPage[0] / count + 1;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return totalRecordnPage;
    }

    public List<Product> findAllByPaginationList(int curPage, int count) {
        //To retrieve records within specific ranage
        Query findRecordPage = mgr.
                createQuery("SELECT p From Product p")
                .setFirstResult((curPage - 1) * count)
                .setMaxResults(count);

        List<Product> productList = findRecordPage.getResultList();

        return productList;
    }

    public boolean updateProduct(Product product) {
        Product tempProduct = findProductByProdId(product.getProdId());
        if (tempProduct != null) {
            tempProduct.setProdName(product.getProdName());
            tempProduct.setUnitPrice(product.getUnitPrice());
            tempProduct.setStock(product.getStock());
            tempProduct.setSupplier(product.getSupplier());
            tempProduct.setProdImg(product.getProdImg());
            tempProduct.setIsDeleted(product.getIsDeleted());
            return true;
        }
        return false;
    }

}
