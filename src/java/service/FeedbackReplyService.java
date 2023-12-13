/**
 *
 * @author Ng Eason, Lee Jing Jet
 */

package service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.FeedbackReply;
import java.util.*;

public class FeedbackReplyService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public FeedbackReplyService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addFeedbackReply(FeedbackReply reply) {
        mgr.persist(reply);
        return true;
    }

    public FeedbackReply findFdbkReplyById(Long id) {
        FeedbackReply fdbkReply = mgr.find(FeedbackReply.class, id);
        return fdbkReply;
    }

    public boolean deleteFdbkReply(Long id) {
        FeedbackReply fdbkReply = findFdbkReplyById(id);
        if (fdbkReply != null) {
            mgr.remove(fdbkReply);
            return true;
        }
        return false;
    }

    public List<FeedbackReply> findAll() {
        List fdbkReplyList = mgr.createNamedQuery("FeedbackReply.findAll").getResultList();
        return fdbkReplyList;
    }

    public boolean updateFdbkReply(FeedbackReply reply) {
        FeedbackReply tempFdbkReply = findFdbkReplyById(reply.getFdbkReplyId());
        if (tempFdbkReply != null) {
            tempFdbkReply.setMessage(reply.getMessage());
            tempFdbkReply.setStfId(reply.getStfId());
            return true;
        }
        return false;
    }
}
