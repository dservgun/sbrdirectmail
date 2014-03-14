
package com.sbr.model.dto
;
/*No license file found*/
/* Reminder Class file generated on 6/4/2009 11:13:02 AM */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.sql.Connection;
import it.gotoandplay.smartfoxserver.lib.ActionscriptObject;

public class Reminder {

public static String ID = "id";
 public static String REMINDERDATE = "reminderDate";
 public static String CONTACTID = "contactId";
 public static String DESCRIPTION = "description";
 public static String REMINDERACTION = "reminderAction";
 public static String ACTIONTAKEN = "actionTaken";
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

 
   private java.util.Date reminderDate;

 
  public java.util.Date getReminderDate() {
    return reminderDate;
  }


  public void setReminderDate(java.util.Date areminderDate){
    this.reminderDate = areminderDate;
  }

 
   private Integer contactId;

 
  public Integer getContactId() {
    return contactId;
  }


  public void setContactId(Integer acontactId){
    this.contactId = acontactId;
  }

 
   private String description;

 
  public String getDescription() {
    return description;
  }


  public void setDescription(String adescription){
    this.description = adescription;
  }

 
   private String reminderAction;

 
  public String getReminderAction() {
    return reminderAction;
  }


  public void setReminderAction(String areminderAction){
    this.reminderAction = areminderAction;
  }

 
   private Boolean actionTaken;

 
  public Boolean getActionTaken() {
    return actionTaken;
  }


  public void setActionTaken(Boolean aactionTaken){
    this.actionTaken = aactionTaken;
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

 

  public Reminder(){}


  public Reminder(com.sbr.model.persistence.Reminder aReminder){
    
  this.id = aReminder.getId(); 
  this.reminderDate = aReminder.getReminderDate(); 
  this.contactId = aReminder.getContactId(); 
  this.description = aReminder.getDescription(); 
  this.reminderAction = aReminder.getReminderAction(); 
  this.actionTaken = aReminder.getActionTaken(); 
  this.createdBy = aReminder.getCreatedBy(); 
  this.creationDate = aReminder.getCreationDate(); 
  this.creationIP = aReminder.getCreationIP(); 
  this.modifiedBy = aReminder.getModifiedBy(); 
  this.modificationDate = aReminder.getModificationDate(); 
  this.modificationIP = aReminder.getModificationIP(); 
  this.deleted = aReminder.getDeleted(); 
  }



  private void copyValues(com.sbr.model.persistence.Reminder aReminder){
    if(aReminder.getId() > 0){
      this.id = aReminder.getId();
    }
    
  this.reminderDate = aReminder.getReminderDate(); 
  this.contactId = aReminder.getContactId(); 
  this.description = aReminder.getDescription(); 
  this.reminderAction = aReminder.getReminderAction(); 
  this.actionTaken = aReminder.getActionTaken(); 
  this.createdBy = aReminder.getCreatedBy(); 
  this.creationDate = aReminder.getCreationDate(); 
  this.creationIP = aReminder.getCreationIP(); 
  this.modifiedBy = aReminder.getModifiedBy(); 
  this.modificationDate = aReminder.getModificationDate(); 
  this.modificationIP = aReminder.getModificationIP(); 
  this.deleted = aReminder.getDeleted(); 
  }


public void save(Connection aConnection) {
  com.sbr.model.persistence.Reminder aReminder = new com.sbr.model.persistence.Reminder();
  aReminder.save(aConnection, this);
  this.copyValues(aReminder);
}


  public void delete(Connection aConnection) {
    com.sbr.model.persistence.Reminder aReminder = new com.sbr.model.persistence.Reminder();
    aReminder.delete(aConnection, this);
  }


public void updateAO(ActionscriptObject ao) {
  ao.put(ID, "" + id);
 ao.put(REMINDERDATE, "" + reminderDate);
 ao.put(CONTACTID, "" + contactId);
 ao.put(DESCRIPTION, "" + description);
 ao.put(REMINDERACTION, "" + reminderAction);
 ao.put(ACTIONTAKEN, "" + actionTaken);
 ao.put(CREATEDBY, "" + createdBy);
 ao.put(CREATIONDATE, "" + creationDate);
 ao.put(CREATIONIP, "" + creationIP);
 ao.put(MODIFIEDBY, "" + modifiedBy);
 ao.put(MODIFICATIONDATE, "" + modificationDate);
 ao.put(MODIFICATIONIP, "" + modificationIP);
 ao.put(DELETED, "" + deleted);
              
}

}//End of class
