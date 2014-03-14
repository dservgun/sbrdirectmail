package com.sbr.model.queries;
import com.sbr.util.HibernateUtil;
import org.hibernate.Session;
import java.sql.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ZipCode {
  public static Logger log = LoggerFactory.getLogger(ZipCode.class);
  
  public static com.sbr.model.dto.ZipCode queryCityByZip(Connection aConnection, String aZipCode){
    Session session = null;
    try{
      session = HibernateUtil.getSingleton().getSession(aConnection);
      List<com.sbr.model.persistence.ZipCode> persistentContacts 
        = (List<com.sbr.model.persistence.ZipCode>)session.createQuery("from ZipCode where zipCode=?").
          setString(0,aZipCode).list();
      if(persistentContacts == null || persistentContacts.isEmpty()){
        log.info("No zip code found " + aZipCode);
        return null;
      }
      if(persistentContacts.size() > 1){
        throw new RuntimeException("Duplicate zipcodes found " + aZipCode);
      }
      com.sbr.model.dto.ZipCode result = new com.sbr.model.dto.ZipCode(persistentContacts.get(0));      
      return result;
    }catch(Exception e){
      throw new RuntimeException(e);
    }finally {
      if(session != null){
        session.close();
      }
      }    
  }
}