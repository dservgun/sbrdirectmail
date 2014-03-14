
package com.sbr.model.dto
;
/*No license file found*/
/* Note Class file generated on 6/4/2009 11:13:02 AM */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.sql.Connection;
import it.gotoandplay.smartfoxserver.lib.ActionscriptObject;

public class Note {

public static String ID = "id";
 public static String NOTES = "notes";
 public static String CONTACTID = "contactId";
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

 
   private String notes;

 
  public String getNotes() {
    return notes;
  }


  public void setNotes(String anotes){
    this.notes = anotes;
  }

 
   private Integer contactId;

 
  public Integer getContactId() {
    return contactId;
  }


  public void setContactId(Integer acontactId){
    this.contactId = acontactId;
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

 

  public Note(){}


  public Note(com.sbr.model.persistence.Note aNote){
    
  this.id = aNote.getId(); 
  this.notes = aNote.getNotes(); 
  this.contactId = aNote.getContactId(); 
  this.createdBy = aNote.getCreatedBy(); 
  this.creationDate = aNote.getCreationDate(); 
  this.creationIP = aNote.getCreationIP(); 
  this.modifiedBy = aNote.getModifiedBy(); 
  this.modificationDate = aNote.getModificationDate(); 
  this.modificationIP = aNote.getModificationIP(); 
  this.deleted = aNote.getDeleted(); 
  }



  private void copyValues(com.sbr.model.persistence.Note aNote){
    if(aNote.getId() > 0){
      this.id = aNote.getId();
    }
    
  this.notes = aNote.getNotes(); 
  this.contactId = aNote.getContactId(); 
  this.createdBy = aNote.getCreatedBy(); 
  this.creationDate = aNote.getCreationDate(); 
  this.creationIP = aNote.getCreationIP(); 
  this.modifiedBy = aNote.getModifiedBy(); 
  this.modificationDate = aNote.getModificationDate(); 
  this.modificationIP = aNote.getModificationIP(); 
  this.deleted = aNote.getDeleted(); 
  }


public void save(Connection aConnection) {
  com.sbr.model.persistence.Note aNote = new com.sbr.model.persistence.Note();
  aNote.save(aConnection, this);
  this.copyValues(aNote);
}


  public void delete(Connection aConnection) {
    com.sbr.model.persistence.Note aNote = new com.sbr.model.persistence.Note();
    aNote.delete(aConnection, this);
  }


public void updateAO(ActionscriptObject ao) {
  ao.put(ID, "" + id);
 ao.put(NOTES, "" + notes);
 ao.put(CONTACTID, "" + contactId);
 ao.put(CREATEDBY, "" + createdBy);
 ao.put(CREATIONDATE, "" + creationDate);
 ao.put(CREATIONIP, "" + creationIP);
 ao.put(MODIFIEDBY, "" + modifiedBy);
 ao.put(MODIFICATIONDATE, "" + modificationDate);
 ao.put(MODIFICATIONIP, "" + modificationIP);
 ao.put(DELETED, "" + deleted);
           
}

}//End of class
