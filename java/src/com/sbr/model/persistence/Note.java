
package com.sbr.model.persistence
;
/*No license file found*/
/* Note Class file generated on 6/4/2009 11:13:02 AM */

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

@Table(name="Note")

public class Note {
private static Logger log = LoggerFactory.getLogger(Note.class);

public Note(){};


  
  @Id @GeneratedValue
     private Integer id;

   
  public Integer getId() {
    return id;
  }

  
  public void setId(Integer aid){
    this.id = aid;
  }

  
  
  @Column(name="notes")
   private String notes;

 
  public String getNotes() {
    return notes;
  }


  public void setNotes(String anotes){
    this.notes = anotes;
  }

 
  @Column(name="contactId")
   private Integer contactId;

 
  public Integer getContactId() {
    return contactId;
  }


  public void setContactId(Integer acontactId){
    this.contactId = acontactId;
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

  
  private void copyValues(com.sbr.model.dto.Note aNote){
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

  
  public void save(Connection aConnection, com.sbr.model.dto.Note aNote) {
    copyValues(aNote);
    save(aConnection);
  }

  
  public void delete(Connection aConnection, String createdBy, String creationIP){
    this.setDeleted(true);
    this.save(aConnection, createdBy, creationIP);
  }

  
  public void delete(Connection aConnection, com.sbr.model.dto.Note aNote){
    copyValues(aNote);
    this.setDeleted(true);
    save(aConnection);
  }

  
  public static Note loadObject(Connection aConnection, int anId){
    Session session = null;
    try {
    session = HibernateUtil.getSingleton().getSession(aConnection);
    Note result = (Note) session.load(Note.class, anId);
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
