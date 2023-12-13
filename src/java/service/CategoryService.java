/**
 *
 * @author Chuah Shee Yeap
 */

package service;

import entity.Category;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class CategoryService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public CategoryService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addCategory(Category category) {
        mgr.persist(category);
        // To show the user that this category has been added successfully
        return true;
    }

    public Category findCategByCategId(Long id) {
        Category category = mgr.find(Category.class, id);
        return category;
    }
    
    public boolean deleteCategory(Long id) {
        Category category = findCategByCategId(id);
        if (category != null) {
            mgr.remove(category);
            return true;
        }
        return false;
    }
    
    public boolean chgDeleteStatus(Long id) {
        Category categ = findCategByCategId(id);
        if (categ != null) {
            categ.setIsDeleted(true);
        }
        return updateCategory(categ);
    }

    public List<Category> findAll() {
        List categoryList = mgr.createNamedQuery("Category.findAll").getResultList();
        return categoryList;
    }

    public boolean updateCategory(Category category) {
        Category tempCateg = findCategByCategId(category.getCategoryId());
        if (tempCateg != null) {
            tempCateg.setCategoryName(category.getCategoryName());
            // After set to true all the products belong to this category should be archived also
            tempCateg.setIsDeleted(category.getIsDeleted());
            return true;
        }
        return false;
    }
}
