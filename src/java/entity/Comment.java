/**
 *
 * @author Ng Eason, Lee Jing Jet
 */


package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "COMMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c")
    , @NamedQuery(name = "Comment.findByCommentId", query = "SELECT c FROM Comment c WHERE c.commentId = :commentId")
    , @NamedQuery(name = "Comment.findByRating", query = "SELECT c FROM Comment c WHERE c.rating = :rating")
    , @NamedQuery(name = "Comment.findByProdId", query = "SELECT c FROM Comment c WHERE c.prodId = :prodId")
    , @NamedQuery(name = "Comment.findByMessage", query = "SELECT c FROM Comment c WHERE c.message = :message")
    , @NamedQuery(name = "Comment.findByCmtDate", query = "SELECT c FROM Comment c WHERE c.cmtDate = :cmtDate")})
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COMMENTID")
    private Long commentId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RATING")
    private int rating;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "MESSAGE")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CMTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cmtDate;
    @JoinColumn(name = "CUSTID", referencedColumnName = "CUSTID")
    @ManyToOne(optional = false)
    private Customer custId;
    @JoinColumn(name = "PRODID", referencedColumnName = "PRODID")
    @ManyToOne(optional = false)
    private Product prodId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commentId")
    private List<CommentReply> commentReplyList;

    public Comment() {
    }

    public Comment(Long commentId) {
        this.commentId = commentId;
    }

    public Comment(Long commentId, int rating, String message, Date cmtDate) {
        this.commentId = commentId;
        this.rating = rating;
        this.message = message;
        this.cmtDate = cmtDate;
    }

    public Comment(int rating, String message, Date cmtDate, Customer custId, Product prodId) {
        this.rating = rating;
        this.message = message;
        this.cmtDate = cmtDate;
        this.custId = custId;
        this.prodId = prodId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCmtDate() {
        return cmtDate;
    }

    public void setCmtDate(Date cmtDate) {
        this.cmtDate = cmtDate;
    }

    public Customer getCustId() {
        return custId;
    }

    public void setCustId(Customer custId) {
        this.custId = custId;
    }

    public Product getProdId() {
        return prodId;
    }

    public void setProdId(Product prodId) {
        this.prodId = prodId;
    }
    
    public int getAverageRating(List<Comment> comList){
        int sum = 0, avg = 0;
        for(Comment com: comList){
            sum+= com.getRating();
        }
        avg = sum / comList.size();
        return avg ;
    }

    @XmlTransient
    public List<CommentReply> getCommentReplyList() {
        return commentReplyList;
    }

    public void setCommentReplyList(List<CommentReply> commentReplyList) {
        this.commentReplyList = commentReplyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentId != null ? commentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.commentId == null && other.commentId != null) || (this.commentId != null && !this.commentId.equals(other.commentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "commentList.add(new Comment(\"" + commentId + "\"," + rating + "\"," + message + "\"," + cmtDate + "\"," + custId + "\"," + prodId + "));";
    }

}
