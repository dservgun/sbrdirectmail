
package com.sbr.model.persistence
;
/*No license file found*/
/* Contact Class file generated on 6/10/2009 8:32:45 PM */

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

@Table(name="Contact")

public class Contact {
private static Logger log = LoggerFactory.getLogger(Contact.class);

public Contact(){};


  
  @Id @GeneratedValue
     private Integer id;

   
  public Integer getId() {
    return id;
  }

  
  public void setId(Integer aid){
    this.id = aid;
  }

  
  
  @Column(name="category")
   private String category;

 
  public String getCategory() {
    return category;
  }


  public void setCategory(String acategory){
    this.category = acategory;
  }

 
  @Column(name="sicCode")
   private String sicCode;

 
  public String getSicCode() {
    return sicCode;
  }


  public void setSicCode(String asicCode){
    this.sicCode = asicCode;
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

 
  @Column(name="Company")
   private String Company;

 
  public String getCompany() {
    return Company;
  }


  public void setCompany(String aCompany){
    this.Company = aCompany;
  }

 
  @Column(name="Address")
   private String Address;

 
  public String getAddress() {
    return Address;
  }


  public void setAddress(String aAddress){
    this.Address = aAddress;
  }

 
  @Column(name="City")
   private String City;

 
  public String getCity() {
    return City;
  }


  public void setCity(String aCity){
    this.City = aCity;
  }

 
  @Column(name="State")
   private String State;

 
  public String getState() {
    return State;
  }


  public void setState(String aState){
    this.State = aState;
  }

 
  @Column(name="Zip")
   private String Zip;

 
  public String getZip() {
    return Zip;
  }


  public void setZip(String aZip){
    this.Zip = aZip;
  }

 
  @Column(name="Phone")
   private String Phone;

 
  public String getPhone() {
    return Phone;
  }


  public void setPhone(String aPhone){
    this.Phone = aPhone;
  }

 
  @Column(name="Fax")
   private String Fax;

 
  public String getFax() {
    return Fax;
  }


  public void setFax(String aFax){
    this.Fax = aFax;
  }

 
  @Column(name="notes")
   private String notes;

 
  public String getNotes() {
    return notes;
  }


  public void setNotes(String anotes){
    this.notes = anotes;
  }

 
  @Column(name="brochureSent")
   private Boolean brochureSent;

 
  public Boolean getBrochureSent() {
    return brochureSent;
  }


  public void setBrochureSent(Boolean abrochureSent){
    this.brochureSent = abrochureSent;
  }

 
  @Column(name="brochureSentDate")
   private java.util.Date brochureSentDate;

 
  public java.util.Date getBrochureSentDate() {
    return brochureSentDate;
  }


  public void setBrochureSentDate(java.util.Date abrochureSentDate){
    this.brochureSentDate = abrochureSentDate;
  }

 
  @Column(name="isCustomer")
   private Boolean isCustomer;

 
  public Boolean getIsCustomer() {
    return isCustomer;
  }


  public void setIsCustomer(Boolean aisCustomer){
    this.isCustomer = aisCustomer;
  }

 
  @Column(name="creationDate")
   private java.util.Date creationDate;

 
  public java.util.Date getCreationDate() {
    return creationDate;
  }


  public void setCreationDate(java.util.Date acreationDate){
    this.creationDate = acreationDate;
  }

 
  @Column(name="createdBy")
   private String createdBy;

 
  public String getCreatedBy() {
    return createdBy;
  }


  public void setCreatedBy(String acreatedBy){
    this.createdBy = acreatedBy;
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

  
  private void copyValues(com.sbr.model.dto.Contact aContact){
    if(aContact.getId() > 0){
      this.id = aContact.getId();
    }
    
  this.category = aContact.getCategory(); 
  this.sicCode = aContact.getSicCode(); 
  this.firstName = aContact.getFirstName(); 
  this.lastName = aContact.getLastName(); 
  this.Company = aContact.getCompany(); 
  this.Address = aContact.getAddress(); 
  this.City = aContact.getCity(); 
  this.State = aContact.getState(); 
  this.Zip = aContact.getZip(); 
  this.Phone = aContact.getPhone(); 
  this.Fax = aContact.getFax(); 
  this.notes = aContact.getNotes(); 
  this.brochureSent = aContact.getBrochureSent(); 
  this.brochureSentDate = aContact.getBrochureSentDate(); 
  this.isCustomer = aContact.getIsCustomer(); 
  this.creationDate = aContact.getCreationDate(); 
  this.createdBy = aContact.getCreatedBy(); 
  this.creationIP = aContact.getCreationIP(); 
  this.modifiedBy = aContact.getModifiedBy(); 
  this.modificationDate = aContact.getModificationDate(); 
  this.modificationIP = aContact.getModificationIP(); 
  this.deleted = aContact.getDeleted(); 
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

  
  public void save(Connection aConnection, com.sbr.model.dto.Contact aContact) {
    copyValues(aContact);
    save(aConnection);
  }

  
  public void delete(Connection aConnection, String createdBy, String creationIP){
    this.setDeleted(true);
    this.save(aConnection, createdBy, creationIP);
  }

  
  public void delete(Connection aConnection, com.sbr.model.dto.Contact aContact){
    copyValues(aContact);
    this.setDeleted(true);
    save(aConnection);
  }

  
  public static Contact loadObject(Connection aConnection, int anId){
    Session session = null;
    try {
    session = HibernateUtil.getSingleton().getSession(aConnection);
    Contact result = (Contact) session.load(Contact.class, anId);
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
