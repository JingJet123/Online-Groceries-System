/**
 *
 * @author Chuah Shee Yeap
 */

package entity;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "STAFF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s")
    , @NamedQuery(name = "Staff.findByStfId", query = "SELECT s FROM Staff s WHERE s.stfId = :stfId")
    , @NamedQuery(name = "Staff.findByUsername", query = "SELECT s FROM Staff s WHERE s.username = :username")
    , @NamedQuery(name = "Staff.findByPassword", query = "SELECT s FROM Staff s WHERE s.password = :password")
    , @NamedQuery(name = "Staff.findByStfName", query = "SELECT s FROM Staff s WHERE s.stfName = :stfName")
    , @NamedQuery(name = "Staff.findByStfContact", query = "SELECT s FROM Staff s WHERE s.stfContact = :stfContact")
    , @NamedQuery(name = "Staff.findByStfEmail", query = "SELECT s FROM Staff s WHERE s.stfEmail = :stfEmail")
    , @NamedQuery(name = "Staff.findByStfSalary", query = "SELECT s FROM Staff s WHERE s.stfSalary = :stfSalary")
    , @NamedQuery(name = "Staff.findByStfRole", query = "SELECT s FROM Staff s WHERE s.stfRole = :stfRole")
    , @NamedQuery(name = "Staff.findByIsDeleted", query = "SELECT s FROM Staff s WHERE s.isDeleted = :isDeleted")})
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STFID")
    private Long stfId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "STFNAME")
    private String stfName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "STFCONTACT")
    private String stfContact;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "STFEMAIL")
    private String stfEmail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STFSALARY")
    private double stfSalary;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STFROLE")
    private Character stfRole;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "PROFILEIMG")
    private byte[] profileImg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private Boolean isDeleted = false;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stfId")
    private List<FeedbackReply> feedbackReplyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staff")
    private List<PromotionItem> promotionItemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stfId")
    private List<Order> ordersList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stfId")
    private List<CommentReply> commentReplyList;

    public Staff() {
    }

    public Staff(Long stfId) {
        this.stfId = stfId;
    }

    public Staff(String username, String password, String stfName, String stfContact, String stfEmail, double stfSalary, Character stfRole) {
        this.username = username;
        this.password = password;
        this.stfName = stfName;
        this.stfContact = stfContact;
        this.stfEmail = stfEmail;
        this.stfSalary = stfSalary;
        this.stfRole = stfRole;
    }

    public Staff(String username, String password, String stfName, String stfContact, String stfEmail,
            double stfSalary, Character stfRole, byte[] profileImg) {
        this.username = username;
        this.password = password;
        this.stfName = stfName;
        this.stfContact = stfContact;
        this.stfEmail = stfEmail;
        this.stfSalary = stfSalary;
        this.stfRole = stfRole;
        this.profileImg = profileImg;
    }

    public Staff(Long stfId, String username, String password, String stfName, String stfContact,
            String stfEmail, double stfSalary, Character stfRole, byte[] profileImg, Boolean isDeleted) {
        this.stfId = stfId;
        this.username = username;
        this.password = password;
        this.stfName = stfName;
        this.stfContact = stfContact;
        this.stfEmail = stfEmail;
        this.stfSalary = stfSalary;
        this.stfRole = stfRole;
        this.profileImg = profileImg;
        this.isDeleted = isDeleted;
    }

    public Long getStfId() {
        return stfId;
    }

    public void setStfId(Long stfId) {
        this.stfId = stfId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStfName() {
        return stfName;
    }

    public void setStfName(String stfName) {
        this.stfName = stfName;
    }

    public String getStfContact() {
        return stfContact;
    }

    public void setStfContact(String stfContact) {
        this.stfContact = stfContact;
    }

    public String getStfEmail() {
        return stfEmail;
    }

    public void setStfEmail(String stfEmail) {
        this.stfEmail = stfEmail;
    }

    public double getStfSalary() {
        return stfSalary;
    }

    public void setStfSalary(double stfSalary) {
        this.stfSalary = stfSalary;
    }

    public Character getStfRole() {
        return stfRole;
    }

    public void setStfRole(Character stfRole) {
        this.stfRole = stfRole;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @XmlTransient
    public List<FeedbackReply> getFeedbackReplyList() {
        return feedbackReplyList;
    }

    public void setFeedbackReplyList(List<FeedbackReply> feedbackReplyList) {
        this.feedbackReplyList = feedbackReplyList;
    }

    @XmlTransient
    public List<PromotionItem> getPromotionItemList() {
        return promotionItemList;
    }

    public void setPromotionItemList(List<PromotionItem> promotionItemList) {
        this.promotionItemList = promotionItemList;
    }

    @XmlTransient
    public List<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

    @XmlTransient
    public List<CommentReply> getCommentReplyList() {
        return commentReplyList;
    }

    public void setCommentReplyList(List<CommentReply> commentReplyList) {
        this.commentReplyList = commentReplyList;
    }

    public String encodeImageToString() {
        return Base64.getEncoder().encodeToString(this.profileImg);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stfId != null ? stfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.stfId == null && other.stfId != null) || (this.stfId != null && !this.stfId.equals(other.stfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "staffList.add(new Staff(\"" + stfId + "\"," + stfName + "\"," + stfContact + "\"," + stfEmail + "\"," + stfSalary + "\"," + stfRole + "));";
    }

}
