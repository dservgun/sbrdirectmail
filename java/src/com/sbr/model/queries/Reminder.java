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

public class Reminder {
  private static Logger log = LoggerFactory.getLogger(Reminder.class);
  public static com.sbr.model.dto.Reminder loadDTOObject(Connection aConnection, int aNoteId){
    Session session = HibernateUtil.getSingleton().getSession(aConnection);
    return loadDTOObject(session, aNoteId);
  }
  public static com.sbr.model.dto.Reminder loadDTOObject(Session aSession, int aNoteId){
    if(aSession == null){
      throw new RuntimeException("Invalid session specified");
    }
    com.sbr.model.persistence.Reminder persistentReminder =
      (com.sbr.model.persistence.Reminder)aSession.load(com.sbr.model.persistence.Reminder.class, aNoteId);
    return new com.sbr.model.dto.Reminder(persistentReminder);

  }
  
  public static List<com.sbr.model.dto.Reminder> queryAllActiveReminders(Connection aConnection){
    log.info("Processing query all reminders...");
    Session session = null;
    try{
      session = HibernateUtil.getSingleton().getSession(aConnection);
      List<com.sbr.model.persistence.Reminder> persistentReminders  
        = (List<com.sbr.model.persistence.Reminder>)session.createQuery("from Reminder where actionTaken=?").
          setBoolean(0, false).list();
      if(persistentReminders == null || persistentReminders.isEmpty()){
        return null;
      }
      List<com.sbr.model.dto.Reminder> resultList = new ArrayList<com.sbr.model.dto.Reminder>();
      for(com.sbr.model.persistence.Reminder reminder : persistentReminders){
        com.sbr.model.dto.Reminder aReminder = new com.sbr.model.dto.Reminder(reminder);
        resultList.add(aReminder);
      }
      return resultList;
    }catch(Exception e){
      throw new RuntimeException(e);
    }finally {
      if(session != null){
        session.close();
      }
      }  
  }
  public static List<com.sbr.model.dto.Reminder> queryAllReminders(Connection aConnection, int aContactId){
    log.info("Processing query all reminders for a contact " + aContactId);
    Session session = null;
    try{
      session = HibernateUtil.getSingleton().getSession(aConnection);
      List<com.sbr.model.persistence.Reminder> persistentReminders  
        = (List<com.sbr.model.persistence.Reminder>)session.createQuery("from Reminder where contactId=? and actionTaken=?").
          setInteger(0,aContactId).
          setBoolean(1, false).list();
      if(persistentReminders == null || persistentReminders.isEmpty()){
        return null;
      }
      List<com.sbr.model.dto.Reminder> resultList = new ArrayList<com.sbr.model.dto.Reminder>();
      for(com.sbr.model.persistence.Reminder reminder : persistentReminders){
        com.sbr.model.dto.Reminder aReminder = new com.sbr.model.dto.Reminder(reminder);
        resultList.add(aReminder);
      }
      return resultList;
    }catch(Exception e){
      throw new RuntimeException(e);
    }finally {
      if(session != null){
        session.close();
      }
      }
  }

}