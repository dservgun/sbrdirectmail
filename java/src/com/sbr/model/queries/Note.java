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

public class Note {
  private static Logger log = LoggerFactory.getLogger(Note.class);
  public static com.sbr.model.dto.Note loadDTOObject(Connection aConnection, int aNoteId){
    Session session = HibernateUtil.getSingleton().getSession(aConnection);
    return loadDTOObject(session, aNoteId);
  }
  public static com.sbr.model.dto.Note loadDTOObject(Session aSession, int aNoteId){
    if(aSession == null){
      throw new RuntimeException("Invalid session specified");
    }
    com.sbr.model.persistence.Note persistentNote =
      (com.sbr.model.persistence.Note)aSession.load(com.sbr.model.persistence.Note.class, aNoteId);
    return new com.sbr.model.dto.Note(persistentNote);

  }
  
  public static List<com.sbr.model.dto.Note> queryAllNotes(Connection aConnection, int aContactId){
    Session session = null;
    try{
      session = HibernateUtil.getSingleton().getSession(aConnection);
      List<com.sbr.model.persistence.Note> persistentNotes  
        = (List<com.sbr.model.persistence.Note>)session.createQuery("from Note where contactId=?").
          setInteger(0,aContactId).list();
      if(persistentNotes == null || persistentNotes.isEmpty()){
        return null;
      }
      List<com.sbr.model.dto.Note> resultList = new ArrayList<com.sbr.model.dto.Note>();
      for(com.sbr.model.persistence.Note aNote : persistentNotes){
        com.sbr.model.dto.Note contact = new com.sbr.model.dto.Note(aNote);
        resultList.add(contact);
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