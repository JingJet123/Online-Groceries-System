/**
 *
 * @author Ng Eason, Lee Jing Jet
 */

package service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.CommentReply;
import java.util.List;

public class CommentReplyService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public CommentReplyService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addCommentReply(CommentReply reply) {
        mgr.persist(reply);
        return true;
    }

    public CommentReply findCmtReplyById(Long id) {
        CommentReply cmtReply = mgr.find(CommentReply.class, id);
        return cmtReply;
    }

    public boolean deleteCmtReply(Long id) {
        CommentReply cmtReply = findCmtReplyById(id);
        if (cmtReply != null) {
            mgr.remove(cmtReply);
            return true;
        }
        return false;
    }

    public List<CommentReply> findAll() {
        List cmtReplyList = mgr.createNamedQuery("CommentReply.findAll").getResultList();
        return cmtReplyList;
    }

    public boolean updateCmtReply(CommentReply reply) {
        CommentReply tempCmtReply = findCmtReplyById(reply.getCmtReplyId());
        if (tempCmtReply != null) {
            tempCmtReply.setMessage(reply.getMessage());
            tempCmtReply.setStfId(reply.getStfId());
            return true;
        }
        return false;
    }
}
