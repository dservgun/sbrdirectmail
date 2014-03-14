
package com.sbr.model.persistence
;
/*No license file found*/
/* UserTable Class file generated on 6/10/2009 8:32:45 PM */

import javax.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import java.sql.Connection;
import com.sbr.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
@Entity

@Table(name="UserTable")

public class UserTable {
private static Logger log = LoggerFactory.getLogger(UserTable.class);

public UserTable(){};


  
  @Id @GeneratedValue
     private Integer id;

   
  public Integer getId() {
    return id;
  }

  
  public void setId(Integer aid){
    this.id = aid;
  }

  
  
  @Column(name="firstName")
   private String firstName;

 
  public String getFirstName() {
    return firstName;
  }


  public void setFirstName(String afirstName){
    this.firstName = afirstName;
  }

 
  @Column(name="lastName")
   private String lastName;

 
  public String getLastName() {
    return lastName;
  }


  public void setLastName(String alastName){
    this.lastName = alastName;
  }

 
  @Column(name="loginName")
   private String loginName;

 
  public String getLoginName() {
    return loginName;
  }


  public void setLoginName(String aloginName){
    this.loginName = aloginName;
  }

 
  @Column(name="password")
   private String password;

 
  public String getPassword() {
    return password;
  }


  public void setPassword(String apassword){
    this.password = apassword;
  }

 
  @Column(name="isAdmin")
   private Boolean isAdmin;

 
  public Boolean getIsAdmin() {
    return isAdmin;
  }


  public void setIsAdmin(Boolean aisAdmin){
    this.isAdmin = aisAdmin;
  }

 
  @Column(name="email")
   private String email;

 
  public String getEmail() {
    return email;
  }


  public void setEmail(String aemail){
    this.email = aemail;
  }

 
  @Column(name="phone")
   private String phone;

 
  public String getPhone() {
    return phone;
  }


  public void setPhone(String aphone){
    this.phone = aphone;
  }

 
  @Column(name="fax")
   private String fax;

 
  public String getFax() {
    return fax;
  }


  public void setFax(String afax){
    this.fax = afax;
  }

 
  @Column(name="sms")
   private String sms;

 
  public String getSms() {
    return sms;
  }


  public void setSms(String asms){
    this.sms = asms;
  }

 
  @Column(name="twitterId")
   private String twitterId;

 
  public String getTwitterId() {
    return twitterId;
  }


  public void setTwitterId(String atwitterId){
    this.twitterId = atwitterId;
  }

 
  @Column(name="isApproved")
   private Boolean isApproved;

 
  public Boolean getIsApproved() {
    return isApproved;
  }


  public void setIsApproved(Boolean aisApproved){
    this.isApproved = aisApproved;
  }

 
  @Column(name="userImage")
   private String userImage;

 
  public String getUserImage() {
    return userImage;
  }


  public void setUserImage(String auserImage){
    this.userImage = auserImage;
  }

 
  @Column(name="defaultCurrency")
   private Integer defaultCurrency;

 
  public Integer getDefaultCurrency() {
    return defaultCurrency;
  }


  public void setDefaultCurrency(Integer adefaultCurrency){
    this.defaultCurrency = adefaultCurrency;
  }

 
  @Column(name="defaultLanguage")
   private Integer defaultLanguage;

 
  public Integer getDefaultLanguage() {
    return defaultLanguage;
  }


  public void setDefaultLanguage(Integer adefaultLanguage){
    this.defaultLanguage = adefaultLanguage;
  }

 
  @Column(name="defaultDateFormat")
   private String defaultDateFormat;

 
  public String getDefaultDateFormat() {
    return defaultDateFormat;
  }


  public void setDefaultDateFormat(String adefaultDateFormat){
    this.defaultDateFormat = adefaultDateFormat;
  }

 
  @Column(name="lastLogin")
   private java.util.Date lastLogin;

 
  public java.util.Date getLastLogin() {
    return lastLogin;
  }


  public void setLastLogin(java.util.Date alastLogin){
    this.lastLogin = alastLogin;
  }

 
  @Column(name="lastUpdated")
   private java.util.Date lastUpdated;

 
  public java.util.Date getLastUpdated() {
    return lastUpdated;
  }


  public void setLastUpdated(java.util.Date alastUpdated){
    this.lastUpdated = alastUpdated;
  }

 
  @Column(name="createdBy")
   private String createdBy;

 
  public String getCreatedBy() {
    return createdBy;
  }


  public void setCreatedBy(String acreatedBy){
    this.createdBy = acreatedBy;
  }

 
  @Column(name="creationDate")
   private java.util.Date creationDate;

 
  public java.util.Date getCreationDate() {
    return creationDate;
  }


  public void setCreationDate(java.util.Date acreationDate){
    this.creationDate = acreationDate;
  }

 
  @Column(name="creationIP")
   private String creationIP;

 
  public String getCreationIP() {
    return creationIP;
  }


  public void setCreationIP(String acreationIP){
    this.creationIP = acreationIP;
  }

 
  @Column(name="modifiedBy")
   private String modifiedBy;

 
  public String getModifiedBy() {
    return modifiedBy;
  }


