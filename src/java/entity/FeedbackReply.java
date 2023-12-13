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
@Table(name = "FEEDBACKREPLY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FeedbackReply.findAll", query = "SELECT f FROM FeedbackReply f")
    , @NamedQuery(name = "FeedbackReply.findByFdbkReplyId", query = "SELECT f FROM FeedbackReply f WHERE f.fdbkReplyId = :fdbkReplyId")
    , @NamedQuery(name = "FeedbackReply.findByMessage", query = "SELECT f FROM FeedbackReply f WHERE f.message = :message")
    , @NamedQuery(name = "FeedbackReply.findByReplyDate", query = "SELECT f FROM FeedbackReply f WHERE f.replyDate = :replyDate")})
public class FeedbackReply implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FDBKREPLYID")
    private Long fdbkReplyId;
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
    @JoinColumn(name = "FEEDBACKID", referencedColumnName = "FEEDBACKID")
    @ManyToOne(optional = false)
    private Feedback feedbackId;
    @JoinColumn(name = "STFID", referencedColumnName = "STFID")
    @ManyToOne(optional = false)
    private Staff stfId;

    public FeedbackReply() {
    }

    public FeedbackReply(Long fdbkReplyId) {
        this.fdbkReplyId = fdbkReplyId;
    }

    public FeedbackReply(Long fdbkReplyId, String message, Date replyDate) {
        this.fdbkReplyId = fdbkReplyId;
        this.message = message;
        this.replyDate = replyDate;
    }

    public FeedbackReply(String message, Date replyDate, Feedback feedbackId, Staff stfId) {
        this.message = message;
        this.replyDate = replyDate;
        this.feedbackId = feedbackId;
        this.stfId = stfId;
    }

    public FeedbackReply(Long fdbkReplyId, String message, Date replyDate, Feedback feedbackId, Staff stfId) {
        this.fdbkReplyId = fdbkReplyId;
        this.message = message;
        this.replyDate = replyDate;
        this.feedbackId = feedbackId;
        this.stfId = stfId;
    }

    public Long getFdbkReplyId() {
        return fdbkReplyId;
    }

    public void setFdbkReplyId(Long fdbkReplyId) {
        this.fdbkReplyId = fdbkReplyId;
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

    public Feedback getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Feedback feedbackId) {
        this.feedbackId = feedbackId;
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
        hash += (fdbkReplyId != null ? fdbkReplyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeedbackReply)) {
            return false;
        }
        FeedbackReply other = (FeedbackReply) object;
        if ((this.fdbkReplyId == null && other.fdbkReplyId != null) || (this.fdbkReplyId != null && !this.fdbkReplyId.equals(other.fdbkReplyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Feedbackreply[ fdbkreplyid=" + fdbkReplyId + " ]";
    }

}
