/**
 *
 * @author Chuah Shee Yeap
 */

package service;

import entity.Staff;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class StaffService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public StaffService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addStaff(Staff staff) {
        mgr.persist(staff);
        return true;
    }

    public Staff findStaffByStfId(Long id) {
        Staff staff = mgr.find(Staff.class, id);
        return staff;
    }
    
    public Staff findStaffByStfUsername(String username) {
        Staff staff = (Staff) mgr.createNamedQuery("Staff.findByUsername").
                setParameter("username", username).getSingleResult();
        return staff;
    }

    public boolean deleteStaff(Long id) {
        Staff staff = findStaffByStfId(id);
        if (staff != null) {
            mgr.remove(staff);
            return true;
        }
        return false;
    }
    
    public boolean chgDeleteStatus(Long id) {
        Staff stf = findStaffByStfId(id);
        if (stf != null) {
            stf.setIsDeleted(true);
        }
        return updateStaff(stf);
    }

    public List<Staff> findAll() {
        List staffList = mgr.createNamedQuery("Staff.findAll").getResultList();
        return staffList;
    }

    public boolean updateStaff(Staff staff) {
        Staff tempStaff = findStaffByStfId(staff.getStfId());

        if (tempStaff != null) {
            tempStaff.setUsername(staff.getUsername());
            tempStaff.setPassword(staff.getPassword());
            tempStaff.setStfName(staff.getStfName());
            tempStaff.setStfContact(staff.getStfContact());
            tempStaff.setStfEmail(staff.getStfEmail());
            tempStaff.setStfSalary(staff.getStfSalary());
            tempStaff.setStfRole(staff.getStfRole());
            tempStaff.setProfileImg(staff.getProfileImg());
            tempStaff.setIsDeleted(staff.getIsDeleted());
            return true;
        }
        return false;
    }
}