  public void setModifiedBy(String amodifiedBy){
    this.modifiedBy = amodifiedBy;
  }

 
  @Column(name="modificationDate")
   private java.util.Date modificationDate;

 
  public java.util.Date getModificationDate() {
    return modificationDate;
  }


  public void setModificationDate(java.util.Date amodificationDate){
    this.modificationDate = amodificationDate;
  }

 
  @Column(name="modificationIP")
   private String modificationIP;

 
  public String getModificationIP() {
    return modificationIP;
  }


  public void setModificationIP(String amodificationIP){
    this.modificationIP = amodificationIP;
  }

 
  @Column(name="deleted")
   private Boolean deleted;

 
  public Boolean getDeleted() {
    return deleted;
  }


  public void setDeleted(Boolean adeleted){
    this.deleted = adeleted;
  }

 
  
  private void updateCreationInformation(){
    if(getId() == null){
      this.setCreationDate(new Date());
    }else {
      this.setModificationDate(new Date());
    }
  }

  
public void updateCreationInformation(String createdBy, String anIPAddress){
  if(getId() == null){
    this.setCreatedBy(createdBy);
    this.setCreationIP(anIPAddress);
    this.setCreationDate(new Date());
  }else {
    this.setModifiedBy(createdBy);
    this.setModificationIP(anIPAddress);
    this.setModificationDate(new Date());
  }
}

  
  private void copyValues(com.sbr.model.dto.UserTable aUserTable){
    if(aUserTable.getId() > 0){
      this.id = aUserTable.getId();
    }
    
  this.firstName = aUserTable.getFirstName(); 
  this.lastName = aUserTable.getLastName(); 
  this.loginName = aUserTable.getLoginName(); 
  this.password = aUserTable.getPassword(); 
  this.isAdmin = aUserTable.getIsAdmin(); 
  this.email = aUserTable.getEmail(); 
  this.phone = aUserTable.getPhone(); 
  this.fax = aUserTable.getFax(); 
  this.sms = aUserTable.getSms(); 
  this.twitterId = aUserTable.getTwitterId(); 
  this.isApproved = aUserTable.getIsApproved(); 
  this.userImage = aUserTable.getUserImage(); 
  this.defaultCurrency = aUserTable.getDefaultCurrency(); 
  this.defaultLanguage = aUserTable.getDefaultLanguage(); 
  this.defaultDateFormat = aUserTable.getDefaultDateFormat(); 
  this.lastLogin = aUserTable.getLastLogin(); 
  this.lastUpdated = aUserTable.getLastUpdated(); 
  this.createdBy = aUserTable.getCreatedBy(); 
  this.creationDate = aUserTable.getCreationDate(); 
  this.creationIP = aUserTable.getCreationIP(); 
  this.modifiedBy = aUserTable.getModifiedBy(); 
  this.modificationDate = aUserTable.getModificationDate(); 
  this.modificationIP = aUserTable.getModificationIP(); 
  this.deleted = aUserTable.getDeleted(); 
  }

  
  private void save(Connection aConnection){
    Session session = null;
    try {
    updateCreationInformation();
    session = HibernateUtil.getSingleton().getSession(aConnection);
    session.beginTransaction();
    session.saveOrUpdate(this);
    session.getTransaction().commit();
    } catch(Exception e){
      log.error("Error saving " + this, e);
      if(session != null){
        session.getTransaction().rollback();
      }
      throw new RuntimeException(e);
    }finally{
      if(session != null){
        session.close();
      }
    }
  } //End of save

   
  public void save(Connection aConnection, String createdBy, String creationIP){
    Session session = null;
    try {
    updateCreationInformation(createdBy, creationIP);
    session = HibernateUtil.getSingleton().getSession(aConnection);
    session.beginTransaction();
    session.saveOrUpdate(this);
    session.getTransaction().commit();
    } catch(Exception e){
      log.error("Error saving " + this, e);
      if(session != null){
        session.getTransaction().rollback();
      }
      throw new RuntimeException(e);
    }finally{
      if(session != null){
        session.close();
      }
    }
  }

  
  public void save(Connection aConnection, com.sbr.model.dto.UserTable aUserTable) {
    copyValues(aUserTable);
    save(aConnection);
  }

  
  public void delete(Connection aConnection, String createdBy, String creationIP){
    this.setDeleted(true);
    this.save(aConnection, createdBy, creationIP);
  }

  
  public void delete(Connection aConnection, com.sbr.model.dto.UserTable aUserTable){
    copyValues(aUserTable);
    this.setDeleted(true);
    save(aConnection);
  }

  
  public static UserTable loadObject(Connection aConnection, int anId){
    Session session = null;
    try {
    session = HibernateUtil.getSingleton().getSession(aConnection);
    UserTable result = (UserTable) session.load(UserTable.class, anId);
    return result;
    }catch(Exception e){
      log.error("Error loading instance " + anId, e);      
    }finally{
      if(session != null){
        session.close();
      }
    }
    return null;
  }


}
