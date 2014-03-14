package com.sbr.util;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;

public class HibernateUtil {
  private static HibernateUtil util;
  public static String DIALECT="hibernate.dialect";
  public static String MYSQL_DIALECT="org.hibernate.dialect.MySQL5Dialect";
  
  private static Logger log = LoggerFactory.getLogger(HibernateUtil.class);
  public synchronized static HibernateUtil getInstance() {
    return getSingleton();
  }
  public synchronized static HibernateUtil getSingleton() {
    if(util == null){
      util = new HibernateUtil();
    }
    return util;
  }
  private AnnotationConfiguration configuration;
  private SessionFactory sessionFactory;
  
  private HibernateUtil() {
    initializeConfiguration();
    initializeSessionFactory();
  }
  public AnnotationConfiguration getConfiguration(){
    return configuration;
  }
  
  public Session getSession(Connection aConnection) {
    if(aConnection == null){
      log.debug("Invalid connection passed. Not returning a session");
      throw new RuntimeException("Invalid connection passed to return a session");
    }
    if(configuration == null){
      log.info("Initializing configuration");
      initializeConfiguration();
    }
    if(sessionFactory == null) {
      log.debug("HibernateUtil::getSession() called without initializing SessionFactory");
      initializeSessionFactory();
    }    
    Session result = sessionFactory.openSession(aConnection);
    return result;
  }
  
  private void initializeConfiguration(){
    try {
    configuration = new AnnotationConfiguration();
    configuration.setProperty(DIALECT,MYSQL_DIALECT);    
    configuration.addAnnotatedClass(com.sbr.model.persistence.Contact.class);
    configuration.addAnnotatedClass(com.sbr.model.persistence.ZipCode.class);
    configuration.addAnnotatedClass(com.sbr.model.persistence.Note.class);
    configuration.addAnnotatedClass(com.sbr.model.persistence.Reminder.class);
    configuration.addAnnotatedClass(com.sbr.model.persistence.UserTable.class);
    }catch(Exception e){
      log.error(e.getMessage());
    }
  }
  private void initializeSessionFactory() {
    if(configuration == null){
      log.debug("Invalid state for session factory. Configuration not initialized");
      throw new RuntimeException("Invalid configuration.Unable to create session factory");
    }
    sessionFactory = configuration.buildSessionFactory();
  }
  
  
  
  
}