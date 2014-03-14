
package com.sbr.model.dto
;
/*No license file found*/
/* Contact Class file generated on 6/10/2009 8:32:45 PM */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.sql.Connection;
import it.gotoandplay.smartfoxserver.lib.ActionscriptObject;

public class Contact {

public static String ID = "id";
 public static String CATEGORY = "category";
 public static String SICCODE = "sicCode";
 public static String FIRSTNAME = "firstName";
 public static String LASTNAME = "lastName";
 public static String COMPANY = "Company";
 public static String ADDRESS = "Address";
 public static String CITY = "City";
 public static String STATE = "State";
 public static String ZIP = "Zip";
 public static String PHONE = "Phone";
 public static String FAX = "Fax";
 public static String NOTES = "notes";
 public static String BROCHURESENT = "brochureSent";
 public static String BROCHURESENTDATE = "brochureSentDate";
 public static String ISCUSTOMER = "isCustomer";
 public static String CREATIONDATE = "creationDate";
 public static String CREATEDBY = "createdBy";
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

 
   private String category;

 
  public String getCategory() {
    return category;
  }


  public void setCategory(String acategory){
    this.category = acategory;
  }

 
   private String sicCode;

 
  public String getSicCode() {
    return sicCode;
  }


  public void setSicCode(String asicCode){
    this.sicCode = asicCode;
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

 
   private String Company;

 
  public String getCompany() {
    return Company;
  }


  public void setCompany(String aCompany){
    this.Company = aCompany;
  }

 
   private String Address;

 
  public String getAddress() {
    return Address;
  }


  public void setAddress(String aAddress){
    this.Address = aAddress;
  }

 
   private String City;

 
  public String getCity() {
    return City;
  }


  public void setCity(String aCity){
    this.City = aCity;
  }

 
   private String State;

 
  public String getState() {
    return State;
  }


  public void setState(String aState){
    this.State = aState;
  }

 
   private String Zip;

 
  public String getZip() {
    return Zip;
  }


  public void setZip(String aZip){
    this.Zip = aZip;
  }

 
   private String Phone;

 
  public String getPhone() {
    return Phone;
  }


  public void setPhone(String aPhone){
    this.Phone = aPhone;
  }

 
   private String Fax;

 
  public String getFax() {
    return Fax;
  }


  public void setFax(String aFax){
    this.Fax = aFax;
  }

 
   private String notes;

 
  public String getNotes() {
    return notes;
  }


  public void setNotes(String anotes){
    this.notes = anotes;
  }

 
   private Boolean brochureSent;

 
  public Boolean getBrochureSent() {
    return brochureSent;
  }


  public void setBrochureSent(Boolean abrochureSent){
    this.brochureSent = abrochureSent;
  }

 
   private java.util.Date brochureSentDate;

 
  public java.util.Date getBrochureSentDate() {
    return brochureSentDate;
  }


  public void setBrochureSentDate(java.util.Date abrochureSentDate){
    this.brochureSentDate = abrochureSentDate;
  }

 
   private Boolean isCustomer;

 
  public Boolean getIsCustomer() {
    return isCustomer;
  }


  public void setIsCustomer(Boolean aisCustomer){
    this.isCustomer = aisCustomer;
  }

 
   private java.util.Date creationDate;

 
  public java.util.Date getCreationDate() {
    return creationDate;
  }


  public void setCreationDate(java.util.Date acreationDate){
    this.creationDate = acreationDate;
  }

 
   private String createdBy;

 
  public String getCreatedBy() {
    return createdBy;
  }


  public void setCreatedBy(String acreatedBy){
    this.createdBy = acreatedBy;
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

 

  public Contact(){}


  public Contact(com.sbr.model.persistence.Contact aContact){
    
  this.id = aContact.getId(); 
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



  private void copyValues(com.sbr.model.persistence.Contact aContact){
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


public void save(Connection aConnection) {
  com.sbr.model.persistence.Contact aContact = new com.sbr.model.persistence.Contact();
  aContact.save(aConnection, this);
  this.copyValues(aContact);
}


  public void delete(Connection aConnection) {
    com.sbr.model.persistence.Contact aContact = new com.sbr.model.persistence.Contact();
    aContact.delete(aConnection, this);
  }


public void updateAO(ActionscriptObject ao) {
  ao.put(ID, "" + id);
 ao.put(CATEGORY, "" + category);
 ao.put(SICCODE, "" + sicCode);
 ao.put(FIRSTNAME, "" + firstName);
 ao.put(LASTNAME, "" + lastName);
 ao.put(COMPANY, "" + Company);
 ao.put(ADDRESS, "" + Address);
 ao.put(CITY, "" + City);
 ao.put(STATE, "" + State);
 ao.put(ZIP, "" + Zip);
 ao.put(PHONE, "" + Phone);
 ao.put(FAX, "" + Fax);
 ao.put(NOTES, "" + notes);
 ao.put(BROCHURESENT, "" + brochureSent);
 ao.put(BROCHURESENTDATE, "" + brochureSentDate);
 ao.put(ISCUSTOMER, "" + isCustomer);
 ao.put(CREATIONDATE, "" + creationDate);
 ao.put(CREATEDBY, "" + createdBy);
 ao.put(CREATIONIP, "" + creationIP);
 ao.put(MODIFIEDBY, "" + modifiedBy);
 ao.put(MODIFICATIONDATE, "" + modificationDate);
 ao.put(MODIFICATIONIP, "" + modificationIP);
 ao.put(DELETED, "" + deleted);
                        
}

}//End of class
