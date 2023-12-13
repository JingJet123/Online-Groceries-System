/**
 *
 * @author Ng Eason, Lee Jing Jet
 */

package service;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.Comment;
import entity.Product;
import java.util.*;

public class CommentService {
        @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public CommentService(EntityManager mgr) {
        this.mgr = mgr;
    }
    
    public boolean addComment(Comment comment) {
        mgr.persist(comment);
        return true;
    }
    
    public Comment findCommentByCmtId(Long id) {
        Comment comment = mgr.find(Comment.class, id);
        return comment;
    }
    
    public List<Comment> findCommentsByProdId(Product prodId) {
        List comment = mgr.createNamedQuery("Comment.findByProdId").setParameter("prodId", prodId).getResultList();
        return comment;
    }
    
    public boolean deleteComment(Long id) {
        Comment comment = findCommentByCmtId(id);
        if (comment != null) {
            mgr.remove(comment);
            return true;
        }
        return false;
    }
    
    public List<Comment> findAll() {
        List commentList = mgr.createNamedQuery("Comment.findAll").getResultList();
        return commentList;
    }
    
    public boolean updateComment(Comment comment) {
        Comment tempComment = findCommentByCmtId(comment.getCommentId());
        if(tempComment != null) {
            tempComment.setRating(comment.getRating());
            tempComment.setMessage(comment.getMessage());
            tempComment.setCmtDate(comment.getCmtDate());
            return true;
        }
        return false;
    }
}
