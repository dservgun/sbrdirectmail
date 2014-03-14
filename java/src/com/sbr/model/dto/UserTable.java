
package com.sbr.model.dto
;
/*No license file found*/
/* UserTable Class file generated on 6/10/2009 8:32:45 PM */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.sql.Connection;
import it.gotoandplay.smartfoxserver.lib.ActionscriptObject;

public class UserTable {

public static String ID = "id";
 public static String FIRSTNAME = "firstName";
 public static String LASTNAME = "lastName";
 public static String LOGINNAME = "loginName";
 public static String PASSWORD = "password";
 public static String ISADMIN = "isAdmin";
 public static String EMAIL = "email";
 public static String PHONE = "phone";
 public static String FAX = "fax";
 public static String SMS = "sms";
 public static String TWITTERID = "twitterId";
 public static String ISAPPROVED = "isApproved";
 public static String USERIMAGE = "userImage";
 public static String DEFAULTCURRENCY = "defaultCurrency";
 public static String DEFAULTLANGUAGE = "defaultLanguage";
 public static String DEFAULTDATEFORMAT = "defaultDateFormat";
 public static String LASTLOGIN = "lastLogin";
 public static String LASTUPDATED = "lastUpdated";
 public static String CREATEDBY = "createdBy";
 public static String CREATIONDATE = "creationDate";
 public static String CREATIONIP = "creationIP";
 public static String MODIFIEDBY = "modifiedBy";
 public static String MODIFICATIONDATE = "modificationDate";
 public static String MODIFICATIONIP = "modificationIP";
 public static String DELETED = "deleted";
 

   private Integer id;

 
  public Integer getId() {
    return id;
  }


  public void setId(Integer aid){
    this.id = aid;
  }

 
   private String firstName;

 
  public String getFirstName() {
    return firstName;
  }


  public void setFirstName(String afirstName){
    this.firstName = afirstName;
  }

 
   private String lastName;

 
  public String getLastName() {
    return lastName;
  }


  public void setLastName(String alastName){
    this.lastName = alastName;
  }

 
   private String loginName;

 
  public String getLoginName() {
    return loginName;
  }


  public void setLoginName(String aloginName){
    this.loginName = aloginName;
  }

 
   private String password;

 
  public String getPassword() {
    return password;
  }


  public void setPassword(String apassword){
    this.password = apassword;
  }

 
   private Boolean isAdmin;

 
  public Boolean getIsAdmin() {
    return isAdmin;
  }


  public void setIsAdmin(Boolean aisAdmin){
    this.isAdmin = aisAdmin;
  }

 
   private String email;

 
  public String getEmail() {
    return email;
  }


  public void setEmail(String aemail){
    this.email = aemail;
  }

 
   private String phone;

 
  public String getPhone() {
    return phone;
  }


  public void setPhone(String aphone){
    this.phone = aphone;
  }

 
   private String fax;

 
  public String getFax() {
    return fax;
  }


  public void setFax(String afax){
    this.fax = afax;
  }

 
   private String sms;

 
  public String getSms() {
    return sms;
  }


  public void setSms(String asms){
    this.sms = asms;
  }

 
   private String twitterId;

 
  public String getTwitterId() {
    return twitterId;
  }


  public void setTwitterId(String atwitterId){
    this.twitterId = atwitterId;
  }

 
   private Boolean isApproved;

 
  public Boolean getIsApproved() {
    return isApproved;
  }


  public void setIsApproved(Boolean aisApproved){
    this.isApproved = aisApproved;
  }

 
   private String userImage;

 
  public String getUserImage() {
    return userImage;
  }


  public void setUserImage(String auserImage){
    this.userImage = auserImage;
  }

 
   private Integer defaultCurrency;

 
  public Integer getDefaultCurrency() {
    return defaultCurrency;
  }


  public void setDefaultCurrency(Integer adefaultCurrency){
    this.defaultCurrency = adefaultCurrency;
  }

 
   private Integer defaultLanguage;

 
  public Integer getDefaultLanguage() {
    return defaultLanguage;
  }


  public void setDefaultLanguage(Integer adefaultLanguage){
    this.defaultLanguage = adefaultLanguage;
  }

 
   private String defaultDateFormat;

 
  public String getDefaultDateFormat() {
    return defaultDateFormat;
  }


