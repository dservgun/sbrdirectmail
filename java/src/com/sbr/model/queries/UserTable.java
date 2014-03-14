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

public class UserTable{
  private static Logger log = LoggerFactory.getLogger(UserTable.class);
  public static com.sbr.model.dto.UserTable loadDTOObject(Connection aConnection, int aUserId){
    Session session = HibernateUtil.getSingleton().getSession(aConnection);
    return loadDTOObject(session, aUserId);
  }  
  
  public static com.sbr.model.dto.UserTable loadDTOObject(Session session, int id){
    if(session == null){
      throw new RuntimeException("Invalid session");
    }
    com.sbr.model.persistence.UserTable userTable = 
      (com.sbr.model.persistence.UserTable)session.load(com.sbr.model.persistence.UserTable.class, id);
    return new com.sbr.model.dto.UserTable(userTable);    
  }
  
  /**
      * Clients are responsible to close the session.
    */
  public static com.sbr.model.dto.UserTable findUser(Session session, String userNickName){
      List<com.sbr.model.persistence.UserTable> persistentUserList
        = (List<com.sbr.model.persistence.UserTable>)session.createQuery("from UserTable where loginName=?").
          setString(0,userNickName).list();
      if(persistentUserList== null || persistentUserList.isEmpty()){
        return null;
      }
      if(persistentUserList.size() > 1){
        throw new RuntimeException("Duplicate users found " + userNickName);
      }
      com.sbr.model.dto.UserTable result = new com.sbr.model.dto.UserTable(persistentUserList.get(0));      
      return result;
  
  }
  /**
    *  Queries for a user against the login name.
    */
  public static com.sbr.model.dto.UserTable findUser(Connection aConnection, String userNickName){
    Session session = null;
    try{
      session = HibernateUtil.getSingleton().getSession(aConnection);
      return findUser(session, userNickName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }finally {
      if(session != null){
        session.close();
      }
      }
  
  }
  
}