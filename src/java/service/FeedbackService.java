/**
 *
 * @author Ng Eason, Lee Jing Jet
 */

package service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.Feedback;
import java.util.*;


public class FeedbackService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public FeedbackService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addFeedback(Feedback feedback) {
        mgr.persist(feedback);
        return true;
    }

    public Feedback findFeedbackByFdbkId(Long id) {
        Feedback feedback = mgr.find(Feedback.class, id);
        return feedback;
    }

    public boolean deleteFeedback(Long id) {
        Feedback feedback = findFeedbackByFdbkId(id);
        if (feedback != null) {
            mgr.remove(feedback);
            return true;
        }
        return false;
    }

    public List<Feedback> findAll() {
        List feedbackList = mgr.createNamedQuery("Feedback.findAll").getResultList();
        return feedbackList;
    }

    public boolean updateFeedback(Feedback feedback) {
        Feedback tempFeedback = findFeedbackByFdbkId(feedback.getFeedbackId());
        if (tempFeedback != null) {
            tempFeedback.setCategory(feedback.getCategory());
            tempFeedback.setMessage(feedback.getMessage());
            tempFeedback.setFdbkDate(feedback.getFdbkDate());
            return true;
        }
        return false;
    }

}
