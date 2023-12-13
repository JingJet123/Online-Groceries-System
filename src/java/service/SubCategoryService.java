/**
 *
 * @author Chuah Shee Yeap
 */

package service;

import entity.SubCategory;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class SubCategoryService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public SubCategoryService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addSubCategory(SubCategory subCategory) {
        mgr.persist(subCategory);
        return true;
    }

    public SubCategory findSubCategBySubId(Long id) {
        SubCategory subCategory = mgr.find(SubCategory.class, id);
        return subCategory;
    }

    public boolean deleteSubCategory(Long id) {
        SubCategory subCategory = findSubCategBySubId(id);
        if (subCategory != null) {
            mgr.remove(subCategory);
            return true;
        }
        return false;
    }
    
    public boolean chgDeleteStatus(Long id) {
        SubCategory subCateg = findSubCategBySubId(id);
        if (subCateg != null) {
            subCateg.setIsDeleted(true);
        }
        return updateSubCategory(subCateg);
    }

    public List<SubCategory> findAll() {
        List subCategoryList = mgr.createNamedQuery("SubCategory.findAll").getResultList();
        return subCategoryList;
    }

    public boolean updateSubCategory(SubCategory subCategory) {
        SubCategory tempSubCateg = findSubCategBySubId(subCategory.getSubCategoryId());
        if (tempSubCateg != null) {
            tempSubCateg.setSubCategoryName(subCategory.getSubCategoryName());
            tempSubCateg.setIsDeleted(subCategory.getIsDeleted());
            // Change the category of this sub category
            tempSubCateg.setCategoryId(subCategory.getCategoryId());
            return true;
        }
        return false;
    }
}
