
package com.sbr.model.persistence
;
/*No license file found*/
/* ZipCode Class file generated on 5/30/2009 2:35:31 PM */

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

@Table(name="ZipCode")

public class ZipCode {
private static Logger log = LoggerFactory.getLogger(ZipCode.class);

public ZipCode(){};


  
  @Id @GeneratedValue
     private Integer id;

   
  public Integer getId() {
    return id;
  }

  
  public void setId(Integer aid){
    this.id = aid;
  }

  
  
  @Column(name="zipCode")
   private String zipCode;

 
  public String getZipCode() {
    return zipCode;
  }


  public void setZipCode(String azipCode){
    this.zipCode = azipCode;
  }

 
  @Column(name="city")
   private String city;

 
  public String getCity() {
    return city;
  }


  public void setCity(String acity){
    this.city = acity;
  }

 
  @Column(name="stateAbbreviation")
   private String stateAbbreviation;

 
  public String getStateAbbreviation() {
    return stateAbbreviation;
  }


  public void setStateAbbreviation(String astateAbbreviation){
    this.stateAbbreviation = astateAbbreviation;
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

  
  private void copyValues(com.sbr.model.dto.ZipCode aZipCode){
    if(aZipCode.getId() > 0){
      this.id = aZipCode.getId();
    }
    
  this.zipCode = aZipCode.getZipCode(); 
  this.city = aZipCode.getCity(); 
  this.stateAbbreviation = aZipCode.getStateAbbreviation(); 
  this.createdBy = aZipCode.getCreatedBy(); 
  this.creationDate = aZipCode.getCreationDate(); 
  this.creationIP = aZipCode.getCreationIP(); 
  this.modifiedBy = aZipCode.getModifiedBy(); 
  this.modificationDate = aZipCode.getModificationDate(); 
  this.modificationIP = aZipCode.getModificationIP(); 
  this.deleted = aZipCode.getDeleted(); 
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

  
  public void save(Connection aConnection, com.sbr.model.dto.ZipCode aZipCode) {
    copyValues(aZipCode);
    save(aConnection);
  }

  
  public void delete(Connection aConnection, String createdBy, String creationIP){
    this.setDeleted(true);
    this.save(aConnection, createdBy, creationIP);
  }

  
  public void delete(Connection aConnection, com.sbr.model.dto.ZipCode aZipCode){
    copyValues(aZipCode);
    this.setDeleted(true);
    save(aConnection);
  }

  
  public static ZipCode loadObject(Connection aConnection, int anId){
    Session session = null;
    try {
    session = HibernateUtil.getSingleton().getSession(aConnection);
    ZipCode result = (ZipCode) session.load(ZipCode.class, anId);
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
