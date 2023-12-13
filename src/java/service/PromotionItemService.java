/**
 *
 * @author Lee Jia Jie
 */

package service;
import entity.PromotionItem;
import java.util.*;
import javax.annotation.Resource;
import javax.persistence.*;


public class PromotionItemService {
    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public PromotionItemService() {
    }
    
    

    public PromotionItemService(EntityManager mgr) {
        this.mgr = mgr;
    }
    
    public boolean addPromotionItem(PromotionItem promotionItem) {
        mgr.persist(promotionItem);
        return true;
    }
    
    public List<PromotionItem> findPromoItemListByProdId(Long prodId) {
        List promoItemList = mgr.createNamedQuery("PromotionItem.findByProdId").setParameter("prodId", prodId).getResultList();
        return promoItemList;
    }
    
    public PromotionItem findPromoItemByCompsId(Long stfId, Long prodId, Date startDate, Date endDate) {
        PromotionItem promoItem = (PromotionItem) mgr.createNamedQuery("PromotionItem.findByCompsId")
                .setParameter("stfId", stfId).setParameter("prodId", prodId).setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getSingleResult();
        return promoItem;
    }
    
    public boolean deletePromoItem(Long stfId, Long prodId, Date startDate, Date endDate) {
        PromotionItem promoItem = findPromoItemByCompsId(stfId, prodId, startDate, endDate);
        if (promoItem != null) {
            mgr.remove(promoItem);
            return true;
        }
        return false;
    }
    
    
    public List<PromotionItem> findAll() {
        List promoItemList = mgr.createNamedQuery("PromotionItem.findAll").getResultList();
        return promoItemList;
    }
    
    public List<PromotionItem> findAllByValidDate(){
        List promoItemList = mgr.createNamedQuery("PromotionItem.findByValidDate").setParameter("todayDate", Calendar.getInstance().getTime()).getResultList();
        return promoItemList;
    }
    
    public boolean updatePromoItem(PromotionItem promoItem) {
        PromotionItem tempPromoItem = findPromoItemByCompsId(promoItem.getStaff().getStfId(), 
                promoItem.getProduct().getProdId(), promoItem.getPromotionItemPK().getStartDate(), 
                promoItem.getPromotionItemPK().getEndDate());
        
        if(tempPromoItem != null) {
            tempPromoItem.setPromotionItemPK(promoItem.getPromotionItemPK());
            tempPromoItem.setPromoRate(promoItem.getPromoRate());
            return true;
        }
        return false;
    }
}

