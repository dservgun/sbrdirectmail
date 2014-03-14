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

public class Contact {
  public static Logger log = LoggerFactory.getLogger(Contact.class);
  
  public static com.sbr.model.dto.Contact loadDTOObject(Session session, int id){
    if(session == null){
      throw new RuntimeException("Invalid session");
    }
    com.sbr.model.persistence.Contact contact = 
      (com.sbr.model.persistence.Contact)session.load(com.sbr.model.persistence.Contact.class, id);
    return new com.sbr.model.dto.Contact(contact);
    
  }
  public static com.sbr.model.dto.Contact loadDTOObject(Connection aConnection, int id){
    Session session = null;
    try {
      session = HibernateUtil.getSingleton().getSession(aConnection);
      return loadDTOObject(session, id);
    }catch(Exception e){
      throw new RuntimeException(e);
    }finally {
      if(session != null){
        session.close();
      }
    }
  }
  
  public static com.sbr.model.dto.Contact queryContact(Connection aConnection, String aCompanyName){
    Session session = null;
    try{
      session = HibernateUtil.getSingleton().getSession(aConnection);
      List<com.sbr.model.persistence.Contact> persistentContacts 
        = (List<com.sbr.model.persistence.Contact>)session.createQuery("from Contact where Company=?").
          setString(0,aCompanyName).list();
      if(persistentContacts == null || persistentContacts.isEmpty()){
        return null;
      }
      if(persistentContacts.size() > 1){
        throw new RuntimeException("Duplicate companies found " + aCompanyName);
      }
      com.sbr.model.dto.Contact result = new com.sbr.model.dto.Contact(persistentContacts.get(0));      
      return result;
    }catch(Exception e){
      throw new RuntimeException(e);
    }finally {
      if(session != null){
        session.close();
      }
      }
    }
    
  public static List<com.sbr.model.dto.Contact> queryAllContacts(Connection aConnection, String aCompanyWildCard){
    Session session = null;
    try {
      session = HibernateUtil.getSingleton().getSession(aConnection);
      List<com.sbr.model.persistence.Contact> persistentContacts = null;
      if(aCompanyWildCard == null || aCompanyWildCard == ""){
        persistentContacts =(List<com.sbr.model.persistence.Contact>)session.
        createQuery("from Contact").list();
      }else {
        persistentContacts = (List<com.sbr.model.persistence.Contact>)session.
          createQuery("from Contact where Company like ?").setString(0, aCompanyWildCard).list();
      }
      List<com.sbr.model.dto.Contact> resultList = new ArrayList<com.sbr.model.dto.Contact>();
      for(com.sbr.model.persistence.Contact aContact : persistentContacts){
        com.sbr.model.dto.Contact contact = new com.sbr.model.dto.Contact(aContact);
        resultList.add(contact);
      }
      return resultList;
    }catch(Exception e){
      throw new RuntimeException(e);
    }finally{
      if(session != null){
        session.close();
      }
    }
  }
}