  public void setDefaultDateFormat(String adefaultDateFormat){
    this.defaultDateFormat = adefaultDateFormat;
  }

 
   private java.util.Date lastLogin;

 
  public java.util.Date getLastLogin() {
    return lastLogin;
  }


  public void setLastLogin(java.util.Date alastLogin){
    this.lastLogin = alastLogin;
  }

 
   private java.util.Date lastUpdated;

 
  public java.util.Date getLastUpdated() {
    return lastUpdated;
  }


  public void setLastUpdated(java.util.Date alastUpdated){
    this.lastUpdated = alastUpdated;
  }

 
   private String createdBy;

 
  public String getCreatedBy() {
    return createdBy;
  }


  public void setCreatedBy(String acreatedBy){
    this.createdBy = acreatedBy;
  }

 
   private java.util.Date creationDate;

 
  public java.util.Date getCreationDate() {
    return creationDate;
  }


  public void setCreationDate(java.util.Date acreationDate){
    this.creationDate = acreationDate;
  }

 
   private String creationIP;

 
  public String getCreationIP() {
    return creationIP;
  }


  public void setCreationIP(String acreationIP){
    this.creationIP = acreationIP;
  }

 
   private String modifiedBy;

 
  public String getModifiedBy() {
    return modifiedBy;
  }


  public void setModifiedBy(String amodifiedBy){
    this.modifiedBy = amodifiedBy;
  }

 
   private java.util.Date modificationDate;

 
  public java.util.Date getModificationDate() {
    return modificationDate;
  }


  public void setModificationDate(java.util.Date amodificationDate){
    this.modificationDate = amodificationDate;
  }

 
   private String modificationIP;

 
  public String getModificationIP() {
    return modificationIP;
  }


  public void setModificationIP(String amodificationIP){
    this.modificationIP = amodificationIP;
  }

 
   private Boolean deleted;

 
  public Boolean getDeleted() {
    return deleted;
  }


  public void setDeleted(Boolean adeleted){
    this.deleted = adeleted;
  }

 

  public UserTable(){}


  public UserTable(com.sbr.model.persistence.UserTable aUserTable){
    
  this.id = aUserTable.getId(); 
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



  private void copyValues(com.sbr.model.persistence.UserTable aUserTable){
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


public void save(Connection aConnection) {
  com.sbr.model.persistence.UserTable aUserTable = new com.sbr.model.persistence.UserTable();
  aUserTable.save(aConnection, this);
  this.copyValues(aUserTable);
}


  public void delete(Connection aConnection) {
    com.sbr.model.persistence.UserTable aUserTable = new com.sbr.model.persistence.UserTable();
    aUserTable.delete(aConnection, this);
  }


public void updateAO(ActionscriptObject ao) {
  ao.put(ID, "" + id);
 ao.put(FIRSTNAME, "" + firstName);
 ao.put(LASTNAME, "" + lastName);
 ao.put(LOGINNAME, "" + loginName);
 ao.put(PASSWORD, "" + password);
 ao.put(ISADMIN, "" + isAdmin);
 ao.put(EMAIL, "" + email);
 ao.put(PHONE, "" + phone);
 ao.put(FAX, "" + fax);
 ao.put(SMS, "" + sms);
 ao.put(TWITTERID, "" + twitterId);
 ao.put(ISAPPROVED, "" + isApproved);
 ao.put(USERIMAGE, "" + userImage);
 ao.put(DEFAULTCURRENCY, "" + defaultCurrency);
 ao.put(DEFAULTLANGUAGE, "" + defaultLanguage);
 ao.put(DEFAULTDATEFORMAT, "" + defaultDateFormat);
 ao.put(LASTLOGIN, "" + lastLogin);
 ao.put(LASTUPDATED, "" + lastUpdated);
 ao.put(CREATEDBY, "" + createdBy);
 ao.put(CREATIONDATE, "" + creationDate);
 ao.put(CREATIONIP, "" + creationIP);
 ao.put(MODIFIEDBY, "" + modifiedBy);
 ao.put(MODIFICATIONDATE, "" + modificationDate);
 ao.put(MODIFICATIONIP, "" + modificationIP);
 ao.put(DELETED, "" + deleted);
                          
}

}//End of class
