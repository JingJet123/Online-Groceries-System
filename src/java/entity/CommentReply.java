/**
 *
 * @author Ng Eason, Lee Jing Jet
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "COMMENTREPLY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CommentReply.findAll", query = "SELECT c FROM CommentReply c")
    , @NamedQuery(name = "CommentReply.findByCmtReplyId", query = "SELECT c FROM CommentReply c WHERE c.cmtReplyId = :cmtReplyId")
    , @NamedQuery(name = "CommentReply.findByMessage", query = "SELECT c FROM CommentReply c WHERE c.message = :message")
    , @NamedQuery(name = "CommentReply.findByReplyDate", query = "SELECT c FROM CommentReply c WHERE c.replyDate = :replyDate")})
public class CommentReply implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CMTREPLYID")
    private Long cmtReplyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "MESSAGE")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPLYDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date replyDate;
    @JoinColumn(name = "COMMENTID", referencedColumnName = "COMMENTID")
    @ManyToOne(optional = false)
    private Comment commentId;
    @JoinColumn(name = "STFID", referencedColumnName = "STFID")
    @ManyToOne(optional = false)
    private Staff stfId;

    public CommentReply() {
    }

    public CommentReply(Long cmtReplyId) {
        this.cmtReplyId = cmtReplyId;
    }

    public CommentReply(Long cmtReplyId, String message, Date replyDate) {
        this.cmtReplyId = cmtReplyId;
        this.message = message;
        this.replyDate = replyDate;
    }

    public CommentReply(String message, Date replyDate, Comment commentId, Staff stfId) {
        this.message = message;
        this.replyDate = replyDate;
        this.commentId = commentId;
        this.stfId = stfId;
    }

    public CommentReply(Long cmtReplyId, String message, Date replyDate, Comment commentId, Staff stfId) {
        this.cmtReplyId = cmtReplyId;
        this.message = message;
        this.replyDate = replyDate;
        this.commentId = commentId;
        this.stfId = stfId;
    }

    public Long getCmtReplyId() {
        return cmtReplyId;
    }

    public void setCmtReplyId(Long cmtReplyId) {
        this.cmtReplyId = cmtReplyId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Comment getCommentId() {
        return commentId;
    }

    public void setCommentId(Comment commentId) {
        this.commentId = commentId;
    }

    public Staff getStfId() {
        return stfId;
    }

    public void setStfId(Staff stfId) {
        this.stfId = stfId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cmtReplyId != null ? cmtReplyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentReply)) {
            return false;
        }
        CommentReply other = (CommentReply) object;
        if ((this.cmtReplyId == null && other.cmtReplyId != null) || (this.cmtReplyId != null && !this.cmtReplyId.equals(other.cmtReplyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Commentreply[ cmtreplyid=" + cmtReplyId + " ]";
    }

}
