
package com.sbr.model.dto
;
/*No license file found*/
/* ZipCode Class file generated on 5/30/2009 2:35:31 PM */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.sql.Connection;
import it.gotoandplay.smartfoxserver.lib.ActionscriptObject;

public class ZipCode {

public static String ID = "id";
 public static String ZIPCODE = "zipCode";
 public static String CITY = "city";
 public static String STATEABBREVIATION = "stateAbbreviation";
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

 
   private String zipCode;

 
  public String getZipCode() {
    return zipCode;
  }


  public void setZipCode(String azipCode){
    this.zipCode = azipCode;
  }

 
   private String city;

 
  public String getCity() {
    return city;
  }


  public void setCity(String acity){
    this.city = acity;
  }

 
   private String stateAbbreviation;

 
  public String getStateAbbreviation() {
    return stateAbbreviation;
  }


  public void setStateAbbreviation(String astateAbbreviation){
    this.stateAbbreviation = astateAbbreviation;
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

 

  public ZipCode(){}


  public ZipCode(com.sbr.model.persistence.ZipCode aZipCode){
    
  this.id = aZipCode.getId(); 
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



  private void copyValues(com.sbr.model.persistence.ZipCode aZipCode){
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


public void save(Connection aConnection) {
  com.sbr.model.persistence.ZipCode aZipCode = new com.sbr.model.persistence.ZipCode();
  aZipCode.save(aConnection, this);
  this.copyValues(aZipCode);
}


  public void delete(Connection aConnection) {
    com.sbr.model.persistence.ZipCode aZipCode = new com.sbr.model.persistence.ZipCode();
    aZipCode.delete(aConnection, this);
  }


public void updateAO(ActionscriptObject ao) {
  ao.put(ID, "" + id);
 ao.put(ZIPCODE, "" + zipCode);
 ao.put(CITY, "" + city);
 ao.put(STATEABBREVIATION, "" + stateAbbreviation);
 ao.put(CREATEDBY, "" + createdBy);
 ao.put(CREATIONDATE, "" + creationDate);
 ao.put(CREATIONIP, "" + creationIP);
 ao.put(MODIFIEDBY, "" + modifiedBy);
 ao.put(MODIFICATIONDATE, "" + modificationDate);
 ao.put(MODIFICATIONIP, "" + modificationIP);
 ao.put(DELETED, "" + deleted);
            
}

}//End of class
